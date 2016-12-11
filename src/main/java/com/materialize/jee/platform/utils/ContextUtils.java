package com.materialize.jee.platform.utils;

import org.springframework.context.ApplicationContext;

public class ContextUtils {
	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}
	
	public static void setContext(ApplicationContext ctx) {
		context = ctx;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanId) {
		if (context == null)
			throw new IllegalStateException("ApplicationContext没有被初始化.");
		return (T)context.getBean(beanId);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		if (context == null)
			throw new IllegalStateException("ApplicationContext没有被初始化.");
		return context.getBean(clazz);
	}

	public static boolean containBean(String beanId) {
		if (context == null)
			throw new IllegalStateException("ApplicationContext没有被初始化.");
		return context.containsBean(beanId);
	}
	
	public static void close() {
		context = null;
	}
}
