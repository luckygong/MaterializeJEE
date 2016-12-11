package com.materialize.jee.platform.authorization.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@RequestMapping(value = "/roleList") 
	public @ResponseBody JsonResponseModel roleList(HttpServletRequest request, 
			HttpServletResponse response) {  
		
		Pagination pagination = RequestUtils.buildPagination(request);
		Page<Role> page = roleService.findPage(pagination);
		
		JsonResponseModel model = createJsonResponse(pagination,page);
		return model;
	}  
	
}
