package com.materialize.jee.platform.batch.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStepInstance;
import com.materialize.jee.platform.batch.mapper.MapreduceBatchJobStepInstanceMapper;
import com.materialize.jee.platform.batch.service.MapreduceBatchJobStepInstanceService;

@Service("mapreduceBatchJobStepInstance")
public class MapreduceBatchJobStepInstanceServiceImpl implements MapreduceBatchJobStepInstanceService, Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(MapreduceBatchJobStepInstanceServiceImpl.class);
	private static final long serialVersionUID = 1L;
	
	@Resource
	private MapreduceBatchJobStepInstanceMapper mapreduceBatchJobStepInstanceMapper;
	
	/**
     * 删除
     */
	@Override
    public int deleteByPrimaryKey(Long id){
    	LOG.debug("删除ID："+id);
    	return mapreduceBatchJobStepInstanceMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
	@Override
    public int insert(MapreduceBatchJobStepInstance record){
    	return mapreduceBatchJobStepInstanceMapper.insert(record);
    }

    /**
     * 查询特定记录
     */
	@Override
    public MapreduceBatchJobStepInstance selectByPrimaryKey(Long id){
    	return mapreduceBatchJobStepInstanceMapper.selectByPrimaryKey(id);
    }
	
	/**
	 * 查询特定记录
	 */
	@Override
	public MapreduceBatchJobStepInstance selectByStepId(Long stepId){
		return mapreduceBatchJobStepInstanceMapper.selectByStepId(stepId);
	}

    /**
     * 查询
     */
	@Override
    public List<MapreduceBatchJobStepInstance> findBy(Map<String,Object> params){
    	return mapreduceBatchJobStepInstanceMapper.findBy(params);
    }
	
	/**
     * 查询指定记录数
     */
    public Integer findCountBy(Map<String,Object> params){
    	return mapreduceBatchJobStepInstanceMapper.findCountBy(params);
    }
    
    /**
     * 修改
     */
	@Override
    public int updateByPrimaryKey(MapreduceBatchJobStepInstance record){
    	return mapreduceBatchJobStepInstanceMapper.updateByPrimaryKey(record);
    }
    
    /**
     * 修改
     */
	@Override
    public int updateSelectiveByPrimaryKey(MapreduceBatchJobStepInstance record){
    	return mapreduceBatchJobStepInstanceMapper.updateSelectiveByPrimaryKey(record);
    }
    
}