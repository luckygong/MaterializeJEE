/**
 * @Company：
 * @Title: RoleResourceMapper.java
 * @Description：描述
 * @Author： zhaosk
 * @Date：2016-09-23 06:49:32
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.authorization.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.materialize.jee.platform.authorization.domain.RoleResource;
import com.materialize.jee.platform.base.page.Pagination;

/**
 * @ClassName: RoleResourceMapper
 * @Description: TODO(RoleResourceMapper DAO层)
 */
@Repository
public interface RoleResourceMapper {

	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	Integer save(RoleResource roleResource);

	/**
	 * @Title: update
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	Integer update(RoleResource roleResource);
	
	/**
	 * @Title: updateSelective
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	Integer updateSelective(RoleResource roleResource);

	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	Integer delete(RoleResource roleResource);

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	RoleResource get(@Param(value="id") java.lang.Long id);

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	List<RoleResource> find(Map<String, Object> entityMap);
	
	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pagination
	 * @return 返回根据条件查询的数据
	 */
	List<RoleResource> find(Pagination pagination);

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	Integer findCount(Map<String, Object> entityMap);
}
