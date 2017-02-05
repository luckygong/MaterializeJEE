/**
 * @Company：
 * @Title: ResourceServiceImpl.java
 * @Description：描述
 * @Author： zhaosk
 * @Date：2016-09-23 06:49:31
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.authorization.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.materialize.jee.platform.authorization.domain.Resource;
import com.materialize.jee.platform.authorization.mapper.ResourceMapper;
import com.materialize.jee.platform.authorization.service.ResourceService;
import com.materialize.jee.platform.base.DefaultBaseService;
import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;

/**
 * @ClassName: ResourceServiceImpl
 * @Description: TODO(ResourceServiceImpl service层)
 */
@Service
public class ResourceServiceImpl extends DefaultBaseService implements ResourceService {
	
	@javax.annotation.Resource
	private ResourceMapper resourceMapper;
	
	protected void setLevel(Resource resource){
		Resource parent = resource.getParent();
		if(parent!=null && parent.getId()!=null){
			parent = get(parent.getId());
			resource.setLevel(parent.getLevel()+1);
		}else{
			resource.setLevel(1);
		}
	}
	
	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	public Integer save(Resource resource){
		setLevel(resource);
		return resourceMapper.save(resource);
	}
	
	/**
	 * @Title: update
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	public Integer update(Resource resource){
		setLevel(resource);
		return resourceMapper.update(resource);
	}
	
	/**
	 * @Title: updateSelective
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	public Integer updateSelective(Resource resource){
		setLevel(resource);
		return resourceMapper.updateSelective(resource);
	}

	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	public Integer delete(java.lang.Long id){
		Resource resource = this.get(id);
		return resourceMapper.delete(resource);
	}
	
	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	public Integer delete(java.lang.Long[] ids){
		Integer res = 0;
		if(ids!=null && ids.length>0){
			for(Long id:ids){
				Resource resource = this.get(id);
				res+=resourceMapper.delete(resource);
			}
		}
		return res;
	}

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	public Resource get(java.lang.Long id){
		return resourceMapper.get(id);
	}
	
	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	public List<Resource> find(Map<String, Object> entityMap){
		return resourceMapper.find(entityMap);
	}
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pageNo 页号
	 * @param pageSize 每页记录数
	 * @param entityMap 查询条件
	 * @return 返回根据条件查询的数据
	 */
	public Page<Resource> findPage(Integer pageNo, Integer pageSize,Map<String,Object> entityMap){
		Pagination pagination = new Pagination(pageNo, pageSize, entityMap);
		return findPage(pagination);
	}
	
	/**
	 * @Title: findPage
	 * @param pagination 分页信息
	 * @return 返回根据条件查询的数据
	 */
	public Page<Resource> findPage(Pagination pagination){
		Page<Resource> page = (Page<Resource>)resourceMapper.find(pagination);
		return page;
	}

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	public Integer findCount(Map<String, Object> entityMap){
		return resourceMapper.findCount(entityMap);
	}
	
	/**
	 * 生成菜单树
	 * @param unTree 未生成树的数据
	 * @return 菜单树
	 */
	public List<Resource> createMenuTree(List<Resource> unTree){
		Integer maxLevel = 1;
		for(int i=0;unTree!=null && i<unTree.size();i++){
			Resource resource = unTree.get(i);
			if(resource.getLevel()>maxLevel){
				maxLevel = resource.getLevel();
			}
		}
		List<Resource> menuTree = createSubTree(unTree, maxLevel);
		return menuTree;
	}
	
	/**
	 * 生成菜单树
	 * @param menus 需要生成树的菜单
	 * @param level 菜单层级
	 * @return
	 */
	private List<Resource> createSubTree(List<Resource> menus, Integer level) {
		Map<Long,List<Resource>> groups = new HashMap<Long,List<Resource>>();
		List<Resource> menuTree = new ArrayList<Resource>();
		//合并指定层级，且同一父菜单下的所有菜单
		for (int i = 0; i < menus.size(); i++) {
			Resource subMenu = (Resource) menus.get(i);
			if(subMenu.getParent()!=null && subMenu.getParent().getId()!=null){
				if(subMenu.getLevel()==level){
					if(groups.get(subMenu.getParent().getId())!=null){
						groups.get(subMenu.getParent().getId()).add(subMenu);
					}else{
						List<Resource> list = new ArrayList<Resource>();
						list.add(subMenu);
						groups.put(subMenu.getParent().getId(), list);
					}
				}else{
					menuTree.add(subMenu);
				}
			}else{
				menuTree.add(subMenu);
			}
		}
		
		if(level==1){
			Collections.sort(menuTree);
			return menuTree;
		}else{
			//生成父菜单对象
			Set<Long> keys = groups.keySet();
			Iterator<Long> it = keys.iterator();
			while(it.hasNext()){
				Long parentId = it.next();
				List<Resource> sub = groups.get(parentId);
				Resource parent = null;
				for(int i=0;i<menuTree.size();i++){
					if(parentId == menuTree.get(i).getId()){
						parent = menuTree.get(i);
						parent.getChilds().clear();
						break;
					}
				}
				if(parent == null){
					parent = get(parentId);
					menuTree.add(parent);
				}
				parent.getChilds().addAll(sub);
				Collections.sort(parent.getChilds());
			}
			return createSubTree(menuTree, --level);
		}
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
		Integer num = this.findCount(params);
		return (num==null || num==0);
	}
}
