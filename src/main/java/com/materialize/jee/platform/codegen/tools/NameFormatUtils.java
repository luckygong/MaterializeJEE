package com.materialize.jee.platform.codegen.tools;

import java.util.regex.Pattern;

public class NameFormatUtils {
	public static String getColumnJavaName(String columnName){
		String[] spilts = columnName.split("_");
		StringBuffer sb = new StringBuffer();
		for(int i=0;spilts!=null && i<spilts.length;i++){
			String spilt = spilts[i];
			//只包含小写字母或只包含大写字母
			Pattern p = Pattern.compile("((^(([a-z])+)$)|(^(([A-Z])+)$))");
			if(p.matcher(spilt).matches()){
				spilt = spilt.toLowerCase();
			}
			Character first = spilt.charAt(0);
			String next ="";
			if(spilt.length()>1){
				next = spilt.substring(1);
			}
			sb.append(first.toString().toUpperCase()).append(next);
		}
		if(sb.length()>0){
			String first = ((Character)sb.charAt(0)).toString().toLowerCase();
			return first+sb.substring(1).toString();
		}
		return sb.toString();
	}
	
	public static String getPropertyInMethodName(String propertyName){
		if(propertyName!=null && propertyName.length()>0){
			String first = ((Character)propertyName.charAt(0)).toString().toUpperCase();
			return first+propertyName.substring(1);
		}
		return propertyName;
	}
	
	public static String getPropertyInstanceName(String propertyName){
		if(propertyName!=null && propertyName.length()>0){
			String first = ((Character)propertyName.charAt(0)).toString().toLowerCase();
			return first+propertyName.substring(1);
		}
		return propertyName;
	}
}
