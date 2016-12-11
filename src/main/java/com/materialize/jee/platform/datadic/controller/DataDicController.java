package com.materialize.jee.platform.datadic.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.materialize.jee.platform.SysConstants;
import com.materialize.jee.platform.base.JsonResponseModel;
import com.materialize.jee.platform.datadic.domain.DataDic;
import com.materialize.jee.platform.datadic.service.DataDicService;

@Controller  
@Produces({ MediaType.APPLICATION_JSON})
public class DataDicController {
	private static final Logger logger = LoggerFactory.getLogger(DataDicController.class);
	@Autowired
	private DataDicService dataDicService;
	
	@RequestMapping(value = "/initSelectOption",method = { RequestMethod.GET, RequestMethod.POST }) 
	public @ResponseBody JsonResponseModel login(
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "exceptValueArray[]", required = false) String[] exceptValue) {  
		logger.debug("initSelectOption:"+category);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("category", category);
		params.put("exceptValue", exceptValue);
		params.put("activeFlag", SysConstants.ACTIVE_FLAG_YES);
        List<DataDic> dics = dataDicService.find(params);
        
        JsonResponseModel model = new JsonResponseModel();
        model.setData(dics);
        return model;
    }  
	
}
