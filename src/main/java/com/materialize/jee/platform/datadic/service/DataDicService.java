/**
 * @Company：
 * @Title: DataDicService.java
 * @Description：描述
 * @Author： zhaosk
 * @Date：2016-09-28 03:58:15
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.datadic.service;

import java.util.List;
import java.util.Map;

import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;
import com.materialize.jee.platform.datadic.domain.DataDic;

/**
 * @ClassName: DataDicService
 * @Description: TODO(DataDicService service层)
 */
public interface DataDicService {

	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	Integer save(DataDic dataDic);

	/**
	 * @Title: update
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	Integer update(DataDic dataDic);
	
	/**
	 * @Title: updateSelective
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	Integer updateSelective(DataDic dataDic);

	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	Integer delete(java.lang.Long id);

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	DataDic get(java.lang.Long id);

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	List<DataDic> find(Map<String, Object> entityMap);
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pageNo 页号
	 * @param pageSize 每页记录数
	 * @param entityMap 查询条件
	 * @return 返回根据条件查询的数据
	 */
	Page<DataDic> findPage(Integer pageNo, Integer pageSize,Map<String,Object> entityMap);

	/**
	 * @Title: findPage
	 * @param pagination 分页信息
	 * @return 返回根据条件查询的数据
	 */
	Page<DataDic> findPage(Pagination pagination);

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	Integer findCount(Map<String, Object> entityMap);
	
	/**
     * 查询指定字典项
     */
    List<DataDic> selectByCategory(String category);
    
    /**
     * 查询指定字典项下指定字典值
     */
    DataDic selectByCategoryAndCode(String category,String code);
    
    /**
     * 查询全部可用记录
     */
    List<DataDic> selectAllInUse();
    
    /**
     * 加载全部数据字典到缓存
     */
    void loadDataDicToCache() throws Exception;
}
