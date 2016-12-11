package com.materialize.jee.platform.activiti;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.materialize.jee.platform.authorization.domain.Role;
import com.materialize.jee.platform.authorization.domain.User;
import com.materialize.jee.platform.authorization.service.RoleService;
import com.materialize.jee.platform.authorization.service.UserService;  
  
/** 
 * 自定义的Activiti用户管理器 
 */  
  
@Service  
public class CustomUserEntityManager extends UserEntityManager {  
  
    @Autowired  
    private UserService userService; 
    @Autowired  
    private RoleService roleService;
  
    @Override
    public UserEntity findUserById(String userId) {
    	if (userId == null){
    		return null;
    	}
        User user = userService.get(Long.valueOf(userId));
        UserEntity userEntity = new UserEntity();  
        userEntity.setId(String.valueOf(user.getId()));  
        userEntity.setFirstName(user.getRealName());  
        userEntity.setLastName(user.getUsername());  
        userEntity.setPassword(user.getPassword());  
        userEntity.setEmail(user.getEmail());  
        userEntity.setRevision(1);  
        return userEntity;  
    }

    @Override
    public List<Group> findGroupsByUser(final String userCode) {
        if (userCode == null){
            return null;
        }

        List<Role> roleList = roleService.findByUser(Long.valueOf(userCode));
        List<Group> gs = new java.util.ArrayList<>();
        if(roleList!=null){
	        for (Role role : roleList) {
	        	GroupEntity g = new GroupEntity();
	            g.setRevision(1);
	            g.setType("assignment");
	            g.setId(String.valueOf(role.getId()));
	            g.setName(role.getRoleName());
	            gs.add(g);
	        }
        }
        return gs;
    }
  
    @Override  
    public List<org.activiti.engine.identity.User> findUserByQueryCriteria(UserQueryImpl query, Page page) {  
        throw new RuntimeException("not implement method.");  
    }  
  
    @Override  
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId,  
            String key) {  
        throw new RuntimeException("not implement method.");  
    }  
  
    @Override  
    public List<String> findUserInfoKeysByUserIdAndType(String userId,  
            String type) {  
        throw new RuntimeException("not implement method.");  
    }  
  
    @Override  
    public long findUserCountByQueryCriteria(UserQueryImpl query) {  
        throw new RuntimeException("not implement method.");  
    }  
}  
