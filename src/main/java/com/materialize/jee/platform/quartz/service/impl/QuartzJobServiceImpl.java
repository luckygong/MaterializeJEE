package com.materialize.jee.platform.quartz.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.materialize.jee.platform.quartz.domain.QuartzJob;
import com.materialize.jee.platform.quartz.mapper.QuartzJobMapper;
import com.materialize.jee.platform.quartz.service.QuartzJobService;

@Service("quartzJob")
public class QuartzJobServiceImpl implements QuartzJobService, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Resource
	private QuartzJobMapper quartzJobMapper;
	
	/**
     * 删除
     */
	@Override
    public int deleteByPrimaryKey(Long id){
    	return quartzJobMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
	@Override
    public int insert(QuartzJob record){
    	return quartzJobMapper.insert(record);
    }

    /**
     * 查询特定记录
     */
	@Override
    public QuartzJob selectByPrimaryKey(Long id){
    	return quartzJobMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询全部可用记录
     */
	@Override
    public List<QuartzJob> selectAllInUse(){
    	return quartzJobMapper.selectAllInUse();
    }
    
    /**
     * 查询
     */
	@Override
    public List<QuartzJob> findBy(Map<String,Object> params){
    	return quartzJobMapper.findBy(params);
    }
	
	/**
     * 查询指定记录数
     */
    public Integer findCountBy(Map<String,Object> params){
    	return quartzJobMapper.findCountBy(params);
    }
    
    /**
     * 修改
     */
	@Override
    public int updateByPrimaryKey(QuartzJob record){
    	return quartzJobMapper.updateByPrimaryKey(record);
    }
    
    /**
     * 修改
     */
	@Override
    public int updateSelectiveByPrimaryKey(QuartzJob record){
    	return quartzJobMapper.updateSelectiveByPrimaryKey(record);
    }
    
}