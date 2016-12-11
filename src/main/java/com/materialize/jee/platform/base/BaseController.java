package com.materialize.jee.platform.base;

import java.util.HashMap;
import java.util.Map;

import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;
import com.materialize.jee.platform.hbase.phoenix.PhoenixPage;
import com.materialize.jee.platform.hbase.phoenix.PhoenixPagination;
import com.materialize.jee.platform.utils.ReflectionUtils;

public class BaseController {
	protected <T> JsonResponseModel createJsonResponse(Pagination pagination,Page<T> page){
		JsonResponseModel model = new JsonResponseModel();
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("currentPage",page.getCurrentPage());
		jsonMap.put("currentSize",pagination.getPageSize());
		jsonMap.put("totalCount",page.getTotalCount());
		jsonMap.put("totalPage",page.getTotalPage());
		jsonMap.put("pageData",page.getPageData());
		model.setData(jsonMap);
		return model;
	}
	
	protected <T> JsonResponseModel createJsonResponse(PhoenixPagination pagination,PhoenixPage<T> pageData){
		JsonResponseModel model = new JsonResponseModel();
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("currentPage", pagination.getPageNo());
		jsonMap.put("currentSize", pagination.getPageSize());
		jsonMap.put("currentDataSize", pageData.getCurrentDataSize());
		jsonMap.put("alreadyQueryCount", pageData.getAlreadyQueryCount());
		jsonMap.put("hasNextPage", pageData.hasNextPage());
		jsonMap.put("nextPagefirstRowKey", pageData.hasNextPage()?ReflectionUtils.getFieldValue(pageData.getNextPagefirstRow(),"rowKey"):"");
		jsonMap.put("pageData", pageData.getPageData());
		model.setData(jsonMap);
		return model;
	}
	
}
