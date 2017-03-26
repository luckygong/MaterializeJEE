package com.materialize.jee.platform.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;
import com.materialize.jee.platform.hbase.phoenix.PhoenixPage;
import com.materialize.jee.platform.hbase.phoenix.PhoenixPagination;
import com.materialize.jee.platform.utils.ReflectionUtils;

public class BaseController {
	
	private String viewPrefix;

    protected BaseController() {
        setViewPrefix(defaultViewPrefix());
    }
	
	/**
     * 当前模块 视图的前缀
     * 默认
     * 1、获取当前类头上的@RequestMapping中的value作为前缀
     * 2、如果没有就使用当前模型小写的简单类名
     */
    public void setViewPrefix(String viewPrefix) {
        if (viewPrefix.startsWith("/")) {
            viewPrefix = viewPrefix.substring(1);
        }
        this.viewPrefix = viewPrefix;
    }

    public String getViewPrefix() {
        return viewPrefix;
    }

    /**
     * 获取视图名称：即prefixViewName + "/" + suffixName
     */
    public String viewName(String suffixName) {
        if (!suffixName.startsWith("/")) {
            suffixName = "/" + suffixName;
        }
        return getViewPrefix() + suffixName;
    }


    /**
     * @param backURL null 将重定向到默认getViewPrefix()
     */
    protected String redirectToUrl(String backURL) {
        if (StringUtils.isEmpty(backURL)) {
            backURL = getViewPrefix();
        }
        if (!backURL.startsWith("/") && !backURL.startsWith("http")) {
            backURL = "/" + backURL;
        }
        return "redirect:" + backURL;
    }
    /**
     * 获取当前类头上的@RequestMapping中的value作为前缀
     */
    protected String defaultViewPrefix() {
        String currentViewPrefix = "";
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            currentViewPrefix = requestMapping.value()[0];
        }
        return currentViewPrefix;
    }
	
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
	
	@SuppressWarnings("unchecked")
	protected <ID,T> List<ZTreeNode<ID>> convertToZtreeList(List<T> datas, String idField, String pidField, 
			String[] nameFields, String iconField, List<ID> selectIds){
		List<ZTreeNode<ID>> result = new ArrayList<ZTreeNode<ID>>();
		if(datas==null || datas.size()==0){
			return result;
		}
		
		Boolean pidIsObject = pidField.indexOf(".")>0;
		String[] pidPath = pidField.split("\\.");
		
		for (T data : datas) {
			ZTreeNode<ID> node = new ZTreeNode<ID>();
			//id
			ID id = (ID)ReflectionUtils.invokeGetterMethod(data,idField);
			node.setId(id);
			
			//pid
			Object temObj = data;
			if(!pidIsObject){
				node.setpId((ID)ReflectionUtils.invokeGetterMethod(data,pidField));
			}else{
				for(int i=0;i<pidPath.length;i++){
					if(i==pidPath.length-1){
						if(temObj!=null){
							node.setpId((ID)ReflectionUtils.invokeGetterMethod(temObj,pidPath[i]));
						}
					}else{
						temObj = ReflectionUtils.invokeGetterMethod(temObj,pidPath[i]);
					}
				}
			}
			
			//name
			String name = "";
			for(String field:nameFields){
				name+=" "+(ID)ReflectionUtils.invokeGetterMethod(data,field);
			}
			node.setName(name.length()==0?name:name.substring(1));
			
			//icon
			if(!StringUtils.isEmpty(iconField)){
				node.setIcon((String)ReflectionUtils.invokeGetterMethod(data,pidField));
			}
			
			//checked
			if(selectIds!=null && selectIds.size()>0){
				node.setChecked(selectIds.contains(id));
			}
			
			result.add(node);
		}
		return result;
	}
}
