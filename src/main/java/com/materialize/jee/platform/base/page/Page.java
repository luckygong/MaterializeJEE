package com.materialize.jee.platform.base.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Page
 * @Description: (分页类)
 */
public class Page<T> extends ArrayList<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long totalCount=0;//查询的总记录数
	private long totalPage=1;//查询的总页数
	private long currentPage=1;//当前页，默认为第一页
	private List<T> pageData;//当前页数据
	
	
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public long getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	public long getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}
	public List<T> getPageData() {
		return pageData;
	}
	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}
	
	
}
