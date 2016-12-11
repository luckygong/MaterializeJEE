package com.materialize.jee.platform.authorization.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.materialize.jee.platform.base.JsonResponseModel;

public class MySimpleLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	public MySimpleLoginFailureHandler(String defaultFailureUrl) {
		super(defaultFailureUrl);
	}
	
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,  
            AuthenticationException exception) throws IOException, ServletException {  
		
		if(!(request.getHeader("X-Requested-With")!=null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With")))){
			super.onAuthenticationFailure(request, response, exception);
		}else{
			ObjectMapper objectMapper = new ObjectMapper();  
			response.setHeader("Content-Type", "application/json;charset=UTF-8");  
			JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(),  
					JsonEncoding.UTF8);  
			try {  
				JsonResponseModel jsonData = new JsonResponseModel(); 
				jsonData.setStatus(0);
				jsonData.setInfo("登录失败");
				objectMapper.writeValue(jsonGenerator, jsonData);  
			} catch (JsonProcessingException ex) {  
				throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);  
			}  
		}
    }  
}
