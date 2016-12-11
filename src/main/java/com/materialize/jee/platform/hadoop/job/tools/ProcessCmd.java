package com.materialize.jee.platform.hadoop.job.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessCmd {
	private static Logger logger=LoggerFactory.getLogger(ProcessCmd.class);
	
	private volatile Process process;
	
	public Integer run(String workDir,Map<String,String> environment,String cmd,File log) throws Exception{
		int exitCode=-1;
		
		//执行命令
		ProcessBuilder builder = new ProcessBuilder(partitionCommandLine(cmd));
		builder.directory(new File(workDir));
		builder.environment().putAll(environment);
		Process process=builder.start();
		final InputStream inputStream = process.getInputStream();
        final InputStream errorStream = process.getErrorStream();
        final FileOutputStream out = new FileOutputStream(log);
        final String lineSeparator = System.getProperty("line.separator", "\n"); 
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
					String line;
					while((line=reader.readLine())!=null){
						System.out.println(line);
						out.write(line.getBytes());
						out.write(lineSeparator.getBytes());
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					BufferedReader reader=new BufferedReader(new InputStreamReader(errorStream));
					String line;
					while((line=reader.readLine())!=null){
						out.write(line.getBytes());
						out.write(lineSeparator.getBytes());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		try{
			exitCode = process.waitFor();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			process = null;
			IOUtils.closeStream(inputStream);
			IOUtils.closeStream(errorStream);
			IOUtils.closeStream(out);
		}
		logger.info("hadoop command result :"+exitCode);
		return exitCode;
	}
	
	private String[] partitionCommandLine(String command) {
		
		ArrayList<String> commands = new ArrayList<String>();
		
		String os=System.getProperties().getProperty("os.name");
		if(os!=null && (os.startsWith("win") || os.startsWith("Win"))){
			commands.add("CMD.EXE");
			commands.add("/C");
			commands.add(command);
		}else{
			int index = 0;

	        StringBuffer buffer = new StringBuffer(command.length());

	        boolean isApos = false;
	        boolean isQuote = false;
	        while(index < command.length()) {
	            char c = command.charAt(index);

	            switch(c) {
	                case ' ':
	                    if(!isQuote && !isApos) {
	                        String arg = buffer.toString();
	                        buffer = new StringBuffer(command.length() - index);
	                        if(arg.length() > 0) {
	                            commands.add(arg);
	                        }
	                    } else {
	                        buffer.append(c);
	                    }
	                    break;
	                case '\'':
	                    if(!isQuote) {
	                        isApos = !isApos;
	                    } else {
	                        buffer.append(c);
	                    }
	                    break;
	                case '"':
	                    if(!isApos) {
	                        isQuote = !isQuote;
	                    } else {
	                        buffer.append(c);
	                    }
	                    break;
	                default:
	                    buffer.append(c);
	            }

	            index++;
	        }

	        if(buffer.length() > 0) {
	            String arg = buffer.toString();
	            commands.add(arg);
	        }
		}
        return commands.toArray(new String[commands.size()]);
	}
	
	public void killProcess(){
		if (process != null) {
			logger.info("WARN Attempting to kill the process ");
			try {
				process.destroy();
				int pid=getProcessId();
				Runtime.getRuntime().exec("kill -9 "+pid);
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				process = null;
			}
		}
	}
	
	private int getProcessId() {
		int processId = 0;
		try {
			Field f = process.getClass().getDeclaredField("pid");
			f.setAccessible(true);
			processId = f.getInt(process);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return processId;
	}

}
