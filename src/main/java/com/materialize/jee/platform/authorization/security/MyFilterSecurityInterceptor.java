package com.materialize.jee.platform.authorization.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;  

public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor  implements Filter {    
	protected final Logger log = LoggerFactory.getLogger(MyFilterSecurityInterceptor.class);
    //配置文件注入  
    private FilterInvocationSecurityMetadataSource securityMetadataSource;  
    
    //登陆后，每次访问资源都通过这个拦截器拦截  
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {   
    	long start = System.currentTimeMillis();
    	FilterInvocation fi = new FilterInvocation(request, response, chain);   
        invoke(fi);
        long end = System.currentTimeMillis();
        long time = end - start;
    	log.info("process in {} ms: {}", time, ((HttpServletRequest)request).getRequestURI());
    }  
      
    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {    
        return this.securityMetadataSource;    
    }     
      
    public Class<? extends Object> getSecureObjectClass() {   
        return FilterInvocation.class;      
    }    
      
    public void invoke(FilterInvocation fi) throws IOException, ServletException {  
        //fi里面有一个被拦截的url  
        //里面调用MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法获取fi对应的所有权限  
        //再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够  
        InterceptorStatusToken token = super.beforeInvocation(fi);  
        try {  
            //执行下一个拦截器  
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());     
        } finally {   
            super.afterInvocation(token, null);    
        }     
    }    
    
    public SecurityMetadataSource obtainSecurityMetadataSource() {   
        return this.securityMetadataSource;     
    }   
    
    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {   
        this.securityMetadataSource = newSource;   
    }   
    public void destroy() {    
          
    }     
    public void init(FilterConfig arg0) throws ServletException {    
          
    }    
}  
