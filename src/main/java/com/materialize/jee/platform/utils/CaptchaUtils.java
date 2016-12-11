package com.materialize.jee.platform.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

public class CaptchaUtils {
	private static Logger logger = LoggerFactory.getLogger(CaptchaUtils.class);
	private static String IMG_PATH = "assets/upload/captcha";
	private static Integer VALID_MINS = 10;
	
	private static Producer producer = null;
	static{
		producer = ContextUtils.getBean("captchaProducer");
		String rootPath = System.getProperty("WebProject.root");
		File file = new File(rootPath+File.separator+IMG_PATH);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	/**
	 * 生成验证码图片，返回图片路径（相对于项目根目录）
	 */
	public static String createCaptchaImg(HttpServletRequest req) throws Exception {
        String capText = createCaptchaString(req);
        BufferedImage image = producer.createImage(capText);
        String fileName = IMG_PATH+File.separator+UUIDUtils.createUUID()+".jpg";
        OutputStream out = new FileOutputStream(System.getProperty("WebProject.root")+fileName);
        ImageIO.write(image, "jpg", out);
        try {
            out.flush();
        } finally {
            IOUtils.closeQuietly(out);
        }
        return fileName;
    }
	
	/**
	 * 生成验证码字符串
	 */
	public static String createCaptchaString(HttpServletRequest req){
		String capText =  producer.createText();
		HttpSession session = req.getSession(true);
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
		session.setAttribute(Constants.KAPTCHA_SESSION_DATE, new Date());
		logger.info("生成验证码："+capText);
		return capText;
	}
	
	/**
	 * 验证
	 */
	public static boolean verify(HttpServletRequest req,String code) {
		String expected = (String) req.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        Date createDate = (Date) req.getSession().getAttribute(Constants.KAPTCHA_SESSION_DATE);
        if(createDate==null || expected==null){
        	logger.info("验证失败，提取服务端验证码失败，请重新获取");
        	return false;
        }
        if(new Date().compareTo(DateUtils.addMinute(createDate, VALID_MINS))>0){
        	logger.info("验证失败，验证码过期");
        	return false;
        }
        boolean result = (expected != null && expected.equalsIgnoreCase(code));
        if(result){
        	req.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
        	req.getSession().removeAttribute(Constants.KAPTCHA_SESSION_DATE);
        }
        return result;
    }
	
}
