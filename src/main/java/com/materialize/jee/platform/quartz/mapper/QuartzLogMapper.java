package com.materialize.jee.platform.quartz.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.materialize.jee.platform.quartz.domain.QuartzLog;

@Repository
public interface QuartzLogMapper {
	/**
     * 删除
     */
    int deleteByPrimaryKey(@Param(value = "id")Long id);

    /**
     * 新增
     */
    int insert(QuartzLog record);

    /**
     * 查询特定记录
     */
    QuartzLog selectByPrimaryKey(@Param(value = "id")Long id);

    /**
     * 查询全部可用记录
     */
    List<QuartzLog> selectAll();
    
    /**
     * 查询指定记录
     */
    List<QuartzLog> findBy(Map<String,Object> params);
    /**
     * 查询指定记录数
     */
    Integer findCountBy(Map<String,Object> params);
    
    /**
     * 修改
     */
    int updateByPrimaryKey(QuartzLog record);
    
    /**
     * 修改
     */
    int updateSelectiveByPrimaryKey(QuartzLog record);
}