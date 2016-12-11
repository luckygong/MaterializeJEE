package com.materialize.jee.platform.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipUtils {
	/**
	 * 使用给定密码解压指定的ZIP压缩文件到指定目录
	 * 
	 * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
	 * @param zipPath 指定的ZIP压缩文件
	 * @param destDir 解压目录
	 * @param passwd ZIP文件的密码
	 */
	public static File [] unzip(String zipPath, String destDir, String passwd) throws ZipException {
		File zipFile = new File(zipPath);
		return unzip(zipFile, destDir, passwd);
	}
	
	/**
	 * 使用给定密码解压指定的ZIP压缩文件到当前目录
	 * 
	 * @param zipPath 指定的ZIP压缩文件
	 * @param passwd ZIP文件的密码
	 */
	public static File [] unzip(String zipPath, String passwd) throws ZipException {
		File zipFile = new File(zipPath);
		File parentDir = zipFile.getParentFile();
		return unzip(zipFile, parentDir.getAbsolutePath(), passwd);
	}
	
	/**
	 * 使用给定密码解压指定的ZIP压缩文件到指定目录
	 * 
	 * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
	 * @param zip 指定的ZIP压缩文件
	 * @param destDir 解压目录
	 * @param passwd ZIP文件的密码
	 */
	@SuppressWarnings("unchecked")
	public static File [] unzip(File zipFile, String destDir, String passwd) throws ZipException {
		ZipFile zFile = new ZipFile(zipFile);
		zFile.setFileNameCharset("GBK");
		if (!zFile.isValidZipFile()) {
			throw new ZipException("压缩文件不合法,可能被损坏.");
		}
		File dest = new File(destDir);
		if (dest.isDirectory() && !dest.exists()) {
			dest.mkdirs();
		}
		if (zFile.isEncrypted()) {
			zFile.setPassword(passwd.toCharArray());
		}
		zFile.extractAll(destDir);
		
		List<FileHeader> headerList = zFile.getFileHeaders();
		List<File> extractedFileList = new ArrayList<File>();
		for(FileHeader fileHeader : headerList) {
			if (!fileHeader.isDirectory()) {
				extractedFileList.add(new File(destDir,fileHeader.getFileName()));
			}
		}
		File [] extractedFiles = new File[extractedFileList.size()];
		extractedFileList.toArray(extractedFiles);
		return extractedFiles;
	}
	
	/**
	 * 压缩指定文件到当前文件夹
	 * @param src 要压缩的指定文件
	 */
	public static String zip(String src) {
		return zip(src,null);
	}
	
	/**
	 * 使用给定密码压缩指定文件或文件夹到当前目录
	 * 
	 * @param src 要压缩的文件
	 * @param passwd 压缩使用的密码
	 */
	public static String zip(String src, String passwd) {
		return zip(src, null, passwd);
	}
	
	/**
	 * 使用给定密码压缩指定文件或文件夹到当前目录
	 * 
	 * @param src 要压缩的文件
	 * @param destFile 压缩文件完整路径名
	 * @param passwd 压缩使用的密码
	 */
	public static String zip(String src, String destFile, String passwd) {
		return zip(src, destFile, true, passwd);
	}
	
	/**
	 * 使用给定密码压缩指定文件或文件夹到指定位置.
	 * 
	 * @param src 要压缩的文件或文件夹路径
	 * @param destFile 压缩文件完整路径名
	 * @param isCreateDir 是否在压缩文件里创建目录,仅在压缩文件为目录时有效.如果为false,将直接压缩目录下文件到压缩文件.
	 * @param passwd 压缩使用的密码
	 */
	public static String zip(String src, String destFile, boolean isCreateDir, String passwd) {
		File srcFile = new File(src);
		destFile = buildDestinationZipFilePath(srcFile, destFile);
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);			// 压缩方式
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);	// 压缩级别
		if (!StringUtils.isEmpty(passwd)) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);	// 加密方式
			parameters.setPassword(passwd.toCharArray());
		}
		try {
			ZipFile zipFile = new ZipFile(destFile);
			if (srcFile.isDirectory()) {
				// 如果不创建目录的话,将直接把给定目录下的文件压缩到压缩文件,即没有目录结构
				if (!isCreateDir) {
					File [] subFiles = srcFile.listFiles();
					ArrayList<File> temp = new ArrayList<File>();
					Collections.addAll(temp, subFiles);
					zipFile.addFiles(temp, parameters);
					return destFile;
				}
				zipFile.addFolder(srcFile, parameters);
			} else {
				zipFile.addFile(srcFile, parameters);
			}
			return destFile;
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 构建压缩文件存放路径,如果不存在将会创建
	 * 
	 * @param srcFile 源文件
	 * @param destParam 压缩目标完整路径
	 */
	private static String buildDestinationZipFilePath(File srcFile,String destParam) {
		if (StringUtils.isEmpty(destParam)) {
			if (srcFile.isDirectory()) {
				destParam = srcFile.getParent() + File.separator + srcFile.getName() + ".zip";
			} else {
				String fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
				destParam = srcFile.getParent() + File.separator + fileName + ".zip";
			}
		} else {
			File destFile = new File(destParam);
			// 在指定路径不存在的情况下将其创建出来
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}	
		}
		return destParam;
	}
	
	public static void main(String[] args) throws ZipException {
		zip("F:/kankan/lib", "F:/kankan/zip/lib/", "11");
//		try {
//			File[] files = unzip("d:\\test\\汉字.zip", "aa");
//			for (int i = 0; i < files.length; i++) {
//				System.out.println(files[i]);
//			}
//		} catch (ZipException e) {
//			e.printStackTrace();
//		}
	}
}
