package com.materialize.jee.platform.quartz.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.materialize.jee.platform.quartz.domain.QuartzJob;

@Repository
public interface QuartzJobMapper {
	/**
     * 删除
     */
    int deleteByPrimaryKey(@Param(value = "id")Long id);

    /**
     * 新增
     */
    int insert(QuartzJob record);

    /**
     * 查询特定记录
     */
    QuartzJob selectByPrimaryKey(@Param(value = "id")Long id);

    /**
     * 查询全部可用记录
     */
    List<QuartzJob> selectAllInUse();
    
    /**
     * 查询指定记录
     */
    List<QuartzJob> findBy(Map<String,Object> params);
    /**
     * 查询指定记录数
     */
    Integer findCountBy(Map<String,Object> params);
    
    /**
     * 修改
     */
    int updateByPrimaryKey(QuartzJob record);
    
    /**
     * 修改
     */
    int updateSelectiveByPrimaryKey(QuartzJob record);
}