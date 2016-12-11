package com.materialize.jee.platform.authorization.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.materialize.jee.platform.utils.CaptchaUtils;
import com.materialize.jee.platform.utils.RequestUtils;

public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {  
    
    private static final String USERNAME = "username";//用户名或手机号  
    private static final String PASSWORD = "password";  
    private static final String USER_TYPE = "userType";  
    private static final String IS_MOBILE_CLIENT = "isMobileClient"; //是否是手机app客户端登录,默认网页登录
    private static final String IS_PASSWD_TYPE_AUTH_CODE = "isPasswdTypeAuthCode"; //手机端登录密码类型是否为动态密码，默认为是
    private static final String AUTH_CODE = "authCode";//动态密码  
    @Override  
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {  
        if(request.getSession(false)==null){
        	request.getSession(true);
        }
    	String username = obtainUsername(request);  
        String password = obtainPassword(request);  
        String userType = obtainUserType(request);
        Boolean isMobileClient = Boolean.valueOf(obtainIsMobileClient(request));
        Boolean isPasswdTypeAuthCode = Boolean.valueOf(obtainIsPasswdTypeAuthCode(request));
        String authCode = obtainAuthCode(request);
        
        Boolean isAuthCodePassed = false;
        if(isPasswdTypeAuthCode){
        	isAuthCodePassed = CaptchaUtils.verify(request, authCode);
        }
        username = username.trim();  
        MyCustomAuthenticationToken authRequest = new MyCustomAuthenticationToken(username, password,userType,isMobileClient,isPasswdTypeAuthCode,isAuthCodePassed);  
        //允许设置用户详细属性  
        setDetails(request, authRequest);  
        //实现验证  
        return this.getAuthenticationManager().authenticate(authRequest);  
    }  

    @Override  
    protected String obtainUsername(HttpServletRequest request) {  
    	String obj = RequestUtils.getRequestParam(request, USERNAME);
        return null == obj ? "" : obj.toString();  
    }  

    @Override  
    protected String obtainPassword(HttpServletRequest request) {  
    	String obj = RequestUtils.getRequestParam(request, PASSWORD); 
        return null == obj ? "" : obj.toString();  
    }  
    
    protected String obtainAuthCode(HttpServletRequest request) {  
    	String obj = RequestUtils.getRequestParam(request, AUTH_CODE); 
    	return null == obj ? "" : obj.toString();  
    }  
    
    protected String obtainIsMobileClient(HttpServletRequest request) {  
    	String obj = RequestUtils.getRequestParam(request, IS_MOBILE_CLIENT); 
    	return null == obj ? "" : obj.toString();  
    }
    
    protected String obtainIsPasswdTypeAuthCode(HttpServletRequest request) {  
    	String obj = RequestUtils.getRequestParam(request, IS_PASSWD_TYPE_AUTH_CODE); 
    	return null == obj ? "" : obj.toString();  
    }  
    
    protected String obtainUserType(HttpServletRequest request) {  
    	String obj = RequestUtils.getRequestParam(request, USER_TYPE); 
    	return null == obj ? "" : obj.toString();  
    }  
    
    @Override  
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {  
    	super.setDetails(request, authRequest);  
    }  
}  
