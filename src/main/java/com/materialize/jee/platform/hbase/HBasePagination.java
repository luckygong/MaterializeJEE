package com.materialize.jee.platform.hbase;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName: Pagination
 * @Description: (分页参数类)
 */
public class HBasePagination implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final Long DEFAULT_PAGE_NO = 1L;
	public static final Long DEFAULT_PAGE_SIZE = 100L;
	
	private Long pageNo;
	private Long pageSize=100L;//默认每页100条数据
	private Map<String, Object> entityMap;//查询参数
	
    /** 当前页的第一行rowkey，首页查询可以传空  */
    private String currentPagefirstRowKey;
    
	public HBasePagination(){
		this(DEFAULT_PAGE_NO,DEFAULT_PAGE_SIZE);
	}
	
	public HBasePagination(Long pageNo,Long pageSize){
		this(pageNo,pageSize,null);
	}
	
	public HBasePagination(Long pageNo,Long pageSize, String currentPagefirstRowKey){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.currentPagefirstRowKey = currentPagefirstRowKey;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public String getCurrentPagefirstRowKey() {
		return currentPagefirstRowKey;
	}

	public void setCurrentPagefirstRowKey(String currentPagefirstRowKey) {
		this.currentPagefirstRowKey = currentPagefirstRowKey;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public Map<String, Object> getEntityMap() {
		return entityMap;
	}

	public void setEntityMap(Map<String, Object> entityMap) {
		this.entityMap = entityMap;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

}
