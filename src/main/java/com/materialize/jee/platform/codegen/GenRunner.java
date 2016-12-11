package com.materialize.jee.platform.codegen;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.util.DateUtil;

import com.materialize.jee.platform.codegen.model.Configuration;
import com.materialize.jee.platform.codegen.model.Table;
import com.materialize.jee.platform.codegen.tools.DBToObject;
import com.materialize.jee.platform.codegen.tools.GenConstants;
import com.materialize.jee.platform.codegen.tools.GenerateFile;
import com.materialize.jee.platform.codegen.tools.NameFormatUtils;
import com.materialize.jee.platform.utils.PropertiesUtils;

public class GenRunner {
	public List<Map<String,Object>> createDataModel() throws Exception {
		Configuration conf = loadConf();
		String[] tables = conf.getTableList().split(",");
		String[] models = conf.getDomainNameList().split(",");
		
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(int i=0;i<tables.length;i++){
			String tableName = tables[i];
			if(conf.getTableNameUpperCase()){
				tableName = tableName.toUpperCase();
			}
			Map<String,Object> model = new HashMap<String,Object>();
			model.put(GenConstants.OVER_WRITE, conf.getOverwriteExistsFiles());
			model.put(GenConstants.CHAR_SET, conf.getFileEncoding());
			model.put(GenConstants.AUTHER, System.getProperty("user.name"));
			model.put(GenConstants.TIME, DateUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
			model.put(GenConstants.TEMPLATE_BASE_PATH, conf.getTemplatePath());
			model.put(GenConstants.TABLE_NAME, tableName);
			model.put(GenConstants.JAVA_MODEL_NAME, models[i]);
			model.put(GenConstants.JAVA_MODEL_INSTANCE_NAME, NameFormatUtils.getPropertyInstanceName(models[i]));
			model.put(GenConstants.PROJECT_ROOT_PATH, conf.getProjectRootPath());
			model.put(GenConstants.JAVA_MODEL_PACKAGE, conf.getJavaModelTargetPackage());
			model.put(GenConstants.JAVA_MODEL_PROJECT, conf.getJavaModelTargetProject());
			model.put(GenConstants.JAVA_SERVICE_PACKAGE, conf.getJavaServiceTargetPackage());
			model.put(GenConstants.JAVA_SERVICE_IMPL_PACKAGE, conf.getJavaServiceTargetPackage()+".impl");
			model.put(GenConstants.JAVA_SERVICE_PROJECT, conf.getJavaServiceTargetProject());
			model.put(GenConstants.JAVA_MAPPER_PACKAGE, conf.getJavaMapperClientTargetPackage());
			model.put(GenConstants.JAVA_MAPPER_PROJECT, conf.getJavaMapperClientTargetProject());
			model.put(GenConstants.SQL_MAPPER_PACKAGE, conf.getSqlMapTargetPackage());
			model.put(GenConstants.SQL_MAPPER_PROJECT, conf.getSqlMapTargetProject());
			
			Table table = DBToObject.loadTable(tables[i]);
			model.put(GenConstants.TABLE_PRIMARY_KEYS, table.getPrimaryKeysList());
			model.put(GenConstants.TABLE_COMMON_KEYS, table.getCommonColumnList());
			model.put(GenConstants.HAS_MUTI_KEY, table.isHasMutiKey());
			
			result.add(model);
		}
		return result;
	}
	
	public Configuration loadConf() throws Exception{
    	Configuration conf = new Configuration();
    	Properties prop = PropertiesUtils.loadProperties("/genconfig.properties");
    	if(prop==null){
    		throw new Exception("load properties file fail");
    	}
    	
    	conf.setDomainNameList(prop.getProperty("domain_name_list"));
    	conf.setFileEncoding(prop.getProperty("file_encoding"));
    	conf.setForceBigDecimals(new Boolean(prop.getProperty("force_bigDecimals")));
    	conf.setTableNameUpperCase(new Boolean(prop.getProperty("table_name_upper_case")));
    	conf.setJavaMapperClientTargetPackage(prop.getProperty("java_mapper_client_target_package"));
    	conf.setJavaMapperClientTargetProject(prop.getProperty("java_mapper_client_target_project"));
    	conf.setJavaModelTargetPackage(prop.getProperty("java_model_target_package"));
    	conf.setJavaModelTargetProject(prop.getProperty("java_model_target_project"));
    	conf.setJavaServiceTargetPackage(prop.getProperty("java_service_target_package"));
    	conf.setJavaServiceTargetProject(prop.getProperty("java_service_target_project"));
    	conf.setOverwriteExistsFiles(new Boolean(prop.getProperty("overwrite_exists_files")));
    	conf.setSqlMapTargetPackage(prop.getProperty("sql_map_target_package"));
    	conf.setSqlMapTargetProject(prop.getProperty("sql_map_target_project"));
    	conf.setTableList(prop.getProperty("table_list"));
    	conf.setTemplatePath(prop.getProperty("template_path"));
    	
    	return conf;
    }
	
    public static void main(String[] args) throws Exception { 
    	GenRunner runner = new GenRunner();
    	List<Map<String,Object>> models = runner.createDataModel();
          
    	GenerateFile.generateModel(models);
    	GenerateFile.generateService(models);
        GenerateFile.generateDao(models);
        GenerateFile.generateMapper(models);
    } 
    
}
