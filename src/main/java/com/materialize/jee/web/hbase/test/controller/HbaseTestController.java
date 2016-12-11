package com.materialize.jee.web.hbase.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.materialize.jee.platform.base.BaseController;
import com.materialize.jee.platform.base.JsonResponseModel;
import com.materialize.jee.platform.hbase.phoenix.PhoenixPage;
import com.materialize.jee.platform.hbase.phoenix.PhoenixPagination;
import com.materialize.jee.platform.utils.RequestUtils;
import com.materialize.jee.web.hbase.test.domain.Alarm;
import com.materialize.jee.web.hbase.test.service.PhoenixTestService;

@Controller  
public class HbaseTestController extends BaseController{
	//@Autowired
	private PhoenixTestService phoenixTestService;

	@RequestMapping(value = "/phoenix" ,produces = "application/json") 
	public @ResponseBody JsonResponseModel phoenixTest(HttpServletRequest request, 
			HttpServletResponse response) {  
		
		PhoenixPagination pagination = RequestUtils.buildPhoenixPagination(request);
		PhoenixPage<Alarm> pageData = phoenixTestService.findPage(pagination);
		JsonResponseModel model = super.createJsonResponse(pagination, pageData);
		return model;
	}  
	
}
