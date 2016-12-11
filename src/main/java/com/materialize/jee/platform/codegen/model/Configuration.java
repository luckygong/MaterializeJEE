package com.materialize.jee.platform.codegen.model;

public class Configuration {
	private String fileEncoding;
	private Boolean overwriteExistsFiles;
	private Boolean forceBigDecimals;
	private Boolean tableNameUpperCase;
	private String projectRootPath;
	private String templatePath;
	private String javaModelTargetProject;
	private String javaModelTargetPackage;
	private String javaMapperClientTargetProject;
	private String javaMapperClientTargetPackage;
	private String javaServiceTargetProject;
	private String javaServiceTargetPackage;
	private String sqlMapTargetProject;
	private String sqlMapTargetPackage;
	private String tableList;
	private String domainNameList;
	public String getFileEncoding() {
		return fileEncoding;
	}
	public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding;
	}
	public Boolean getOverwriteExistsFiles() {
		return overwriteExistsFiles;
	}
	public void setOverwriteExistsFiles(Boolean overwriteExistsFiles) {
		this.overwriteExistsFiles = overwriteExistsFiles;
	}
	public Boolean getForceBigDecimals() {
		return forceBigDecimals;
	}
	public void setForceBigDecimals(Boolean forceBigDecimals) {
		this.forceBigDecimals = forceBigDecimals;
	}
	public String getTemplatePath() {
		return templatePath;
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public String getJavaModelTargetProject() {
		return javaModelTargetProject;
	}
	public void setJavaModelTargetProject(String javaModelTargetProject) {
		this.javaModelTargetProject = javaModelTargetProject;
	}
	public String getJavaModelTargetPackage() {
		return javaModelTargetPackage;
	}
	public void setJavaModelTargetPackage(String javaModelTargetPackage) {
		this.javaModelTargetPackage = javaModelTargetPackage;
	}
	public String getJavaMapperClientTargetProject() {
		return javaMapperClientTargetProject;
	}
	public void setJavaMapperClientTargetProject(String javaMapperClientTargetProject) {
		this.javaMapperClientTargetProject = javaMapperClientTargetProject;
	}
	public String getJavaMapperClientTargetPackage() {
		return javaMapperClientTargetPackage;
	}
	public void setJavaMapperClientTargetPackage(String javaMapperClientTargetPackage) {
		this.javaMapperClientTargetPackage = javaMapperClientTargetPackage;
	}
	public String getJavaServiceTargetProject() {
		return javaServiceTargetProject;
	}
	public void setJavaServiceTargetProject(String javaServiceTargetProject) {
		this.javaServiceTargetProject = javaServiceTargetProject;
	}
	public String getJavaServiceTargetPackage() {
		return javaServiceTargetPackage;
	}
	public void setJavaServiceTargetPackage(String javaServiceTargetPackage) {
		this.javaServiceTargetPackage = javaServiceTargetPackage;
	}
	public String getSqlMapTargetProject() {
		return sqlMapTargetProject;
	}
	public void setSqlMapTargetProject(String sqlMapTargetProject) {
		this.sqlMapTargetProject = sqlMapTargetProject;
	}
	public String getSqlMapTargetPackage() {
		return sqlMapTargetPackage;
	}
	public void setSqlMapTargetPackage(String sqlMapTargetPackage) {
		this.sqlMapTargetPackage = sqlMapTargetPackage;
	}
	public String getTableList() {
		return tableList;
	}
	public void setTableList(String tableList) {
		this.tableList = tableList;
	}
	public String getDomainNameList() {
		return domainNameList;
	}
	public void setDomainNameList(String domainNameList) {
		this.domainNameList = domainNameList;
	}
	public String getProjectRootPath() {
		return projectRootPath;
	}
	public void setProjectRootPath(String projectRootPath) {
		this.projectRootPath = projectRootPath;
	}
	
	public Boolean getTableNameUpperCase() {
		return tableNameUpperCase;
	}
	public void setTableNameUpperCase(Boolean tableNameUpperCase) {
		this.tableNameUpperCase = tableNameUpperCase;
	}
	public Configuration(){
		 String rootPath=System.getProperty("user.dir"); 
		 this.projectRootPath = rootPath;
	}
	
	public static void main(String[] args) {
		System.out.println(new Configuration().getProjectRootPath());
	}
	
}
