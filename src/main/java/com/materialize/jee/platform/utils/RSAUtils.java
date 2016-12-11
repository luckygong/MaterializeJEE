
package com.materialize.jee.platform.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.junit.Test;


public class RSAUtils{
	/**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 默认编码
     */
    public static final String DEFAULT_CHARSET = "utf-8";
	/**
	 * 签名算法
	 */
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	/**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 128;
     
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
	
    /**
	* RSA签名
	* @param content 待签名数据
	* @param privateKey 私钥
	* @return 签名值
	*/
	public static String sign(String content, String privateKey){
		return sign(content, privateKey, DEFAULT_CHARSET);
	}
    
	/**
	* RSA签名
	* @param content 待签名数据
	* @param privateKey 私钥
	* @param inputCharset 编码格式
	* @return 签名值
	*/
	public static String sign(String content, String privateKey, String inputCharset){
        try 
        {
        	PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec( Base64.decodeBase64(privateKey) ); 
        	KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
        	PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(inputCharset) );
            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param publicKey 公钥
	* @return 布尔值
	*/
	public static boolean verify(String content, String sign, String publicKey){
		return verify(content, sign, publicKey, DEFAULT_CHARSET);
	}
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param publicKey 公钥
	* @param inputCharset 编码格式
	* @return 布尔值
	*/
	public static boolean verify(String content, String sign, String publicKey, String inputCharset){
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	        byte[] encodedKey = Base64.decodeBase64(publicKey);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
		
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(inputCharset) );
			return signature.verify( Base64.decodeBase64(sign) );
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	* 私钥解密
	* @param content 密文
	* @param privatekey 私钥
	* @param inputCharset 编码格式
	* @return 解密后的字符串
	*/
	public static String decryptByPrivateKey(String content, String privateKey) throws Exception {
		return decryptByPrivateKey(content, privateKey, DEFAULT_CHARSET);
	}
	
	/**
	* 私钥解密
	* @param content 密文
	* @param privatekey 私钥
	* @param inputCharset 编码格式
	* @return 解密后的字符串
	*/
	public static String decryptByPrivateKey(String content, String privateKey, String inputCharset) throws Exception {
        PrivateKey prikey = getPrivateKey(privateKey);

        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decodeBase64(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[MAX_DECRYPT_BLOCK];
        int bufl;
        try{
	        while ((bufl = ins.read(buf)) != -1) {
	            byte[] block = null;
	            if (buf.length == bufl) {
	                block = buf;
	            } else {
	                block = new byte[bufl];
	                for (int i = 0; i < bufl; i++) {
	                    block[i] = buf[i];
	                }
	            }
	            writer.write(cipher.doFinal(block));
	        }
	        return new String(writer.toByteArray(), inputCharset);
        }catch(Exception e){
        	e.printStackTrace();
        	throw e;
        }finally{
        	IOUtils.closeQuietly(writer);
        }
    }
	
	/**
     * 公钥解密
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String content, String publicKey) throws Exception {
    	return decryptByPublicKey(content, publicKey, DEFAULT_CHARSET);
    }
	
	/**
     * 公钥解密
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param inputCharset 编码格式
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String content, String publicKey, String inputCharset) throws Exception {
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        byte[] encryptedData = Base64.decodeBase64(content);
        int inputLen = encryptedData.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        try{
        	while (inputLen - offSet > 0) {  
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);  
                } else {  
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
                }  
                out.write(cache, 0, cache.length);  
                i++;  
                offSet = i * MAX_DECRYPT_BLOCK;  
            }
	        return new String(out.toByteArray(), inputCharset);
        }catch(Exception e){
        	e.printStackTrace();
        	throw e;
        }finally{
        	IOUtils.closeQuietly(out);
        }
    }
	
    /**
     * 公钥加密
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String content, String publicKey) throws Exception {
    	return encryptByPublicKey(content, publicKey, DEFAULT_CHARSET);
    }
    
    /**
     * 公钥加密
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @param inputCharset 编码格式
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String content, String publicKey, String inputCharset) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        byte[] data = content.getBytes(inputCharset);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        try{
	        while (inputLen - offSet > 0) {
	            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
	                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
	            } else {
	                cache = cipher.doFinal(data, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            i++;
	            offSet = i * MAX_ENCRYPT_BLOCK;
	        }
	        return new String(Base64.encodeBase64(out.toByteArray()));
        }catch(Exception e){
        	e.printStackTrace();
        	throw e;
        }finally{
        	IOUtils.closeQuietly(out);
        }
    }
    
    /**
     * 私钥加密
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String content, String privateKey) throws Exception {
    	return encryptByPrivateKey(content, privateKey, DEFAULT_CHARSET);
    }
 
    /**
     * 私钥加密
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @param inputCharset 编码格式
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String content, String privateKey, String inputCharset) throws Exception {
    	PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec( Base64.decodeBase64(privateKey) ); 
    	KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
    	PrivateKey priKey = keyf.generatePrivate(priPKCS8);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, priKey);
        byte[] data = content.getBytes(inputCharset);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        try{
	        while (inputLen - offSet > 0) {
	            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
	                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
	            } else {
	                cache = cipher.doFinal(data, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            i++;
	            offSet = i * MAX_ENCRYPT_BLOCK;
	        }
	        return new String(Base64.encodeBase64(out.toByteArray()));
        }catch(Exception e){
        	e.getMessage();
        	throw e;
        }finally{
        	IOUtils.closeQuietly(out);
        }
    }
    
    /**
	* 得到私钥
	* @param privateKey 密钥字符串（经过base64编码）
	* @throws Exception
	*/
	public static PrivateKey getPrivateKey(String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey key = keyFactory.generatePrivate(keySpec);
		return key;
	}
    
	@Test
    public static void test() throws Exception {
		String content = "搜索了肯德基";
		String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnkEF/H13p6gcAE9e+Td94xp9cnDgmhZ+ogt8KXX0iZFce9pEyG/OQ1rFg4JPqMzsARx3sr7o5MpFY76tNR1NV1xwndji/+wIkFfEdpoFpFE1MLRZYcu43uNlRfP/OrEzo5T6s6VTNjMDHZMneD0O3bvHV2bnCtwgYL1d6I0jyIQIDAQAB";
		String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKeQQX8fXenqBwAT175N33jGn1ycOCaFn6iC3wpdfSJkVx72kTIb85DWsWDgk+ozOwBHHeyvujkykVjvq01HU1XXHCd2OL/7AiQV8R2mgWkUTUwtFlhy7je42VF8/86sTOjlPqzpVM2MwMdkyd4PQ7du8dXZucK3CBgvV3ojSPIhAgMBAAECgYEAi8qyia6Od/6RNoRXYeGvxR/Xhbiis6iY6Um9b/VM1ytk72T+/xIIolYqh0r0u9dfqryp/3MfZmaEkfDpNcCCiWoO683fxeirN5W3EyeLJXbim861iId0QEh/5Z333LkwezLQvzmyckMB7IgcLKLHpy2QSe9JoSRqhoKknqT+xxECQQDUAmQP/7A/GxK0gXt6c2UTYq2qrMP45XVLHeC3w2g4e3g5mYzdz0lG5T4izUh0/qTTgSAlx2KzcG+VRx8U6ZVNAkEAylT4cyI8l5sCQxZqHlDTe030KhJOnj4oG9blfnUVox3sGM+ighOkCiFg0m8uyAvkyTm3AHrGeFJQvA+SiZjWJQJBAK6qPZf0tlZ3ToTJ/FnYwhgjfyKS7W2cVXMKbX3YDoAiMN17hNOlGCDBctynNZ+1v0PFKSAW0Vk2M6fPjWrkOi0CQAwpLUGmRiQKkgk8OKaXzw4zMkCxFv6V181MjkCagvFp4/3dxPOygIKHXQ1o11P4uwiJKoGh33nt4KJdAT656+0CQCDA5MtVaI336ACZMmQ6PIREp0diiK3zPBiK8lizpmHP6jx4+rPqpsmeaIIOQB9bBW29VRQburm+G2TTw9/ukio=";
		
		System.out.println("原始数据："+content);
		String encode = RSAUtils.encryptByPrivateKey(content, privateKey, "utf-8");
		System.out.println("私钥加密后数据："+encode);
		String decode = RSAUtils.decryptByPublicKey(encode, publicKey, "utf-8");
		System.out.println("公钥解密后数据："+decode);
		encode = RSAUtils.encryptByPublicKey(content, publicKey, "utf-8");
		
		String sign = RSAUtils.sign(content, privateKey, "utf-8");
		System.out.println("私钥签名数据："+sign);
		System.out.println("公钥验证签名："+RSAUtils.verify(content, sign, publicKey, "utf-8"));
	}

}
