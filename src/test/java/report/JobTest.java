package report;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJob;
import com.materialize.jee.platform.batch.domain.MapreduceBatchJobInstance;
import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStepInstance;
import com.materialize.jee.platform.batch.service.MapreduceBatchJobInstanceService;
import com.materialize.jee.platform.batch.service.MapreduceBatchJobService;
import com.materialize.jee.platform.batch.service.MapreduceBatchJobStepInstanceService;
import com.materialize.jee.platform.batch.tools.JobBuilder;
import com.materialize.jee.platform.batch.tools.MapreduceBatchContext;
import com.materialize.jee.platform.utils.ContextUtils;

public class JobTest {
	private static ApplicationContext context;
	
	public static void main(String[] args) throws Exception {
		context = new ClassPathXmlApplicationContext("spring/spring-context-common.xml");
		ContextUtils.setContext(context);
		
		MapreduceBatchJobService jobService = ContextUtils.getBean(MapreduceBatchJobService.class);
		MapreduceBatchJobInstanceService jobInstanceService = ContextUtils.getBean(MapreduceBatchJobInstanceService.class);
		MapreduceBatchJobStepInstanceService stepInstanceService = ContextUtils.getBean(MapreduceBatchJobStepInstanceService.class);
		
		MapreduceBatchContext batchContext = new MapreduceBatchContext();
		MapreduceBatchJob batchJob = jobService.selectByPrimaryKey(2L);
		MapreduceBatchJobInstance jobInstance = jobInstanceService.selectByPrimaryKey(1L);
		batchContext.setBatchJob(batchJob);
		batchContext.setBatchJobInstance(jobInstance);
		MapreduceBatchJobStepInstance step1 = stepInstanceService.selectByPrimaryKey(4L);
		MapreduceBatchJobStepInstance step2 = stepInstanceService.selectByPrimaryKey(5L);
		MapreduceBatchJobStepInstance step3 = stepInstanceService.selectByPrimaryKey(6L);
		MapreduceBatchJobStepInstance step4 = stepInstanceService.selectByPrimaryKey(7L);
		MapreduceBatchJobStepInstance step5 = stepInstanceService.selectByPrimaryKey(8L);
		batchContext.putBatchStep(4L, step1);
		batchContext.putBatchStep(5L, step2);
		batchContext.putBatchStep(6L, step3);
		batchContext.putBatchStep(7L, step4);
		batchContext.putBatchStep(8L, step5);
		
		Job job = JobBuilder.buildJob(batchContext);
		JobLauncher jobLauncher = context.getBean(JobLauncher.class);
		JobExecution result = jobLauncher.run(job, new JobParameters());
		System.out.println(result.toString());
	}
}
