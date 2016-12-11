/**
 * @Description sftp上传下载
 * @Package com.huateng.netbank.util
 * @author shukun.zhao
 * @date 2015-3-19 下午3:15:33 
 * @Copyright Copyright (c) 2014
 * @Company Shanghai Huateng Software Systems Co., Ltd.
 */
package com.materialize.jee.platform.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * @author zhaoshukun
 * @Description 单线程sftp上传下载工具类,暂不支持断点续传，暂只支持单文件操作
 */
public class SFTPUtil {
	/**
	* 连接sftp服务器
	* @param host 主机
	* @param port 端口
	* @param username 用户名
	* @param password 密码
	* @return
	*/
	protected ChannelSftp connect() throws Exception {
		System.out.println("Session connecting.");
		ChannelSftp sftp = null;
		JSch jsch = new JSch();
		jsch.getSession(loginName, host, port);
		sshSession = jsch.getSession(loginName, host, port);
		sshSession.setPassword(loginPwd);
		Properties sshConfig = new Properties();
		sshConfig.put("StrictHostKeyChecking", "no");
		sshSession.setConfig(sshConfig);
		sshSession.connect();
		System.out.println("Session connected.");
		System.out.println("Opening Channel.");
		channel = sshSession.openChannel("sftp");
		channel.connect();
		sftp = (ChannelSftp) channel;
		System.out.println("Connected to " + host + ".");
		return sftp;
	}
	
	//关闭链接
	protected void closeChannel(){
		if(channel!=null){
			channel.disconnect();
		}
		if(sshSession!=null){
			sshSession.disconnect();
		}
		System.out.println("disconnected to " + host);
	}

