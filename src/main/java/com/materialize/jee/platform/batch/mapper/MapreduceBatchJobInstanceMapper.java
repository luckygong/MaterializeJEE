package com.materialize.jee.platform.batch.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJobInstance;

@Repository
public interface MapreduceBatchJobInstanceMapper {
	/**
     * 删除
     */
    int deleteByPrimaryKey(@Param(value = "id")Long id);

    /**
     * 新增
     */
    int insert(MapreduceBatchJobInstance record);

    /**
     * 查询特定记录
     */
    MapreduceBatchJobInstance selectByPrimaryKey(@Param(value = "id")Long id);

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