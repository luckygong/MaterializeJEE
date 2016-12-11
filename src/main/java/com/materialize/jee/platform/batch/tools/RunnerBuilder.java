package com.materialize.jee.platform.batch.tools;

import org.apache.commons.lang.StringUtils;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStep;
import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStepInstance;
import com.materialize.jee.platform.batch.service.MapreduceBatchJobStepInstanceService;
import com.materialize.jee.platform.hadoop.job.main.MapReduceRunner;
import com.materialize.jee.platform.hadoop.job.tools.JobContext;
import com.materialize.jee.platform.utils.ContextUtils;

public class RunnerBuilder {
	public static MapReduceRunner buildMapreduceRunner(MapreduceBatchJobStep step){
		MapreduceBatchJobStepInstanceService instanceService = ContextUtils.getBean(MapreduceBatchJobStepInstanceService.class);
		MapreduceBatchJobStepInstance instance = instanceService.selectByStepId(step.getId());
		JobContext context = JobContext.getTempJobContext();
		context.setJarFile(step.getLocalJarFile());
		context.setRunClass(step.getRunClass());
		String params = instance.getJobParams();
		if(!StringUtils.isBlank(params)){
			String[] sp = params.split(",");
			for(String s:sp){
				context.addJobParams(s);
			}
		}
		return new MapReduceRunner(context);
	}
	public static MapReduceRunner buildMapreduceRunner(MapreduceBatchJobStep step,MapreduceBatchJobStepInstance instance){
		JobContext context = JobContext.getTempJobContext();
		context.setJarFile(step.getLocalJarFile());
		context.setRunClass(step.getRunClass());
		String params = instance.getJobParams();
		if(!StringUtils.isBlank(params)){
			String[] sp = params.split(",");
			for(String s:sp){
				context.addJobParams(s);
			}
		}
		return new MapReduceRunner(context);
	}
}
