package com.materialize.jee.platform.codegen.model;

import java.util.ArrayList;
import java.util.List;

public class Table implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
     * 表名
     */
	private String tableName;
	
	/**
     * 是否存在主键
     */
	private boolean hasPrimary;
	
	/**
	 * 是否联合主键
	 */
	private boolean hasMutiKey;
	
	/**
     * 主键集合
     */
	private List<Column> primaryKeysList;
	
	/**
	 * 列集合
	 */
	private List<Column> commonColumnList;
	
	public Table(String tableName){
		super();
		this.tableName = tableName;
		primaryKeysList = new ArrayList<Column>();
		commonColumnList = new ArrayList<Column>();
	}
	
	public boolean isHasPrimary() {
		return hasPrimary;
	}
	public void setHasPrimary(boolean hasPrimary) {
		this.hasPrimary = hasPrimary;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<Column> getPrimaryKeysList() {
		return primaryKeysList;
	}

	public void addPrimaryKeysList(Column primaryKey) {
		this.primaryKeysList.add(primaryKey);
	}

	public List<Column> getCommonColumnList() {
		return commonColumnList;
	}

	public void addCommonColumnList(Column commonColumn) {
		this.commonColumnList.add(commonColumn);
	}

	public boolean isHasMutiKey() {
		return hasMutiKey;
	}

	public void setHasMutiKey(boolean hasMutiKey) {
		this.hasMutiKey = hasMutiKey;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("table name :").append(this.tableName).append("\n");
		for(Column col : this.primaryKeysList){
			buf.append(col.toString());
		}
		for(Column col : this.commonColumnList){
			buf.append(col.toString());
		}
		return buf.toString();
	}
}
