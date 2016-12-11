package com.materialize.jee.platform.authorization.domain;

import java.io.Serializable;

public class RoleResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private java.lang.Long id;
	
	/**
	 * 角色ID
	 */
	private java.lang.Long roleId;
	
	/**
	 * 资源ID
	 */
	private java.lang.Long resourceId;
	
	public java.lang.Long getId() {
        return id;
    }
    
    public void setId(java.lang.Long id) {
        this.id = id;
    }
    
	public java.lang.Long getRoleId() {
        return roleId;
    }
    
    public void setRoleId(java.lang.Long roleId) {
        this.roleId = roleId;
    }
    
	public java.lang.Long getResourceId() {
        return resourceId;
    }
    
    public void setResourceId(java.lang.Long resourceId) {
        this.resourceId = resourceId;
    }
    
}