/**
 * @Company：
 * @Title: OrganizationMapper.java
 * @Description：描述
 * @Author： 97802
 * @Date：2016-06-07 10:57:11
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.organization.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.materialize.jee.platform.base.page.Pagination;
import com.materialize.jee.platform.organization.domain.Organization;

/**
 * @ClassName: OrganizationMapper
 * @Description: TODO(OrganizationMapper DAO层)
 */
@Repository
public interface OrganizationMapper {

	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	Long save(Organization organization);

	/**
	 * @Title: update
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	Long update(Organization organization);
	
	/**
	 * @Title: updateSelective
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	Long updateSelective(Organization organization);

	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	Long delete(Organization organization);

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	Organization get(@Param(value="id") java.lang.Long id);

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	List<Organization> find(Map<String, Object> entityMap);
	
	/**
	 * @Title: findByUser
	 * @Description: 查询用户所属机构
	 * @param userId
	 * @return 返回根据条件查询的数据
	 */
	List<Organization> findByUser(@Param(value = "userId") Long userId);
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pagination
	 * @return 返回根据条件查询的数据
	 */
	List<Organization> find(Pagination pagination);

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	Long findCount(Map<String, Object> entityMap);
}
