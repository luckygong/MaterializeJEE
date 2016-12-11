package com.materialize.jee.platform.authorization.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.materialize.jee.platform.authorization.domain.User;
import com.materialize.jee.platform.base.JsonResponseModel;
import com.materialize.jee.platform.utils.HttpUtils;

public class MySimpleLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(MySimpleLoginSuccessHandler.class);

	public static final String saveLoginHistorySql = "INSERT INTO SYS_USER_LOGIN_HISTORY(USER_ID, LOGIN_IP, SESSION_ID, LOGIN_TIME) values (?, ?, ?, ?)";
	
	public static final String CURRENT_USER = "user";
	
	private SessionRegistry sessionRegistry;
	
	private String defaultTargetUrl;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	protected JdbcTemplate jdbcTemplate;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        WebAuthenticationDetails details = (WebAuthenticationDetails)authentication.getDetails();
		String sessionId = details.getSessionId();
        //用户信息放置到缓存
        sessionRegistry.registerNewSession(sessionId, authentication.getPrincipal());
        
        this.saveHistoryInfo(request, authentication);
		super.clearAuthenticationAttributes(request);
		
		if(!(request.getHeader("X-Requested-With")!=null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With")))){
			logger.debug("Login success,Redirecting to " + this.defaultTargetUrl);
			this.redirectStrategy.sendRedirect(request, response, this.defaultTargetUrl);
		}else{
			ObjectMapper objectMapper = new ObjectMapper();  
	        response.setHeader("Content-Type", "application/json;charset=UTF-8");  
	        JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);  
	        try {  
	            JsonResponseModel jsonData = new JsonResponseModel();  
	            objectMapper.writeValue(jsonGenerator, jsonData);  
	        } catch (JsonProcessingException ex) {  
	            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);  
	        }  
		}
		return;
	}

	public void saveHistoryInfo(HttpServletRequest request, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		try {
			getJdbcTemplate().update(saveLoginHistorySql, user.getId(), HttpUtils.getIpAddress(request), request.getSession().getId(), new Date());
		} catch (DataAccessException e) {
			if (logger.isWarnEnabled()) {
				logger.debug("无法更新用户登录信息至数据库");
			}
		}
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isEmpty(defaultTargetUrl)) {
			throw new BeanInitializationException("You must configure defaultTargetUrl");
		}
	}

	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		Assert.notNull(jdbcTemplate, "jdbcTemplate cannot be null");
		this.jdbcTemplate = jdbcTemplate;
	}

	public SessionRegistry getSessionRegistry() {
		return sessionRegistry;
	}

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
	
}
