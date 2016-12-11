package com.materialize.jee.platform.activiti;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;

public class CustomGroupEntityManagerFactory implements SessionFactory {  
    private GroupEntityManager groupEntityManager;  
    
    public GroupEntityManager getGroupEntityManager() {
		return groupEntityManager;
	}

	public void setGroupEntityManager(GroupEntityManager groupEntityManager) {  
        this.groupEntityManager = groupEntityManager;  
    }  
  
    public Class<?> getSessionType() {  
        // 返回原始的GroupEntityManager类型  
        return GroupEntityManager.class;  
    }  
  
    public Session openSession() {  
        // 返回自定义的GroupEntityManager实例  
        return groupEntityManager;  
    }  
}  