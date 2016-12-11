package com.materialize.jee.platform.organization.domain;

import java.io.Serializable;

public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private java.lang.Long id;
	
	/**
	 * 机构代码
	 */
	private java.lang.String orgCode;
	
	/**
	 * 机构名称
	 */
	private java.lang.String orgName;
	
	/**
	 * 机构等级，0表示顶层机构
	 */
	private java.lang.Integer level;
	
	/**
	 * 所属父机构
	 */
	private java.lang.Long parentId;
	
	/**
	 * 公司id
	 */
	private Company company;
	
	/**
	 * 结果描述
	 */
	private java.lang.String description;
	
	/**
	 * 是否有效#1:有效;0:无效
	 */
	private java.lang.Boolean activeFlag;
	
	/**
	 * 创建人id
	 */
	private java.lang.Long createId;
	
	/**
	 * 创建时间
	 */
	private java.util.Date createDate;
	
	/**
	 * 更新人id
	 */
	private java.lang.Long updateId;
	
	/**
	 * 更新时间
	 */
	private java.util.Date updateDate;
	
	public java.lang.Long getId() {
        return id;
    }
    
    public void setId(java.lang.Long id) {
        this.id = id;
    }
    
	public java.lang.String getOrgCode() {
        return orgCode;
    }
    
    public void setOrgCode(java.lang.String orgCode) {
        this.orgCode = orgCode;
    }
    
	public java.lang.String getOrgName() {
        return orgName;
    }
    
    public void setOrgName(java.lang.String orgName) {
        this.orgName = orgName;
    }
    
	public java.lang.Integer getLevel() {
        return level;
    }
    
    public void setLevel(java.lang.Integer level) {
        this.level = level;
    }
    
	public java.lang.Long getParentId() {
        return parentId;
    }
    
    public void setParentId(java.lang.Long parentId) {
        this.parentId = parentId;
    }
    
	public Company getCompany() {
        return company;
    }
    
    public void setCompanyId(Company company) {
        this.company = company;
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