package com.materialize.jee.platform.quartz;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.materialize.jee.platform.quartz.domain.QuartzJob;
import com.materialize.jee.platform.quartz.mapper.QuartzJobMapper;
import com.materialize.jee.platform.utils.ContextUtils;

public class QuartzJobUtils {
	private static Log logger = LogFactory.getLog(QuartzJobUtils.class);
	public static final String JOB_DATA_MAP_KEY = "scheduleJob";
	
	private static QuartzJobMapper quartzJobMapper;
	static{
		quartzJobMapper = CreateInstance.getQuartzJobMapper();
	}
	
	/**  
	 * @Description: 查询数据库中配置的所有有效任务信息
	 * @param @return
	 * @param @throws Exception      
	 * @return List<QuartzJob>    
	 * @throws  
	 */
	public static List<QuartzJob> loadAllJobInfo() throws Exception{
		return quartzJobMapper.selectAllInUse();
	}
	
    /**  
     * @Description: 启动所有任务
     * @param @throws Exception      
     * @return void    
     * @throws  
     */
    public static void startAllJob() throws Exception {    
        //获取任务信息数据
        List<QuartzJob> jobList = loadAllJobInfo();
        
        if(jobList!=null){
	        for (QuartzJob job : jobList) {
	        	if(isJobRuning(job)){
	        		updateJob(job);
	        	}else{
	        		startJob(job);
	        	}
	        }
    	}
        logger.info("启动所有定时任务成功");
    }
    
    /**  
     * @Description: 启动单个任务
     * @param job
     * @throws Exception      
     * @return void    
     */
    public static void startJob(Long jobId) throws Exception{
    	QuartzJob sysQuartzJob = quartzJobMapper.selectByPrimaryKey(jobId);
    	startJob(sysQuartzJob);
    }
    
    /**  
     * @Description: 启动单个任务    
     * @param job
     * @throws Exception   
     */
	@SuppressWarnings("unchecked")
	public static void startJob(QuartzJob job) throws Exception{
        Scheduler scheduler = CreateInstance.getQuartzScheduler();
        if(isJobRuning(job)){
        	logger.info("任务["+job.getJobName()+"]已运行");
        	return;
        }
        
        JobDetail jobDetail = JobBuilder.newJob(((Class<Job>)Class.forName(job.getJobClass())))
            .withIdentity(job.getJobName(), job.getJobGroup()).build();
        jobDetail.getJobDataMap().put(JOB_DATA_MAP_KEY, job);
 
        //表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
 
        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
        		.withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
        logger.info("任务["+job.getJobName()+"]启动成功");
    }
    
    /**  
     * @Description: 更新运行中任务的定时配置    
     * @param jobId
     * @throws Exception   
     */
    public static void updateJob(Long jobId) throws Exception{
    	QuartzJob sysQuartzJob = quartzJobMapper.selectByPrimaryKey(jobId);
    	updateJob(sysQuartzJob);
    }
    
    /**  
     * @Description: 更新运行中任务的定时配置
     * @param job
     * @throws Exception      
     * @return void    
     */
    public static void updateJob(QuartzJob job) throws Exception{
    	Scheduler scheduler = CreateInstance.getQuartzScheduler();
    	if(!isJobRuning(job)){
    		logger.info("任务["+job.getJobName()+"]不存在");
    		return;
    	}
		//表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
 
        //按新的cronExpression表达式重新构建trigger
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
 
        //按新的trigger重新设置job执行
		scheduler.rescheduleJob(triggerKey, trigger);
		logger.info("任务["+job.getJobName()+"]更新成功");
    }
    
    /**  
     * @Description: 暂停任务    
     * @param jobId
     * @throws Exception   
     */
    public static void pauseJob(Long jobId) throws Exception{
    	QuartzJob sysQuartzJob = quartzJobMapper.selectByPrimaryKey(jobId);
    	pauseJob(sysQuartzJob);
    }
    
    /**  
     * @Description: 暂停任务
     * @param @param job      
     * @return void    
     * @throws  
     */
    public static void pauseJob(QuartzJob job) throws Exception {
    	Scheduler scheduler = CreateInstance.getQuartzScheduler();
    	JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
    	scheduler.pauseJob(jobKey);
    	logger.info("任务["+job.getJobName()+"]暂停成功");
    }
    
    /**  
     * @Description: 恢复暂停任务    
     * @param jobId
     * @throws Exception   
     */
    public static void resumeJob(Long jobId) throws Exception{
    	QuartzJob sysQuartzJob = quartzJobMapper.selectByPrimaryKey(jobId);
    	resumeJob(sysQuartzJob);
    }
    
    /**  
     * @Description: 恢复暂停任务
     * @param @param job
     * @param @throws Exception      
     * @return void    
     * @throws  
     */
    public static void resumeJob(QuartzJob job) throws Exception {
    	Scheduler scheduler = CreateInstance.getQuartzScheduler();
    	JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
    	scheduler.resumeJob(jobKey);
    	logger.info("任务["+job.getJobName()+"]恢复运行成功");
    }
    
    /**  
     * @Description: 立即运行，只会运行一次，方便测试时用    
     * @param jobId
     * @throws Exception   
     */
    public static void triggerJob(Long jobId) throws Exception{
    	QuartzJob sysQuartzJob = quartzJobMapper.selectByPrimaryKey(jobId);
    	triggerJob(sysQuartzJob);
    }
    
    /**  
     * @Description: 立即运行，只会运行一次，方便测试时用
     * @param @param job
     * @param @throws Exception      
     * @return void    
     * @throws  
     */
    public static void triggerJob(QuartzJob job) throws Exception {
    	Scheduler scheduler = CreateInstance.getQuartzScheduler();
    	JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
    	scheduler.triggerJob(jobKey);
    	logger.info("任务["+job.getJobName()+"]立即运行一次");
    }
    
    /**  
     * @Description: 任务是否启动
     * @param @param job
     * @param @return      
     * @return boolean    
     * @throws  
     */
    public static boolean isJobRuning(QuartzJob job){
    	try {
    		Scheduler scheduler = CreateInstance.getQuartzScheduler();
    		JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(job.getJobName(),job.getJobGroup()));
			if (jobDetail == null){
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
    }
    
    private static class CreateInstance {
        public static Scheduler getQuartzScheduler() { 
        	return (Scheduler)ContextUtils.getBean("schedulerFactoryBean");
        }
        
        public static QuartzJobMapper getQuartzJobMapper() { 
        	return (QuartzJobMapper)ContextUtils.getBean(QuartzJobMapper.class);
        }
    }
}
