package com.materialize.jee.platform.batch.tools;

import java.util.HashMap;
import java.util.Map;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJob;
import com.materialize.jee.platform.batch.domain.MapreduceBatchJobInstance;
import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStepInstance;

public class MapreduceBatchContext {
	private MapreduceBatchJob batchJob;
	private MapreduceBatchJobInstance batchJobInstance;
	private Map<Long,MapreduceBatchJobStepInstance> batchStepInstanceMap = new HashMap<Long,MapreduceBatchJobStepInstance>();
	
	public MapreduceBatchJob getBatchJob() {
		return batchJob;
	}

	public void setBatchJob(MapreduceBatchJob batchJob) {
		this.batchJob = batchJob;
	}

	public MapreduceBatchJobInstance getBatchJobInstance() {
		return batchJobInstance;
	}

	public void setBatchJobInstance(MapreduceBatchJobInstance batchJobInstance) {
		this.batchJobInstance = batchJobInstance;
	}

	public Map<Long, MapreduceBatchJobStepInstance> getBatchStepInstanceMap() {
		return batchStepInstanceMap;
	}

	public void setBatchStepInstanceMap(Map<Long, MapreduceBatchJobStepInstance> batchStepInstanceMap) {
		this.batchStepInstanceMap = batchStepInstanceMap;
	}

	public void putBatchStep(Long stepId, MapreduceBatchJobStepInstance stepInstance){
		this.batchStepInstanceMap.put(stepId, stepInstance);
	}
	
	public MapreduceBatchJobStepInstance getBatchStep(Long stepId){
		return batchStepInstanceMap.get(stepId);
	}
}

