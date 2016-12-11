/**
 * @Company：
 * @Title: RoleResourceServiceImpl.java
 * @Description：描述
 * @Author： zhaosk
 * @Date：2016-09-23 06:49:32
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.authorization.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.materialize.jee.platform.authorization.domain.RoleResource;
import com.materialize.jee.platform.authorization.mapper.RoleResourceMapper;
import com.materialize.jee.platform.authorization.service.RoleResourceService;
import com.materialize.jee.platform.base.DefaultBaseService;
import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;

/**
 * @ClassName: RoleResourceServiceImpl
 * @Description: TODO(RoleResourceServiceImpl service层)
 */
@Service
public class RoleResourceServiceImpl extends DefaultBaseService implements RoleResourceService {
	
	@Resource
	private RoleResourceMapper roleResourceMapper;
	
	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	public Integer save(RoleResource roleResource){
		return roleResourceMapper.save(roleResource);
	}

	/**
	 * @Title: update
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	public Integer update(RoleResource roleResource){
		return roleResourceMapper.update(roleResource);
	}
	
	/**
	 * @Title: updateSelective
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	public Integer updateSelective(RoleResource roleResource){
		return roleResourceMapper.updateSelective(roleResource);
	}

	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	public Integer delete(java.lang.Long id){
		RoleResource roleResource = this.get(id);
		return roleResourceMapper.delete(roleResource);
	}

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	public RoleResource get(java.lang.Long id){
		return roleResourceMapper.get(id);
	}

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	public List<RoleResource> find(Map<String, Object> entityMap){
		return roleResourceMapper.find(entityMap);
	}
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pageNo 页号
	 * @param pageSize 每页记录数
	 * @param entityMap 查询条件
	 * @return 返回根据条件查询的数据
	 */
	public Page<RoleResource> findPage(Integer pageNo, Integer pageSize,Map<String,Object> entityMap){
		Pagination pagination = new Pagination(pageNo, pageSize, entityMap);
		return findPage(pagination);
	}
	
	/**
	 * @Title: findPage
	 * @param pagination 分页信息
	 * @return 返回根据条件查询的数据
	 */
	public Page<RoleResource> findPage(Pagination pagination){
		Page<RoleResource> page = (Page<RoleResource>)roleResourceMapper.find(pagination);
		return page;
	}

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	public Integer findCount(Map<String, Object> entityMap){
		return roleResourceMapper.findCount(entityMap);
	}
}
