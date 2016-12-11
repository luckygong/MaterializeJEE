package com.materialize.jee.platform.batch.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.JdbcTemplate;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJobInstance;
import com.materialize.jee.platform.utils.ContextUtils;

public class MapreduceJobExecutionListener implements JobExecutionListener {
	private static Logger logger = LoggerFactory.getLogger(MapreduceJobExecutionListener.class);

	private JdbcTemplate jdbcTemplate = ContextUtils.getBean(JdbcTemplate.class);
	private MapreduceBatchJobInstance jobInstance;
	
	public MapreduceJobExecutionListener(MapreduceBatchJobInstance jobInstance){
		this.jobInstance = jobInstance;
	}

	@Override
	public void afterJob(JobExecution paramJobExecution) {
		logger.info("job "+paramJobExecution.getJobConfigurationName()+" execute finished");
		String sql = "UPDATE SYS_MAPREDUCE_BATCH_JOB_INSTANCE SET END_TIME = ?, RESULT_CODE = ?, RESULT_DESC = ?, JOB_STATE = ? WHERE ID = ?";
		Object[] params = new Object[5];
		params[0] = paramJobExecution.getEndTime();
		params[1] = paramJobExecution.getExitStatus().getExitCode();
		String des = paramJobExecution.getExitStatus().getExitDescription();
		des = des==null?null:(des.length()>512?des.substring(0, 512):des);
		params[2] = des;
		params[3] = 2;
		params[4] = jobInstance.getId();
		jdbcTemplate.update(sql.toString(), params);
	}

	@Override
	public void beforeJob(JobExecution paramJobExecution) {
		logger.info("job "+paramJobExecution.getJobConfigurationName()+" execute start");
		String sql = "UPDATE SYS_MAPREDUCE_BATCH_JOB_INSTANCE SET START_TIME = ?, JOB_STATE = ? WHERE ID = ?";
		Object[] params = new Object[3];
		params[0] = paramJobExecution.getStartTime();
		params[1] = 1;
		params[2] = jobInstance.getId();
		jdbcTemplate.update(sql.toString(), params);
	}

}
