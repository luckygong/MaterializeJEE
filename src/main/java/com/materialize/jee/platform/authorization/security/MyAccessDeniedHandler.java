package com.materialize.jee.platform.authorization.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;

public class MyAccessDeniedHandler implements AccessDeniedHandler  {  
	private String accessDeniedUrl;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
    public String getAccessDeniedUrl() {  
        return accessDeniedUrl;  
    }  
    
    public void setAccessDeniedUrl(String accessDeniedUrl) {  
        this.accessDeniedUrl = accessDeniedUrl;  
    }  
  
    @Override  
    public void handle(HttpServletRequest req, HttpServletResponse resp, AccessDeniedException reason) throws ServletException, IOException {  
        
    	boolean isAjax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With")); //如果是ajax请求  
        if (isAjax) {         
            String jsonObject = "{\"status\":0,\"info\":\"You are not privileged to request this resource\"}";
            String contentType = "application/json";  
            resp.setContentType(contentType);  
            PrintWriter out = resp.getWriter();  
            out.print(jsonObject);  
            out.flush();  
            out.close();  
            return;  
        }else{  
        	this.redirectStrategy.sendRedirect(req, resp, this.accessDeniedUrl);
	         return;
        }  
    }  
}
