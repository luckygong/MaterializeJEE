package com.materialize.jee.platform.batch.service;

import java.util.List;
import java.util.Map;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStep;

public interface MapreduceBatchJobStepService {
	/**
     * 删除
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增
     */
    int insert(MapreduceBatchJobStep record);

    /**
     * 查询特定记录
     */
    MapreduceBatchJobStep selectByPrimaryKey(Long id);

    /**
     * 查询指定记录
     */
    List<MapreduceBatchJobStep> findBy(Map<String,Object> params);
    
    /**
     * 查询指定记录数
     */
    Integer findCountBy(Map<String,Object> params);
    
    /**
     * 修改
     */
    int updateByPrimaryKey(MapreduceBatchJobStep record);
    
    /**
     * 修改
     */
    int updateSelectiveByPrimaryKey(MapreduceBatchJobStep record);
}
