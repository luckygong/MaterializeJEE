package com.materialize.jee.platform.quartz.domain;

import java.io.Serializable;

public class QuartzLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private java.lang.Long id;
	
	/**
	 * 任务主键
	 */
	private java.lang.Long jobId;
	
	/**
	 * 任务名称
	 */
	private java.lang.String jobName;
	
	/**
	 * 开始时间
	 */
	private java.util.Date startTime;
	
	/**
	 * 结束时间
	 */
	private java.util.Date endTime;
	
	/**
	 * 运行结果，0-失败；1-成功
	 */
	private java.lang.Integer resultFlag;
	
	/**
	 * 结果描述
	 */
	private java.lang.String description;
	
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
    
	public java.lang.String getJobName() {
        return jobName;
    }
    
    public void setJobName(java.lang.String jobName) {
        this.jobName = jobName;
    }
    
	public java.util.Date getStartTime() {
        return startTime;
    }
    
    public void setStartTime(java.util.Date startTime) {
        this.startTime = startTime;
    }
    
	public java.util.Date getEndTime() {
        return endTime;
    }
    
    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }
    
	public java.lang.Integer getResultFlag() {
        return resultFlag;
    }
    
    public void setResultFlag(java.lang.Integer resultFlag) {
        this.resultFlag = resultFlag;
    }
    
	public java.lang.String getDescription() {
        return description;
    }
    
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
}