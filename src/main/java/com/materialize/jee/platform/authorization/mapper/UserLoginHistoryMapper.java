/**
 * @Company：
 * @Title: UserLoginHistoryMapper.java
 * @Description：描述
 * @Author： 97802
 * @Date：2016-06-04 08:54:57
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.authorization.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.materialize.jee.platform.authorization.domain.UserLoginHistory;
import com.materialize.jee.platform.base.page.Pagination;

/**
 * @ClassName: UserLoginHistoryMapper
 * @Description: TODO(UserLoginHistoryMapper DAO层)
 */
@Repository
public interface UserLoginHistoryMapper {

	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	Long save(UserLoginHistory userLoginHistory);

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	List<UserLoginHistory> find(Map<String, Object> entityMap);
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pagination
	 * @return 返回根据条件查询的数据
	 */
	List<UserLoginHistory> find(Pagination pagination);

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	Long findCount(Map<String, Object> entityMap);
}
