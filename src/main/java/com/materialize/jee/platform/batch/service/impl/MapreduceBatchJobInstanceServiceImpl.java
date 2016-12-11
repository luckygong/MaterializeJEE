package com.materialize.jee.platform.batch.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJobInstance;
import com.materialize.jee.platform.batch.mapper.MapreduceBatchJobInstanceMapper;
import com.materialize.jee.platform.batch.service.MapreduceBatchJobInstanceService;

@Service("mapreduceBatchJobInstance")
public class MapreduceBatchJobInstanceServiceImpl implements MapreduceBatchJobInstanceService, Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(MapreduceBatchJobInstanceServiceImpl.class);
	private static final long serialVersionUID = 1L;
	
	@Resource
	private MapreduceBatchJobInstanceMapper mapreduceBatchJobInstanceMapper;
	
	/**
     * 删除
     */
	@Override
    public int deleteByPrimaryKey(Long id){
    	LOG.debug("删除ID："+id);
    	return mapreduceBatchJobInstanceMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
	@Override
    public int insert(MapreduceBatchJobInstance record){
    	return mapreduceBatchJobInstanceMapper.insert(record);
    }

    /**
     * 查询特定记录
     */
	@Override
    public MapreduceBatchJobInstance selectByPrimaryKey(Long id){
    	return mapreduceBatchJobInstanceMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询
     */
	@Override
    public List<MapreduceBatchJobInstance> findBy(Map<String,Object> params){
    	return mapreduceBatchJobInstanceMapper.findBy(params);
    }
	
	/**
     * 查询指定记录数
     */
    public Integer findCountBy(Map<String,Object> params){
    	return mapreduceBatchJobInstanceMapper.findCountBy(params);
    }
    
    /**
     * 修改
     */
	@Override
    public int updateByPrimaryKey(MapreduceBatchJobInstance record){
    	return mapreduceBatchJobInstanceMapper.updateByPrimaryKey(record);
    }
    
    /**
     * 修改
     */
	@Override
    public int updateSelectiveByPrimaryKey(MapreduceBatchJobInstance record){
    	return mapreduceBatchJobInstanceMapper.updateSelectiveByPrimaryKey(record);
    }
    
}