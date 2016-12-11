package com.materialize.jee.platform.quartz.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.materialize.jee.platform.quartz.domain.QuartzLog;
import com.materialize.jee.platform.quartz.mapper.QuartzLogMapper;
import com.materialize.jee.platform.quartz.service.QuartzLogService;

@Service("quartzLog")
public class QuartzLogServiceImpl implements QuartzLogService, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Resource
	private QuartzLogMapper quartzLogMapper;
	
	/**
     * 删除
     */
	@Override
    public int deleteByPrimaryKey(Long id){
    	return quartzLogMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
	@Override
    public int insert(QuartzLog record){
    	return quartzLogMapper.insert(record);
    }

    /**
     * 查询特定记录
     */
	@Override
    public QuartzLog selectByPrimaryKey(Long id){
    	return quartzLogMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询全部可用记录
     */
	@Override
    public List<QuartzLog> selectAll(){
    	return quartzLogMapper.selectAll();
    }
    
    /**
     * 查询
     */
	@Override
    public List<QuartzLog> findBy(Map<String,Object> params){
    	return quartzLogMapper.findBy(params);
    }
	
	/**
     * 查询指定记录数
     */
    public Integer findCountBy(Map<String,Object> params){
    	return quartzLogMapper.findCountBy(params);
    }
    
    /**
     * 修改
     */
	@Override
    public int updateByPrimaryKey(QuartzLog record){
    	return quartzLogMapper.updateByPrimaryKey(record);
    }
    
    /**
     * 修改
     */
	@Override
    public int updateSelectiveByPrimaryKey(QuartzLog record){
    	return quartzLogMapper.updateSelectiveByPrimaryKey(record);
    }
    
}