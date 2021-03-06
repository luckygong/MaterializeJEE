/**
 * @Company：
 * @Title: OrganizationService.java
 * @Description：描述
 * @Author： 97802
 * @Date：2016-06-07 10:57:11
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.organization.service;

import java.util.List;
import java.util.Map;

import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.organization.domain.Organization;

/**
 * @ClassName: OrganizationService
 * @Description: TODO(OrganizationService service层)
 */
public interface OrganizationService {

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
	long update(Organization organization);
	
	/**
	 * @Title: updateSelective
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	long updateSelective(Organization organization);

	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	Long delete(java.lang.Long id);

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	Organization get(java.lang.Long id);

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
	List<Organization> findByUser(Long userId);
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pageNo 页号
	 * @param pageSize 每页记录数
	 * @param entityMap 查询条件
	 * @return 返回根据条件查询的数据
	 */
	Page<Organization> findPage(Integer pageNo, Integer pageSize,Map<String,Object> entityMap);

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	Long findCount(Map<String, Object> entityMap);
}
