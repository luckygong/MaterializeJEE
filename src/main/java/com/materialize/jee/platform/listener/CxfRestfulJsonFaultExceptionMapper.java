package com.materialize.jee.platform.listener;

import java.util.Locale;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.util.StringUtils;

import com.materialize.jee.platform.base.JsonResponseModel;

@Provider
public class CxfRestfulJsonFaultExceptionMapper implements ExceptionMapper<Throwable> {
	public Response toResponse(Throwable ex) { 
		JsonResponseModel jsonData = new JsonResponseModel(); 
		jsonData.setStatus(0);
		jsonData.setInfo(StringUtils.isEmpty(ex.getMessage())?"操作失败":ex.getMessage());
		
		ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);  
		rb.type("application/json;charset=UTF-8");  
		rb.entity(jsonData);  
		rb.language(Locale.SIMPLIFIED_CHINESE);  
		Response r = rb.build();  
		return r;  
   }  
}
