package com.materialize.jee.platform.batch.service;

import java.util.List;
import java.util.Map;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStepBranch;

public interface MapreduceBatchJobStepBranchService {
	/**
     * 删除
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增
     */
    int insert(MapreduceBatchJobStepBranch record);

    /**
     * 查询特定记录
     */
    MapreduceBatchJobStepBranch selectByPrimaryKey(Long id);

    /**
     * 查询指定记录
     */
    List<MapreduceBatchJobStepBranch> findByStepId(Long stepId);
    
    /**
     * 查询指定记录
     */
    List<MapreduceBatchJobStepBranch> findBy(Map<String,Object> params);
    
    /**
     * 查询指定记录数
     */
    Integer findCountBy(Map<String,Object> params);
    
    /**
     * 修改
     */
    int updateByPrimaryKey(MapreduceBatchJobStepBranch record);
    
    /**
     * 修改
     */
    int updateSelectiveByPrimaryKey(MapreduceBatchJobStepBranch record);
}
