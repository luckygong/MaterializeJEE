package com.materialize.jee.platform.authorization.security;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import com.materialize.jee.platform.base.JsonResponseModel;

public class MyConcurrentSessionFilter extends ConcurrentSessionFilter {
	private SessionRegistry sessionRegistry;
	private String expiredUrl;
	private Pattern excludeUrlPattern;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private LogoutHandler[] handlers = { new MyLogoutHandler() };
	
	public MyConcurrentSessionFilter(SessionRegistry sessionRegistry) {
		super(sessionRegistry);
		this.sessionRegistry = sessionRegistry;
	}

	public MyConcurrentSessionFilter(SessionRegistry sessionRegistry, String expiredUrl) {
		super(sessionRegistry, expiredUrl);
		this.sessionRegistry = sessionRegistry;
		this.expiredUrl = expiredUrl;
	}
	
	public MyConcurrentSessionFilter(SessionRegistry sessionRegistry, String expiredUrl, String excludeUrl) {
		super(sessionRegistry, expiredUrl);
		this.sessionRegistry = sessionRegistry;
		this.expiredUrl = expiredUrl;
		this.excludeUrlPattern = Pattern.compile(excludeUrl);
	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String servletPath = request.getServletPath();

        //请求的路径是排除的URL时，则直接放行
        if (excludeUrlPattern!=null && excludeUrlPattern.matcher(servletPath).matches()) {
            chain.doFilter(req, res);
            return;
        }

		HttpSession session = request.getSession(false);
		if (session != null) {
			SessionInformation info = sessionRegistry.getSessionInformation(session.getId());
			if (info != null) {
				if (info.isExpired()) {
					doLogout(request, response, (Authentication)info.getPrincipal());

					String targetUrl = determineExpiredUrl(request, info);
					if (targetUrl != null) {
						this.redirectStrategy.sendRedirect(request, response, targetUrl);
						return;
					}

					ObjectMapper objectMapper = new ObjectMapper();  
			        response.setHeader("Content-Type", "application/json;charset=UTF-8");  
			        JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);  
			        try {  
			            JsonResponseModel jsonData = new JsonResponseModel();  
			            jsonData.setStatus(0);
			            jsonData.setInfo("session已过期");
			            objectMapper.writeValue(jsonGenerator, jsonData);  
			        } catch (JsonProcessingException ex) {  
			            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);  
			        }  
					return;
				}
				this.sessionRegistry.refreshLastRequest(info.getSessionId());
			}
		}
		chain.doFilter(req, res);
	}
	
	private void doLogout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		for (LogoutHandler handler : this.handlers){
			handler.logout(request, response, authentication);
		}
	}

	public SessionRegistry getSessionRegistry() {
		return sessionRegistry;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	public String getExpiredUrl() {
		return expiredUrl;
	}

	public void setExpiredUrl(String expiredUrl) {
		this.expiredUrl = expiredUrl;
	}
	
}
