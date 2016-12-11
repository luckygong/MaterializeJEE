package com.materialize.jee.platform.batch.domain;

import java.io.Serializable;

public class MapreduceBatchJobStep implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private java.lang.Long id;
	
	/**
	 * 任务ID
	 */
	private java.lang.Long jobId;
	
	/**
	 * 步骤代码
	 */
	private java.lang.String stepCode;
	
	/**
	 * 步骤描述
	 */
	private java.lang.String stepDesc;
	
	/**
	 * 本地jar文件路径
	 */
	private java.lang.String localJarFile;
	
	/**
	 * 运行class类,带包名
	 */
	private java.lang.String runClass;
	
	/**
	 * 步骤序号
	 */
	private java.lang.Integer ranking;
	
	/**
	 * 父步骤
	 */
	private java.lang.Long parentStepId;
	
	/**
	 * 子步骤
	 */
	private java.lang.Long childStepId;
	
	/**
	 * 创建者
	 */
	private java.lang.Long createId;
	
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	
	public java.lang.Long getId() {
        return id;
    }
    
    public void setId(java.lang.Long id) {
        this.id = id;
    }
    
	public java.lang.Long getJobId() {
        return jobId;
    }
    
    public void setJobId(java.lang.Long jobId) {
        this.jobId = jobId;
    }
    
	public java.lang.String getStepCode() {
        return stepCode;
    }
    
    public void setStepCode(java.lang.String stepCode) {
        this.stepCode = stepCode;
    }
    
	public java.lang.String getStepDesc() {
        return stepDesc;
    }
    
    public void setStepDesc(java.lang.String stepDesc) {
        this.stepDesc = stepDesc;
    }
    
	public java.lang.String getLocalJarFile() {
        return localJarFile;
    }
    
    public void setLocalJarFile(java.lang.String localJarFile) {
        this.localJarFile = localJarFile;
    }
    
	public java.lang.String getRunClass() {
        return runClass;
    }
    
    public void setRunClass(java.lang.String runClass) {
        this.runClass = runClass;
    }
    
	public java.lang.Integer getRanking() {
        return ranking;
    }
    
    public void setRanking(java.lang.Integer ranking) {
        this.ranking = ranking;
    }
    
	public java.lang.Long getParentStepId() {
        return parentStepId;
    }
    
    public void setParentStepId(java.lang.Long parentStepId) {
        this.parentStepId = parentStepId;
    }
    
	public java.lang.Long getChildStepId() {
        return childStepId;
    }
    
    public void setChildStepId(java.lang.Long childStepId) {
        this.childStepId = childStepId;
    }
    
	public java.lang.Long getCreateId() {
        return createId;
    }
    
    public void setCreateId(java.lang.Long createId) {
        this.createId = createId;
    }
    
	public java.util.Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }
    
}