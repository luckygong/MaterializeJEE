package com.materialize.jee.platform.authorization.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.alibaba.fastjson.JSONObject;
import com.materialize.jee.platform.SysConstants;
import com.materialize.jee.platform.authorization.domain.Resource;
import com.materialize.jee.platform.authorization.domain.Role;
import com.materialize.jee.platform.authorization.domain.User;  
  
public class MyUserDetailService implements IMyUserDetailsService { 
	
	protected JdbcTemplate jdbcTemplate;
	public static final String DEF_SQL_AUTH_KEY = "roleCode";
	
	public static final String LOAD_USER_BY_NAME_SQL="SELECT DISTINCT usr.ID id,"
													      + "usr.COMPANY_ID companyId,"
													      + "usr.USERNAME username,"
													      + "usr.PASSWORD password,"
													      + "usr.NIKE_NAME nikeName,"
													      + "usr.REAL_NAME realName,"
													      + "usr.SEX sex,"
													      + "usr.AVATAR avatar,"
													      + "usr.TEL_PHONE telPhone,"
													      + "usr.PHONE phone,"
													      + "usr.EMAIL email,"
													      + "usr.USER_TYPE userType,"
													      + "usr.REF_ID refId,"
													      + "usr.IS_ENABLED isEnabled,"
													      + "usr.IS_LOCK isLock "
													 +"FROM SYS_USER usr WHERE usr.username = ?";
	
	public static final String LOAD_USER_BY_NAME_AND_TYPE_SQL="SELECT DISTINCT usr.ID id,"
													+ "usr.COMPANY_ID companyId,"
													+ "usr.USERNAME username,"
													+ "usr.PASSWORD password,"
													+ "usr.NIKE_NAME nikeName,"
													+ "usr.REAL_NAME realName,"
													+ "usr.SEX sex,"
													+ "usr.AVATAR avatar,"
													+ "usr.TEL_PHONE telPhone,"
													+ "usr.PHONE phone,"
													+ "usr.EMAIL email,"
													+ "usr.USER_TYPE userType,"
													+ "usr.REF_ID refId,"
													+ "usr.IS_ENABLED isEnabled,"
													+ "usr.IS_LOCK isLock "
													+"FROM SYS_USER usr WHERE usr.username = ? and usr.USER_TYPE = ?";
	
	public static final String LOAD_USER_BY_PHONE_SQL="SELECT DISTINCT usr.ID id,"
													+ "usr.COMPANY_ID companyId,"
													+ "usr.USERNAME username,"
													+ "usr.PASSWORD password,"
													+ "usr.NIKE_NAME nikeName,"
													+ "usr.REAL_NAME realName,"
													+ "usr.SEX sex,"
													+ "usr.AVATAR avatar,"
													+ "usr.TEL_PHONE telPhone,"
													+ "usr.PHONE phone,"
													+ "usr.EMAIL email,"
													+ "usr.USER_TYPE userType,"
													+ "usr.REF_ID refId,"
													+ "usr.IS_ENABLED isEnabled,"
													+ "usr.IS_LOCK isLock "
													+"FROM SYS_USER usr WHERE usr.phone = ?";
	
	public static final String LOAD_USER_BY_PHONE_AND_TYPE_SQL="SELECT DISTINCT usr.ID id,"
													+ "usr.COMPANY_ID companyId,"
													+ "usr.USERNAME username,"
													+ "usr.PASSWORD password,"
													+ "usr.NIKE_NAME nikeName,"
													+ "usr.REAL_NAME realName,"
													+ "usr.SEX sex,"
													+ "usr.AVATAR avatar,"
													+ "usr.TEL_PHONE telPhone,"
													+ "usr.PHONE phone,"
													+ "usr.EMAIL email,"
													+ "usr.USER_TYPE userType,"
													+ "usr.REF_ID refId,"
													+ "usr.IS_ENABLED isEnabled,"
													+ "usr.IS_LOCK isLock "
													+"FROM SYS_USER usr WHERE usr.phone = ? and usr.USER_TYPE = ?";
	
	public static final String LOAD_USER_ROLE_SQL="SELECT DISTINCT r.ID id,"
														+ "r.ROLE_CODE roleCode,"
														+ "r.ROLE_NAME roleName "
												+ "FROM SYS_ROLE r "
												+ "INNER JOIN sys_user_role ur on ur.ROLE_ID = r.ID "
												+ "WHERE r.ACTIVE_FLAG=1 and ur.USER_ID = ?";
	
