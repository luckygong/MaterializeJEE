package com.materialize.jee.platform.batch.domain;

import java.io.Serializable;
import java.util.List;

public class MapreduceBatchJob implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private java.lang.Long id;
	
	/**
	 * 任务代码
	 */
	private java.lang.String jobCode;
	
	/**
	 * 任务描述
	 */
	private java.lang.String jobDesc;
	
	/**
	 * 运行类型：1-自动调度；2-手工运行；3-调试运行
	 */
	private java.lang.Integer runType;
	
	/**
	 * 任务类型：1-simple；2-flow
	 */
	private java.lang.Integer jobType;
	
	/**
	 * 运行次数
	 */
	private java.lang.Integer runTimes;
	
	/**
	 * 创建者
	 */
	private java.lang.Long createId;
	
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	
	private List<MapreduceBatchJobStep> steps;
	
	public java.lang.Long getId() {
        return id;
    }
    
    public void setId(java.lang.Long id) {
        this.id = id;
    }
    
	public java.lang.String getJobCode() {
        return jobCode;
    }
    
    public void setJobCode(java.lang.String jobCode) {
        this.jobCode = jobCode;
    }
    
	public java.lang.String getJobDesc() {
        return jobDesc;
    }
    
    public void setJobDesc(java.lang.String jobDesc) {
        this.jobDesc = jobDesc;
    }
    
	public java.lang.Integer getRunType() {
        return runType;
    }
    
    public void setRunType(java.lang.Integer runType) {
        this.runType = runType;
    }
    
	public java.lang.Integer getJobType() {
        return jobType;
    }
    
    public void setJobType(java.lang.Integer jobType) {
        this.jobType = jobType;
    }
    
	public java.lang.Integer getRunTimes() {
        return runTimes;
    }
    
    public void setRunTimes(java.lang.Integer runTimes) {
        this.runTimes = runTimes;
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

	public List<MapreduceBatchJobStep> getSteps() {
		return steps;
	}

	public void setSteps(List<MapreduceBatchJobStep> steps) {
		this.steps = steps;
	}
    
}