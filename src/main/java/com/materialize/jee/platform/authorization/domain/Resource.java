package com.materialize.jee.platform.authorization.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Resource implements Comparable<Resource> ,Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private java.lang.Long id;
	
	/**
	 * 资源名称
	 */
	private java.lang.String name;
	
	/**
	 * 类型：0-url ; 1-method
	 */
	private java.lang.Integer type;
	
	/**
	 * 菜单是否为目录，0-页面;1-目录
	 */
	private java.lang.Integer isDirectory;
	
	/**
	 * 资源值
	 */
	private java.lang.String value;
	
	/**
	 * 图标
	 */
	private java.lang.String icon;
	
	/**
	 * 所属父资源
	 */
	private Resource parent;
	
	/**
	 * 排序
	 */
	private java.lang.Integer orders;
	
	/**
	 * 层级
	 */
	private java.lang.Integer level;
	
	/**
	 * 描述
	 */
	private java.lang.String descn;
	
	/**
	 * 是否有效#1:有效;0:无效
	 */
	private java.lang.Boolean activeFlag;
	
	/**
	 * 创建人ID
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
	
	private List<Resource> childs = new ArrayList<Resource>();
	
	public java.lang.Long getId() {
        return id;
    }
    
    public void setId(java.lang.Long id) {
        this.id = id;
    }
    
	public java.lang.String getName() {
        return name;
    }
    
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
	public java.lang.Integer getType() {
        return type;
    }
    
    public void setType(java.lang.Integer type) {
        this.type = type;
    }
    
	public java.lang.String getValue() {
        return value;
    }
    
    public void setValue(java.lang.String value) {
        this.value = value;
    }
    
	public java.lang.String getIcon() {
        return icon;
    }
    
    public void setIcon(java.lang.String icon) {
        this.icon = icon;
    }
    
	public Resource getParent() {
        return parent;
    }
    
    public void setParent(Resource parent) {
        this.parent = parent;
    }
    
	public java.lang.Integer getOrders() {
        return orders;
    }
    
    public void setOrders(java.lang.Integer orders) {
        this.orders = orders;
    }
    
	public java.lang.Integer getLevel() {
        return level;
    }
    
    public void setLevel(java.lang.Integer level) {
        this.level = level;
    }
    
	public java.lang.String getDescn() {
        return descn;
    }
    
    public void setDescn(java.lang.String descn) {
        this.descn = descn;
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

	public java.lang.Integer getIsDirectory() {
		return isDirectory;
	}

	public void setIsDirectory(java.lang.Integer isDirectory) {
		this.isDirectory = isDirectory;
	}

	public List<Resource> getChilds() {
		return childs;
	}

	public void setChilds(List<Resource> childs) {
		this.childs = childs;
	}
    
	@Override
    public int compareTo(Resource resource){  
		return this.getOrders() - resource.getOrders();  
    }  
	
}