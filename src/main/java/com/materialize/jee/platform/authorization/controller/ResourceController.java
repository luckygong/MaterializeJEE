package com.materialize.jee.platform.authorization.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.materialize.jee.platform.SysConstants;
import com.materialize.jee.platform.authorization.domain.Resource;
import com.materialize.jee.platform.authorization.service.ResourceService;
import com.materialize.jee.platform.base.BaseController;
import com.materialize.jee.platform.base.JsonResponseModel;
import com.materialize.jee.platform.base.ZTreeNode;
import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;
import com.materialize.jee.platform.utils.RequestUtils;

@Controller  
@RequestMapping(value="/admin/resource",produces = "application/json")
public class ResourceController extends BaseController{
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(method = RequestMethod.GET,value = "/resourceList")
	public String resourceList(HttpServletRequest request, Model model) {
		return viewName("resourceList");
	}
	
	@RequestMapping(value = "/listResource") 
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
	
	@RequestMapping(value = "/getResourceById", method = RequestMethod.POST) 
	public @ResponseBody JsonResponseModel getResourceById(HttpServletRequest request,HttpServletResponse response, 
			@RequestParam(value = "id", required = false) Long id) {  
		JsonResponseModel json = new JsonResponseModel();
		Resource resource = resourceService.get(id);
        json.setData(resource);
        return json;
    }  
	
	@RequestMapping(value = "/{id}") 
	public String view(@PathVariable("id")Long id, Model model) {  
		model.addAttribute("id", id);
		return viewName("resourceView");
    }  
	
	@RequestMapping(value = "/resourceCreate") 
	public String resourceCreate(HttpServletRequest request, Model model,
			@RequestParam(value = "parentId", required = false) Long parentId,  
			@RequestParam(value = "type", required = false) Integer type) {  
		if(parentId!=null){
			Resource res = resourceService.get(parentId);
			model.addAttribute("parentId", res.getId());
			model.addAttribute("parentName", res.getName());
		}
		model.addAttribute("type", type);
		return viewName("resourceCreate");
	}  
	
	@RequestMapping(value = "/save", method = RequestMethod.POST) 
	public @ResponseBody JsonResponseModel save(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute Resource resource) {  
		JsonResponseModel json = new JsonResponseModel();
		if(StringUtils.isBlank(resource.getName()) || StringUtils.isBlank(resource.getValue())){
			json.setStatus(0);
			json.setInfo("资源名或资源值不能为空");
			return json;
		}
		
		resourceService.save(resource);
		return json;
	}  
	
	@RequestMapping(value = "{id}/resourceUpdate", method = RequestMethod.GET)
	public String resourceUpdate(@PathVariable("id")Long id, Model model) {
		model.addAttribute("id", id);
		return viewName("resourceUpdate");
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST) 
	public @ResponseBody JsonResponseModel update(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute Resource resource) {  
		JsonResponseModel json = new JsonResponseModel();
		if(resource.getId()==null){
			json.setStatus(0);
			json.setInfo("主键丢失");
			return json;
		}
		if(StringUtils.isBlank(resource.getName()) || StringUtils.isBlank(resource.getValue())){
			json.setStatus(0);
			json.setInfo("资源名或资源值不能为空");
			return json;
		}
		
		resourceService.update(resource);
		return json;
	}  
	
	@RequestMapping(value = "/delete") 
	public @ResponseBody JsonResponseModel delete(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "id", required = false) Long[] ids) {  
		JsonResponseModel json = new JsonResponseModel();
		resourceService.delete(ids);
		return json;
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

	@RequestMapping(value = "/checkOnly", method = RequestMethod.POST) 
	public @ResponseBody Boolean checkOnly(HttpServletRequest request, 
			@RequestParam(value = "fieldName", required = false) String fieldName,
			@RequestParam(value = "fieldValue", required = false) String fieldValue,
			@RequestParam(value = "excludeId", required = false) Long excludeId) {  

		Map<String,Object> params = new HashMap<String,Object>();
		params.put(fieldName, fieldValue);
		params.put("excludeId", excludeId);
		return resourceService.checkOnly(fieldName, fieldValue, excludeId);
	}  
	
	@RequestMapping(value = "/loadResourseZTree") 
	public @ResponseBody List<ZTreeNode<Long>> loadResourseZTree(HttpServletRequest request, 
			HttpServletResponse response,@RequestParam Map<String, Object> parameterMap) {  
		
		List<Resource> resourses = resourceService.find(parameterMap);
		
		List<ZTreeNode<Long>> nodes = convertToZtreeList(resourses, "id", "parent.id", 
				new String[]{"name"}, null, null);
		return nodes;
	}  
}
