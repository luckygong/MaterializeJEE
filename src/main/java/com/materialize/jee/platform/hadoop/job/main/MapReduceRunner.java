package com.materialize.jee.platform.hadoop.job.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.materialize.jee.platform.hadoop.common.HadoopConfigUtils;
import com.materialize.jee.platform.hadoop.job.tools.AbstractJobRunner;
import com.materialize.jee.platform.hadoop.job.tools.JobContext;
import com.materialize.jee.platform.hadoop.job.tools.ProcessCmd;
import com.materialize.jee.platform.utils.ZipUtils;

public class MapReduceRunner extends AbstractJobRunner{
	private static Logger logger = LoggerFactory.getLogger(MapReduceRunner.class);
	
	/*
	 * mapreduce任务运行日志文件名
	 */
	public static final String JOB_RUN_LOG_NAME = "job-run.log";
	/*
	 * mapreduce任务取消日志文件名
	 */
	public static final String JOB_CANCEL_LOG_NAME = "job-cancel.log";
	
	/*
	 * process运行结果
	 */
	public static final Integer PEOCESS_RESULT_SUCCESS = 1;
	public static final Integer PEOCESS_RESULT_FAIL = -1;
	
	private ProcessCmd processCmd;
	
	public MapReduceRunner(JobContext jobContext){
		super(jobContext);
		processCmd = new ProcessCmd();
	}
	
	@Override
	public Integer run() throws Exception{
		int exitCode=PEOCESS_RESULT_FAIL;
		//准备jar包
		prepareJar(jobContext);
		//获取命令
		String command = getRunCommand(jobContext);
		//执行命令
		exitCode = processCmd.run(jobContext.getWorkDir(), jobContext.getRunParams(), command, new File(jobContext.getWorkDir()+File.separator+JOB_RUN_LOG_NAME));
		logger.info("hadoop command result :"+exitCode);
		return exitCode==0?PEOCESS_RESULT_SUCCESS:PEOCESS_RESULT_FAIL;
	}
	
