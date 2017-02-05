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

import com.materialize.jee.platform.authorization.domain.User;
import com.materialize.jee.platform.authorization.service.UserService;
import com.materialize.jee.platform.base.BaseController;
import com.materialize.jee.platform.base.JsonResponseModel;
import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;
import com.materialize.jee.platform.utils.RequestUtils;

@Controller  
@RequestMapping(value="/admin/user",produces = "application/json")
public class UserController extends BaseController{
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET,value = "/userList")
	public String userList(HttpServletRequest request, Model model) {
		return viewName("userList");
	}
	
	@RequestMapping(value = "/listUser") 
	public @ResponseBody JsonResponseModel menuList(HttpServletRequest request, 
			HttpServletResponse response) {  
		
		Pagination pagination = RequestUtils.buildPagination(request);
		Page<User> page = userService.findPage(pagination);
		
        JsonResponseModel model = createJsonResponse(pagination,page);
        return model;
    }  
	
	@RequestMapping(value = "/getUserById", method = RequestMethod.POST) 
	public @ResponseBody JsonResponseModel getUserById(HttpServletRequest request,HttpServletResponse response, 
			@RequestParam(value = "id", required = false) Long id) {  
		JsonResponseModel json = new JsonResponseModel();
        User user = userService.get(id);
        json.setData(user);
        return json;
    }  
	
	@RequestMapping(value = "/{id}") 
	public String view(@PathVariable("id")Long id, Model model) {  
		model.addAttribute("id", id);
		return viewName("userView");
    }  
	
	@RequestMapping(value = "/userCreate") 
	public String userCreate(HttpServletRequest request, HttpServletResponse response) {  
		return viewName("userCreate");
	}  
	
	@RequestMapping(value = "/save", method = RequestMethod.POST) 
	public @ResponseBody JsonResponseModel save(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute User user) {  
		JsonResponseModel json = new JsonResponseModel();
		if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPhone())){
			json.setStatus(0);
			json.setInfo("用户名或手机号不能为空");
			return json;
		}
		
		userService.save(user, request);
		return json;
	}  
	
	@RequestMapping(value = "{id}/userUpdate", method = RequestMethod.GET)
	public String userUpdate(@PathVariable("id")Long id, Model model) {
		model.addAttribute("id", id);
		return viewName("userUpdate");
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST) 
	public @ResponseBody JsonResponseModel update(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute User user) {  
		JsonResponseModel json = new JsonResponseModel();
		if(user.getId()==null){
			json.setStatus(0);
			json.setInfo("主键丢失");
			return json;
		}
		if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPhone())){
			json.setStatus(0);
			json.setInfo("用户名或手机号不能为空");
			return json;
		}
		
		userService.update(user, request);
		return json;
	}  
	
	@RequestMapping(value = "/delete") 
	public @ResponseBody JsonResponseModel delete(HttpServletRequest request,
			HttpServletResponse response, 
			@RequestParam(value = "id", required = false) Long[] ids) {  
		JsonResponseModel json = new JsonResponseModel();
		userService.delete(ids);
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
		return userService.checkOnly(fieldName, fieldValue, excludeId);
	}  
	

}
