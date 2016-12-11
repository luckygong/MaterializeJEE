package com.materialize.jee.platform.hbase.phoenix;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import com.materialize.jee.platform.base.DefaultBaseService;

public abstract class BasePhoenixService<T> extends DefaultBaseService{
	@Resource(name="phoenixJdbcTemplate")
	private JdbcTemplate phoenixJdbcTemplate;
	@Resource(name="phoenixSqlSessionTemplate")
	private SqlSessionTemplate phoenixSqlSessionTemplate;
	
	protected PhoenixPage<T> createPhoenixPage(PhoenixPagination pagination, List<T> queryResult){
		PhoenixPage<T> page = new PhoenixPage<T>();
		page.setCurrentPage(pagination.getPageNo());
		page.setCurrentPageSize(pagination.getPageSize());
		page.setPageData(queryResult);
		return page;
	}
	
	public JdbcTemplate getPhoenixJdbcTemplate() {
		return phoenixJdbcTemplate;
	}

	public void setPhoenixJdbcTemplate(JdbcTemplate phoenixJdbcTemplate) {
		this.phoenixJdbcTemplate = phoenixJdbcTemplate;
	}

}
