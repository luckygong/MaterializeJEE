package com.materialize.jee.platform.batch.service;

import java.util.List;
import java.util.Map;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStepInstance;

public interface MapreduceBatchJobStepInstanceService {
	/**
     * 删除
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增
     */
    int insert(MapreduceBatchJobStepInstance record);

    /**
     * 查询特定记录
     */
    MapreduceBatchJobStepInstance selectByPrimaryKey(Long id);
    
    /**
     * 查询特定记录
     */
    MapreduceBatchJobStepInstance selectByStepId(Long stepId);

    /**
     * 查询指定记录
     */
    List<MapreduceBatchJobStepInstance> findBy(Map<String,Object> params);
    
    /**
     * 查询指定记录数
     */
    Integer findCountBy(Map<String,Object> params);
    
    /**
     * 修改
     */
    int updateByPrimaryKey(MapreduceBatchJobStepInstance record);
    
    /**
     * 修改
     */
    int updateSelectiveByPrimaryKey(MapreduceBatchJobStepInstance record);
}
