package com.materialize.jee.platform.utils;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.materialize.jee.platform.base.page.Pagination;
import com.materialize.jee.platform.hbase.HBasePagination;
import com.materialize.jee.platform.hbase.phoenix.PhoenixPagination;

/**
 * HttpServletRequest帮助类
 */
public class RequestUtils {
	private static final Logger log = LoggerFactory.getLogger(RequestUtils.class);
	public static final String UTF8 = "UTF-8";
	
	/**
	 * 获取QueryString的参数，并使用URLDecoder以UTF-8格式转码。如果请求是以post方法提交的，
	 * 那么将通过HttpServletRequest#getParameter获取。
	 * 
	 * @param request
	 *            web请求
	 * @param name
	 *            参数名称
	 * @return
	 */
	public static String getRequestParam(HttpServletRequest request, String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		if (request.getMethod().equalsIgnoreCase("post")) {
			return request.getParameter(name);
		}
		String s = request.getQueryString();
		if (StringUtils.isBlank(s)) {
			return null;
		}
		try {
			s = URLDecoder.decode(s, UTF8);
		} catch (UnsupportedEncodingException e) {
			log.error("encoding " + UTF8 + " not support?", e);
		}
		String[] values = parseQueryString(s).get(name);
		if (values != null && values.length > 0) {
			return values[values.length - 1];
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getRequestParamMap(HttpServletRequest request) {
		Map<String, String[]> map;
		if (request.getMethod().equalsIgnoreCase("POST")) {
			map = request.getParameterMap();
		} else {
			String s = request.getQueryString();
			if (StringUtils.isBlank(s)) {
				return new HashMap<String, Object>();
			}
			try {
				s = URLDecoder.decode(s, UTF8);
			} catch (UnsupportedEncodingException e) {
				log.error("encoding " + UTF8 + " not support?", e);
			}
			map = parseQueryString(s);
		}

		Map<String, Object> params = new HashMap<String, Object>(map.size());
		int len;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			len = entry.getValue().length;
			if (len == 1) {
				params.put(entry.getKey(), entry.getValue()[0]);
			} else if (len > 1) {
				params.put(entry.getKey(), entry.getValue());
			}
		}
		
		Set<String> keyset = params.keySet();
		if(keyset!=null){
			String[] keys = keyset.toArray(new String[] {}); 
			for(int i=0;i<keys.length;i++){
				String key = keys[i];
				Object value = params.get(key);
				if(value==null || StringUtils.isEmpty(String.valueOf(value))){
					params.remove(key);
				}
			}
		}
		return params;
	}

	public static Map<String, String[]> parseQueryString(String s) {
		String valArray[] = null;
		if (s == null) {
			throw new IllegalArgumentException();
		}
		Map<String, String[]> ht = new HashMap<String, String[]>();
		StringTokenizer st = new StringTokenizer(s, "&");
		while (st.hasMoreTokens()) {
			String pair = (String) st.nextToken();
			int pos = pair.indexOf('=');
			if (pos == -1) {
				continue;
			}
			String key = pair.substring(0, pos);
			String val = pair.substring(pos + 1, pair.length());
			if (ht.containsKey(key)) {
				String oldVals[] = (String[]) ht.get(key);
				valArray = new String[oldVals.length + 1];
				for (int i = 0; i < oldVals.length; i++) {
					valArray[i] = oldVals[i];
				}
				valArray[oldVals.length] = val;
			} else {
				valArray = new String[1];
				valArray[0] = val;
			}
			ht.put(key, valArray);
		}
		return ht;
	}
	
	public static Pagination buildPagination(HttpServletRequest request){
		Map<String,Object> params = getRequestParamMap(request);
		String pageNo = request.getHeader("pageNo");
		String pageSize = request.getHeader("pageSize");
		Pagination pagination = new Pagination(StringUtils.isBlank(pageNo)?Pagination.DEFAULT_PAGE_NO:Integer.valueOf(pageNo),
				StringUtils.isBlank(pageSize)?Pagination.DEFAULT_PAGE_SIZE:Integer.valueOf(pageSize),
				params);
		return pagination;
	}
	public static HBasePagination buildHbasePagination(HttpServletRequest request){
		Map<String,Object> params = getRequestParamMap(request);
		String pageNo = request.getHeader("pageNo");
		String pageSize = request.getHeader("pageSize");
		String pagefirstRowKey = request.getHeader("pagefirstRowKey");
		HBasePagination pagination = new HBasePagination(StringUtils.isBlank(pageNo)?Pagination.DEFAULT_PAGE_NO:Long.valueOf(pageNo),
				StringUtils.isBlank(pageSize)?Pagination.DEFAULT_PAGE_SIZE:Long.valueOf(pageSize),
						pagefirstRowKey);
		pagination.setEntityMap(params);
		return pagination;
	}
	public static PhoenixPagination buildPhoenixPagination(HttpServletRequest request){
		Map<String,Object> params = getRequestParamMap(request);
		String pageNo = request.getHeader("pageNo");
		String pageSize = request.getHeader("pageSize");
		String pagefirstRowKey = request.getHeader("pagefirstRowKey");
		PhoenixPagination pagination = new PhoenixPagination(StringUtils.isBlank(pageNo)?Pagination.DEFAULT_PAGE_NO:Long.valueOf(pageNo),
				StringUtils.isBlank(pageSize)?Pagination.DEFAULT_PAGE_SIZE:Long.valueOf(pageSize),
						pagefirstRowKey);
		pagination.setEntityMap(params);
		return pagination;
	}
	
}
