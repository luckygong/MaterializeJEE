package com.materialize.jee.web.cxf.service.impl;

import org.springframework.stereotype.Service;

import com.materialize.jee.web.cxf.service.ICxfServerTest;

@Service
public class CxfServerTestImpl implements ICxfServerTest {
	
    public String sayHello(String phone){
    	return phone;
    }
}