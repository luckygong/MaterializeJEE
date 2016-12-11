package com.materialize.jee.platform.batch.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStep;
import com.materialize.jee.platform.batch.mapper.MapreduceBatchJobStepMapper;
import com.materialize.jee.platform.batch.service.MapreduceBatchJobStepService;

@Service("mapreduceBatchJobStepService")
public class MapreduceBatchJobStepServiceImpl implements MapreduceBatchJobStepService, Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(MapreduceBatchJobStepServiceImpl.class);
	private static final long serialVersionUID = 1L;
	
	@Resource
	private MapreduceBatchJobStepMapper mapreduceBatchJobStepMapper;
	
	/**
     * 删除
     */
	@Override
    public int deleteByPrimaryKey(Long id){
    	LOG.debug("删除ID："+id);
    	return mapreduceBatchJobStepMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
	@Override
    public int insert(MapreduceBatchJobStep record){
    	return mapreduceBatchJobStepMapper.insert(record);
    }

    /**
     * 查询特定记录
     */
	@Override
    public MapreduceBatchJobStep selectByPrimaryKey(Long id){
    	return mapreduceBatchJobStepMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询
     */
	@Override
    public List<MapreduceBatchJobStep> findBy(Map<String,Object> params){
    	return mapreduceBatchJobStepMapper.findBy(params);
    }
	
	/**
     * 查询指定记录数
     */
    public Integer findCountBy(Map<String,Object> params){
    	return mapreduceBatchJobStepMapper.findCountBy(params);
    }
    
    /**
     * 修改
     */
	@Override
    public int updateByPrimaryKey(MapreduceBatchJobStep record){
    	return mapreduceBatchJobStepMapper.updateByPrimaryKey(record);
    }
    
    /**
     * 修改
     */
	@Override
    public int updateSelectiveByPrimaryKey(MapreduceBatchJobStep record){
    	return mapreduceBatchJobStepMapper.updateSelectiveByPrimaryKey(record);
    }
    
}