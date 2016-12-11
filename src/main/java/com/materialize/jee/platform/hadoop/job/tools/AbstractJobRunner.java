package com.materialize.jee.platform.hadoop.job.tools;

public abstract class AbstractJobRunner implements JobRunner {

	protected JobContext jobContext;

	protected boolean canceled=false;

	public AbstractJobRunner(JobContext jobContext){
		this.jobContext=jobContext;
	}

	@Override
	public boolean isCanceled() {
		return canceled;
	}

	public JobContext getJobContext() {
		return jobContext;
	}

}