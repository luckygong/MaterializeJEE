/**
 * @Company：
 * @Title: DataDicServiceImpl.java
 * @Description：描述
 * @Author： zhaosk
 * @Date：2016-09-28 03:58:15
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.datadic.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.materialize.jee.platform.base.DefaultBaseService;
import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;
import com.materialize.jee.platform.datadic.domain.DataDic;
import com.materialize.jee.platform.datadic.mapper.DataDicMapper;
import com.materialize.jee.platform.datadic.service.DataDicService;
import com.materialize.jee.platform.utils.CacheUtils;

/**
 * @ClassName: DataDicServiceImpl
 * @Description: TODO(DataDicServiceImpl service层)
 */
@Service
public class DataDicServiceImpl extends DefaultBaseService implements DataDicService {
	private static Log log = LogFactory.getLog(DataDicServiceImpl.class);
	@javax.annotation.Resource
	private DataDicMapper dataDicMapper;
	
	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	public Integer save(DataDic dataDic){
		return dataDicMapper.save(dataDic);
	}

	/**
	 * @Title: update
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	public Integer update(DataDic dataDic){
		return dataDicMapper.update(dataDic);
	}
	
	/**
	 * @Title: updateSelective
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	public Integer updateSelective(DataDic dataDic){
		return dataDicMapper.updateSelective(dataDic);
	}

	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	public Integer delete(java.lang.Long id){
		DataDic dataDic = this.get(id);
		return dataDicMapper.delete(dataDic);
	}

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	public DataDic get(java.lang.Long id){
		return dataDicMapper.get(id);
	}

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	public List<DataDic> find(Map<String, Object> entityMap){
		return dataDicMapper.find(entityMap);
	}
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pageNo 页号
	 * @param pageSize 每页记录数
	 * @param entityMap 查询条件
	 * @return 返回根据条件查询的数据
	 */
	public Page<DataDic> findPage(Integer pageNo, Integer pageSize,Map<String,Object> entityMap){
		Pagination pagination = new Pagination(pageNo, pageSize, entityMap);
		return findPage(pagination);
	}
	
	/**
	 * @Title: findPage
	 * @param pagination 分页信息
	 * @return 返回根据条件查询的数据
	 */
	public Page<DataDic> findPage(Pagination pagination){
		Page<DataDic> page = (Page<DataDic>)dataDicMapper.find(pagination);
		return page;
	}

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	public Integer findCount(Map<String, Object> entityMap){
		return dataDicMapper.findCount(entityMap);
	}
	
	/**
     * 查询指定字典项
     */
	@Override
    public List<DataDic> selectByCategory(String category){
    	return dataDicMapper.selectByCategory(category);
    }
    
    /**
     * 查询指定字典项下指定字典值
     */
	@Override
    public DataDic selectByCategoryAndCode(String category,String code){
    	return dataDicMapper.selectByCategoryAndCode(category, code);
    }
	
	/**
     * 查询全部可用记录
     */
	@Override
    public List<DataDic> selectAllInUse(){
    	return dataDicMapper.selectAllInUse();
    }
	
	/**
     * 加载全部数据字典到缓存
     */
    public void loadDataDicToCache() throws Exception{
    	List<DataDic> dics = this.selectAllInUse();
    	if(dics==null || dics.size()==0){
    		return;
    	}
    	String preCategory = null;
    	int i=0;
    	List<DataDic> cacheList = null;
    	while(i<dics.size()){
    		DataDic dic = dics.get(i);
    		String category = dic.getCategory();
    		if(!category.equals(preCategory)){
    			if(StringUtils.isNotBlank(preCategory)){
    				CacheUtils.putCacheInfo(CacheUtils.EHCACHE_DATADIC_CONF_NAME, CacheUtils.CACHE_DATADIC_NAME_PIX+preCategory, cacheList);
    			}
    			preCategory = category;
    			cacheList = new ArrayList<DataDic>();
    			cacheList.add(dic);
    		}else{
    			cacheList.add(dic);
    			if(i==dics.size()-1){
    				CacheUtils.putCacheInfo(CacheUtils.EHCACHE_DATADIC_CONF_NAME, CacheUtils.CACHE_DATADIC_NAME_PIX+preCategory, cacheList);
    			}
    		}
    		i++;
    	}
    	log.info("加载数据字典成功");
    }
}
