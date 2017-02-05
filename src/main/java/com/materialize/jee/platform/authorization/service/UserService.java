/**
 * @Company：
 * @Title: UserService.java
 * @Description：描述
 * @Author： 97802
 * @Date：2016-06-04 08:54:57
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.authorization.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.materialize.jee.platform.authorization.domain.User;
import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;

/**
 * @ClassName: UserService
 * @Description: TODO(UserService service层)
 */
public interface UserService {

	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	Long save(User user);
	
	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	Long save(User user, HttpServletRequest request);

	/**
	 * @Title: update
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	long update(User user);
	

	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	Long update(User user, HttpServletRequest request);
	
	/**
	 * @Title: updateSelective
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	long updateSelective(User user);

	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	Long delete(java.lang.Long id);
	
	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	Long delete(java.lang.Long[] ids);

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	User get(java.lang.Long id);

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	List<User> find(Map<String, Object> entityMap);
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pageNo 页号
	 * @param pageSize 每页记录数
	 * @param entityMap 查询条件
	 * @return 返回根据条件查询的数据
	 */
	Page<User> findPage(Integer pageNo, Integer pageSize,Map<String,Object> entityMap);
	
	/**
	 * @Title: findPage
	 * @param pagination 分页信息
	 * @return 返回根据条件查询的数据
	 */
	Page<User> findPage(Pagination pagination);

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	Long findCount(Map<String, Object> entityMap);
	
	/**
	 * 
	 * @Description: 密码加密    
	 * @param loginId 登录用户名
	 * @param password 密码
	 * @return  
	 */
	String encodePassword(String loginId, String password);
	
	/**
	 * 校验指定字段值是否唯一 ，true-不存在；false-存在
	 * @param fieldName 字段名
	 * @param fieldValue 值
	 * @param excludeId 本身ID，用于排除本身
	 */
	public boolean checkOnly(String fieldName, String fieldValue, Long excludeId);
}
