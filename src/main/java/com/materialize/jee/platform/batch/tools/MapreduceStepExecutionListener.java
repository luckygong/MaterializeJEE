package com.materialize.jee.platform.batch.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.jdbc.core.JdbcTemplate;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStepInstance;
import com.materialize.jee.platform.utils.ContextUtils;

public class MapreduceStepExecutionListener implements StepExecutionListener {
	private static Logger logger = LoggerFactory.getLogger(MapreduceStepExecutionListener.class);

	private JdbcTemplate jdbcTemplate = ContextUtils.getBean(JdbcTemplate.class);
	private MapreduceBatchJobStepInstance stepInstance;
	
	public MapreduceStepExecutionListener(MapreduceBatchJobStepInstance stepInstance){
		this.stepInstance = stepInstance;
	}
	
	@Override
	public ExitStatus afterStep(StepExecution arg0) {
		logger.info("step "+arg0.getStepName()+" execute finished");
		String sql = "UPDATE SYS_MAPREDUCE_BATCH_JOB_STEP_INSTANCE SET END_TIME = ?, RESULT_CODE = ?, RESULT_DESC =? WHERE ID = ?";
		Object[] params = new Object[4];
		params[0] = arg0.getEndTime();
		params[1] = arg0.getExitStatus().getExitCode();
		String des = arg0.getExitStatus().getExitDescription();
		des = des==null?null:(des.length()>512?des.substring(0, 512):des);
		params[2] = des;
		params[3] = stepInstance.getId();
		jdbcTemplate.update(sql.toString(), params);
		return arg0.getExitStatus();
	}

	@Override
	public void beforeStep(StepExecution arg0) {
		logger.info("step "+arg0.getStepName()+" execute start");
		String sql = "UPDATE SYS_MAPREDUCE_BATCH_JOB_STEP_INSTANCE SET START_TIME = ? WHERE ID = ?";
		Object[] params = new Object[2];
		params[0] = arg0.getStartTime();
		params[1] = stepInstance.getId();
		jdbcTemplate.update(sql.toString(), params);
	}

}
