package com.materialize.jee.platform.hadoop.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HDFSFileSystemUtils {
	private static Logger logger = LoggerFactory.getLogger(HDFSFileSystemUtils.class);
	
	public static final Long BUFFER_SIZE = 4096L;
	public static final Long NOTICE_SIZE = 64*1024L;//现hadoop版本每向datanode写入64kb进行通知，后续版本可能改变
	/**
	 * @Description: 删除HDFS中指定文件或目录    
	 * @param conf HDFS配置信息
	 * @param remote 文件或目录在HDFS中的路径,从/根目录开始
	 */
	public static Boolean delete(Configuration conf,Path remote){
		Boolean result = false;
		FileSystem fs = null;
		try{
			fs = getDefaultFileSystem(conf);
			if(!fs.exists(remote)){
				logger.info("delete hdfs directory with return true : source file "+remote.toString()+" not exists");
				return true;
			}
			if(fs.isDirectory(remote)){
				result = fs.delete(remote, true);
				logger.info("delete hdfs directory with return "+result+":"+remote.toString());
			}else{
				result = fs.delete(remote, false);
				logger.info("delete hdfs file with return "+result+":"+remote.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeFileSystem(fs);
		}
		return result;
	}
	
	/**
	 * @Description: 重命名文件    
	 * @param conf HDFS配置信息
	 * @param remote 文件或目录在HDFS中的路径,从/根目录开始
	 * @param newName 新文件名，不包含父路径的简单文件名
	 */
	public static Boolean rename(Configuration conf,Path remote,String newName){
		Boolean result = false;
		FileSystem fs = null;
		try{
			fs = getDefaultFileSystem(conf);
			if(!fs.exists(remote)){
				logger.info("rename hdfs directory fail: source file "+remote.toString()+" not exists");
				return false;
			}
            String fileName = remote.getParent()+Path.SEPARATOR+newName;
            Path newFile = new Path(fileName);
            if(!fs.exists(newFile)){
            	result = fs.rename(remote, new Path(fileName));
            	logger.info("rename hdfs file with return "+result+": from "+remote.toString()+" to "+fileName);
            }else{
            	logger.info("rename hdfs file fail: target file "+newName+" exists");
            }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeFileSystem(fs);
		}
		return result;
	}
	
	/**
	 * @Description: 创建目录    
	 * @param conf HDFS配置信息
	 * @param remote 文件或目录在HDFS中的路径,从/根目录开始
	 */
	public static Boolean mkdirs(Configuration conf,Path remote) {
		Boolean result = false;
		FileSystem fs = null;
		try{
			fs = getDefaultFileSystem(conf);
			if(!fs.exists(remote)){
				result = fs.mkdirs(remote);
				logger.info("make hdfs dir with return "+result+": "+remote.toString());
			}else{
				if(!fs.isDirectory(remote)){
					result = false;
					logger.info("make hdfs dir fail: target "+remote.toString()+" exists, but is not a dir");
				}else{
					result = true;
					logger.info("make hdfs dir with return true : target dir "+remote.toString()+" exists");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeFileSystem(fs);
		}
		return result;
    }
	
	/**
	 * @Description: 创建一个新文件，文件内容为空，不能覆盖同名文件    
	 * @param conf HDFS配置信息
	 * @param remote 文件或目录在HDFS中的路径,从/根目录开始
	 */
	public static Boolean newFile(Configuration conf, Path remote){
        return newFile(conf, remote, "",false);
    }
	
	/**
	 * @Description: 创建一个新文件，文件内容为空 
	 * @param conf HDFS配置信息
	 * @param remote 文件或目录在HDFS中的路径,从/根目录开始
	 * @param overwrite 是否可以覆盖已有文件
	 */
	public static Boolean newFile(Configuration conf, Path remote,boolean overwrite){
		return newFile(conf, remote, "",overwrite);
	}
	
	/**
	 * @Description: 创建一个新文件，文件内容为空，不能覆盖同名文件    
	 * @param conf HDFS配置信息
	 * @param remote 文件或目录在HDFS中的路径,从/根目录开始
	 */
	public static Boolean newFile(Configuration conf, Path remote, String content){
		return newFile(conf, remote, content , false);
	}
	
	/**
	 * @Description: 创建一个新文件    
	 * @param conf HDFS配置信息
	 * @param remote 文件或目录在HDFS中的路径,从/根目录开始
	 * @param content 文件内容
	 * @param overwrite 是否可以覆盖已有文件
	 */
	public static Boolean newFile(Configuration conf, Path remote, String content,boolean overwrite){
        Boolean result = false;
        FSDataOutputStream os = null;
        FileSystem fs = null;
        try {
        	fs = getDefaultFileSystem(conf);
        	if(fs.exists(remote) && !overwrite){
				logger.info("create new hdfs file fail: "+remote.toString()+" exists when overwrite is false");
				return false;
			}
    		if(!StringUtils.isEmpty(content)){
    			os = fs.create(remote);
    			byte[] buff = null;
    			buff = content.getBytes();
    			os.write(buff, 0, buff.length);
    			result = true;
    			logger.info("create new hdfs file with return true: "+remote.toString()+" append content '"+content+"'");
    		}else{
    			result = fs.createNewFile(remote);
    			logger.info("create new empty hdfs file with return "+result+ ": "+remote.toString());
    		}
        }catch(Exception e){
        	e.printStackTrace();
        }finally {
        	IOUtils.closeStream(os);
        	closeFileSystem(fs);
        }
        return result;
    }

	/**
	 * @Description: 上传文件，不能覆盖已有文件，无法打印进度，每64k可以显示一个'.'
	 * @param conf HDFS配置信息
	 * @param remote 文件或目录在HDFS中的路径,从/根目录开始
	 * @param content 文件内容
	 */
	public static Boolean upload(Configuration conf, Path remote, InputStream localInputStream){
		return upload(conf, remote, localInputStream, false);
	}
	
	/**
	 * @Description: 上传文件，无法打印进度，每64k可以显示一个'.'
	 * @param conf HDFS配置信息
	 * @param remote 文件或目录在HDFS中的路径,从/根目录开始
	 * @param content 文件内容
	 * @param overwrite 是否可以覆盖已有文件
	 */
	public static Boolean upload(Configuration conf, Path remote, InputStream localInputStream,boolean overwrite){
        Boolean result = false;
        OutputStream  os = null;
        FileSystem fs = null;
        try {
        	fs = getDefaultFileSystem(conf);
        	if(fs.exists(remote) && !overwrite){
				logger.info("upload file to hdfs through stream fail: "+remote.toString()+" exists when overwrite is false");
				return false;
			}
        	
            os = fs.create(remote, new Progressable() { public void progress() {System.out.print(".");}});
            IOUtils.copyBytes(localInputStream, os, BUFFER_SIZE, true);
            result = true;
            logger.info("upload file to hdfs through stream with return true: "+remote.toString());
        }catch(Exception e){
        	e.printStackTrace();
        }finally {
        	IOUtils.closeStream(os);
        	closeFileSystem(fs);
        }
        return result;
    }
	
	/**
	 * @Description: 上传文件,可以打印进度(进度可能不准确)
	 * @param conf HDFS配置信息
	 * @param remote 文件或目录在HDFS中的路径,从/根目录开始
	 * @param local 待上传文件
	 */
	public static Boolean upload(Configuration conf, Path remote, File local){
		return upload(conf, remote, local, false);
	}
	
	/**
	 * @Description: 上传文件,可以打印进度(进度可能不准确)
	 * @param conf HDFS配置信息
	 * @param remote 文件或目录在HDFS中的路径,从/根目录开始
	 * @param local 待上传文件
	 * @param overwrite 是否可以覆盖已有文件
	 */
	public static Boolean upload(Configuration conf, Path remote, File local,boolean overwrite){
		Boolean result = false;
		FSDataOutputStream  os = null;
		InputStream in = null;
		FileSystem fs = null;
		try {
			fs = getDefaultFileSystem(conf);
			if(fs.exists(remote) && !overwrite){
				logger.info("upload file to hdfs through file fail: "+remote.toString()+" exists when overwrite is false");
				return false;
			}
			if(!local.exists()){
    			logger.info("upload file to hdfs through file fail: "+local.getPath()+" not exists");
    			return false;
    		}
			in = new BufferedInputStream(new FileInputStream(local));  
			os = fs.create(remote, new HDFSFileSystemUtils.PercentProgressable(local.length()));
			IOUtils.copyBytes(in, os, BUFFER_SIZE.intValue());
			result = true;
			logger.info("upload file to hdfs through file with return true: "+local.getName()+" to "+remote.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			IOUtils.closeStream(in);
			IOUtils.closeStream(os);
			closeFileSystem(fs);
		}
		return result;
	}
	
	/**
	 * @Description: 下载文件    
	 * @param conf HDFS配置信息
	 * @param remote 文件或目录在HDFS中的路径,从/根目录开始
	 * @param local 本地保存路径，完整路径
	 */
	public static Boolean download(Configuration conf,Path remote, String local){
		Boolean result = false;
		FileSystem fs = null;
		try {
			fs = getDefaultFileSystem(conf);
			if(!fs.exists(remote)){
				logger.info("download file from hdfs fail: "+remote.toString()+" not exists");
				return false;
			}
			File file = new File(local);
			if(!file.exists() || !file.isDirectory()){
				file.mkdirs();
			}
			fs.copyToLocalFile(remote, new Path(local));
			result = true;
			logger.info("download file from hdfs with return true: "+remote.toString()+" to "+local);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			closeFileSystem(fs);
		}
		return result;
    }

	/**
	 * @Description: 追加内容    
	 * @param conf HDFS配置信息
	 * @param remote 文件或目录在HDFS中的路径,从/根目录开始
	 * @param content 追加内容
	 */
    public static Boolean append(Configuration conf, Path remote, String content) {
    	Boolean result = false;
		FileSystem fs = null;
		OutputStream os = null;
		try {
			fs = getDefaultFileSystem(conf);
			if(!fs.exists(remote)){
				logger.info("append String to hdfs file fail: "+remote.toString()+" not exists");
				return false;
			}
			if(!StringUtils.isEmpty(content)){
				os = fs.append(remote);  
				byte[] buff = content.getBytes();
				os.write(buff, 0, buff.length);
			}
			result = true;
			logger.info("append String to hdfs file with return true: "+remote.toString()+" append '"+content+"'");
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			IOUtils.closeStream(os);
			closeFileSystem(fs);
		}
		return result;
    }
    
    /**
     * @Description: 追加内容    
     * @param conf HDFS配置信息
     * @param remote 文件或目录在HDFS中的路径,从/根目录开始
     * @param localInputStream 输入流
     */
    public static Boolean append(Configuration conf, Path remote, InputStream localInputStream) {
    	Boolean result = false;
    	FileSystem fs = null;
    	OutputStream os = null;
    	try {
    		fs = getDefaultFileSystem(conf);
    		if(!fs.exists(remote)){
    			logger.info("append to hdfs file through stream fail: "+remote.toString()+" not exists");
    			return false;
    		}
    		os = fs.append(remote); 
    		IOUtils.copyBytes(localInputStream, os, BUFFER_SIZE.intValue());
    		result = true;
    		logger.info("append to hdfs file through stream with return true: "+remote.toString());
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally {
    		IOUtils.closeStream(os);
    		closeFileSystem(fs);
    	}
    	return result;
    }
    
    /**
     * @Description: 追加内容    
     * @param conf HDFS配置信息
     * @param remote 文件或目录在HDFS中的路径,从/根目录开始
     * @param local 本地文件
     */
    public static Boolean append(Configuration conf, Path remote, File local) {
    	Boolean result = false;
    	FileSystem fs = null;
    	OutputStream os = null;
    	InputStream in = null;
    	try {
    		fs = getDefaultFileSystem(conf);
    		if(!fs.exists(remote)){
    			logger.info("append to hdfs file through file fail: "+remote.toString()+" not exists");
    			return false;
    		}
    		if(!local.exists()){
    			logger.info("append to hdfs file through file fail: "+local.getPath()+" not exists");
    			return false;
    		}
    		os = fs.append(remote); 
    		in = new BufferedInputStream(new FileInputStream(local));  
    		IOUtils.copyBytes(in, os, BUFFER_SIZE.intValue());
    		result = true;
    		logger.info("append to hdfs file through file with return true: "+local.getPath()+" to "+remote.toString());
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally {
    		IOUtils.closeStream(os);
    		IOUtils.closeStream(in);
    		closeFileSystem(fs);
    	}
    	return result;
    }
	
    /**
     * @Description: 合并多个文件，将文件合并到第一个非目录文件尾部，保留原文件，若存在目录不进行递归   
     * @param conf HDFS配置信息
     * @param remotes 文件或目录在HDFS中的路径,从/根目录开始
     */
    public static Boolean merge(Configuration conf, Path... remotes ) {
    	return merge(conf, false,false, remotes);
    }
    public static Boolean merge(Configuration conf, Boolean keepone,Boolean recursion, Path... remotes) {
    	Boolean result = false;
    	FileSystem fs = null;
    	OutputStream os = null;
    	InputStream in = null;
    	try {
    		if(remotes==null || remotes.length==0){
    			logger.info("merge hdfs file with return true: file number is 0, no merge");
    			return true;
    		}
    		List<Path> toBeMerge = new ArrayList<Path>();
    		if(recursion){
    			List<FileStatus> status = new ArrayList<FileStatus>(); 
    			for(int i=0;i<remotes.length;i++){
    				List<FileStatus> child = listFileStatus(conf, remotes[i], recursion);
    				status.addAll(child);
    			}
    			for(FileStatus s : status){
    				if(s.isFile()){
    					toBeMerge.add(s.getPath());
    				}
    			}
    		}
    		
    		fs = getDefaultFileSystem(conf);
    		for(Path p : remotes){
    			if(fs.isFile(p)){
    				toBeMerge.add(p);
    			}
    		}
    		if(toBeMerge.size()>1){
				os = fs.append(toBeMerge.get(0));
				for(int i=1;i<toBeMerge.size();i++){
					in = fs.open(toBeMerge.get(i));
					IOUtils.copyBytes(in, os, BUFFER_SIZE.intValue());
				}
				if(keepone){
					for(int i=1;i<toBeMerge.size();i++){
    					fs.delete(toBeMerge.get(i), false);
    				}
				}
			}
    		result = true;
    		logger.info("merge hdfs file with return true: file number is "+toBeMerge.size()+", and merged file had "+(keepone?"":"not")+" been deleted");
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally {
    		IOUtils.closeStream(os);
    		IOUtils.closeStream(in);
    		closeFileSystem(fs);
    	}
    	return result;
    }
    
	/**
	 * @Description: 查看文件属性    
	 * @param conf HDFS配置信息
	 * @param file 文件或目录在HDFS中的路径,从/根目录开始
	 */
	public static List<FileStatus> listFileStatus(Configuration conf,Path file) throws Exception{
		return listFileStatus(conf, file, false);
	}
		/**
		 * @Description: 查看文件属性    
		 * @param conf HDFS配置信息
		 * @param file 文件或目录在HDFS中的路径,从/根目录开始
		 */
		public static List<FileStatus> listFileStatus(Configuration conf,Path file,Boolean recursion) throws Exception{
		FileSystem fs = getDefaultFileSystem(conf);
		List<FileStatus> result = new ArrayList<FileStatus>();
		FileStatus[] status = fs.listStatus(file);
		if(recursion){
			for(int i=0;status!=null && i<status.length;i++){
				FileStatus s = status[i];
				result.add(s);
				if(s.isDirectory()){
					result.addAll(listFileStatus(conf, s.getPath(), recursion));
				}
			}
		}else{
			result.addAll(Arrays.asList(status));
		}
		closeFileSystem(fs);
		return result;
	}
	
	/**
	 * @Description: 获取defaultFS配置
	 * @param conf HDFS配置信息
	 */
	public static String getDefaultFS(Configuration conf){
		String defaultFS = conf.get("fs.defaultFS");
		return defaultFS;
	}
	
	/**
	 * @Description: 获取默认文件系统实例    
	 * @param conf HDFS配置信息
	 */
	public static FileSystem getDefaultFileSystem(Configuration conf) throws Exception{
		String defaultFS = getDefaultFS(conf);
		FileSystem fs = FileSystem.get(URI.create(defaultFS), conf);
		return fs;
	}
	
	/**
	 * @Description: 关闭文件系统实例    
	 * @param fs 文件系统实例
	 */
	public static void closeFileSystem(FileSystem fs){
		if(fs!=null){
			IOUtils.closeStream(fs);
		}
	}
	
	/**
	 * 上传下载进度
	 */
	private static class PercentProgressable implements Progressable{
		private Long total = 0L;
		private Long progressNum = 0L;
		private Long percent = 0L;
		public PercentProgressable(Long total){
			this.total = total;
		}
		
		@Override
		public void progress() {
			progressNum++;
			if(total==0){
				logger.info("progressing percent:100%");
			}else{
				Long newPercent = progressNum*NOTICE_SIZE*100/total;
				if(percent != newPercent){
					percent = newPercent;
					logger.info("progressing percent:"+ (percent>100?100:percent)+"%");
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
//		System.out.println(HDFSUtils.mkdirs(HadoopConfigUtils.getInstance(true), new Path("/input/test/test1")));
//		HDFSUtils.upload(HadoopConfigUtils.getInstance(true), new Path("/input/Head First jQuery.pdf")
//				,new File("C:/Users/97802/Desktop/Head First jQuery.pdf"));
//		System.out.println(HDFSUtils.append(HadoopConfigUtils.getInstance(true), new Path("/input/file1"), "i am ok"));
//		System.out.println(HDFSUtils.newFile(HadoopConfigUtils.getInstance(true), new Path("/input/file1"),"hello hadoop, i am johe",true));
//		System.out.println(HDFSUtils.rename(HadoopConfigUtils.getInstance(true), new Path("/input/file4"), "file5"));
//		List<FileStatus> list = HDFSUtils.listFileStatus(HadoopConfigUtils.getInstance(true), new Path("/input"), false);
//		for(FileStatus s:list){
//			System.out.println(s.getPath());
//		}
//		System.out.println(HDFSUtils.merge(HadoopConfigUtils.getInstance(true),false,false, new Path[]{new Path("/input/file1"),new Path("/input/file2")}));
//		System.out.println(HDFSUtils.merge(HadoopConfigUtils.getInstance(true),true,true, new Path[]{new Path("/input")}));

	}
}
