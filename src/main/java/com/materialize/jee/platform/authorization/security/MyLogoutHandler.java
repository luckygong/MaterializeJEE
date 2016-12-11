package com.materialize.jee.platform.authorization.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.materialize.jee.platform.utils.CacheUtils;

public class MyLogoutHandler extends SecurityContextLogoutHandler {

    public MyLogoutHandler() {
    	super();
    }

    @Override
    public void logout(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication) {
    	HttpSession session = request.getSession(false);
        if (session != null) {
        	String sessionId = session.getId();
        	CacheUtils.deleteCacheInfo(CacheUtils.EHCACHE_SESSION_CONF_NAME, sessionId);
        }
        super.logout(request, response, authentication);
    }

}
