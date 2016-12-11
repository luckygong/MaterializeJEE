package com.materialize.jee.platform.authorization.domain;

import java.io.Serializable;

public class UserLoginHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户ID
	 */
	private java.lang.Long userId;
	
	/**
	 * 登录IP
	 */
	private java.lang.String loginIp;
	
	/**
	 * session ID
	 */
	private java.lang.String sessionId;
	
	/**
	 * 登录时间
	 */
	private java.util.Date loginTime;
	
	public java.lang.Long getUserId() {
        return userId;
    }
    
    public void setUserId(java.lang.Long userId) {
        this.userId = userId;
    }
    
	public java.lang.String getLoginIp() {
        return loginIp;
    }
    
    public void setLoginIp(java.lang.String loginIp) {
        this.loginIp = loginIp;
    }
    
	public java.lang.String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(java.lang.String sessionId) {
        this.sessionId = sessionId;
    }
    
	public java.util.Date getLoginTime() {
        return loginTime;
    }
    
    public void setLoginTime(java.util.Date loginTime) {
        this.loginTime = loginTime;
    }
    
}