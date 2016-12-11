package com.materialize.jee.platform.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.materialize.jee.platform.quartz.QuartzJobUtils;
import com.materialize.jee.platform.utils.DataDicUtils;

public class ApplicationListener implements ServletContextListener {
	private static Log log = LogFactory.getLog(ApplicationListener.class);
	
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent arg0) {
		try{
			DataDicUtils.loadAllDataDic();
			QuartzJobUtils.startAllJob();
		} catch (Exception ce) {
			ce.printStackTrace();
			log.error("初始化不成功,系统退出!");
			System.exit(-1);
		}
	}

}