	/**
	* 上传文件
	* @param directory 上传的目录
	* @param uploadFile 要上传的文件
	* @param sftp
	*/
	public void sftpUpload(String directory, String uploadFile) throws Exception {
		ChannelSftp sftp = connect();
		FileInputStream fin = null;
		System.out.println("sftp upload file \"" + uploadFile + "\" to \""+ directory +"\"");
		try {
			sftpCreateAndChangeIntoDir(sftp, directory);
			System.out.println("sftp change dir to :"+directory+",start to upload file:"+uploadFile);
			File file=new File(uploadFile);
			fin = new FileInputStream(file);
			sftp.put(fin, file.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(fin!=null){
				try{
					fin.close();
				} catch (Exception e) {
				}
			}
			closeChannel();
		}
		System.out.println("sftp upload file end");
	}

	/**
	* 下载单个文件
	* @param directory 下载目录
	* @param downloadFile 下载的文件
	* @param localDir 存在本地的路径
	* @param sftp
	*/
	public void sftDownload(String remoteFile,String localDir) throws Exception{
		ChannelSftp sftp = connect();
		FileOutputStream fos = null;
		String directory = remoteFile.substring(0,remoteFile.lastIndexOf("/"));
		String downloadFile = remoteFile.substring(remoteFile.lastIndexOf("/")+1,remoteFile.length());
		System.out.println("sftp download file \"" + downloadFile + "\" from \""+ directory +"\" to local \""+localDir+"\"");
		try {
			sftp.cd(directory);
			System.out.println("sftp change dir to :"+directory+",start to download file:"+downloadFile);
			File local = new File(localDir);
			if(!local.exists()){
				local.mkdirs();
			}
			File file=new File(localDir+"/"+downloadFile);
			fos = new FileOutputStream(file);
			sftp.get(downloadFile, fos);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(fos!=null){
				try{
					fos.close();
				} catch (Exception e) {
				}
			}
			closeChannel();
		}
		System.out.println("sftp download file end");
	}
	/**
	* 下载整个文件夹
	* @param directory 下载目录
	* @param downloadFile 下载的文件
	* @param localDir 存在本地的路径
	* @param sftp
	*/
	@SuppressWarnings("rawtypes")
	public void sftDownloadAll(String remoteFile, String localDir)
			throws Exception {
		ChannelSftp sftp = connect();
		FileOutputStream fos = null;
		try {
			Vector vector = sftp.ls(remoteFile);
			if (!vector.isEmpty()) {
				File local = new File(localDir);
				if (!local.exists()) {
					local.mkdirs();
				}
				sftp.cd(remoteFile);
				Iterator iter = vector.iterator();
				while (iter.hasNext()) {
					LsEntry lsEntry = (LsEntry)iter.next();
					if (!lsEntry.getFilename().equals(".")
							&& !lsEntry.getFilename().equals("..")) {
						System.out.println("Filename:" + lsEntry.getFilename());
						File file1 = new File(localDir + "/" + lsEntry.getFilename());
						fos = new FileOutputStream(file1);
						sftp.get(lsEntry.getFilename(), fos);
					}
				}
			}

		} catch (Exception e) {
//			e.printStackTrace();
			throw e;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
				}
			}
			closeChannel();
		}
		System.out.println("sftp download file end");
	}
	/**
	* 获取文件列表
	* @param remoteFile 相对路径
	*/
	@SuppressWarnings("rawtypes")
	public List<String> getSftFileList(String remoteFile)
			throws Exception {
		ChannelSftp sftp = connect();
		Vector vector = sftp.ls(remoteFile);
		List<String> fileList = new ArrayList<String>();
		if (!vector.isEmpty()) {
			sftp.cd(remoteFile);
			Iterator iter = vector.iterator();
			while (iter.hasNext()) {
				LsEntry lsEntry = (LsEntry)iter.next();
				if (!lsEntry.getFilename().equals(".")
						&& !lsEntry.getFilename().equals("..")) {
					fileList.add(lsEntry.getFilename());
				}
			}
		}
				return fileList;
		
	}
	/**
	* 删除文件
	* @param directory 要删除文件所在目录
	* @param deleteFile 要删除的文件
	* @param sftp
	*/
	public void sftDelete(String directory, String deleteFile) throws Exception {
		ChannelSftp sftp = connect();
		System.out.println("sftp delete file \"" + deleteFile + "\" from \""+ directory +"\"");
		try {
			sftp.cd(directory);
			System.out.println("sftp change dir to :"+directory+",start to delete file:"+deleteFile);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			closeChannel();
		}
		System.out.println("sftp delete file end");
	}
	
	protected void sftpCreateAndChangeIntoDir(ChannelSftp sftp,String path) throws SftpException{
		String exdir = null;
		try {
			System.out.println("sftp create dirs :"+path);
			if(path.startsWith("/")){
	    		path = path.substring(1, path.length());
	    	}
			if(path.endsWith("/")){
				path = path.substring(0, path.length()-1);
			}
			
			String[] dirs = path.split("/");
			for(String dir:dirs){
				exdir = dir;
				String base = sftp.pwd();
				System.out.println("sftp work base dir :"+base);
//				File f = new File(base+"/"+dir);
//				if(!f.exists()){
//   			sftp.mkdir(dir);
//					System.out.println("sftp create dir :"+dir);
//				}
				sftp.cd(dir);
				System.out.println("sftp change dir to :"+dir);
			}
		} catch (SftpException e) {
			 if(ChannelSftp.SSH_FX_NO_SUCH_FILE == e.id){
				 try {
					sftp.mkdir(exdir);
					sftp.cd(exdir);
				} catch (SftpException e1) {
					throw e;
				}
				 
		    }else{
		    	throw e;
		    }
		}
	}
	
	public SFTPUtil(String host,int port,String loginName,String loginPwd){
		this.host=host;//ftp主机
		this.port=port;//ftp端口
		this.loginName=loginName;//ftp登陆用户名
		this.loginPwd=loginPwd;
	}
	
	private String host;
	private int port;
	private String loginName;
	private String loginPwd;
	
	private Channel channel = null;
	private Session sshSession = null;
	
	public static void main(String[] args) throws Exception {
		/*SFTPUtil sftp = new SFTPUtil("10.143.20.137",22,"weblogic","Haier,137");
		String uploadDir = "/hry/upload/";
		String uploadFille = "/home/weblogic/share/sftptest/jsch-0.1.49.jar";
		sftp.sftpUpload(uploadDir, uploadFille);
		
		String downDir = "/home/weblogic/hry/upload/";
		String downFile = "jsch-0.1.49.jar";
		String localFile = "/home/weblogic/hry/download/download2";
		sftp.sftDownload(downDir+downFile, localFile);*/
		
	}
}
