package com.materialize.jee.web.cxf.service.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.materialize.jee.platform.base.IWebServiceSignValidate;

public class CxfWebServiceMd5SignValidate implements IWebServiceSignValidate {
	private final static Log log = LogFactory.getLog(CxfWebServiceMd5SignValidate.class);
	
	@Override
	public boolean validate(Map<String,Object> fields, String sign) {
		log.info("cxf restful webservice sign validate, client sign:"+sign);
		//根据需要自主实现校验代码
		return true;
	}

}
