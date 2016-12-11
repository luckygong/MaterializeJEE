package com.materialize.jee.platform.batch.service;

import java.util.List;
import java.util.Map;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJob;

public interface MapreduceBatchJobService {
	/**
     * 删除
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增
     */
    int insert(MapreduceBatchJob record);

    /**
     * 查询特定记录
     */
    MapreduceBatchJob selectByPrimaryKey(Long id);

    /**
     * 查询指定记录
     */
    List<MapreduceBatchJob> findBy(Map<String,Object> params);
    
    /**
     * 查询指定记录数
     */
    Integer findCountBy(Map<String,Object> params);
    
    /**
     * 修改
     */
    int updateByPrimaryKey(MapreduceBatchJob record);
    
    /**
     * 修改
     */
    int updateSelectiveByPrimaryKey(MapreduceBatchJob record);
}
