package com.materialize.jee.platform.organization.domain;

import java.io.Serializable;

public class UserOrganization implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private java.lang.Long id;
	
	/**
	 * 用户id
	 */
	private java.lang.Long userId;
	
	/**
	 * 机构id
	 */
	private java.lang.Long organizationId;
	
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
    
	public java.lang.Long getOrganizationId() {
        return organizationId;
    }
    
    public void setOrganizationId(java.lang.Long organizationId) {
        this.organizationId = organizationId;
    }
    
}