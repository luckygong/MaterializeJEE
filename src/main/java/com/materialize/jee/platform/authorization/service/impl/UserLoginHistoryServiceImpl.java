/**
 * @Company：
 * @Title: UserLoginHistoryServiceImpl.java
 * @Description：描述
 * @Author： 97802
 * @Date：2016-06-04 08:54:57
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.authorization.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.materialize.jee.platform.authorization.domain.UserLoginHistory;
import com.materialize.jee.platform.authorization.mapper.UserLoginHistoryMapper;
import com.materialize.jee.platform.authorization.service.UserLoginHistoryService;
import com.materialize.jee.platform.base.DefaultBaseService;
import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;

/**
 * @ClassName: UserLoginHistoryServiceImpl
 * @Description: TODO(UserLoginHistoryServiceImpl service层)
 */
@Service
public class UserLoginHistoryServiceImpl extends DefaultBaseService implements UserLoginHistoryService {
	
	@Resource
	private UserLoginHistoryMapper userLoginHistoryMapper;
	
	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	public Long save(UserLoginHistory userLoginHistory){
		return userLoginHistoryMapper.save(userLoginHistory);
	}

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	public List<UserLoginHistory> find(Map<String, Object> entityMap){
		return userLoginHistoryMapper.find(entityMap);
	}
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pageNo 页号
	 * @param pageSize 每页记录数
	 * @param entityMap 查询条件
	 * @return 返回根据条件查询的数据
	 */
	public Page<UserLoginHistory> findPage(Integer pageNo, Integer pageSize,Map<String,Object> entityMap){
		Pagination pagination = new Pagination(pageNo, pageSize, entityMap);
		Page<UserLoginHistory> page = (Page<UserLoginHistory>)userLoginHistoryMapper.find(pagination);
		return page;
	}

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	public Long findCount(Map<String, Object> entityMap){
		return userLoginHistoryMapper.findCount(entityMap);
	}
}
