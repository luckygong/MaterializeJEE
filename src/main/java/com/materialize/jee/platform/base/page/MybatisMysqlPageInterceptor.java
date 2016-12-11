package com.materialize.jee.platform.base.page;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mybatis分页拦截器 ：挡截 RowBounds 分页
 *  为MyBatis提供基于方言(Dialect)的分页查询的插件
 *  将拦截Executor.query()方法实现分页方言的插入.
 */
@Intercepts({@Signature(type= Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MybatisMysqlPageInterceptor implements Interceptor{
    private static Logger logger = LoggerFactory.getLogger(MybatisMysqlPageInterceptor.class);
	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;

    static ExecutorService Pool;
    String dialectClass;
    boolean asyncTotalCount = false;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object intercept(final Invocation invocation) throws Throwable {
        final Executor executor = (Executor) invocation.getTarget();
        final Object[] queryArgs = invocation.getArgs();
        final MappedStatement ms = (MappedStatement)queryArgs[MAPPED_STATEMENT_INDEX];
        final Object parameter = queryArgs[PARAMETER_INDEX];
        
        if(!(parameter instanceof Pagination)){
        	//不分页
            return invocation.proceed();
        }
        
        final Pagination pagination = (Pagination)parameter;
        final RowBounds rowBounds = new RowBounds(pagination.getFirstRecord(),pagination.getPageSize());
        final Boolean isPage = true;
        final Dialect dialect;
        try {
            Class clazz = Class.forName(dialectClass);
            Constructor constructor = clazz.getConstructor(MappedStatement.class, Object.class, Boolean.class, RowBounds.class);
            dialect = (Dialect)constructor.newInstance(new Object[]{ms, pagination.getEntityMap(), isPage, rowBounds});
        } catch (Exception e) {
            throw new ClassNotFoundException("Cannot create dialect instance: "+dialectClass,e);
        }

        final BoundSql boundSql = ms.getBoundSql(pagination.getEntityMap());

        queryArgs[MAPPED_STATEMENT_INDEX] = copyFromNewSql(ms,boundSql,dialect.getPageSQL(), dialect.getParameterMappings(), dialect.getParameterObject());
        queryArgs[PARAMETER_INDEX] = dialect.getParameterObject();

        Future<List> listFuture = call(new Callable<List>() {
            public List call() throws Exception {
                return (List)invocation.proceed();
            }
        }, false);


        Callable<Integer> countTask = new Callable() {
            public Object call() throws Exception {
            	return getCount(ms,executor.getTransaction(),pagination.getEntityMap(),boundSql,dialect);
            }
        };
        Future<Integer> countFutrue = call(countTask, false);
        Page page = new Page();
        page.setPageData(listFuture.get());
        page.setTotalCount(countFutrue.get());
        page.setCurrentPage(pagination.getPageNo());
        page.setTotalPage(page.getTotalCount()%pagination.getPageSize()==0?page.getTotalCount()/pagination.getPageSize():(page.getTotalCount()/pagination.getPageSize()+1));
        return page;
	}
	
	public static int getCount(
            final MappedStatement mappedStatement, final Transaction transaction, final Object parameterObject,
            final BoundSql boundSql, Dialect dialect) throws SQLException {
		final String count_sql = dialect.getCountSQL();
		logger.debug("Total count SQL [{}] ", count_sql);
		logger.debug("Total count Parameters: {} ", parameterObject);
		
		java.sql.Connection connection = transaction.getConnection();
		java.sql.PreparedStatement countStmt = connection.prepareStatement(count_sql);
		DefaultParameterHandler handler = new DefaultParameterHandler(mappedStatement,parameterObject,boundSql);
		handler.setParameters(countStmt);
		
		ResultSet rs = countStmt.executeQuery();
		int count = 0;
		if (rs.next()) {
		count = rs.getInt(1);
		}
		logger.debug("Total count: {}", count);
		return count;
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> Future<T> call(Callable callable, boolean async){
        if(async){
             return Pool.submit(callable);
        }else{
            FutureTask<T> future = new FutureTask(callable);
            future.run();
            return future;
        }
    }

	private MappedStatement copyFromNewSql(MappedStatement ms, BoundSql boundSql,
                                           String sql, List<ParameterMapping> parameterMappings, Object parameter){
        BoundSql newBoundSql = copyFromBoundSql(ms, boundSql, sql, parameterMappings, parameter);
        return copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
    }

	private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql,
			String sql, List<ParameterMapping> parameterMappings,Object parameter) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, parameterMappings, parameter);
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
		    String prop = mapping.getProperty();
		    if (boundSql.hasAdditionalParameter(prop)) {
		        newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
		    }
		}
		return newBoundSql;
	}
	
	private MappedStatement copyFromMappedStatement(MappedStatement ms,SqlSource newSqlSource) {
		Builder builder = new Builder(ms.getConfiguration(),ms.getId(),newSqlSource,ms.getSqlCommandType());
		
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if(ms.getKeyProperties() != null && ms.getKeyProperties().length !=0){
            StringBuffer keyProperties = new StringBuffer();
            for(String keyProperty : ms.getKeyProperties()){
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length()-1, keyProperties.length());
			builder.keyProperty(keyProperties.toString());
		}
		
		builder.timeout(ms.getTimeout());
		
		builder.parameterMap(ms.getParameterMap());
		
        builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
	    
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		
		return builder.build();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		String dialectClass = properties.getProperty("dialectClass");
		setDialectClass(dialectClass);
        setPoolMaxSize(Integer.valueOf(properties.getProperty("poolMaxSize","0")));
	}
	
	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;
		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}
		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

    public void setDialectClass(String dialectClass) {
        logger.debug("dialectClass: {} ", dialectClass);
        this.dialectClass = dialectClass;
    }

    public void setPoolMaxSize(int poolMaxSize) {
        if(poolMaxSize > 0){
            logger.debug("poolMaxSize: {} ", poolMaxSize);
            Pool = Executors.newFixedThreadPool(poolMaxSize);
        }else{
            Pool = Executors.newCachedThreadPool();
        }
    }
}
