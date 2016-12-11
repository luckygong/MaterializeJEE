package com.materialize.jee.platform.quartz.base;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.materialize.jee.platform.SysConstants;
import com.materialize.jee.platform.quartz.QuartzJobUtils;
import com.materialize.jee.platform.quartz.domain.QuartzJob;
import com.materialize.jee.platform.quartz.domain.QuartzLog;
import com.materialize.jee.platform.quartz.mapper.QuartzJobMapper;
import com.materialize.jee.platform.quartz.mapper.QuartzLogMapper;

@DisallowConcurrentExecution
public abstract class BaseJob implements Job {
	private static Log logger = LogFactory.getLog(BaseJob.class);
	private static final String LOG_KEY_NAME = "LOG_ID";
	@Resource
	private QuartzJobMapper quartzJobMapper;
	@Resource
	private QuartzLogMapper quartzLogMapper;
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
        try{
	        beforeExecute(context);
	        executeJob(context.getMergedJobDataMap());
        }catch(Exception e){
        	e.printStackTrace();
        	afterExecuteFailt(context,e);
        	throw new JobExecutionException(e.getMessage());
        }
        afterExecuteSuccess(context);
	}

	protected void beforeExecute(JobExecutionContext context){
		QuartzJob scheduleJob = (QuartzJob)context.getMergedJobDataMap().get(QuartzJobUtils.JOB_DATA_MAP_KEY);
		logger.info("任务名称 [" + scheduleJob.getJobName() + "] 开始运行...");
        //设置job参数
		String jobParams = scheduleJob.getJobParams();
		if(StringUtils.isNotBlank(jobParams)){
			String[] paramSpilt = jobParams.split(",");
			if(paramSpilt.length>0){
				for(String spilt:paramSpilt){
					String[] param = spilt.split("=");
					context.getMergedJobDataMap().put(param[0], param[1]);
				}
			}
		}
		//更新job表信息
		Date now = new Date();
		QuartzJob job = quartzJobMapper.selectByPrimaryKey(scheduleJob.getId());
		job.setPreviousRunTime(job.getLastRunTime());
		job.setLastRunTime(now);
		job.setNextRunTime(context.getNextFireTime());
		quartzJobMapper.updateByPrimaryKey(job);
		
		//保存日志
		QuartzLog log = new QuartzLog();
		log.setDescription("running");
		log.setJobName(scheduleJob.getJobName());
		log.setJobId(scheduleJob.getId());
		log.setResultFlag(SysConstants.QUARTZ_RESULT_RUNNING);
		log.setStartTime(now);
		quartzLogMapper.insert(log);
		context.getMergedJobDataMap().put(QuartzJobUtils.JOB_DATA_MAP_KEY, job);
		context.getMergedJobDataMap().put(LOG_KEY_NAME, log.getId());
		logger.info("任务名称 [" + scheduleJob.getJobName() + "] 更新job表时间信息及日志保存成功...");
	}
	
	protected void afterExecuteSuccess(JobExecutionContext context){
		QuartzJob scheduleJob = (QuartzJob)context.getMergedJobDataMap().get(QuartzJobUtils.JOB_DATA_MAP_KEY);
		Long logId = (Long)context.getMergedJobDataMap().get(LOG_KEY_NAME);
		QuartzLog log = quartzLogMapper.selectByPrimaryKey(logId);
		if(log!=null){
			logger.info("任务名称 [" + scheduleJob.getJobName() + "] 运行成功，更新任务日志...");
			log.setDescription("success");
			log.setEndTime(new Date());
			log.setResultFlag(SysConstants.QUARTZ_RESULT_SUCCESS);
			quartzLogMapper.updateByPrimaryKey(log);
		}
	}
	
	protected void afterExecuteFailt(JobExecutionContext context,Exception e){
		QuartzJob scheduleJob = (QuartzJob)context.getMergedJobDataMap().get(QuartzJobUtils.JOB_DATA_MAP_KEY);
		Long logId = (Long)context.getMergedJobDataMap().get(LOG_KEY_NAME);
		QuartzLog log = quartzLogMapper.selectByPrimaryKey(logId);
		if(log!=null){
			logger.info("任务名称 [" + scheduleJob.getJobName() + "] 运行失败，保存任务日志...");
			log.setDescription("fail:"+e.getMessage());
			log.setEndTime(new Date());
			log.setResultFlag(SysConstants.QUARTZ_RESULT_FAIL);
			quartzLogMapper.updateByPrimaryKey(log);
		}
	}
	
	protected abstract void executeJob(JobDataMap params) throws Exception;
}
