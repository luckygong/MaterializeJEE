package com.materialize.jee.platform.hbase;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import com.materialize.jee.platform.hadoop.common.HadoopConfigUtils;

/**
 * 加载和读取hbase配置
 */
public class HBaseConfigUtils {
	private static Configuration conf;
	
	private HBaseConfigUtils(){}
	
	public static Configuration getConfInstance(){
		return getConfInstance(false);
	}
	
	public static Configuration getConfInstance(boolean refresh){
		if(conf!=null && !refresh){
			return conf;
		}
		
		conf = HadoopConfigUtils.getConfInstance();
		conf.addResource(new Path(getLocalHbaseConfDir()+File.separator+"hbase-site.xml"));
		return conf;
	}
	
	public static String getHbaseHome(){
		return System.getenv("HBASE_HOME");
	}
	
	public static String getLocalHbaseConfDir(){
		//获取本地配置
		String dir = ClassLoader.getSystemResource("").toString();
		if(dir.startsWith("file:/")){
			dir = dir.substring("file:/".length());
		}
		return dir+"cluster/conf/hbase";
	}
	
	public static void main(String[] args) {
		System.out.println(getLocalHbaseConfDir());
		
		Configuration conf = HBaseConfigUtils.getConfInstance(false);
		System.out.println(conf.toString());
		System.out.println(conf.get("fs.defaultFS"));
		System.out.println(conf.get("hbase.rootdir"));
	}
}
