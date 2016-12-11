package com.materialize.jee.platform.utils;

import java.util.List;

import org.apache.log4j.Logger;

import com.materialize.jee.platform.datadic.domain.DataDic;
import com.materialize.jee.platform.datadic.service.DataDicService;

public class DataDicUtils {
	private static Logger log = Logger.getLogger(DataDicUtils.class);

	/**  
	 * @Description: 加载全部数据字典
	 * @param       
	 * @return void    
	 * @throws  
	 */
	public static void loadAllDataDic() throws Exception{
		DataDicService dataDicService = ContextUtils.getBean(DataDicService.class);
		dataDicService.loadDataDicToCache();
		log.info("数据字典加载成功");
	}
	
	/**  
	 * @Description: 根据category查询数据字典
	 * @param @param category
	 * @throws  
	 */
	@SuppressWarnings("unchecked")
	public static List<DataDic> getDataDicsByCategory(String category){
		Object object;
		try {
			object = CacheUtils.getCacheInfo(CacheUtils.EHCACHE_DATADIC_CONF_NAME, CacheUtils.CACHE_DATADIC_NAME_PIX+category);
			if(object!=null){
				return (List<DataDic>)object;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**  
	 * @Description: 根据category,code查询数据字典
	 * @param category
	 * @param code
	 * @throws  
	 */
	@SuppressWarnings("unchecked")
	public static String getDataDicTranslation(String category, String code){
		Object object;
		try {
			object = CacheUtils.getCacheInfo(CacheUtils.EHCACHE_DATADIC_CONF_NAME, CacheUtils.CACHE_DATADIC_NAME_PIX+category);
			if(object!=null){
				List<DataDic> dics = (List<DataDic>)object;
				for(DataDic dic : dics){
					if(dic.getCode().equals(code)){
						return dic.getTranslation();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
