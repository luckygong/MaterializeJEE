package com.materialize.jee.platform.authorization.controller;

import java.util.HashMap;
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

import com.materialize.jee.platform.authorization.domain.Role;
import com.materialize.jee.platform.authorization.service.RoleService;
import com.materialize.jee.platform.base.BaseController;
import com.materialize.jee.platform.base.JsonResponseModel;
import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;
import com.materialize.jee.platform.utils.RequestUtils;

@Controller  
@RequestMapping(value="/admin/role",produces = "application/json")
public class RoleController extends BaseController{
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(method = RequestMethod.GET,value = "/roleList")
	public String roleList(HttpServletRequest request, Model model) {
		return viewName("roleList");
	}
	
	@RequestMapping(value = "/listRole") 
	public @ResponseBody JsonResponseModel roleList(HttpServletRequest request, 
			HttpServletResponse response) {  
		
		Pagination pagination = RequestUtils.buildPagination(request);
		Page<Role> page = roleService.findPage(pagination);
		
		JsonResponseModel model = createJsonResponse(pagination,page);
		return model;
	}  
	
	@RequestMapping(value = "/getRoleById", method = RequestMethod.POST) 
	public @ResponseBody JsonResponseModel getRoleById(HttpServletRequest request,HttpServletResponse response, 
			@RequestParam(value = "id", required = false) Long id) {  
		JsonResponseModel json = new JsonResponseModel();
		Role role = roleService.get(id);
        json.setData(role);
        return json;
    }  
	
	@RequestMapping(value = "/{id}") 
	public String view(@PathVariable("id")Long id, Model model) {  
		model.addAttribute("id", id);
		return viewName("roleView");
    }  
	
	@RequestMapping(value = "/roleCreate") 
	public String roleCreate(HttpServletRequest request, HttpServletResponse response) {  
		return viewName("roleCreate");
	}  
	
	@RequestMapping(value = "/save", method = RequestMethod.POST) 
	public @ResponseBody JsonResponseModel save(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute Role role) {  
		JsonResponseModel json = new JsonResponseModel();
		if(StringUtils.isBlank(role.getRoleName()) || StringUtils.isBlank(role.getRoleCode())){
			json.setStatus(0);
			json.setInfo("角色名称或角色编码不能为空");
			return json;
		}
		
		roleService.save(role);
		return json;
	}  
	
	@RequestMapping(value = "{id}/roleUpdate", method = RequestMethod.GET)
	public String roleUpdate(@PathVariable("id")Long id, Model model) {
		model.addAttribute("id", id);
		return viewName("roleUpdate");
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST) 
	public @ResponseBody JsonResponseModel update(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute Role role) {  
		JsonResponseModel json = new JsonResponseModel();
		if(role.getId()==null){
			json.setStatus(0);
			json.setInfo("主键丢失");
			return json;
		}
		if(StringUtils.isBlank(role.getRoleName()) || StringUtils.isBlank(role.getRoleCode())){
			json.setStatus(0);
			json.setInfo("角色名称或角色编码不能为空");
			return json;
		}
		
		roleService.updateSelective(role);
		return json;
	}  
	
	@RequestMapping(value = "/delete") 
	public @ResponseBody JsonResponseModel delete(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "id", required = false) Long[] ids) {  
		JsonResponseModel json = new JsonResponseModel();
		roleService.delete(ids);
		return json;
	}  
	
	@RequestMapping(value = "/checkOnly", method = RequestMethod.POST) 
	public @ResponseBody Boolean checkOnly(HttpServletRequest request, 
			@RequestParam(value = "fieldName", required = false) String fieldName,
			@RequestParam(value = "fieldValue", required = false) String fieldValue,
			@RequestParam(value = "excludeId", required = false) Long excludeId) {  

		Map<String,Object> params = new HashMap<String,Object>();
		params.put(fieldName, fieldValue);
		params.put("excludeId", excludeId);
		return roleService.checkOnly(fieldName, fieldValue, excludeId);
	}  
	
}
