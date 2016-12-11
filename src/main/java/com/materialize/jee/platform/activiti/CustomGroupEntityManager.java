package com.materialize.jee.platform.activiti;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.materialize.jee.platform.authorization.domain.Role;
import com.materialize.jee.platform.authorization.service.RoleService;  
@Service  
public class CustomGroupEntityManager extends GroupEntityManager {
	
    @Autowired  
    private RoleService roleService;  
  
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
    
    public GroupEntity findGroupById(final String groupCode) {
        if (groupCode == null){
            return null;
        }

        Role role = roleService.get(Long.valueOf(groupCode));
        GroupEntity e = new GroupEntity();
        e.setRevision(1);
        e.setType("assignment");
        e.setId(String.valueOf(role.getId()));
        e.setName(role.getRoleName());
        return e;
    }
 
  
    @Override  
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {  
        throw new RuntimeException("not implement method.");  
    }  
  
    @Override  
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {  
        throw new RuntimeException("not implement method.");  
    }  
}  
