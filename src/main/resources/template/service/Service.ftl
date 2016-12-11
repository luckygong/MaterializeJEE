/**
 * @Company：
 * @Title: ${javaModelName}Service.java
 * @Description：描述
 * @Author： ${auther}
 * @Date：${time}
 * @Version： V1.0
 * @Modify：
 */
package ${javaServicePackage};

import java.util.List;
import java.util.Map;

import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;
import ${javaModelPackage}.${javaModelName};

/**
 * @ClassName: ${javaModelName}Service
 * @Description: TODO(${javaModelName}Service service层)
 */
public interface ${javaModelName}Service {

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
	Integer delete(<#list tablePrimaryKeys as column>${column.javaType} ${column.propertyName}<#if column_has_next>, </#if></#list>);

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	${javaModelName} get(<#list tablePrimaryKeys as column>${column.javaType} ${column.propertyName}<#if column_has_next>, </#if></#list>);

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	List<${javaModelName}> find(Map<String, Object> entityMap);
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pageNo 页号
	 * @param pageSize 每页记录数
	 * @param entityMap 查询条件
	 * @return 返回根据条件查询的数据
	 */
	Page<${javaModelName}> findPage(Integer pageNo, Integer pageSize,Map<String,Object> entityMap);

	/**
	 * @Title: findPage
	 * @param pagination 分页信息
	 * @return 返回根据条件查询的数据
	 */
	Page<${javaModelName}> findPage(Pagination pagination);

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	Integer findCount(Map<String, Object> entityMap);
}
