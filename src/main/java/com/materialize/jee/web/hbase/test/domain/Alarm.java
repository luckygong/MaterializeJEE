package com.materialize.jee.web.hbase.test.domain;

public class Alarm {
    private String rowKey;
	
    private String mpuId;
    
    private String dvcCode;
    
    private Integer almType;
    
    private String almFrom;
    
    private String callEmp;
    
    
	public String getRowKey() {
		return rowKey;
	}



	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}



	public String getMpuId() {
		return mpuId;
	}



	public void setMpuId(String mpuId) {
		this.mpuId = mpuId;
	}



	public String getDvcCode() {
		return dvcCode;
	}



	public void setDvcCode(String dvcCode) {
		this.dvcCode = dvcCode;
	}



	public Integer getAlmType() {
		return almType;
	}



	public void setAlmType(Integer almType) {
		this.almType = almType;
	}



	public String getAlmFrom() {
		return almFrom;
	}



	public void setAlmFrom(String almFrom) {
		this.almFrom = almFrom;
	}



	public String getCallEmp() {
		return callEmp;
	}



	public void setCallEmp(String callEmp) {
		this.callEmp = callEmp;
	}



	@Override
    public String toString() {
    	String s = "row:"+rowKey+",mpuId:"+mpuId+",dvcCode:"+dvcCode
    			+",almType:"+almType+",almFrom:"+almFrom+",callEmp:"+callEmp;
    	return s;
    }
}
