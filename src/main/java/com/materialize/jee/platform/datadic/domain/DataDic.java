package com.materialize.jee.platform.datadic.domain;

import java.io.Serializable;

public class DataDic implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private java.lang.Long id;
	
	/**
	 * 数据字典名称
	 */
	private java.lang.String category;
	
	/**
	 * 字典值
	 */
	private java.lang.String code;
	
	/**
	 * 字典值翻译
	 */
	private java.lang.String translation;
	
	/**
	 * 组内顺序
	 */
	private java.lang.Integer ranking;
	
	/**
	 * 状态，0-废弃；1-正常；
	 */
	private java.lang.Integer activeFlag;
	
	public java.lang.Long getId() {
        return id;
    }
    
    public void setId(java.lang.Long id) {
        this.id = id;
    }
    
	public java.lang.String getCategory() {
        return category;
    }
    
    public void setCategory(java.lang.String category) {
        this.category = category;
    }
    
	public java.lang.String getCode() {
        return code;
    }
    
    public void setCode(java.lang.String code) {
        this.code = code;
    }
    
	public java.lang.String getTranslation() {
        return translation;
    }
    
    public void setTranslation(java.lang.String translation) {
        this.translation = translation;
    }
    
	public java.lang.Integer getRanking() {
        return ranking;
    }
    
    public void setRanking(java.lang.Integer ranking) {
        this.ranking = ranking;
    }

	public java.lang.Integer getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(java.lang.Integer activeFlag) {
		this.activeFlag = activeFlag;
	}
    
}