package com.materialize.jee.platform.authorization.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.materialize.jee.platform.authorization.domain.Resource;
import com.materialize.jee.platform.authorization.domain.User;
import com.materialize.jee.platform.authorization.service.ResourceService;
import com.materialize.jee.platform.base.BaseController;
import com.materialize.jee.platform.base.JsonResponseModel;
import com.materialize.jee.platform.utils.CacheUtils;
import com.materialize.jee.web.CommonUtils;

@Controller  
@RequestMapping("/")
public class HomeController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value = "/sessionTimeout")
	public void sessionTimeout(HttpSession session,HttpServletRequest request, HttpServletResponse response,ModelMap model) throws IOException {
		logger.debug("session超时");
		response.setContentType("text/html;charset=utf-8");
	    response.getWriter().write("<script>top.location.href='"+request.getContextPath()+"/login.jsp';</script>");
	    response.getWriter().flush();
	}
	
	@RequestMapping(value = "/accessDenied")
	public String accessDenied(HttpSession session,HttpServletRequest request, HttpServletResponse response,ModelMap model) throws IOException {
		return "/error/page-403";
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "main")
	public String main(HttpServletRequest request, Model model) {
		return "main";
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "home")
	public String home(HttpServletRequest request, Model model) {
		return "home";
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "reveal")
	public String reveal(HttpServletRequest request, Model model) {
		return "reveal";
	}
	
	@RequestMapping(value = "initMenu" ,produces = "application/json") 
	public @ResponseBody JsonResponseModel home(HttpServletRequest request, 
			HttpServletResponse response) {  
		JsonResponseModel model = new JsonResponseModel();
		try {
			Map<String,Object> result = new HashMap<String,Object>();
			User user = CommonUtils.getCurrentLogIn(request);
			result.put("realName", user.getRealName());
			result.put("nikeName", user.getNikeName());
			result.put("username", user.getUsername());
			result.put("sex", user.getSex());
			result.put("avatar", user.getAvatar());
			result.put("telPhone", user.getTelPhone());
			result.put("phone", user.getPhone());
			result.put("email", user.getEmail());
			
			List<Resource> menuTree = resourceService.createMenuTree(user.getMenus());
			List<Map<String,Object>> menus = new ArrayList<Map<String,Object>>();
			if(menuTree!=null && menuTree.size()>0){
				for(Resource menu:menuTree){
					menus.add(createMenuMap(menu));
				}
			}
			result.put("menus", menus);
			model.setData(result);
			logger.info("用户"+user.getUsername()+"登录成功");
		} catch (Exception e) {
			e.printStackTrace();
			model.setStatus(0);
			model.setInfo(e.getMessage());
		}
        return model;
    }
	
	@RequestMapping(value = "checkAuth" ,produces = "application/json") 
	public @ResponseBody JsonResponseModel checkAuth(HttpServletRequest request, 
			HttpServletResponse response) {  
		JsonResponseModel model = new JsonResponseModel();
		try {
			HttpSession session = request.getSession(false);
	    	SessionInformation sessionInfo = (SessionInformation)CacheUtils.getCacheInfo(CacheUtils.EHCACHE_SESSION_CONF_NAME, session.getId());
	    	if(sessionInfo==null){
	    		model.setStatus(0);
	    		model.setInfo("登录超时，请重新登录");
	    		return model;
	    	}
	    	User user = (User)sessionInfo.getPrincipal();
	    	System.out.println(user.getMenus().size());
	    	System.out.println(user.getMethods().size());
		} catch (Exception e) {
			e.printStackTrace();
			model.setStatus(0);
			model.setInfo(e.getMessage());
		}
		return model;
	}
	
	private static Map<String,Object> createMenuMap(Resource menu){ 
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("id", menu.getId());
		jsonMap.put("parent", menu.getParent());
		jsonMap.put("level", menu.getLevel());
		jsonMap.put("menuName", menu.getName());
		jsonMap.put("isDirectory", menu.getIsDirectory());
		jsonMap.put("menuUrl", menu.getValue());
		jsonMap.put("icon", menu.getIcon());

		List<Resource> childs = menu.getChilds();
		if(childs!=null && childs.size()>0){
			List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
			for(Resource m:childs){
				Map<String,Object> childJson = createMenuMap(m);
				result.add(childJson);
			}
			jsonMap.put("childs", result);
		}
		return jsonMap;
	}

}
