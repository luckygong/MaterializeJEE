package com.materialize.jee.platform.batch.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.transaction.PlatformTransactionManager;

import com.materialize.jee.platform.SysConstants;
import com.materialize.jee.platform.batch.domain.MapreduceBatchJob;
import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStep;
import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStepBranch;
import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStepInstance;
import com.materialize.jee.platform.batch.service.MapreduceBatchJobService;
import com.materialize.jee.platform.batch.service.MapreduceBatchJobStepBranchService;
import com.materialize.jee.platform.utils.ContextUtils;

public class JobBuilder {
	private static Logger logger = LoggerFactory.getLogger(JobBuilder.class);

	private static MapreduceBatchJobService jobService = ContextUtils.getBean(MapreduceBatchJobService.class);
	private static MapreduceBatchJobStepBranchService jobBranchService = ContextUtils
			.getBean(MapreduceBatchJobStepBranchService.class);

	private static JobRepository jobRepository = ContextUtils.getBean(JobRepository.class);
	private static PlatformTransactionManager transactionManager = ContextUtils
			.getBean(PlatformTransactionManager.class);
	private static StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, transactionManager);
	private static JobBuilderFactory jobBuilderFactory = new JobBuilderFactory(jobRepository);

	public static Job buildJob(MapreduceBatchContext context) throws Exception{
		MapreduceBatchJob job = jobService.selectByPrimaryKey(context.getBatchJob().getId());
		if (SysConstants.BATCH_JOB_JOB_TYPE_SIMPLE == job.getJobType()) {
			return buildSimpleJob(job,context);
		} else if (SysConstants.BATCH_JOB_JOB_TYPE_FLOW == job.getJobType()) {
			return buildFlowJob(job,context);
		} else {
			return null;
		}
	}

	public static Job buildSimpleJob(MapreduceBatchJob job,MapreduceBatchContext context) {
		Job batchJob = null;
		
		StringBuffer sb = new StringBuffer();
		List<MapreduceBatchJobStep> batchSteps = job.getSteps();
		if (SysConstants.BATCH_JOB_JOB_TYPE_SIMPLE != job.getJobType()) {
			return batchJob;
		}
		if (batchSteps != null && batchSteps.size() > 0) {
			MapreduceBatchJobStep batchStep = batchSteps.get(0);
			Step first = buildStep(batchStep,context);
			sb.append("build simple job ").append(job.getId()).append(" : ").append(batchStep.getStepCode());
			SimpleJobBuilder builder = jobBuilderFactory.get(job.getJobCode()).incrementer(new RunIdIncrementer())
					.listener(new MapreduceJobExecutionListener(context.getBatchJobInstance())).start(first);
			for (int i = 1; i < batchSteps.size(); i++) {
				batchStep = batchSteps.get(i);
				Step next = buildStep(batchStep,context);
				builder.next(next);
				sb.append(" -> ").append(batchStep.getStepCode());
			}
			batchJob = builder.build();
		}
		logger.info(sb.toString());
		return batchJob;
	}

	public static Job buildFlowJob(MapreduceBatchJob job,MapreduceBatchContext context) throws Exception {
		Job batchJob = null;
		StringBuffer sb = new StringBuffer();
		List<MapreduceBatchJobStep> batchSteps = job.getSteps();
		if(batchSteps==null || batchSteps.size()==0 || SysConstants.BATCH_JOB_JOB_TYPE_FLOW != job.getJobType()){
			return batchJob;
		}
		
		//构建所有batch步骤，并放入map
		Map<Long,Step> stepsMap = new HashMap<Long,Step>();
		for(MapreduceBatchJobStep s:batchSteps){
			Step step = buildStep(s,context);
			stepsMap.put(s.getId(),step);
		}
		
		//构建flow中第一个步骤
		FlowBuilder<FlowJobBuilder> flowBuilder = null;
		MapreduceBatchJobStep batchStep = batchSteps.get(0);
		Step first = stepsMap.get(batchStep.getId());
		sb.append("build flow job ").append(job.getId()).append(" : start(").append(batchStep.getStepCode()).append(")");
		SimpleJobBuilder simpleBuilder = jobBuilderFactory.get(job.getJobCode()).incrementer(new RunIdIncrementer()).
				listener(new MapreduceJobExecutionListener(context.getBatchJobInstance())).start(first);
		Map<String,MapreduceBatchJobStepBranch> branchMap = getStepBranchMap(batchStep);
		
		if(branchMap.keySet().isEmpty()){
			sb.append("first step no branch,single step, build end ");
			return simpleBuilder.build();
		}
		
		flowBuilder = buildFlow(simpleBuilder, flowBuilder, batchSteps, stepsMap,sb);
		
		batchJob = flowBuilder.build().build();
		logger.info(sb.toString());
		return batchJob;
	}

	private static FlowBuilder<FlowJobBuilder> buildFlow(SimpleJobBuilder simpleBuilder,FlowBuilder<FlowJobBuilder> flowBuilder,
			List<MapreduceBatchJobStep> batchSteps,Map<Long,Step> stepsMap,StringBuffer sb) throws Exception{
		
		for(int i=0;i<batchSteps.size();i++){
			MapreduceBatchJobStep batchStep = batchSteps.get(i);
			Map<String,MapreduceBatchJobStepBranch> branchMap = getStepBranchMap(batchStep);
			
			if(branchMap.keySet().contains(SysConstants.BATCH_JOB_STEP_BRANCH_STEP_STATUS_ALL_MATCH)){
				//"*" 构建到最末尾
				MapreduceBatchJobStepBranch allMatch = branchMap.get(SysConstants.BATCH_JOB_STEP_BRANCH_STEP_STATUS_ALL_MATCH);
				branchMap.remove(SysConstants.BATCH_JOB_STEP_BRANCH_STEP_STATUS_ALL_MATCH);
				Iterator<String> keys = branchMap.keySet().iterator();
				while(keys.hasNext()){
					MapreduceBatchJobStepBranch br = branchMap.get(keys.next());
					if(i==0){
						flowBuilder = buildBranch(simpleBuilder, flowBuilder, br,stepsMap,true,sb);
					}else{
						flowBuilder = buildBranch(simpleBuilder, flowBuilder, br,stepsMap,false,sb);
					}
				}
				if(i==0 && branchMap.size()==0){
					flowBuilder = buildBranch(simpleBuilder, flowBuilder, allMatch,stepsMap,true,sb);
				}else{
					flowBuilder = buildBranch(simpleBuilder, flowBuilder, allMatch,stepsMap,false,sb);
				}
			}else{
				Iterator<String> keys = branchMap.keySet().iterator();
				while(keys.hasNext()){
					MapreduceBatchJobStepBranch br = branchMap.get(keys.next());
					if(i==0){
						flowBuilder = buildBranch(simpleBuilder, flowBuilder, br,stepsMap,true,sb);
					}else{
						flowBuilder = buildBranch(simpleBuilder, flowBuilder, br,stepsMap,false,sb);
					}
				}
			}
		}
		return flowBuilder;
	}
	
	private static FlowBuilder<FlowJobBuilder> buildBranch(SimpleJobBuilder simpleBuilder, FlowBuilder<FlowJobBuilder> flowBuilder, 
			MapreduceBatchJobStepBranch br,Map<Long,Step> stepsMap,boolean isFirst,StringBuffer sb ) throws Exception{
		if(SysConstants.BATCH_JOB_STEP_BRANCH_BRANCH_TYPE_NEXT.equals(br.getBranchType())){
			if(flowBuilder!=null){
				if(isFirst){
					sb.append(".on(").append(br.getStepStatus()).append(")");
					sb.append(".to(").append(stepsMap.get(br.getToStepId()).getName()).append(")");
					return flowBuilder.on(br.getStepStatus()).to(stepsMap.get(br.getToStepId()));
				}else{
					sb.append(".from(").append(stepsMap.get(br.getStepId()).getName()).append(")");
					sb.append(".on(").append(br.getStepStatus()).append(")");
					sb.append(".to(").append(stepsMap.get(br.getToStepId()).getName()).append(")");
					return flowBuilder.from(stepsMap.get(br.getStepId())).on(br.getStepStatus()).to(stepsMap.get(br.getToStepId()));
				}
			}else{
				sb.append(".on(").append(br.getStepStatus()).append(")");
				sb.append(".to(").append(stepsMap.get(br.getToStepId()).getName()).append(")");
				return simpleBuilder.on(br.getStepStatus()).to(stepsMap.get(br.getToStepId()));
			}
		}else if(SysConstants.BATCH_JOB_STEP_BRANCH_BRANCH_TYPE_END.equals(br.getBranchType())){
			if(flowBuilder!=null){
				if(isFirst){
					sb.append(".on(").append(br.getStepStatus()).append(")");
					sb.append(".end()");
					return flowBuilder.on(br.getStepStatus()).end();
				}else{
					sb.append(".from(").append(stepsMap.get(br.getStepId()).getName()).append(")");
					sb.append(".on(").append(br.getStepStatus()).append(")");
					sb.append(".end()");
					return flowBuilder.from(stepsMap.get(br.getStepId())).on(br.getStepStatus()).end();
				}
			}else{
				sb.append(".on(").append(br.getStepStatus()).append(")");
				sb.append(".end()");
				return simpleBuilder.on(br.getStepStatus()).end();
			}
		}else if(SysConstants.BATCH_JOB_STEP_BRANCH_BRANCH_TYPE_FAIL.equals(br.getBranchType())){
			if(flowBuilder!=null){
				if(isFirst){
					sb.append(".on(").append(br.getStepStatus()).append(")");
					sb.append(".fail()");
					return flowBuilder.on(br.getStepStatus()).fail();
				}else{
					sb.append(".from(").append(stepsMap.get(br.getStepId()).getName()).append(")");
					sb.append(".on(").append(br.getStepStatus()).append(")");
					sb.append(".fail()");
					return flowBuilder.from(stepsMap.get(br.getStepId())).on(br.getStepStatus()).fail();
				}
			}else{
				sb.append(".on(").append(br.getStepStatus()).append(")");
				sb.append(".fail()");
				return simpleBuilder.on(br.getStepStatus()).fail();
			}
		}else if(SysConstants.BATCH_JOB_STEP_BRANCH_BRANCH_TYPE_STOP.equals(br.getBranchType())){
			if(flowBuilder!=null){
				if(isFirst){
					sb.append(".on(").append(br.getStepStatus()).append(")");
					sb.append(".stop()");
					return flowBuilder.on(br.getStepStatus()).stop();
				}else{
					sb.append(".from(").append(stepsMap.get(br.getStepId()).getName()).append(")");
					sb.append(".on(").append(br.getStepStatus()).append(")");
					sb.append(".stop()");
					return flowBuilder.from(stepsMap.get(br.getStepId())).on(br.getStepStatus()).stop();
				}
			}else{
				sb.append(".on(").append(br.getStepStatus()).append(")");
				sb.append(".stop()");
				return simpleBuilder.on(br.getStepStatus()).stop();
			}
		}else{
			throw new Exception("not supported branch type");
		}
	}
	
	private static Step buildStep(MapreduceBatchJobStep batchStep,MapreduceBatchContext context){
		MapreduceBatchJobStepInstance instance = context.getBatchStep(batchStep.getId());
		Step step = stepBuilderFactory.get(batchStep.getStepCode()).
				tasklet(new MapReduceRunTasklet(RunnerBuilder.buildMapreduceRunner(batchStep,instance))).
				listener(new MapreduceStepExecutionListener(instance)).build();
		return step;
	}
	
	private static Map<String,MapreduceBatchJobStepBranch> getStepBranchMap(MapreduceBatchJobStep batchStep){
		Map<String,MapreduceBatchJobStepBranch> branchMap = new HashMap<String,MapreduceBatchJobStepBranch>();
		List<MapreduceBatchJobStepBranch> branchs = jobBranchService.findByStepId(batchStep.getId());
		for(int i=0;branchs!=null && i<branchs.size();i++){
			MapreduceBatchJobStepBranch branch = branchs.get(i);
			branchMap.put(branch.getStepStatus(), branch);
		}
		return branchMap;
	}

}
