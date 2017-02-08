package com.materialize.jee.platform.listener;


import java.io.OutputStream;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.materialize.jee.platform.base.JsonResponseModel;

public class CxfRestfulJsonOutInterceptor extends AbstractPhaseInterceptor<Message> {
	private static Logger logger = LoggerFactory.getLogger(CxfRestfulJsonOutInterceptor.class);
	
	public CxfRestfulJsonOutInterceptor() {  
        super(Phase.PRE_STREAM);  
    }  
	
	public void handleMessage(Message message) throws Fault {  
    } 
	
	public void handleFault(Message message) {
		OutputStream os = message.getContent(OutputStream.class); 
		ObjectMapper objectMapper = new ObjectMapper();  
		try {  
			JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(os,  
					JsonEncoding.UTF8);  
			JsonResponseModel jsonData = new JsonResponseModel(); 
			jsonData.setStatus(0);
			jsonData.setInfo("登录失败");
			objectMapper.writeValue(jsonGenerator, jsonData); 
			os.flush();
		} catch (Exception ex) {  
		}  
	}

}
