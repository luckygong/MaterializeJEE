package com.materialize.jee.platform.authorization.domain;

import java.io.Serializable;

public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private java.lang.Long id;
	
	/**
	 * 角色编码
	 */
	private java.lang.String roleCode;
	
	/**
	 * 角色名称
	 */
	private java.lang.String roleName;
	
	/**
	 * 结果描述
	 */
	private java.lang.String description;
	
	/**
	 * 是否有效#1:有效;0:无效
	 */
	private java.lang.Boolean activeFlag;
	
	/**
	 * 创建人
	 */
	private java.lang.Long createId;
	
	/**
	 * 创建时间
	 */
	private java.util.Date createDate;
	
	/**
	 * 修改人
	 */
	private java.lang.Long updateId;
	
	/**
	 * 修改时间
	 */
	private java.util.Date updateDate;
	
	public java.lang.Long getId() {
        return id;
    }
    
    public void setId(java.lang.Long id) {
        this.id = id;
    }
    
	public java.lang.String getRoleCode() {
        return roleCode;
    }
    
    public void setRoleCode(java.lang.String roleCode) {
        this.roleCode = roleCode;
    }
    
	public java.lang.String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(java.lang.String roleName) {
        this.roleName = roleName;
    }
    
	public java.lang.String getDescription() {
        return description;
    }
    
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
	public java.lang.Boolean getActiveFlag() {
        return activeFlag;
    }
    
    public void setActiveFlag(java.lang.Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
    
	public java.lang.Long getCreateId() {
        return createId;
    }
    
    public void setCreateId(java.lang.Long createId) {
        this.createId = createId;
    }
    
	public java.util.Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }
    
	public java.lang.Long getUpdateId() {
        return updateId;
    }
    
    public void setUpdateId(java.lang.Long updateId) {
        this.updateId = updateId;
    }
    
	public java.util.Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }
    
}