	@Override
	public void cancel(){
		try {
			String command = getCancelCommand();
			processCmd.run(jobContext.getWorkDir(), jobContext.getRunParams(), command, new File(jobContext.getWorkDir()+File.separator+JOB_CANCEL_LOG_NAME));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//强制kill 进程
		processCmd.killProcess();
		this.canceled = true;
	}
	
	private String getRunCommand(JobContext jobContext){
		StringBuffer sb = new StringBuffer();
		sb.append(getHadoopCmd());
		sb.append("jar ");
		sb.append(jobContext.getJarFile()).append(" ");
		List<String> jobParams = jobContext.getJobParams();
		for(int i=0;jobParams!=null && i<jobParams.size();i++){
			sb.append(jobParams.get(i)).append(" ");
		}
		logger.info("hadoop command: "+sb.toString());
		return sb.toString(); 
	}
	
	private String getCancelCommand() {
		StringBuffer sb = new StringBuffer();
		// 检测日志，如果有hadoop的job，先kill 这些job
		String jobId = getJobId();
		if(!StringUtils.isBlank(jobId)){
			sb.append(getHadoopCmd());
			sb.append("job -kill ");
			sb.append(jobId);
		}
		return sb.toString();
	}
	
	public String getJobId(){
		String jobId = null;
		InputStream in = null;
		BufferedReader reader = null;
		try{
			in = new FileInputStream(jobContext.getWorkDir()+File.separator+JOB_RUN_LOG_NAME);
			reader=new BufferedReader(new InputStreamReader(in));
			String line;
			while((line=reader.readLine())!=null){
				if (line.contains("Running job: ")) {// mapreduce
					jobId = line.substring(line.indexOf("job_"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			IOUtils.closeStream(reader);
			IOUtils.closeStream(in);
		}
		return jobId;
	}
	
	private void prepareJar(JobContext jobContext) throws Exception{
		OutputStream out = null;
		InputStream in = null;
		try{
			//获取jar中设置的main class
			String mainClassName = checkManifest(jobContext);
			String jarFile = jobContext.getJarFile().replace("\\", "/");
			String newJar = jobContext.getWorkDir()+File.separator+jarFile.substring(jarFile.lastIndexOf("/")+1);
			//若jar中未设置main class，或者调度时传入main class 且设置的与运行时传入的不一致，重新设置MANIFEST
			if(StringUtils.isBlank(mainClassName) && StringUtils.isBlank(jobContext.getRunClass())){
				throw new Exception("未指定main class");
			}
			if(StringUtils.isBlank(mainClassName) 
					|| (!StringUtils.isBlank(jobContext.getRunClass()) && !mainClassName.equals(jobContext.getRunClass()))){
				//设置MANIFEST main-class
				String unjarPath = jobContext.getWorkDir()+File.separator+"unjar";
				unJar(new File(jobContext.getJarFile()),new File(unjarPath));
				Manifest manifest = new Manifest();  
			    manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
			    manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, jobContext.getRunClass());
			    out  = new FileOutputStream(unjarPath+File.separator+"META-INF/MANIFEST.MF");
			    manifest.write(out);
			    
			    //重新打包
			    ZipUtils.zip(unjarPath, newJar, true, null);
			    logger.info("MainClass in jar manifest :"+mainClassName+"; job param set: "+jobContext.getRunClass()+"; unjar and rejar");
			}else{
				out  = new FileOutputStream(new File(newJar));
				in = new FileInputStream(jobContext.getJarFile());
				IOUtils.copyBytes(in, out, 8192);
			}
			jobContext.setJarFile(newJar);
			logger.info("mapreduce jar temp dir："+newJar);
		}catch(Exception e){
			throw e;
		}finally{
			IOUtils.closeStream(out);
			IOUtils.closeStream(in);
		}
	}
	
	private String checkManifest(JobContext jobContext) throws Exception{
		String mainClassName = null;
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jobContext.getJarFile());
			Manifest manifest = jarFile.getManifest();
			if (manifest != null) {
				mainClassName = manifest.getMainAttributes().getValue("Main-Class");
			}
		} catch (IOException io) {
			throw new Exception("Error opening job jar: " + jobContext.getJarFile());
		}finally{
			IOUtils.closeStream(jarFile);
		}
		return mainClassName;
	}
	
	private void unJar(File jarFile, File toDir) throws IOException {
		JarFile jar = new JarFile(jarFile);
		try {
			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if ((!(entry.isDirectory())) && (Pattern.compile(".*").matcher(entry.getName()).matches())) {
					InputStream in = jar.getInputStream(entry);
					try {
						File file = new File(toDir, entry.getName());
						ensureDirectory(file.getParentFile());
						OutputStream out = new FileOutputStream(file);
						try {
							IOUtils.copyBytes(in, out, 8192);
						} finally {
							out.close();
						}
					} finally {
						in.close();
					}
				}
			}
		} finally {
			jar.close();
		}
	}
	
	private void ensureDirectory(File dir) throws IOException {
		if ((!(dir.mkdirs())) && (!(dir.isDirectory())))
			throw new IOException("Mkdirs failed to create " + dir.toString());
	}
	
	public String getHadoopCmd(){
		StringBuffer sb = new StringBuffer();
		String hadoopHome = HadoopConfigUtils.getHadoopHome();
		if(!StringUtils.isBlank(hadoopHome)){
			sb.append(hadoopHome).append(File.separator).append("bin/hadoop ");
			String confHome = HadoopConfigUtils.getLocalHadoopConfDir();
			if(!StringUtils.isBlank(confHome)){
				sb.append("--config ");
				sb.append(confHome).append(" ");
			}
		}else{
			sb.append("hadoop ");
		}
		return sb.toString();
	}
	
	public static void test() throws Exception{
	    JobContext context = JobContext.getTempJobContext();
	    context.setJarFile("/usr/local/java/apache-tomcat-8.0.33/webapps/UFit/WEB-INF/classes/cluster/mrjar/WordCount.jar");
	    context.setRunClass("hadoop.io.WordCount");
	    context.addJobParams("/input/file*");
	    context.addJobParams("/output/wordcount");
	    System.out.println(new MapReduceRunner(context).run());
	    
	}
}
