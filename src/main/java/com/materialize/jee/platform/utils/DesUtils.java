package com.materialize.jee.platform.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class DesUtils {  
  
    private static final String Algorithm = "DESede"; //定义加密算法,可用 DES,DESede,Blowfish  
    
    /**
     *  3des加密
     * @param key 密钥，长度必须为8的倍数
     * @param src 待加密字符串
     */
    public static String encode(String key, String src){
    	if(src==null){
    		return null;
    	}
    	
    	byte[] enk = hex(key==null?"":key);
    	byte[] encoded = null; 
    	try {  
            //生成密钥  
            SecretKey deskey = new SecretKeySpec(enk, Algorithm);  
            //加密  
            Cipher c1 = Cipher.getInstance(Algorithm);  
            c1.init(Cipher.ENCRYPT_MODE, deskey);  
            encoded = c1.doFinal(src.getBytes());//在单一方面的加密或解密  
        } catch (java.security.NoSuchAlgorithmException e1) {  
             e1.printStackTrace();  
        }catch(javax.crypto.NoSuchPaddingException e2){  
            e2.printStackTrace();  
        }catch(java.lang.Exception e3){  
            e3.printStackTrace();  
        }  
    	
    	if(encoded==null){
    		return null;
    	}
    	return new String(Base64.encodeBase64(encoded)); 
    }
    
    /**
     * 3des解密
     * @param key 密钥，长度必须为8的倍数
     * @param src 待解密字符串
     */
    public static String decode(String key, String src){
    	if(src==null){
    		return null;
    	}
    	
    	byte[] enk = hex(key==null?"":key);
    	byte[] reqPassword = Base64.decodeBase64(src);  
        byte[] srcBytes = null;  
        try {  
            //生成密钥  
            SecretKey deskey = new SecretKeySpec(enk, Algorithm);  
            //解密  
            Cipher c1 = Cipher.getInstance(Algorithm);  
            c1.init(Cipher.DECRYPT_MODE, deskey);  
            srcBytes = c1.doFinal(reqPassword);  
        } catch (java.security.NoSuchAlgorithmException e1) {  
            e1.printStackTrace();  
        }catch(javax.crypto.NoSuchPaddingException e2){  
            e2.printStackTrace();  
        }catch(java.lang.Exception e3){  
            e3.printStackTrace();  
        }  
        
        if(srcBytes==null){
        	return null;
        }
    	return new String(srcBytes); 
    }
    
    private static byte[] hex(String key){  
        String f = DigestUtils.md5Hex(key);  
        byte[] bkeys = new String(f).getBytes();  
        byte[] enk = new byte[24];  
        for (int i=0;i<24;i++){  
            enk[i] = bkeys[i];  
        }  
        return enk;  
    } 
    
    public static void main(String[] args) {  
        String key = "zeromikezeromike";
        String signKey = "sksldjklklj";
        System.out.println("加密前的字符串:" + signKey);  
        String pword = encode(key,signKey);   
        System.out.println("加密后的字符串:" + pword);  
        System.out.println("加密后的字符串:" + pword.length());  
        String src = decode(key,pword);
        System.out.println("解密后的字符串:" + src);  
    }  
}  