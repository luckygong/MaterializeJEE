package com.materialize.jee.platform.quartz.domain;

import java.io.Serializable;

public class QuartzJob implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 任务ID
	 */
	private java.lang.Long id;
	
	/**
	 * 任务名称
	 */
	private java.lang.String jobName;
	
	/**
	 * 任务描述
	 */
	private java.lang.String description;
	
	/**
	 * 任务组
	 */
	private java.lang.String jobGroup;
	
	/**
	 * 任务实现类
	 */
	private java.lang.String jobClass;
	
	/**
	 * 任务参数，格式：A=a,B=b
	 */
	private java.lang.String jobParams;
	
	/**
	 * 任务状态，0-禁用 ;1-启用 
	 */
	private java.lang.Integer jobStatus;
	
	/**
	 * 允许并发执行，0-不允许；1-允许
	 */
	private java.lang.Integer isConcurrent;
	
	/**
	 * 任务运行时间表达式
	 */
	private java.lang.String cronExpression;
	
	/**
	 * 上次运行时间
	 */
	private java.util.Date previousRunTime;
	
	/**
	 * 最后运行时间
	 */
	private java.util.Date lastRunTime;
	
	/**
	 * 下次运行时间
	 */
	private java.util.Date nextRunTime;
	
	/**
	 * 创建者ID
	 */
	private java.lang.Long createId;
	
	public java.lang.Long getId() {
        return id;
    }
    
    public void setId(java.lang.Long id) {
        this.id = id;
    }
    
	public java.lang.String getJobName() {
        return jobName;
    }
    
    public void setJobName(java.lang.String jobName) {
        this.jobName = jobName;
    }
    
	public java.lang.String getDescription() {
        return description;
    }
    
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
	public java.lang.String getJobGroup() {
        return jobGroup;
    }
    
    public void setJobGroup(java.lang.String jobGroup) {
        this.jobGroup = jobGroup;
    }
    
	public java.lang.String getJobClass() {
        return jobClass;
    }
    
    public void setJobClass(java.lang.String jobClass) {
        this.jobClass = jobClass;
    }
    
	public java.lang.String getJobParams() {
        return jobParams;
    }
    
    public void setJobParams(java.lang.String jobParams) {
        this.jobParams = jobParams;
    }
    
	public java.lang.Integer getJobStatus() {
        return jobStatus;
    }
    
    public void setJobStatus(java.lang.Integer jobStatus) {
        this.jobStatus = jobStatus;
    }
    
	public java.lang.Integer getIsConcurrent() {
        return isConcurrent;
    }
    
    public void setIsConcurrent(java.lang.Integer isConcurrent) {
        this.isConcurrent = isConcurrent;
    }
    
	public java.lang.String getCronExpression() {
        return cronExpression;
    }
    
    public void setCronExpression(java.lang.String cronExpression) {
        this.cronExpression = cronExpression;
    }
    
	public java.util.Date getPreviousRunTime() {
        return previousRunTime;
    }
    
    public void setPreviousRunTime(java.util.Date previousRunTime) {
        this.previousRunTime = previousRunTime;
    }
    
	public java.util.Date getLastRunTime() {
        return lastRunTime;
    }
    
    public void setLastRunTime(java.util.Date lastRunTime) {
        this.lastRunTime = lastRunTime;
    }
    
	public java.util.Date getNextRunTime() {
        return nextRunTime;
    }
    
    public void setNextRunTime(java.util.Date nextRunTime) {
        this.nextRunTime = nextRunTime;
    }
    
	public java.lang.Long getCreateId() {
        return createId;
    }
    
    public void setCreateId(java.lang.Long createId) {
        this.createId = createId;
    }
    
}