package com.materialize.jee.platform.utils;

import java.util.UUID;

public class UUIDUtils {
	
	/**
	 * 生成UUID序号,带分隔符
	 * @return
	 */
	public static String createUUID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 生成UUID序号
	 * @param withSpilts 是否带分隔符
	 * @return
	 */
	public static String createUUID(boolean withSpilts) {
		if(withSpilts){
			//生成UUID序号
			return UUID.randomUUID().toString();
		}else{
			//生成UUID序号，用""替换"-"，即去掉UUID中的"-"字符
			return UUID.randomUUID().toString().replace("-", "");
		}
	}
	
}
