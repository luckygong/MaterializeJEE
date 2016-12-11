package com.materialize.jee.platform.batch.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.materialize.jee.platform.hadoop.job.main.MapReduceRunner;

public class MapReduceRunTasklet implements Tasklet {
	private static Logger logger = LoggerFactory.getLogger(MapReduceRunTasklet.class);
	
	private MapReduceRunner mapReduceRunner;
	
	public MapReduceRunTasklet(MapReduceRunner mapReduceRunner){
		this.mapReduceRunner = mapReduceRunner;
	}

	@Override
	public RepeatStatus execute(StepContribution paramStepContribution, ChunkContext paramChunkContext)
			throws Exception {
		try {
			int exitCode = mapReduceRunner.run();
			logger.info("tasklet run with exitCode "+exitCode+" :"+mapReduceRunner.getJobContext().getJarFile());
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return RepeatStatus.FINISHED;
	}

}
