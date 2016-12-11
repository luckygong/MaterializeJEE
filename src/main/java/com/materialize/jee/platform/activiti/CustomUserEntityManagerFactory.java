package com.materialize.jee.platform.activiti;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;

public class CustomUserEntityManagerFactory implements SessionFactory {  
    private UserEntityManager userEntityManager;  
  
    public UserEntityManager getUserEntityManager() {
		return userEntityManager;
	}

	public void setUserEntityManager(UserEntityManager userEntityManager) {  
        this.userEntityManager = userEntityManager;  
    }  
  
    public Class<?> getSessionType() {  
        // 返回原始的UserManager类型  
        return UserEntityManager.class;  
    }  
  
    public Session openSession() {  
        // 返回自定义的UserManager实例  
        return userEntityManager;  
    }  
}  
