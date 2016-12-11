package com.materialize.jee.platform.utils;

import org.apache.commons.codec.binary.Base64;

public final class Base64Utils {
	/**
	 * 加密
	 * @param string
	 * @return
	 */
	public static String encode(String string){
		byte[] b = Base64.encodeBase64(string.getBytes(), true);
		return new String(b);
	}
	 
	/**
	 * 解密
	 * @param string
	 * @return
	 */
	public static String decode(String string){
		byte[] b = Base64.decodeBase64(string.getBytes());
		return new String(b);
	}
}
