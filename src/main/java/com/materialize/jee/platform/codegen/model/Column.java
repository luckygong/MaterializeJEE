package com.materialize.jee.platform.codegen.model;

public class Column implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
     * 列名
     */
	private String columnName;
	
	/**
	 * java中属性名
	 */
	private String propertyName;
	
	/**
	 * java属性getter/setter中名称
	 */
	private String propertyInMethodName;
	
	/**
	 * 列描述
	 */
	private String remarks;
	
	/**
     * jdbc java.sql.types类型
     */
	private Integer jdbcType;
	
	/**
	 * jdbc类型
	 */
	private String jdbcTypeName;
	
	/**
     * 列对应java类型
     */
	private String javaType;
	
	/**
     * 列长度
     */
	private int columnLength;
	
	/**
	 * 小数位数
	 */
	private int decimalDigits;
	
	/**
     * 是否为主键
     */
	private boolean isPrimary;
	
	/**
     * 是否可以为空
     */
	private boolean isNullAble;

	public Column(){
		super();
	}
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getColumnLength() {
		return columnLength;
	}

	public void setColumnLength(int columnLength) {
		this.columnLength = columnLength;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean getIsNullAble() {
		return isNullAble;
	}

	public void setIsNullAble(boolean isNullAble) {
		this.isNullAble = isNullAble;
	}
	
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public void setNullAble(boolean isNullAble) {
		this.isNullAble = isNullAble;
	}

	public Integer getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(Integer jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getJdbcTypeName() {
		return jdbcTypeName;
	}

	public void setJdbcTypeName(String jdbcTypeName) {
		this.jdbcTypeName = jdbcTypeName;
	}

	public String getPropertyInMethodName() {
		return propertyInMethodName;
	}

	public void setPropertyInMethodName(String propertyInMethodName) {
		this.propertyInMethodName = propertyInMethodName;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("columnName：").append(this.columnName).append(",");
		buf.append("remarks :").append(this.remarks).append(",");
		buf.append("isPrimary :").append(this.isPrimary).append(",");
		buf.append("isNullAble :").append(this.isNullAble).append(",");
		buf.append("jdbcType :").append(this.jdbcType).append(",");
		buf.append("javaType :").append(this.javaType).append(",");
		buf.append("columnLength :").append(this.columnLength).append(",");
		buf.append("columndecimalDigits :").append(this.decimalDigits).append("\n");
		return buf.toString();
	}
}
