package com.materialize.jee.platform.base;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.JstlView;

import com.materialize.jee.platform.utils.PropertiesUtils;



public class BaseJstlView extends JstlView {
	@Override
	protected void exposeHelpers(HttpServletRequest request) throws Exception {
		super.exposeHelpers(request);
		//设置通用数据
		request.setAttribute("sftpUrl" , PropertiesUtils.getProperties("/jdbc.properties","sftp_web_url"));
	}
}
