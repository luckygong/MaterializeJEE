package com.materialize.jee.platform.authorization.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class MyAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
	
	public MyAuthenticationEntryPoint(String loginFormUrl){
		super(loginFormUrl);
	}
	  
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)  
            throws IOException, ServletException {  
  
        HttpServletRequest httpRequest = (HttpServletRequest) request;  
        HttpServletResponse httpResponse = (HttpServletResponse) response;  
  
        // 非ajax请求  
        if (!(request.getHeader("X-Requested-With")!=null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With")))) {  
        	super.commence(httpRequest, httpResponse, authException);  
        } 
    }
    
}
