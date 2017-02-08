package com.materialize.jee.platform.base;

import java.util.Map;

public interface IWebServiceSignValidate {
	boolean validate(Map<String,Object> fields, String sign);
}
