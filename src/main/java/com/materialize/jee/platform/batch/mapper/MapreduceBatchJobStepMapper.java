package com.materialize.jee.platform.batch.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStep;

@Repository
public interface MapreduceBatchJobStepMapper {
	/**
     * 删除
     */
    int deleteByPrimaryKey(@Param(value = "id")Long id);

    /**
     * 新增
     */
    int insert(MapreduceBatchJobStep record);

    /**
     * 查询特定记录
     */
    MapreduceBatchJobStep selectByPrimaryKey(@Param(value = "id")Long id);

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