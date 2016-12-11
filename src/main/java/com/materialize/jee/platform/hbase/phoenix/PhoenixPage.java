package com.materialize.jee.platform.hbase.phoenix;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询结果中，每页的结果多一条，表示下一页的起始rowkey(注：分页查询结果中必须返回rowkey)
 * @ClassName: PhoenixPage
 * @Description: (分页类)
 */
public class PhoenixPage<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
    /** 当前页码  */
    private Long currentPage;
    
    /** 每页记录数  */
    private Long currentPageSize;
    
    /** 获取当前页的第一行序号  */
    private T currentPagefirstRow;
    
    /** 获取后一页的第一行序号  */
    private T nextPagefirstRow;
    
    /** 分页结果  */
    private List<T> pageData = new ArrayList<T>();
    
    /** 
     * 获取当前页记录数 
     */
	public Long getCurrentPageDataCount() {
		if(pageData==null){
			return 0L;
		}
		return pageData.size()>currentPageSize?currentPageSize:Long.valueOf(pageData.size());
	}

	/** 
     * 获取当前页首行
     */
	public T getCurrentPagefirstRow() {
		if(pageData==null || pageData.size()==0){
			return null;
		}
		currentPagefirstRow = (T)pageData.get(0);
		return currentPagefirstRow;
	}

	/** 
     * 获取下一页首行
     */
	public T getNextPagefirstRow() {
		if(hasNextPage()){
			nextPagefirstRow = (T)pageData.get(currentPageSize.intValue());
			return nextPagefirstRow;
		}
		return null;
	}

	/**
	 * 如果返回了下一页首条记录，则表示有下一页
	 */
    public boolean hasNextPage(){
    	if(pageData!=null && pageData.size()>currentPageSize){
    		return true;
    	}
    	return false;
    }
    
    /** 
     * 获取当前页记录
     * 如果有下一页首行记录，去除掉
     */
    public List<T> getPageData() {
    	if(hasNextPage()){
    		return pageData.subList(0, pageData.size()-1);
    	}
		return pageData;
	}
    
    public Long getAlreadyQueryCount(){
    	return (currentPage-1)*currentPageSize+getCurrentPageDataCount();
    }

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}
	
	public Long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}

	public Long getCurrentPageSize() {
		return currentPageSize;
	}

	public void setCurrentPageSize(Long currentPageSize) {
		this.currentPageSize = currentPageSize;
	}

	public Long getCurrentDataSize() {
		if(hasNextPage()){
    		return currentPageSize;
    	}
		return Long.valueOf(pageData.size());
	}
}
