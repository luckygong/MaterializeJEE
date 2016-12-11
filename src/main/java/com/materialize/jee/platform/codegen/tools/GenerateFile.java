package com.materialize.jee.platform.codegen.tools;

import java.io.File;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class GenerateFile {
	
	public static void generateDao(List<Map<String,Object>> dataModels){
		String templateName = GenConstants.TEMPLATE_PATH_DAO;
		 for(Map<String,Object> dataModel:dataModels){
			 String rootPath = (String)dataModel.get(GenConstants.PROJECT_ROOT_PATH);
			 String targetPath = (String)dataModel.get(GenConstants.JAVA_MAPPER_PROJECT);
			 String packagePath = (String)dataModel.get(GenConstants.JAVA_MAPPER_PACKAGE);
			 String fileName = (String)dataModel.get(GenConstants.JAVA_MODEL_NAME)+"Mapper.java";
			 String filePath = rootPath+File.separator+targetPath+File.separator+packagePath.replace(".", "/");
			 generateFile(filePath,fileName,templateName,dataModel);
		 }
	}
	
	public static void generateService(List<Map<String,Object>> dataModels){
		String serviceTemplateName = GenConstants.TEMPLATE_PATH_SERVICE;
		String serviceImplTemplateName = GenConstants.TEMPLATE_PATH_SERVICE_IMPL;
		 for(Map<String,Object> dataModel:dataModels){
			 String rootPath = (String)dataModel.get(GenConstants.PROJECT_ROOT_PATH);
			 String targetPath = (String)dataModel.get(GenConstants.JAVA_SERVICE_PROJECT);
			 String packagePath = (String)dataModel.get(GenConstants.JAVA_SERVICE_PACKAGE);
			 
			 String fileName = (String)dataModel.get(GenConstants.JAVA_MODEL_NAME)+"Service.java";
			 String filePath = rootPath+File.separator+targetPath+File.separator+packagePath.replace(".", "/");
			 generateFile(filePath,fileName,serviceTemplateName,dataModel);
			 
			 fileName = (String)dataModel.get(GenConstants.JAVA_MODEL_NAME)+"ServiceImpl.java";
			 packagePath = (String)dataModel.get(GenConstants.JAVA_SERVICE_IMPL_PACKAGE);
			 filePath = rootPath+File.separator+targetPath+File.separator+packagePath.replace(".", "/");
			 generateFile(filePath,fileName,serviceImplTemplateName,dataModel);
		 }
	}
	
	public static void generateModel(List<Map<String,Object>> dataModels){
		String templateName = GenConstants.TEMPLATE_PATH_MODEL;
		for(Map<String,Object> dataModel:dataModels){
			String rootPath = (String)dataModel.get(GenConstants.PROJECT_ROOT_PATH);
			String targetPath = (String)dataModel.get(GenConstants.JAVA_MODEL_PROJECT);
			String packagePath = (String)dataModel.get(GenConstants.JAVA_MODEL_PACKAGE);
			String fileName = (String)dataModel.get(GenConstants.JAVA_MODEL_NAME)+".java";
			String filePath = rootPath+File.separator+targetPath+File.separator+packagePath.replace(".", "/");
			generateFile(filePath,fileName,templateName,dataModel);
		}
	}
	
	public static void generateMapper(List<Map<String,Object>> dataModels){
		String templateName = GenConstants.TEMPLATE_PATH_MAPPER;
		for(Map<String,Object> dataModel:dataModels){
			String rootPath = (String)dataModel.get(GenConstants.PROJECT_ROOT_PATH);
			String targetPath = (String)dataModel.get(GenConstants.SQL_MAPPER_PROJECT);
			String packagePath = (String)dataModel.get(GenConstants.SQL_MAPPER_PACKAGE);
			String fileName = (String)dataModel.get(GenConstants.JAVA_MODEL_NAME)+"Mapper.xml";
			String filePath = rootPath+File.separator+targetPath+File.separator+packagePath.replace(".", "/");
			generateFile(filePath,fileName,templateName,dataModel);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void generateFile(String filePath,String fileName,String templeName,Map<String,Object> dataModel){
		
		Configuration cfg = new Configuration();   
		String templatePath = (String)dataModel.get(GenConstants.TEMPLATE_BASE_PATH);
		String charSet = (String)dataModel.get(GenConstants.CHAR_SET);
		Boolean overWrite = (Boolean)dataModel.get(GenConstants.OVER_WRITE);
		
		Writer javaWriter = null;
		try {
      		String projectPath = System.getProperty("user.dir");
      		//指定 模板文件从何处加载的数据源，这里设置一个文件目录 
      		cfg.setDirectoryForTemplateLoading(new File(projectPath+"/"+templatePath)); 
      		cfg.setObjectWrapper(new DefaultObjectWrapper()); 
            
      		//获取 模板文件 
      		Template template = cfg.getTemplate(templeName); 
            
      		//合并 模板 和 数据模型 
      		if(filePath != null){
				File fileDir = new File(filePath);
				if(!fileDir.exists()){
					fileDir.mkdirs();
				}
				File file = new File(filePath+"/"+fileName);
				if(file.exists() && !overWrite){
					System.out.println(fileName+"文件已存在");
					return;
				}
				javaWriter = new FileWriterWithEncoding(file,charSet);
				template.process(dataModel, javaWriter); 
				javaWriter.flush(); 
				javaWriter.close(); 
				System.out.println(fileName+"文件生成路径：" + filePath); 
				System.out.println(fileName+"文件生成成功");
      		} 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} finally{
			IOUtils.closeQuietly(javaWriter);
		}
	}
	
}
