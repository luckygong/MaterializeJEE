package com.materialize.jee.platform.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.materialize.jee.platform.utils.ContextUtils;

public class ContextLoaderListener implements ServletContextListener {
	private static Log log = LogFactory.getLog(ContextLoaderListener.class);
	
	public void contextDestroyed(ServletContextEvent event) {
		ContextUtils.close();
	}

	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		try {
			ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
			ContextUtils.setContext(ctx);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("初始化不成功,系统退出!");
			System.exit(-1);
		}
	}

}
