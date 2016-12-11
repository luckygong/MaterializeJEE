package com.materialize.jee.platform.batch.service;

import java.util.List;
import java.util.Map;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJobInstance;

public interface MapreduceBatchJobInstanceService {
	/**
     * 删除
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增
     */
    int insert(MapreduceBatchJobInstance record);

    /**
     * 查询特定记录
     */
    MapreduceBatchJobInstance selectByPrimaryKey(Long id);

    /**
     * 查询指定记录
     */
    List<MapreduceBatchJobInstance> findBy(Map<String,Object> params);
    
    /**
     * 查询指定记录数
     */
    Integer findCountBy(Map<String,Object> params);
    
    /**
     * 修改
     */
    int updateByPrimaryKey(MapreduceBatchJobInstance record);
    
    /**
     * 修改
     */
    int updateSelectiveByPrimaryKey(MapreduceBatchJobInstance record);
}
