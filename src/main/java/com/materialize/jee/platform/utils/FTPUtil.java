/**
 * @Description ftp上传下载
 * @Package com.huateng.netbank.util
 * @author shukun.zhao
 * @date 2015-1-26 上午10:00:38 
 * @Copyright Copyright (c) 2014
 * @Company Shanghai Huateng Software Systems Co., Ltd.
 */
package com.materialize.jee.platform.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * @Description 单线程ftp上传下载工具类,暂不支持断点续传
 * @author zhaoshukun
 *
 */
public class FTPUtil {
	
	/**
     * @Description ftp上传 文件或文件夹
     * @param fileName 待上传文件完整路径名
     * @param targetDir 文件上传路径,从ftp工作空间开始的相对路径
     */
	public  void ftpUpload(String fileName,String targetDir) throws Exception{
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(host, port);
			ftpClient.login(loginName, loginPwd);
			File srcFile = new File(fileName);
			if(!srcFile.exists()){
				throw new Exception("文件不存在！");
			}
			//设置上传目录
			if(!ftpClient.changeWorkingDirectory(targetDir)){
				createAndChangeIntoDir(ftpClient,targetDir);
			}
			ftpClient.setBufferSize(1024);
			ftpClient.setControlEncoding("GBK");
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			
			uploadFile(srcFile,ftpClient);
			
		} catch (SocketException e) {
			e.printStackTrace();
			throw (Exception)e;
		} catch (IOException e) {
			e.printStackTrace();
			throw (Exception)e;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			ftpClient.disconnect();
		}
		System.out.println("FTP文件上传成功！");
	}
	
	/**
     * @Description ftp上传 文件
     * @param fileName 待上传文件完整路径名
     * @param targetDir 文件上传路径,从ftp工作空间开始的相对路径
     */
	public void uploadFile(String filePath, String simpleName, InputStream fileStream) throws Exception{
		if(fileStream!=null && !StringUtils.isBlank(simpleName)){
			FTPClient ftpClient = new FTPClient();
			try {
				ftpClient.connect(host, port);
				ftpClient.login(loginName, loginPwd);
				
				ftpClient.setBufferSize(1024);
				ftpClient.setControlEncoding("GBK");
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
				if(!StringUtils.isBlank(filePath)){
					createAndChangeIntoDir(ftpClient, filePath);
				}
				ftpClient.storeFile(simpleName, fileStream);
				ftpClient.changeToParentDirectory();
			}finally{
				ftpClient.disconnect();
			}
		}
	}
	
	/**
     * @Description ftp下载 下载单个文件
     * @param fileName 待下载文件,从ftp工作空间开始的相对路径
     * @param targetDir 文件下载保存路径,本地完整路径名
     */
	public void ftpDownloadSingleFile(String fileName,String targetDir) throws Exception{
		FTPClient ftpClient = new FTPClient();
		FileOutputStream fos = null;
		try {
			ftpClient.connect(host, port);
			ftpClient.login(loginName, loginPwd);
			File dir = new File(targetDir);
			if(!dir.exists()){
				dir.mkdirs();
			}
			String simpleName = fileName.substring(fileName.lastIndexOf("/")+1,fileName.length());
			String local="";
			if(targetDir.endsWith("/")){
				local = targetDir+simpleName;
			}else{
				local = targetDir+"/"+simpleName;
			}
			fos = new FileOutputStream(new File(local));
			ftpClient.setBufferSize(1024);
			ftpClient.setControlEncoding("GBK");
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			
			ftpClient.retrieveFile(fileName, fos);
			
		} catch (SocketException e) {
			e.printStackTrace();
			throw (Exception)e;
		} catch (IOException e) {
			e.printStackTrace();
			throw (Exception)e;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(fos!=null){
				fos.close();
			}
			ftpClient.disconnect();
		}
		System.out.println("FTP文件下载成功！");
	}
	
	/**
     * @Description ftp下载 下载文件文件夹
     * @param remotePath 待下载文件夹路径,从ftp工作空间开始的相对路径
     * @param targetDir 文件下载保存路径,本地完整路径名
     */
	public void ftpDownloadFolder(String remotePath,String targetDir) throws Exception{
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(host, port);
			ftpClient.login(loginName, loginPwd);
			
			if(remotePath.endsWith("/")){
				remotePath=remotePath.substring(0, remotePath.length()-1);
			}
			
			String localName = targetDir;
			File dir = new File(localName);
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			ftpClient.setBufferSize(1024);
			ftpClient.setControlEncoding("GBK");
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			
			if(ftpClient.changeWorkingDirectory(remotePath)){
				downFile(remotePath,localName,ftpClient);
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
			throw (Exception)e;
		} catch (IOException e) {
			e.printStackTrace();
			throw (Exception)e;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			ftpClient.disconnect();
		}
		System.out.println("FTP文件下载成功！");
	}
	
	/**
     * @Description ftp删除文件
     * @param fileName 待删除文件路径,从ftp工作空间开始的相对路径
     * @param isFile 是否是文件，true：文件 false：文件夹(删除文件夹及文件夹下所有文件)
     */
	public void ftpDelete(String fileName,boolean isFile) throws Exception{
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(host, port);
			ftpClient.login(loginName, loginPwd);
			
			ftpClient.setBufferSize(1024);
			ftpClient.setControlEncoding("GBK");
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			deleteFile(fileName,isFile,ftpClient);
		} catch (SocketException e) {
			e.printStackTrace();
			throw (Exception)e;
		} catch (IOException e) {
			e.printStackTrace();
			throw (Exception)e;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			ftpClient.disconnect();
		}
		System.out.println("FTP删除成功！");
	}
	
	protected void deleteFile(String fileName,boolean isFile,FTPClient ftpClient) throws Exception{
		if(isFile){
			ftpClient.deleteFile(fileName);
		}else{
			if(ftpClient.changeWorkingDirectory(fileName)){
				FTPFile[] files = ftpClient.listFiles();
				for(FTPFile file:files){
					deleteFile(file.getName(),file.isFile(),ftpClient);
				}
				ftpClient.changeToParentDirectory();
				ftpClient.removeDirectory(fileName);
			}
		}
	}
	
	protected void uploadFile(File fileName,FTPClient ftpClient) throws Exception{
		if(fileName.isDirectory()){
			createAndChangeIntoDir(ftpClient,fileName.getName());
			File[] files = fileName.listFiles();
			for(int i=0;i<files.length;i++){
				if(!".".equals(files[i].getName()) && !"..".equals(files[i].getName())){
					uploadFile(files[i],ftpClient);
				}
			}
		}else{
			FileInputStream fis = null;
			try{
				fis = new FileInputStream(fileName);
				ftpClient.storeFile(fileName.getName(), fis);
				ftpClient.changeToParentDirectory();
			}finally{
				if(fis!=null){
					fis.close();
				}
			}
		}
	}
	
	
	
	protected void downFile(String remotefileName,String localFileName,FTPClient ftpClient) throws Exception{
		FTPFile[] files = ftpClient.listFiles();
		for(int i=0;i<files.length;i++){
			FTPFile file = files[i];
			String name = file.getName();
			if(file.isDirectory()){
				ftpClient.changeWorkingDirectory(file.getName());
				File f = new File(localFileName+"/"+file.getName());
				if(!f.exists()){
					f.mkdir();
				}
				downFile(remotefileName+"/"+name,localFileName+"/"+file.getName(),ftpClient);
			}else{
				if(!".".equals(name) && !"..".equals(name)){
					FileOutputStream fos = null;
					try{
						fos = new FileOutputStream(new File(localFileName+"/"+name));
						ftpClient.retrieveFile(remotefileName, fos);
					}finally{
						if(fos!=null){
							fos.close();
						}
					}
				}
			}
		}
		ftpClient.changeToParentDirectory();
	}
	
	//ftp工作目录递归创建文件夹
	protected void createAndChangeIntoDir(FTPClient ftpClient,String path) throws Exception{
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!ftpClient.changeWorkingDirectory(path)) {
        	if(path.startsWith("/")){
        		path = path.substring(1, path.length());
        	}
        	String[] dirs = path.replace("\\", "/").split("/");
        	for(int i=0;i<dirs.length;i++){
        		String dir = dirs[i];
        		if(!ftpClient.changeWorkingDirectory(dir)){
        			if (ftpClient.makeDirectory(dir)) {
        				ftpClient.changeWorkingDirectory(dir);
                    } else {
                        System.out.println("创建目录失败");
                        throw new Exception();
                    }
        		}
        	}
        }
	}
	
	

	public FTPUtil(String host,int port,String loginName,String loginPwd){
		this.host=host;//ftp主机
		this.port=port;//ftp端口
		this.loginName=loginName;//ftp登陆用户名
		this.loginPwd=loginPwd;
	}
	
	private String host;
	private int port;
	private String loginName;
	private String loginPwd;
	
	public static void main(String[] args) throws Exception {
		FTPUtil upload = new FTPUtil("192.168.1.109",21,"ftp","!QAZ3edc");
		FileInputStream fis = null;
			fis = new FileInputStream("D:/图片/IMG_0367.JPG");
		upload.uploadFile("ufit", "1.jpg", fis);
		fis.close();
		
//		FTPUtil download = new FTPUtil("10.143.19.141",2121,"ftp","111111");
//		download.ftpDownloadSingleFile("/local/101.pdf", "F:/kankan/ftp/");
//		download.ftpDownloadFolder("/local/", "F:/kankan/ftp/");
//		
//		upload.ftpDelete("/hry/local/1/", false);
	}
}
