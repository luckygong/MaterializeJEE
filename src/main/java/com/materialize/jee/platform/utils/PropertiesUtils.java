package com.materialize.jee.platform.utils;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class PropertiesUtils {
	
	/**
	 * 加载properties文件
	 * @param propertyFile 文件相对于CLASSPATH的路径
	 * @return
	 */
	public static Properties loadProperties(String propertyFile){
		InputStream input = null;
		try {
        	Properties prop = new Properties();
        	input = PropertiesUtils.class.getResource(propertyFile).openStream();
        	prop.load(input);
        	return prop;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(input);
		}
		return null;
	}
	
	/**
	 * 获取指定key的值
	 * @param propertyFile 文件相对于CLASSPATH的路径
	 * @param propertyKey 需要获取的key值
	 * @return
	 */
	public static String getProperties(String propertyFile,String propertyKey){
		return getProperties(propertyFile, propertyKey,null);
	}
	
	/**
	 * 获取指定key的值，如果值未空，返回指定的默认值
	 * @param propertyFile 文件相对于CLASSPATH的路径
	 * @param propertyKey 需要获取的key值
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String getProperties(String propertyFile,String propertyKey,String defaultValue){
		String value = null;
		InputStream input = null;
		try {
        	Properties prop = new Properties();
        	input = PropertiesUtils.class.getResource(propertyFile).openStream();
        	prop.load(input);
        	value = prop.getProperty(propertyKey);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(input);
		}
		return value==null?defaultValue:value;
	}
	
	public static void main(String[] args) {
		Properties p = loadProperties("/genconfig.properties");
		System.out.println(p.get("table_list"));
		System.out.println(getProperties("/genconfig.properties", "table_list"));
	}
}
