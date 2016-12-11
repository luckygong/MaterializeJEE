package com.materialize.jee.platform.base.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: Pagination
 * @Description: (分页参数类)
 */
public class Pagination implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_PAGE_NO = 1;
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	private int pageSize=10;//默认每页10条数据
	private int pageNo=1;//需要查询的页
	private int firstRecord=0;
	private Map<String, Object> entityMap;//查询参数
	
	public Pagination(){
		this(DEFAULT_PAGE_NO);
	}
	
	public Pagination(int pageNo){
		this(pageNo,DEFAULT_PAGE_SIZE);
	}
	
	public Pagination(int pageNo, int pageSize){
		this(pageNo,pageSize,null);
	}
	
	public Pagination(int pageNo, int pageSize, Map<String, Object> params){
		if(params!=null){
			this.entityMap = params;
		}else{
			this.entityMap = new HashMap<String,Object>();
		}
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.firstRecord = getFirstRecordNumber();
	}
	
	private int getFirstRecordNumber(){
		return (pageNo-1)*pageSize;
	}
	
	public Map<String, Object> getEntityMap() {
		return entityMap;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getFirstRecord() {
		return firstRecord;
	}
	
}
