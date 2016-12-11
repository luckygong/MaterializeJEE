package com.materialize.jee.platform.hadoop.job.tools;

public interface JobRunner {

	Integer run() throws Exception;
	
	void cancel();
	
	JobContext getJobContext();
	
	boolean isCanceled();
	
}
