package com.materialize.jee.platform.authorization.domain;

import java.io.Serializable;

public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private java.lang.Long id;
	
	/**
	 * 用户ID
	 */
	private java.lang.Long userId;
	
	/**
	 * 角色ID
	 */
	private java.lang.Long roleId;
	
	public java.lang.Long getId() {
        return id;
    }
    
    public void setId(java.lang.Long id) {
        this.id = id;
    }
    
	public java.lang.Long getUserId() {
        return userId;
    }
    
    public void setUserId(java.lang.Long userId) {
        this.userId = userId;
    }
    
	public java.lang.Long getRoleId() {
        return roleId;
    }
    
    public void setRoleId(java.lang.Long roleId) {
        this.roleId = roleId;
    }
    
}