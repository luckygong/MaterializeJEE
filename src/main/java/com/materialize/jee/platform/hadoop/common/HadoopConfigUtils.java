package com.materialize.jee.platform.hadoop.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HadoopConfigUtils {
	private static Logger log=LoggerFactory.getLogger(HadoopConfigUtils.class);
	private static Configuration conf;
	
	private HadoopConfigUtils(){}
	
	public static Configuration getConfInstance(){
		return getConfInstance(false);
	}
	
	public static Configuration getConfInstance(boolean refresh){
		if(conf!=null && !refresh){
			return conf;
		}
		
		String hadoopConfig=null;
		try {
			hadoopConfig = getLocalHadoopConfDir();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.isBlank(hadoopConfig)){
			return new Configuration();
		}
		
		conf = new Configuration();
		conf.addResource(new Path(hadoopConfig+File.separator+"core-site.xml"));
		conf.addResource(new Path(hadoopConfig+File.separator+"hdfs-site.xml"));
		conf.addResource(new Path(hadoopConfig+File.separator+"mapred-site.xml"));
		conf.addResource(new Path(hadoopConfig+File.separator+"yarn-site.xml"));
		return conf;
	}
	
	public static String getHadoopHome(){
		return System.getenv("HADOOP_HOME");
	}
	
	public static String getLocalHadoopConfDir(){
		//获取本地配置
		String dir = ClassLoader.getSystemResource("").toString();
		if(dir.startsWith("file:/")){
			dir = dir.substring("file:/".length());
		}
		return dir+"cluster/conf/hadoop";
	}
	
	public static Configuration getDefaultCoreSite(){
		try {
			File f=new File(getLocalHadoopConfDir()+File.separator+"core-site.xml");
			if(f.exists()){
				Configuration conf=new Configuration(false);
				conf.addResource(f.toURI().toURL());
				return conf;
			}
		} catch (IOException e) {
			log.error("load core-site.xml error",e);
		}
		return null;
	}
	
	public static Configuration getDefaultHdfsSite(){
		try{
			File f=new File(getLocalHadoopConfDir()+File.separator+"hdfs-site.xml");
			if(f.exists()){
				Configuration conf=new Configuration(false);
				conf.addResource(f.toURI().toURL());
				return conf;
			}
		} catch (IOException e) {
			log.error("load hdfs-site.xml error",e);
		}
		return null;
	}
	
	public static Configuration getDefaultMapredSite(){
		try{
			File f=new File(getLocalHadoopConfDir()+File.separator+"mapred-site.xml");
			if(f.exists()){
				Configuration conf=new Configuration(false);
				conf.addResource(f.toURI().toURL());
				return conf;
			}
		} catch (IOException e) {
			log.error("load mapred-site.xml error",e);
		}
		return null;
	}

	public static Configuration getDefaultYarnSite(){
		try{
			File f=new File(getLocalHadoopConfDir()+File.separator+"yarn-site.xml");
			if(f.exists()){
				Configuration conf=new Configuration(false);
				conf.addResource(f.toURI().toURL());
				return conf;
			}
		} catch (IOException e) {
			log.error("load yarn-site.xml",e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		Configuration conf = HadoopConfigUtils.getConfInstance(false);
		System.out.println(conf.toString());
		System.out.println(conf.get("fs.defaultFS"));
	}
}
