package com.materialize.jee.platform.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringUtil extends StringUtils{
	/**
	 * 功能描述：判断输入字符串是否为整数
	 * 
	 * @param str 传入的字符串
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断输入字符串是否为浮点数，包括double和float
	 * 
	 * @param str 传入的字符串
	 * @return 是浮点数返回true,否则返回false
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 功能描述：人民币转成大写
	 * 
	 * @param str 数字字符串
	 * @return String 人民币转换成大写后的字符串
	 */
	public static String hangeToBig(String str) {
		double value;
		try {
			value = Double.parseDouble(str.trim());
		} catch (Exception e) {
			return null;
		}
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (value * 100); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串

		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
		String rail = valStr.substring(valStr.length() - 2); // 取小数部分

		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角"
					+ digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0') { // 标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示
			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}

		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示
	}
	
	/**
	 * 使用指定字符，扩充源字符串长度
	 * 
	 * @param str 源字符串 
	 * @param size 扩大后的长度 
	 * @param catChar 补充的字符串
	 * @return
	 */
	public static String rightCat(String str, int size, char catChar) {  
		if (str == null) {  
            return null;  
        }  
        int cats = size - str.length();  
        if (cats <= 0) {  
            return str; 
        }
        StringBuffer bf = new StringBuffer(str);
        for(int i=0;i<cats;i++){
        	bf.append(catChar);
        }
    	return bf.toString();
    }  
	
	/**
	 * 使用指定字符，扩充源字符串长度
	 * 
	 * @param str 源字符串 
	 * @param size 扩大后的长度 
	 * @param catChar 补充的字符串
	 * @return
	 */
	public static String leftCat(String str, int size, char catChar) {  
		if (str == null) {  
			return null;  
		}  
		int cats = size - str.length();  
		if (cats <= 0) {  
			return str; 
		}
		StringBuffer bf = new StringBuffer();
		for(int i=0;i<cats;i++){
			bf.append(catChar);
		}
		bf.append(str);
		return bf.toString();
	} 
	
	/**
	 * 数字格式化 #,##0.00
	 * 
	 * @param value 待格式化数字
	 * @return
	 */
	public static String formatNumber(String value) {
		return formatNumber(value, 3, 2);
	}
	
	/**
	 * 数字格式化 #,##0.00
	 * 
	 * @param value 待格式化数字
	 * @return
	 */
	public static String formatNumber(BigDecimal value) {
		return formatNumber(value, 3, 2);
	}
	
	/**
	 * 数字格式化 #,##0.00##
	 * 
	 * @param value 待格式化数字
	 * @param groupSize 逗号分组每组位数
	 * @param scan 小数位数
	 * @return
	 */
	public static String formatNumber(String value,int groupSize,int scan) {
		return formatNumber(new BigDecimal(value), groupSize, scan);
	}
	
	/**
	 * 数字格式化,默认#,##0.00##
	 * 
	 * @param value 待格式化数字
	 * @param groupSize 逗号分组每组位数
	 * @param scan 小数位数
	 * @return
	 */
	public static String formatNumber(BigDecimal value,int groupSize,int scan) {
		DecimalFormat df=(DecimalFormat) DecimalFormat.getInstance(); 
		StringBuffer pattern = new StringBuffer();
		if(groupSize>0){
			pattern.append(rightCat("#,", 2+groupSize, '#'));
		}
		if(scan>0){
			pattern.append(rightCat(".", 1+scan, '0'));
		}
		df.applyPattern(pattern.toString()); 
		return df.format(value); 
	}
   
    public static void main(String[] args) {
		System.out.println(formatNumber("12345.12344",3,2));
	}
}
