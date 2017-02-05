/**
 * @Company：
 * @Title: RoleServiceImpl.java
 * @Description：描述
 * @Author： 97802
 * @Date：2016-06-07 12:00:05
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.authorization.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.materialize.jee.platform.authorization.domain.Role;
import com.materialize.jee.platform.authorization.mapper.RoleMapper;
import com.materialize.jee.platform.authorization.service.RoleService;
import com.materialize.jee.platform.base.DefaultBaseService;
import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;

/**
 * @ClassName: RoleServiceImpl
 * @Description: TODO(RoleServiceImpl service层)
 */
@Service
public class RoleServiceImpl extends DefaultBaseService implements RoleService {
	
	@Resource
	private RoleMapper roleMapper;
	
	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	public Long save(Role role){
		return roleMapper.save(role);
	}

	/**
	 * @Title: update
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	public long update(Role role){
		return roleMapper.update(role);
	}
	
	/**
	 * @Title: updateSelective
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	public long updateSelective(Role role){
		return roleMapper.updateSelective(role);
	}

	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	public Long delete(java.lang.Long id){
		Role role = this.get(id);
		return roleMapper.delete(role);
	}
	
	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	public Long delete(java.lang.Long[] ids){
		Long res = 0L;
		if(ids!=null && ids.length>0){
			for(Long id:ids){
				Role role = this.get(id);
				res+=roleMapper.delete(role);
			}
		}
		return res;
	}

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	public Role get(java.lang.Long id){
		return roleMapper.get(id);
	}

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	public List<Role> find(Map<String, Object> entityMap){
		return roleMapper.find(entityMap);
	}
	
	/**
	 * @Title: findByUser
	 * @Description: 查询用户所属角色
	 * @param userId
	 * @return 返回根据条件查询的数据
	 */
	public List<Role> findByUser(Long userId){
		return roleMapper.findByUser(userId);
	}
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pageNo 页号
	 * @param pageSize 每页记录数
	 * @param entityMap 查询条件
	 * @return 返回根据条件查询的数据
	 */
	public Page<Role> findPage(Integer pageNo, Integer pageSize,Map<String,Object> entityMap){
		Pagination pagination = new Pagination(pageNo, pageSize, entityMap);
		Page<Role> page = (Page<Role>)roleMapper.find(pagination);
		return page;
	}
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pageNo 页号
	 * @param pageSize 每页记录数
	 * @param entityMap 查询条件
	 * @return 返回根据条件查询的数据
	 */
	public Page<Role> findPage(Pagination pagination){
		Page<Role> page = (Page<Role>)roleMapper.find(pagination);
		return page;
	}

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	public Long findCount(Map<String, Object> entityMap){
		return roleMapper.findCount(entityMap);
	}
	
	/**
	 * 校验指定字段值是否唯一 ，true-不存在；false-存在
	 * @param fieldName 字段名
	 * @param fieldValue 值
	 * @param excludeId 本身ID，用于排除本身
	 */
	public boolean checkOnly(String fieldName, String fieldValue, Long excludeId){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(fieldName, fieldValue);
		params.put("excludeId", excludeId);
		Long num = this.findCount(params);
		return (num==null || num==0);
	}
}
