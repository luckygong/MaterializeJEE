package com.materialize.jee.platform.quartz.service;

import java.util.List;
import java.util.Map;

import com.materialize.jee.platform.quartz.domain.QuartzJob;

public interface QuartzJobService {
	/**
     * 删除
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增
     */
    int insert(QuartzJob record);

    /**
     * 查询特定记录
     */
    QuartzJob selectByPrimaryKey(Long id);

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
