package com.materialize.jee.platform.hbase.phoenix;

import com.materialize.jee.platform.hbase.HBasePagination;

/**
 * @ClassName: Pagination
 * @Description: (分页参数类)
 */
public class PhoenixPagination extends HBasePagination{
	private static final long serialVersionUID = 1L;
	
	public static final Long DEFAULT_PAGE_NO = 1L;
	public static final Long DEFAULT_PAGE_SIZE = 100L;
	
	public Long querySize;//查询limit条数，等于pagesize+1,多查询的一条为下一页查询时的条件
	
	public PhoenixPagination(){
		this(DEFAULT_PAGE_NO,DEFAULT_PAGE_SIZE);
	}
	
	public PhoenixPagination(Long pageNo,Long pageSize){
		this(pageNo,pageSize,null);
	}
	
	public PhoenixPagination(Long pageNo,Long pageSize, String currentPagefirstRowKey){
		super(pageNo, pageSize, currentPagefirstRowKey);
		this.querySize = pageSize+1;
	}

	public Long getQuerySize() {
		return querySize;
	}

	public void setQuerySize(Long querySize) {
		this.querySize = querySize;
	}
	
	
}
