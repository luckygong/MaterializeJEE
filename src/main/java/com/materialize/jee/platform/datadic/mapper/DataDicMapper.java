/**
 * @Company：
 * @Title: DataDicMapper.java
 * @Description：描述
 * @Author： zhaosk
 * @Date：2016-09-28 03:58:15
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.datadic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.materialize.jee.platform.base.page.Pagination;
import com.materialize.jee.platform.datadic.domain.DataDic;

/**
 * @ClassName: DataDicMapper
 * @Description: TODO(DataDicMapper DAO层)
 */
@Repository
public interface DataDicMapper {

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
	Integer delete(DataDic dataDic);

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	DataDic get(@Param(value="id") java.lang.Long id);

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	List<DataDic> find(Map<String, Object> entityMap);
	
	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pagination
	 * @return 返回根据条件查询的数据
	 */
	List<DataDic> find(Pagination pagination);

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
    List<DataDic> selectByCategory(@Param(value = "category")String category);
    
    /**
     * 查询指定字典项下指定字典值
     */
    DataDic selectByCategoryAndCode(@Param(value = "category")String category,@Param(value = "code")String code);
    
    /**
     * 查询全部可用记录
     */
    List<DataDic> selectAllInUse();
}
