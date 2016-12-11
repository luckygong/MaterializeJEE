/**
 * @Company：
 * @Title: ${javaModelName}Mapper.java
 * @Description：描述
 * @Author： ${auther}
 * @Date：${time}
 * @Version： V1.0
 * @Modify：
 */
package ${javaMapperPackage};

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import ${javaModelPackage}.${javaModelName};
import com.materialize.jee.platform.base.page.Pagination;

/**
 * @ClassName: ${javaModelName}Mapper
 * @Description: TODO(${javaModelName}Mapper DAO层)
 */
@Repository
public interface ${javaModelName}Mapper {

	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	Integer save(${javaModelName} ${javaModelInstanceName});

	/**
	 * @Title: update
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	Integer update(${javaModelName} ${javaModelInstanceName});
	
	/**
	 * @Title: updateSelective
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	Integer updateSelective(${javaModelName} ${javaModelInstanceName});

	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	Integer delete(${javaModelName} ${javaModelInstanceName});

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	${javaModelName} get(<#list tablePrimaryKeys as column>@Param(value="${column.propertyName}") ${column.javaType} ${column.propertyName}<#if column_has_next>, </#if></#list>);

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	List<${javaModelName}> find(Map<String, Object> entityMap);
	
	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pagination
	 * @return 返回根据条件查询的数据
	 */
	List<${javaModelName}> find(Pagination pagination);

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	Integer findCount(Map<String, Object> entityMap);
}
