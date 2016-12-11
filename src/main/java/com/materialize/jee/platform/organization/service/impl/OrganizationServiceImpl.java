/**
 * @Company：
 * @Title: OrganizationServiceImpl.java
 * @Description：描述
 * @Author： 97802
 * @Date：2016-06-07 10:57:11
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.organization.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.materialize.jee.platform.base.DefaultBaseService;
import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;
import com.materialize.jee.platform.organization.domain.Organization;
import com.materialize.jee.platform.organization.mapper.OrganizationMapper;
import com.materialize.jee.platform.organization.service.OrganizationService;

/**
 * @ClassName: OrganizationServiceImpl
 * @Description: TODO(OrganizationServiceImpl service层)
 */
@Service
public class OrganizationServiceImpl extends DefaultBaseService implements OrganizationService {
	
	@Resource
	private OrganizationMapper organizationMapper;
	
	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	public Long save(Organization organization){
		return organizationMapper.save(organization);
	}

	/**
	 * @Title: update
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	public long update(Organization organization){
		return organizationMapper.update(organization);
	}
	
	/**
	 * @Title: updateSelective
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	public long updateSelective(Organization organization){
		return organizationMapper.updateSelective(organization);
	}

	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	public Long delete(java.lang.Long id){
		Organization organization = this.get(id);
		return organizationMapper.delete(organization);
	}

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	public Organization get(java.lang.Long id){
		return organizationMapper.get(id);
	}

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	public List<Organization> find(Map<String, Object> entityMap){
		return organizationMapper.find(entityMap);
	}
	
	/**
	 * @Title: findByUser
	 * @Description: 查询用户所属机构
	 * @param userId
	 * @return 返回根据条件查询的数据
	 */
	public List<Organization> findByUser(Long userId){
		return organizationMapper.findByUser(userId);
	}
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pageNo 页号
	 * @param pageSize 每页记录数
	 * @param entityMap 查询条件
	 * @return 返回根据条件查询的数据
	 */
	public Page<Organization> findPage(Integer pageNo, Integer pageSize,Map<String,Object> entityMap){
		Pagination pagination = new Pagination(pageNo, pageSize, entityMap);
		Page<Organization> page = (Page<Organization>)organizationMapper.find(pagination);
		return page;
	}

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	public Long findCount(Map<String, Object> entityMap){
		return organizationMapper.findCount(entityMap);
	}
}
