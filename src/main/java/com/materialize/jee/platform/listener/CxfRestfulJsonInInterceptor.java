package com.materialize.jee.platform.listener;


import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.materialize.jee.platform.base.IWebServiceSignValidate;

public class CxfRestfulJsonInInterceptor extends AbstractPhaseInterceptor<Message> implements InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(CxfRestfulJsonInInterceptor.class);
	
	private IWebServiceSignValidate validateService;
	private String signFieldName;
	
	public void afterPropertiesSet() throws Exception {
        if(!StringUtils.isEmpty(signFieldName)){
        	Assert.notNull(validateService, "validateService cannot be null while signFieldName is not null");
        }
    }
	
	public CxfRestfulJsonInInterceptor() {  
        super(Phase.RECEIVE);  
    }  
	
	@SuppressWarnings("unchecked")
	public void handleMessage(Message message) throws Fault {  
        //未配置签名字段，直接放行
    	if (StringUtils.isEmpty(signFieldName)) {
            return;
        }
        
    	//报文头
        Map<String,Object> protocolHeaders = (Map<String,Object>)message.get(Message.PROTOCOL_HEADERS);
        
        //报文体
        JSONObject jsonObject = null;
        try{
	        InputStream in = message.getContent(InputStream.class); 
	        String json = IOUtils.toString(in,"UTF-8"); 
	        jsonObject = JSON.parseObject(json);
        }catch(Exception e){
        	throw new RuntimeException("check sign fault");
        }
        
        //查找签名字段
        String sign = null;
        List<Object> signArray = (List<Object>)protocolHeaders.get(signFieldName);
        if(signArray!=null && signArray.size()>0){
        	sign = (String)signArray.get(0);
        }
        if(StringUtils.isEmpty(sign) && jsonObject!=null){
        	sign = jsonObject.getString(signFieldName);
        }
        
        if(StringUtils.isEmpty(sign)){
        	logger.info("not found sign field");
        	throw new RuntimeException("not found sign field");
        }
        
        //将报文体和报文体内容添加到map中，传给验证方法进行验证
        Map<String,Object> requests = new HashMap<String,Object>();
        requests.putAll(protocolHeaders);
        if(jsonObject!=null){
        	Set<String> keys = jsonObject.keySet();
    		Iterator<String> it = keys.iterator();
    		while(it.hasNext()){
    			String key = it.next();
    			requests.put(key, jsonObject.get(key));
    		}
    	}
        
        if(!validateService.validate(requests, sign)){
        	logger.info("sign field not match");
        	throw new RuntimeException("sign field not match");
        }
    } 
	
	public String getSignFieldName() {
		return signFieldName;
	}

	public void setSignFieldName(String signFieldName) {
		this.signFieldName = signFieldName;
	}

	public IWebServiceSignValidate getValidateService() {
		return validateService;
	}

	public void setValidateService(IWebServiceSignValidate validateService) {
		this.validateService = validateService;
	}

}
