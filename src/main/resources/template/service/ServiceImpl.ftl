/**
 * @Company：
 * @Title: ${javaModelName}ServiceImpl.java
 * @Description：描述
 * @Author： ${auther}
 * @Date：${time}
 * @Version： V1.0
 * @Modify：
 */
package ${javaServicePackage}.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ${javaModelPackage}.${javaModelName};
import ${javaServicePackage}.${javaModelName}Service;
import com.materialize.jee.platform.base.DefaultBaseService;
import ${javaMapperPackage}.${javaModelName}Mapper;
import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;

/**
 * @ClassName: ${javaModelName}ServiceImpl
 * @Description: TODO(${javaModelName}ServiceImpl service层)
 */
@Service
public class ${javaModelName}ServiceImpl extends DefaultBaseService implements ${javaModelName}Service {
	protected static final Logger logger = LoggerFactory.getLogger(${javaModelName}ServiceImpl.class);
	@javax.annotation.Resource
	private ${javaModelName}Mapper ${javaModelInstanceName}Mapper;
	
	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	@Override
	public Integer save(${javaModelName} ${javaModelInstanceName}){
		return ${javaModelInstanceName}Mapper.save(${javaModelInstanceName});
	}

	/**
	 * @Title: update
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	@Override
	public Integer update(${javaModelName} ${javaModelInstanceName}){
		return ${javaModelInstanceName}Mapper.update(${javaModelInstanceName});
	}
	
	/**
	 * @Title: updateSelective
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	@Override
	public Integer updateSelective(${javaModelName} ${javaModelInstanceName}){
		return ${javaModelInstanceName}Mapper.updateSelective(${javaModelInstanceName});
	}

	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	@Override
	public Integer delete(<#list tablePrimaryKeys as column>${column.javaType} ${column.propertyName}<#if column_has_next>, </#if></#list>){
		${javaModelName} ${javaModelInstanceName} = this.get(<#list tablePrimaryKeys as column>${column.propertyName}<#if column_has_next>, </#if></#list>);
		return ${javaModelInstanceName}Mapper.delete(${javaModelInstanceName});
	}

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	@Override
	public ${javaModelName} get(<#list tablePrimaryKeys as column>${column.javaType} ${column.propertyName}<#if column_has_next>, </#if></#list>){
		return ${javaModelInstanceName}Mapper.get(<#list tablePrimaryKeys as column>${column.propertyName}<#if column_has_next>, </#if></#list>);
	}

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	@Override
	public List<${javaModelName}> find(Map<String, Object> entityMap){
		return ${javaModelInstanceName}Mapper.find(entityMap);
	}
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pageNo 页号
	 * @param pageSize 每页记录数
	 * @param entityMap 查询条件
	 * @return 返回根据条件查询的数据
	 */
	@Override
	public Page<${javaModelName}> findPage(Integer pageNo, Integer pageSize,Map<String,Object> entityMap){
		Pagination pagination = new Pagination(pageNo, pageSize, entityMap);
		return findPage(pagination);
	}
	
	/**
	 * @Title: findPage
	 * @param pagination 分页信息
	 * @return 返回根据条件查询的数据
	 */
	@Override
	public Page<${javaModelName}> findPage(Pagination pagination){
		Page<${javaModelName}> page = (Page<${javaModelName}>)${javaModelInstanceName}Mapper.find(pagination);
		return page;
	}

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	@Override
	public Integer findCount(Map<String, Object> entityMap){
		return ${javaModelInstanceName}Mapper.findCount(entityMap);
	}
}
