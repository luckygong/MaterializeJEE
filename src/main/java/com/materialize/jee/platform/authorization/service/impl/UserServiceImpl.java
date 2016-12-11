/**
 * @Company：
 * @Title: UserServiceImpl.java
 * @Description：描述
 * @Author： 97802
 * @Date：2016-06-04 08:54:57
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.authorization.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.materialize.jee.platform.authorization.domain.User;
import com.materialize.jee.platform.authorization.mapper.UserMapper;
import com.materialize.jee.platform.authorization.service.UserService;
import com.materialize.jee.platform.base.DefaultBaseService;
import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;
import com.materialize.jee.platform.utils.Base64Utils;
import com.materialize.jee.platform.utils.DateUtils;
import com.materialize.jee.platform.utils.FTPUtil;
import com.materialize.jee.platform.utils.MD5Utils;
import com.materialize.jee.web.CommonConstants;
import com.materialize.jee.web.CommonUtils;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO(UserServiceImpl service层)
 */
@Service
public class UserServiceImpl extends DefaultBaseService implements UserService {
	
	@Resource
	private UserMapper userMapper;
	
	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	public Long save(User user){
		return userMapper.save(user);
	}
	
	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	public Long save(User user, HttpServletRequest request){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file"); 
		if(file!=null && !file.isEmpty()){
			FTPUtil ftp = new FTPUtil(CommonConstants.FTP_HOST, CommonConstants.FTP_PORT, CommonConstants.FTP_USERNAME, CommonConstants.FTP_PASSWORD);
			String originalFilename = file.getOriginalFilename();
			String fileExt = originalFilename.lastIndexOf(".")>0?originalFilename.substring(originalFilename.lastIndexOf(".")):"";
			String fileName = MD5Utils.hash(originalFilename+DateUtils.getCurrent().getTime())+fileExt;
			try{
				ftp.uploadFile(CommonConstants.AVATAR_PATH, fileName, file.getInputStream());
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
			user.setAvatar(CommonConstants.AVATAR_PATH+"/"+fileName);
		}
		user.setPassword(encodePassword(user.getUsername(), user.getPassword()));
		User login = CommonUtils.getCurrentLogIn(request);
		user.setCreateId(login!=null?login.getId():null);
		user.setCreateTime(new Date());
		return userMapper.save(user);
	}
	
	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	public Long update(User user, HttpServletRequest request){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file"); 
		if(file!=null && !file.isEmpty()){
			User old = get(user.getId());
			FTPUtil ftp = new FTPUtil(CommonConstants.FTP_HOST, CommonConstants.FTP_PORT, CommonConstants.FTP_USERNAME, CommonConstants.FTP_PASSWORD);
			String originalFilename = file.getOriginalFilename();
			String fileExt = originalFilename.lastIndexOf(".")>0?originalFilename.substring(originalFilename.lastIndexOf(".")):"";
			String fileName = MD5Utils.hash(originalFilename+DateUtils.getCurrent().getTime())+fileExt;
			try{
				ftp.uploadFile(CommonConstants.AVATAR_PATH, fileName, file.getInputStream());
				ftp.ftpDelete(old.getAvatar(), true);
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
			user.setAvatar(CommonConstants.AVATAR_PATH+"/"+fileName);
		}
		if(StringUtils.isEmpty(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(encodePassword(user.getUsername(), user.getPassword()));
		}
		return userMapper.updateSelective(user);
	}

	/**
	 * @Title: update
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	public long update(User user){
		return userMapper.update(user);
	}
	
	/**
	 * @Title: updateSelective
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	public long updateSelective(User user){
		return userMapper.updateSelective(user);
	}

	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	public Long delete(java.lang.Long id){
		User user = this.get(id);
		return userMapper.delete(user);
	}

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	public User get(java.lang.Long id){
		return userMapper.get(id);
	}

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	public List<User> find(Map<String, Object> entityMap){
		return userMapper.find(entityMap);
	}
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pageNo 页号
	 * @param pageSize 每页记录数
	 * @param entityMap 查询条件
	 * @return 返回根据条件查询的数据
	 */
	public Page<User> findPage(Integer pageNo, Integer pageSize,Map<String,Object> entityMap){
		Pagination pagination = new Pagination(pageNo, pageSize, entityMap);
		Page<User> page = (Page<User>)userMapper.find(pagination);
		return page;
	}
	
	/**
	 * @Title: findPage
	 * @param pagination 分页信息
	 * @return 返回根据条件查询的数据
	 */
	public Page<User> findPage(Pagination pagination){
		Page<User> page = (Page<User>)userMapper.find(pagination);
		return page;
	}

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	public Long findCount(Map<String, Object> entityMap){
		return userMapper.findCount(entityMap);
	}
	
	/**
	 * 
	 * @Description: 密码加密    
	 * @param loginId 登录用户名
	 * @param password 密码
	 * @return  
	 */
	public String encodePassword(String loginId, String password){
		String passString = password+loginId;
		String encode = MD5Utils.encode(Base64Utils.encode(passString));
		return encode;
	}
	
	/**
	 * 校验指定字段值是否唯一 ，true-不存在；false-存在
	 * @param fieldName 字段名
	 * @param fieldValue 值
	 * @param excludeId 本身ID，用于排除本身
	 */
	public boolean checkOnly(String fieldName, String fieldValue, Long excludeId){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(fieldName, fieldValue);
		params.put("excludeId", excludeId);
		Long num = this.findCount(params);
		return (num==null || num==0);
	}
}
