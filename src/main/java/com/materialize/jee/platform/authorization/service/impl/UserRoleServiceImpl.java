/**
 * @Company：
 * @Title: UserRoleServiceImpl.java
 * @Description：描述
 * @Author： 97802
 * @Date：2016-06-07 12:00:05
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.authorization.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.materialize.jee.platform.authorization.domain.UserRole;
import com.materialize.jee.platform.authorization.mapper.UserRoleMapper;
import com.materialize.jee.platform.authorization.service.UserRoleService;
import com.materialize.jee.platform.base.DefaultBaseService;
import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;

/**
 * @ClassName: UserRoleServiceImpl
 * @Description: TODO(UserRoleServiceImpl service层)
 */
@Service
public class UserRoleServiceImpl extends DefaultBaseService implements UserRoleService {
	
	@Resource
	private UserRoleMapper userRoleMapper;
	
	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	public Long save(UserRole userRole){
		return userRoleMapper.save(userRole);
	}

	/**
	 * @Title: update
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	public long update(UserRole userRole){
		return userRoleMapper.update(userRole);
	}
	
	/**
	 * @Title: updateSelective
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	public long updateSelective(UserRole userRole){
		return userRoleMapper.updateSelective(userRole);
	}

	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	public Long delete(java.lang.Long id){
		UserRole userRole = this.get(id);
		return userRoleMapper.delete(userRole);
	}

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	public UserRole get(java.lang.Long id){
		return userRoleMapper.get(id);
	}

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	public List<UserRole> find(Map<String, Object> entityMap){
		return userRoleMapper.find(entityMap);
	}
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pageNo 页号
	 * @param pageSize 每页记录数
	 * @param entityMap 查询条件
	 * @return 返回根据条件查询的数据
	 */
	public Page<UserRole> findPage(Integer pageNo, Integer pageSize,Map<String,Object> entityMap){
		Pagination pagination = new Pagination(pageNo, pageSize, entityMap);
		Page<UserRole> page = (Page<UserRole>)userRoleMapper.find(pagination);
		return page;
	}

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	public Long findCount(Map<String, Object> entityMap){
		return userRoleMapper.findCount(entityMap);
	}
}
