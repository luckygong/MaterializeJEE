package com.materialize.jee.platform.base;

import java.io.Serializable;

public class JsonResponseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer status=1;//0-失败 1-成功 
	private String info="操作成功";
	private Object data;
	public Integer getStatus() {
		return status;
	}
	public String getInfo() {
		return info;
	}
	public Object getData() {
		return data;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
