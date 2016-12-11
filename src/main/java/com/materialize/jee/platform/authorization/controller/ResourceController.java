package com.materialize.jee.platform.authorization.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.materialize.jee.platform.SysConstants;
import com.materialize.jee.platform.authorization.domain.Resource;
import com.materialize.jee.platform.authorization.service.ResourceService;
import com.materialize.jee.platform.base.BaseController;
import com.materialize.jee.platform.base.JsonResponseModel;
import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;
import com.materialize.jee.platform.utils.RequestUtils;

@Controller  
@RequestMapping(value="/admin/resource",produces = "application/json")
public class ResourceController extends BaseController{
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value = "/resourceList") 
	public @ResponseBody JsonResponseModel resourceList(HttpServletRequest request, 
			HttpServletResponse response) {  
		
		Pagination pagination = RequestUtils.buildPagination(request);
		Page<Resource> page = resourceService.findPage(pagination);
		
		JsonResponseModel model = createJsonResponse(pagination,page);
		return model;
	}  
	
	@RequestMapping(value = "/resourceTree") 
	public @ResponseBody JsonResponseModel resourceTree(HttpServletRequest request, 
			HttpServletResponse response) {  
		
		Map<String,Object> params = RequestUtils.getRequestParamMap(request);
		List<Resource> all = resourceService.find(params);
		List<Resource> result = new ArrayList<Resource>();
		result.addAll(all);
		if(params.size()>0 && all!=null && all.size()>0){
			for(Resource r:all){
				result.addAll(findAllChildren(r));
			}
		}
		List<Resource> tree = resourceService.createMenuTree(result);
		
		JsonResponseModel model = new JsonResponseModel();
		model.setData(tree);
		return model;
	} 
	
	private List<Resource> findAllChildren(Resource resource){
		List<Resource> result = new ArrayList<Resource>();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentId", resource.getId());
		params.put("activeFlag", 1);
		List<Resource> data = resourceService.find(params);
		if(data!=null && data.size()>0){
			result.addAll(data);
			for(Resource r:data){
				if(r.getIsDirectory()==SysConstants.MENU_IS_DIRECTORY_YES){
					result.addAll(findAllChildren(r));
				}
			}
		}
		return result;
	}

}
