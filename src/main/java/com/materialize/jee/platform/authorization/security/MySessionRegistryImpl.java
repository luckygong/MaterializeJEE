package com.materialize.jee.platform.authorization.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.util.Assert;

import com.materialize.jee.platform.authorization.domain.User;
import com.materialize.jee.platform.utils.CacheUtils;

public class MySessionRegistryImpl extends SessionRegistryImpl {
	public List<Object> getAllPrincipals() {
		List<Object> result = new ArrayList<Object>();
		List<Object> sessions = CacheUtils.getAllCacheInfo(CacheUtils.EHCACHE_SESSION_CONF_NAME, false);
		if(sessions!=null){
			for(Object o:sessions){
				result.add(((SessionInformation)o).getPrincipal());
			}
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
		Set<String> sessionsUsedByPrincipal = (Set<String>) CacheUtils.getCacheInfo(CacheUtils.EHCACHE_USER_LOGIN_NAME, getPrincipalCacheKey(principal));
		if (sessionsUsedByPrincipal == null) {
			return Collections.emptyList();
		}

		List list = new ArrayList(sessionsUsedByPrincipal.size());
		for (String sessionId : sessionsUsedByPrincipal) {
			SessionInformation sessionInformation = getSessionInformation(sessionId);

			if (sessionInformation == null) {
				continue;
			}

			if ((includeExpiredSessions) || (!(sessionInformation.isExpired()))) {
				list.add(sessionInformation);
			}
		}

		return list;
	}

	public SessionInformation getSessionInformation(String sessionId) {
		Assert.hasText(sessionId, "SessionId required as per interface contract");
		return (SessionInformation)CacheUtils.getCacheInfo(CacheUtils.EHCACHE_SESSION_CONF_NAME, sessionId);
	}

	public void onApplicationEvent(SessionDestroyedEvent event) {
		String sessionId = event.getId();
		removeSessionInformation(sessionId);
	}

	public void refreshLastRequest(String sessionId) {
		Assert.hasText(sessionId, "SessionId required as per interface contract");
		SessionInformation info = getSessionInformation(sessionId);
		if (info != null){
			info.refreshLastRequest();
		}
	}

	@SuppressWarnings("unchecked")
	public void registerNewSession(String sessionId, Object principal) {
		Assert.hasText(sessionId, "SessionId required as per interface contract");
		Assert.notNull(principal, "Principal required as per interface contract");

		if (getSessionInformation(sessionId) != null) {
			removeSessionInformation(sessionId);
		}
		synchronized(this){
			CacheUtils.putCacheInfo(CacheUtils.EHCACHE_SESSION_CONF_NAME, sessionId, new SessionInformation(principal, sessionId, new Date()));
	
			Set<String> sessionsUsedByPrincipal = (Set<String>) CacheUtils.getCacheInfo(CacheUtils.EHCACHE_USER_LOGIN_NAME, getPrincipalCacheKey(principal));
			if (sessionsUsedByPrincipal == null) {
				sessionsUsedByPrincipal = new HashSet<String>();
			}
			sessionsUsedByPrincipal.add(sessionId);
			CacheUtils.putCacheInfo(CacheUtils.EHCACHE_USER_LOGIN_NAME, getPrincipalCacheKey(principal), sessionsUsedByPrincipal);
		}
		this.logger.debug("Registering session " + sessionId + ", for principal " + principal);
	}

	@SuppressWarnings("unchecked")
	public void removeSessionInformation(String sessionId) {
		Assert.hasText(sessionId, "SessionId required as per interface contract");

		SessionInformation info = getSessionInformation(sessionId);
		if (info == null) {
			return;
		}

		CacheUtils.deleteCacheInfo(CacheUtils.EHCACHE_SESSION_CONF_NAME, sessionId);
		this.logger.debug("Removing session " + sessionId + " from set of registered sessions");
		Set<String> sessionsUsedByPrincipal = (Set<String>) CacheUtils.getCacheInfo(CacheUtils.EHCACHE_USER_LOGIN_NAME, getPrincipalCacheKey(info.getPrincipal()));
		if (sessionsUsedByPrincipal == null) {
			return;
		}
		sessionsUsedByPrincipal.remove(sessionId);
		this.logger.debug("Removing session " + sessionId + " from principal's set of registered sessions");

		if (sessionsUsedByPrincipal.isEmpty()) {
			CacheUtils.deleteCacheInfo(CacheUtils.EHCACHE_USER_LOGIN_NAME, getPrincipalCacheKey(info.getPrincipal()));
			this.logger.debug("Removing principal " + info.getPrincipal() + " from registry");
		}
	}
	
	private String getPrincipalCacheKey(Object principal){
		String username = ((User)principal).getUsername();
		String id = String.valueOf(((User)principal).getId());
		return id+"_"+username;
	}
}
