package com.materialize.jee.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionInformation;

import com.materialize.jee.platform.authorization.domain.User;
import com.materialize.jee.platform.utils.CacheUtils;

public class CommonUtils {
	
	/**
	 * 获取当前登录用户
	 */
	public static User getCurrentLogIn(HttpServletRequest request){
		HttpSession session = request.getSession(false);
    	SessionInformation sessionInfo = (SessionInformation)CacheUtils.getCacheInfo(CacheUtils.EHCACHE_SESSION_CONF_NAME, session.getId());
    	if(sessionInfo==null){
    		return null;
    	}
    	return (User)sessionInfo.getPrincipal();
	}
	
	/**
	 * 获取当前登录用户角色代码
	 */
	public static List<String> getCurrentLogInRoleCodes(HttpServletRequest request){
		List<String> roleCodes = new ArrayList<String>();
    	User user = getCurrentLogIn(request);
    	if(user!=null){
    		Collection<GrantedAuthority> authorities = user.getAuthorities();
    		if(authorities!=null && authorities.size()>0){
    			for(GrantedAuthority auth:authorities){
    				roleCodes.add(auth.getAuthority());
    			}
    		}
    	}
    	return roleCodes;
	}
	
	/**
	 * 当前登录用户是否为指定角色
	 */
	public static Boolean isRole(HttpServletRequest request, String roleCode){
		List<String> roleCodes = getCurrentLogInRoleCodes(request);
		if(roleCodes!=null && roleCodes.size()>0){
			return roleCodes.contains(roleCode);
		}
		return false;
	}
	
}
