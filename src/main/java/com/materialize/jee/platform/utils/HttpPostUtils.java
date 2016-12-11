package com.materialize.jee.platform.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONObject;

public class HttpPostUtils {
	/**
	 * post请求传递xml数据
	 * @param urlStr 请求url
	 * @param xmlString xml请求字符串
	 * @param charset 编码
	 * @return
	 */
	public String postXmlRequest(String urlStr,String xmlString,String charset){    
		HttpClient client = new HttpClient();    
		PostMethod method = new PostMethod(urlStr);    
		String responseString = null;    
		try{    
			//设置请求头部类型   
			method.setRequestHeader("Content-Type","text/xml");  
			method.setRequestHeader("charset",charset);  
			//设置请求体，即xml文本内容   
			method.setRequestEntity(new StringRequestEntity(xmlString,"text/xml",charset));        
			int statusCode = client.executeMethod(method);    
			if(statusCode == HttpStatus.SC_OK){
				responseString = getResponseInfo(method.getResponseBodyAsStream(), charset);
			}    
		}catch (Exception e) {    
			e.printStackTrace();    
		}    
		method.releaseConnection();    
		return responseString;    
	}    

	/**
	 * post请求传递key-value数据
	 * @param urlStr
	 * @param formData
	 * @param charset 编码
	 * @return
	 */
	public static String postFormDataRequest(String urlStr, Map<String,String> formData,String charset){
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(urlStr);
		String responseString = null; 

		client.getParams().setContentCharset(charset);
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset="+charset);

		try {
			List<NameValuePair> NameValuePairs = new ArrayList<NameValuePair>();
			if(formData!=null){
				Iterator<String> it = formData.keySet().iterator();
				while(it.hasNext()){
					String key = it.next();
					String value = formData.get(key);
					NameValuePairs.add(new NameValuePair(key, value));
				}
			}
			NameValuePair[] data = NameValuePairs.toArray(new NameValuePair[NameValuePairs.size()]);
			method.setRequestBody(data);
			
			int statusCode = client.executeMethod(method);    
			if(statusCode == HttpStatus.SC_OK){
				responseString = getResponseInfo(method.getResponseBodyAsStream(), charset);
			}  
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return responseString;
	}
	
	public static JSONObject postJsonRequest(String url,String jsonString,String charset){
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		JSONObject response = null;
		try {
			method.setRequestEntity(new StringRequestEntity(jsonString,"application/json",charset));
			int statusCode = client.executeMethod(method);    
			if(statusCode == HttpStatus.SC_OK){
				String responseString = getResponseInfo(method.getResponseBodyAsStream(), charset);
				response = JSONObject.parseObject(responseString);
			}    
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static String getResponseInfo(InputStream input,String charset){
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
        try {
        	br = new BufferedReader(new InputStreamReader(input,charset));
        	String line;
			for (line = br.readLine(); line != null; line = br.readLine()) {
			    sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(br);
		}
        return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXmlToMap(String xml){
		Map<String, String> map = new HashMap<String, String>();
		Document document;
		try {
			document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			List<Element> elementList = root.elements();
			for (Element e : elementList){
				map.put(e.getName(), e.getText());
			}
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		return map;
	}
	
	
}
