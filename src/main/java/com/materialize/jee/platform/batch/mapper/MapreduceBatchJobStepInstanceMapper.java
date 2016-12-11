package com.materialize.jee.platform.batch.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStepInstance;

@Repository
public interface MapreduceBatchJobStepInstanceMapper {
	/**
     * 删除
     */
    int deleteByPrimaryKey(@Param(value = "id")Long id);

    /**
     * 新增
     */
    int insert(MapreduceBatchJobStepInstance record);

    /**
     * 查询特定记录
     */
    MapreduceBatchJobStepInstance selectByPrimaryKey(@Param(value = "id")Long id);
    
    /**
     * 查询特定记录
     */
    MapreduceBatchJobStepInstance selectByStepId(@Param(value = "stepId")Long stepId);

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