package com.materialize.jee.platform.authorization.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource,InitializingBean { 
	
	public static final String DEF_SQL_RES_KEY = "value";
	public static final String DEF_SQL_ROLE_KEY = "roleCode";
	
	private static final String LOAD_ROLE_RESOURCE_SQL = "SELECT r.ROLE_CODE roleCode,"
																   + "rs.VALUE value "
														    + "FROM SYS_RESOURCE AS rs "
														    + "INNER JOIN SYS_ROLE_RESOURCE AS rr ON rr.RESOURCE_ID = rs.ID "
														    + "INNER JOIN SYS_ROLE AS r ON rr.ROLE_ID = r.ID "
														    + "WHERE rs.ACTIVE_FLAG=1 AND rs.VALUE IS NOT NULL " 
														    + "ORDER BY rs.ORDERS DESC";
    
	private PathMatcher pathMatcher = new AntPathMatcher();
	private Map<String, Collection<ConfigAttribute>> configAttributeMap;
	
	private JdbcTemplate jdbcTemplate;
     
	@Override
	public void afterPropertiesSet() throws Exception {
		this.configAttributeMap=bindResourceDefine();
	}
	
	private Map<String, Collection<ConfigAttribute>> bindResourceDefine() {
		return loadMetadataSource();
	}
	
	public void reloadResourceDefine() {
		configAttributeMap.clear();
		bindResourceDefine();
	}
	
	private Map<String, Collection<ConfigAttribute>> loadMetadataSource() {
		Map<String, Collection<ConfigAttribute>> configAttributeMap = new LinkedHashMap<String, Collection<ConfigAttribute>>();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(LOAD_ROLE_RESOURCE_SQL);
        for (Map<String, Object> map : list) {
			String resKeyVal = map.get(DEF_SQL_RES_KEY) != null ? map.get(DEF_SQL_RES_KEY).toString() : null;
			String authKeyVal = map.get(DEF_SQL_ROLE_KEY) != null ? map.get(DEF_SQL_ROLE_KEY).toString() : null;
            ConfigAttribute ca = new SecurityConfig(authKeyVal);
    		if (!configAttributeMap.containsKey(resKeyVal)) {
    			Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
    			configAttributeMap.put(resKeyVal, configAttributes);
    		}
    		configAttributeMap.get(resKeyVal).add(ca);
        }
		return configAttributeMap;
	}
	
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
		for (Entry<String, Collection<ConfigAttribute>> entry : this.configAttributeMap.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}
		return allAttributes;
	}
	
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String url = ((FilterInvocation) object).getRequestUrl();
		int firstQuestionMarkIndex = url.indexOf("?");

		if (firstQuestionMarkIndex != -1) {
			url = url.substring(0, firstQuestionMarkIndex);
		}
		Iterator<String> ite = configAttributeMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if (pathMatcher.match(url, resURL)) {
				return configAttributeMap.get(resURL);
			}
		}
		return null;
	}
    
    public boolean supports(Class<?>clazz) {   
    	return true;    
    }

	public Map<String, Collection<ConfigAttribute>> getConfigAttributeMap() {
		return configAttributeMap;
	}

	public void setConfigAttributeMap(Map<String, Collection<ConfigAttribute>> configAttributeMap) {
		this.configAttributeMap = configAttributeMap;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}   
    
}  