	public static final String LOAD_USER_RESOURCE_SQL="select r.ID id, r.NAME name, r.TYPE type, r.VALUE value,"
													   + "r.IS_DIRECTORY isDirectory, r.LEVEL level,"
													   + "r.ICON icon, r.PARENT_ID parentId, r.ORDERS orders "
												+ "FROM SYS_RESOURCE r "
												+ "LEFT JOIN SYS_ROLE_RESOURCE rr ON rr.RESOURCE_ID = r.ID "
												+ "LEFT JOIN SYS_ROLE ro ON ro.ID = rr.ROLE_ID "
												+ "LEFT JOIN SYS_USER_ROLE ur ON ur.ROLE_ID = ro.ID "
												+ "LEFT JOIN SYS_USER u ON u.ID = ur.USER_ID "
												+ "WHERE r.ACTIVE_FLAG = 1 and ro.ACTIVE_FLAG = 1 and u.IS_ENABLED = 1 "
												+ "and r.TYPE = ? and u.ID = ? "
												+ "ORDER BY r.ORDERS ASC";
	
    //登陆验证时，通过username获取用户的所有权限信息，  
    //并返回User放到spring的全局缓存SecurityContextHolder中，以供授权器使用  
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {   
    	User userDetail = null;
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		
		userDetail = this.jdbcTemplateLoadUserStatusByUsername(username);
		if (userDetail == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		
		obtionGrantedAuthorities(userDetail,auths);
		userDetail.setMenus(obtionResource(userDetail,SysConstants.RESOURCE_TYPE_MENU));
		userDetail.setMethods(obtionResource(userDetail,SysConstants.RESOURCE_TYPE_METHOD));
		return userDetail;
    }
    
    public UserDetails loadUserByUsername(String username,String userType) throws UsernameNotFoundException, DataAccessException {   
    	User userDetail = null;
    	Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
    	
    	userDetail = this.jdbcTemplateLoadUserStatusByUsername(username,userType);
    	if (userDetail == null) {
    		throw new UsernameNotFoundException("用户名不存在");
    	}
    	
    	obtionGrantedAuthorities(userDetail,auths);
    	userDetail.setMenus(obtionResource(userDetail,SysConstants.RESOURCE_TYPE_MENU));
    	userDetail.setMethods(obtionResource(userDetail,SysConstants.RESOURCE_TYPE_METHOD));
    	return userDetail;
    }
    
    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException, DataAccessException {   
    	User userDetail = null;
    	Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
    	
    	userDetail = this.jdbcTemplateLoadUserStatusByPhone(phone);
    	if (userDetail == null) {
    		throw new UsernameNotFoundException("手机号不存在");
    	}
    	
    	obtionGrantedAuthorities(userDetail,auths);
    	userDetail.setMenus(obtionResource(userDetail,SysConstants.RESOURCE_TYPE_MENU));
    	userDetail.setMethods(obtionResource(userDetail,SysConstants.RESOURCE_TYPE_METHOD));
    	return userDetail;
    }
    
    public UserDetails loadUserByPhone(String phone,String userType) throws UsernameNotFoundException, DataAccessException {   
    	User userDetail = null;
    	Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
    	
    	userDetail = this.jdbcTemplateLoadUserStatusByPhone(phone,userType);
    	if (userDetail == null) {
    		throw new UsernameNotFoundException("手机号不存在");
    	}
    	
    	obtionGrantedAuthorities(userDetail,auths);
    	userDetail.setMenus(obtionResource(userDetail,SysConstants.RESOURCE_TYPE_MENU));
    	userDetail.setMethods(obtionResource(userDetail,SysConstants.RESOURCE_TYPE_METHOD));
    	return userDetail;
    }
    
    private User jdbcTemplateLoadUserStatusByUsername(String username) {
		List<Map<String, Object>> results = jdbcTemplate.queryForList(LOAD_USER_BY_NAME_SQL, username);
		User user = null;
		if (results.size() == 1) {
			Map<String, Object> result  = results.get(0);
			user = mapObject(result);
		}
		return user;
	}
    
    private User jdbcTemplateLoadUserStatusByUsername(String username,String userType) {
    	List<Map<String, Object>> results = jdbcTemplate.queryForList(LOAD_USER_BY_NAME_AND_TYPE_SQL, username, userType);
    	User user = null;
    	if (results.size() == 1) {
    		Map<String, Object> result  = results.get(0);
    		user = mapObject(result);
    	}
    	return user;
    }
    
    private User jdbcTemplateLoadUserStatusByPhone(String phone) {
    	List<Map<String, Object>> results = jdbcTemplate.queryForList(LOAD_USER_BY_PHONE_SQL, phone);
    	User user = null;
    	if (results.size() == 1) {
    		Map<String, Object> result  = results.get(0);
    		user = mapObject(result);
    	}
    	return user;
    }
    
    private User jdbcTemplateLoadUserStatusByPhone(String phone,String userType) {
    	List<Map<String, Object>> results = jdbcTemplate.queryForList(LOAD_USER_BY_PHONE_AND_TYPE_SQL, phone, userType);
    	User user = null;
    	if (results.size() == 1) {
    		Map<String, Object> result  = results.get(0);
    		user = mapObject(result);
    	}
    	return user;
    }
    
    private User mapObject(Map<String, Object> result){
    	JSONObject jSONObject = new JSONObject(result);
    	User user = new User();
		user.setId(jSONObject.getLong("id"));
		user.setCompanyId(jSONObject.getLong("companyId"));
		user.setUsername(jSONObject.getString("username"));
		user.setPassword(jSONObject.getString("password"));
		user.setNikeName(jSONObject.getString("nikeName"));
		user.setRealName(jSONObject.getString("realName"));
		user.setSex(jSONObject.getString("sex"));
		user.setAvatar(jSONObject.getString("avatar"));
		user.setTelPhone(jSONObject.getString("telPhone"));
		user.setPhone(jSONObject.getString("phone"));
		user.setEmail(jSONObject.getString("email"));
		user.setUserType(jSONObject.getInteger("userType"));
		user.setRefId(jSONObject.getLong("refId"));
		user.setIsEnabled(jSONObject.getBoolean("isEnabled"));
		user.setIsLock(jSONObject.getBoolean("isLock"));
		return user;
    }
    
    protected void obtionGrantedAuthorities(User user, Collection<GrantedAuthority> auths) {
		List<Map<String, Object>> perms = jdbcTemplate.queryForList(LOAD_USER_ROLE_SQL, user.getId());
		List<Role> roles = new ArrayList<Role>();
		for (int i=0;perms!=null && i<perms.size();i++) {
			Map<String, Object> map = perms.get(i);
			JSONObject jSONObject = new JSONObject(map);
			if(map.get(DEF_SQL_AUTH_KEY)!=null){
				GrantedAuthority authority = new SimpleGrantedAuthority(jSONObject.getString(DEF_SQL_AUTH_KEY));
				auths.add(authority);
			}
			
			Role role = new Role();
			role.setId(jSONObject.getLong("id"));
			role.setRoleCode(jSONObject.getString("roleCode"));
			role.setRoleName(jSONObject.getString("roleName"));
			roles.add(role);
		}
		user.setAuthorities(auths);
		user.setRoles(roles);
	}
    
    protected List<Resource> obtionResource(User user, Integer type) {
    	List<Map<String, Object>> Resources = jdbcTemplate.queryForList(LOAD_USER_RESOURCE_SQL, new Object[]{type,user.getId()});
    	List<Resource> resourceList = new ArrayList<Resource>();
    	for (int i=0;Resources!=null && i<Resources.size();i++) {
    		Map<String, Object> map = Resources.get(i);
    		JSONObject jSONObject = new JSONObject(map);
    		Resource resource = new Resource();
    		resource.setId(jSONObject.getLong("id"));
    		resource.setName(jSONObject.getString("name"));
    		resource.setType(jSONObject.getInteger("type"));
    		resource.setIsDirectory(jSONObject.getInteger("isDirectory"));
    		resource.setValue(jSONObject.getString("value"));
    		resource.setLevel(jSONObject.getInteger("level"));
    		resource.setIcon(jSONObject.getString("icon"));
    		Resource parent = new Resource();
    		parent.setId(jSONObject.getLong("parentId"));
    		resource.setParent(parent);
    		resource.setOrders(jSONObject.getInteger("orders"));
    		resourceList.add(resource);
    	}
    	return resourceList;
    }

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}   
}   
