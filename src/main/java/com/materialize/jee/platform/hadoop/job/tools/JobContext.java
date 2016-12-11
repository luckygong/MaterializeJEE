package com.materialize.jee.platform.hadoop.job.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;

import com.materialize.jee.platform.hadoop.common.HadoopConfigUtils;
import com.materialize.jee.platform.utils.DateUtils;

/**
 * Job上下文
 * 当存在多个Job顺序处理时，通过上下文莱传递Job状态与信息
 * @author zhoufang
 *
 */
public class JobContext {
	
	public static JobContext getTempJobContext(){
		JobContext jobContext=new JobContext();
		Configuration conf = HadoopConfigUtils.getConfInstance(true);
		String hadoopTmpDir = conf.get("hadoop.tmp.dir");
		File f=new File(hadoopTmpDir+File.separator+DateUtils.formatDate(new Date(), "yyyyMMddHHmmss"));
		if(!f.exists()){
			f.mkdirs();
		}
		jobContext.setWorkDir(f.getAbsolutePath());
		return jobContext;
	}

    //调度执行
	public static final int SCHEDULE_RUN=1;
    //手动执行
	public static final int MANUAL_RUN=2;
    //DEBUG执行
	public static final int DEBUG_RUN=3;
	
	private final int runType;

	//main方法输入参数
	private List<String> jobParams = new ArrayList<String>();
	//job调度参数
	private Map<String, String> runParams = new HashMap<String, String>();
	
	private Integer exitCode;
	
	private String workDir;

	//jar文件本地路径
	private String jarFile;
	
	//main方法所在类完整类名
	private String runClass;
	
	private List<Map<String, String>> resources;
	
	public JobContext(){
		this(MANUAL_RUN);
	}
	
	public JobContext(int runType){
		this.runType=runType;
	}
	
	public String getWorkDir() {
		return workDir;
	}
	public void setWorkDir(String workDir) {
		this.workDir = workDir;
	}
	public List<Map<String, String>> getResources() {
		return resources;
	}
	public void setResources(List<Map<String, String>> resources) {
		this.resources = resources;
	}

	public int getRunType() {
		return runType;
	}

	public List<String> getJobParams() {
		return jobParams;
	}

	public void setJobParams(List<String> jobParams) {
		this.jobParams = jobParams;
	}

	public Map<String, String> getRunParams() {
		return runParams;
	}

	public void setRunParams(Map<String, String> runParams) {
		this.runParams = runParams;
	}

	public Integer getExitCode() {
		return exitCode;
	}

	public void setExitCode(Integer exitCode) {
		this.exitCode = exitCode;
	}

	public String getJarFile() {
		return jarFile;
	}

	public void setJarFile(String jarFile) {
		this.jarFile = jarFile;
	}

	public String getRunClass() {
		return runClass;
	}

	public void setRunClass(String runClass) {
		this.runClass = runClass;
	}
	
	public void addJobParams(String value){
		this.jobParams.add(value);
	}
}
