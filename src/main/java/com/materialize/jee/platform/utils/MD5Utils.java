package com.materialize.jee.platform.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
	public static String encode(String string) {
		return DigestUtils.md5Hex(string);
	}
	
	public static String hash(String s) {
        try {
            return new String(DigestUtils.md5Hex(DigestUtils.md5(s)).getBytes("UTF-8"), "UTF-8");
        } catch (Exception e) {
            return s;
        }
    }
}
