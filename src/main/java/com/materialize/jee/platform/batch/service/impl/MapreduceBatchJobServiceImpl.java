package com.materialize.jee.platform.batch.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJob;
import com.materialize.jee.platform.batch.mapper.MapreduceBatchJobMapper;
import com.materialize.jee.platform.batch.service.MapreduceBatchJobService;

@Service("mapreduceBatchJobService")
public class MapreduceBatchJobServiceImpl implements MapreduceBatchJobService, Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(MapreduceBatchJobServiceImpl.class);
	private static final long serialVersionUID = 1L;
	
	@Resource
	private MapreduceBatchJobMapper mapreduceBatchJobMapper;
	
	/**
     * 删除
     */
	@Override
    public int deleteByPrimaryKey(Long id){
    	LOG.debug("删除ID："+id);
    	return mapreduceBatchJobMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
	@Override
    public int insert(MapreduceBatchJob record){
    	return mapreduceBatchJobMapper.insert(record);
    }

    /**
     * 查询特定记录
     */
	@Override
    public MapreduceBatchJob selectByPrimaryKey(Long id){
    	return mapreduceBatchJobMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询
     */
	@Override
    public List<MapreduceBatchJob> findBy(Map<String,Object> params){
    	return mapreduceBatchJobMapper.findBy(params);
    }
	
	/**
     * 查询指定记录数
     */
    public Integer findCountBy(Map<String,Object> params){
    	return mapreduceBatchJobMapper.findCountBy(params);
    }
    
    /**
     * 修改
     */
	@Override
    public int updateByPrimaryKey(MapreduceBatchJob record){
    	return mapreduceBatchJobMapper.updateByPrimaryKey(record);
    }
    
    /**
     * 修改
     */
	@Override
    public int updateSelectiveByPrimaryKey(MapreduceBatchJob record){
    	return mapreduceBatchJobMapper.updateSelectiveByPrimaryKey(record);
    }
    
}