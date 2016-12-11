package com.materialize.jee.platform.batch.domain;

import java.io.Serializable;

public class MapreduceBatchJobStepBranch implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private java.lang.Long id;
	
	/**
	 * 步骤ID
	 */
	private java.lang.Long stepId;
	
	/**
	 * 分支类型：0-next；1-end;2-fail;3-stop
	 */
	private java.lang.Integer branchType;
	
	/**
	 * 步骤状态代码
	 */
	private java.lang.String stepStatus;
	
	/**
	 * 对应状态时跳转到步骤ID执行
	 */
	private java.lang.Long toStepId;
	
	public java.lang.Long getId() {
        return id;
    }
    
    public void setId(java.lang.Long id) {
        this.id = id;
    }
    
	public java.lang.Long getStepId() {
        return stepId;
    }
    
    public void setStepId(java.lang.Long stepId) {
        this.stepId = stepId;
    }
    
	public java.lang.Integer getBranchType() {
        return branchType;
    }
    
    public void setBranchType(java.lang.Integer branchType) {
        this.branchType = branchType;
    }
    
	public java.lang.String getStepStatus() {
        return stepStatus;
    }
    
    public void setStepStatus(java.lang.String stepStatus) {
        this.stepStatus = stepStatus;
    }
    
	public java.lang.Long getToStepId() {
        return toStepId;
    }
    
    public void setToStepId(java.lang.Long toStepId) {
        this.toStepId = toStepId;
    }
    
}