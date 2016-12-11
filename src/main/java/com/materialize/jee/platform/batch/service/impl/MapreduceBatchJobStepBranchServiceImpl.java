package com.materialize.jee.platform.batch.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.materialize.jee.platform.batch.domain.MapreduceBatchJobStepBranch;
import com.materialize.jee.platform.batch.mapper.MapreduceBatchJobStepBranchMapper;
import com.materialize.jee.platform.batch.service.MapreduceBatchJobStepBranchService;

@Service("mapreduceBatchJobStepBranch")
public class MapreduceBatchJobStepBranchServiceImpl implements MapreduceBatchJobStepBranchService, Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(MapreduceBatchJobStepBranchServiceImpl.class);
	private static final long serialVersionUID = 1L;
	
	@Resource
	private MapreduceBatchJobStepBranchMapper mapreduceBatchJobStepBranchMapper;
	
	/**
     * 删除
     */
	@Override
    public int deleteByPrimaryKey(Long id){
    	LOG.debug("删除ID："+id);
    	return mapreduceBatchJobStepBranchMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增
     */
	@Override
    public int insert(MapreduceBatchJobStepBranch record){
    	return mapreduceBatchJobStepBranchMapper.insert(record);
    }

    /**
     * 查询特定记录
     */
	@Override
    public MapreduceBatchJobStepBranch selectByPrimaryKey(Long id){
    	return mapreduceBatchJobStepBranchMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询
     */
	@Override
    public List<MapreduceBatchJobStepBranch> findByStepId(Long stepId){
    	return mapreduceBatchJobStepBranchMapper.findByStepId(stepId);
    }
	
	/**
	 * 查询
	 */
	@Override
	public List<MapreduceBatchJobStepBranch> findBy(Map<String,Object> params){
		return mapreduceBatchJobStepBranchMapper.findBy(params);
	}
	
	/**
     * 查询指定记录数
     */
    public Integer findCountBy(Map<String,Object> params){
    	return mapreduceBatchJobStepBranchMapper.findCountBy(params);
    }
    
    /**
     * 修改
     */
	@Override
    public int updateByPrimaryKey(MapreduceBatchJobStepBranch record){
    	return mapreduceBatchJobStepBranchMapper.updateByPrimaryKey(record);
    }
    
    /**
     * 修改
     */
	@Override
    public int updateSelectiveByPrimaryKey(MapreduceBatchJobStepBranch record){
    	return mapreduceBatchJobStepBranchMapper.updateSelectiveByPrimaryKey(record);
    }
    
}