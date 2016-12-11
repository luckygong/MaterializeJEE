package com.materialize.jee.platform.organization.domain;

import java.io.Serializable;

public class Company implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private java.lang.Long id;
	
	/**
	 * 公司名称
	 */
	private java.lang.String companyName;
	
	/**
	 * 公司类型：1-总公司/个体； 2-分公司/连锁
	 */
	private java.lang.Integer companyType;
	
	/**
	 * 父公司id
	 */
	private java.lang.Long parentId;
	
	/**
	 * 公司地址
	 */
	private java.lang.String companyAddress;
	
	/**
	 * 公司电话
	 */
	private java.lang.String companyTelphone;
	
	/**
	 * 公司传真
	 */
	private java.lang.String companyFax;
	
	/**
	 * 公司邮编
	 */
	private java.lang.String companyPost;
	
	/**
	 * 组织机构
	 */
	private java.lang.String orgCode;
	
	/**
	 * 营业执照
	 */
	private java.lang.String bussinessLicence;
	
	/**
	 * 联系人
	 */
	private java.lang.String companyLinkman;
	
	/**
	 * 联系人电话
	 */
	private java.lang.String linkmanTelphone;
	
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
    
	public java.lang.String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(java.lang.String companyName) {
        this.companyName = companyName;
    }
    
	public java.lang.Integer getCompanyType() {
        return companyType;
    }
    
    public void setCompanyType(java.lang.Integer companyType) {
        this.companyType = companyType;
    }
    
	public java.lang.Long getParentId() {
        return parentId;
    }
    
    public void setParentId(java.lang.Long parentId) {
        this.parentId = parentId;
    }
    
	public java.lang.String getCompanyAddress() {
        return companyAddress;
    }
    
    public void setCompanyAddress(java.lang.String companyAddress) {
        this.companyAddress = companyAddress;
    }
    
	public java.lang.String getCompanyTelphone() {
        return companyTelphone;
    }
    
    public void setCompanyTelphone(java.lang.String companyTelphone) {
        this.companyTelphone = companyTelphone;
    }
    
	public java.lang.String getCompanyFax() {
        return companyFax;
    }
    
    public void setCompanyFax(java.lang.String companyFax) {
        this.companyFax = companyFax;
    }
    
	public java.lang.String getCompanyPost() {
        return companyPost;
    }
    
    public void setCompanyPost(java.lang.String companyPost) {
        this.companyPost = companyPost;
    }
    
	public java.lang.String getOrgCode() {
        return orgCode;
    }
    
    public void setOrgCode(java.lang.String orgCode) {
        this.orgCode = orgCode;
    }
    
	public java.lang.String getBussinessLicence() {
        return bussinessLicence;
    }
    
    public void setBussinessLicence(java.lang.String bussinessLicence) {
        this.bussinessLicence = bussinessLicence;
    }
    
	public java.lang.String getCompanyLinkman() {
        return companyLinkman;
    }
    
    public void setCompanyLinkman(java.lang.String companyLinkman) {
        this.companyLinkman = companyLinkman;
    }
    
	public java.lang.String getLinkmanTelphone() {
        return linkmanTelphone;
    }
    
    public void setLinkmanTelphone(java.lang.String linkmanTelphone) {
        this.linkmanTelphone = linkmanTelphone;
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