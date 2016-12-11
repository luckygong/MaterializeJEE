/**
 * @Company：
 * @Title: CompanyServiceImpl.java
 * @Description：描述
 * @Author： zhaosk
 * @Date：2016-12-11 07:53:39
 * @Version： V1.0
 * @Modify：
 */
package com.materialize.jee.platform.organization.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.materialize.jee.platform.organization.domain.Company;
import com.materialize.jee.platform.organization.service.CompanyService;
import com.materialize.jee.platform.base.DefaultBaseService;
import com.materialize.jee.platform.organization.mapper.CompanyMapper;
import com.materialize.jee.platform.base.page.Page;
import com.materialize.jee.platform.base.page.Pagination;

/**
 * @ClassName: CompanyServiceImpl
 * @Description: TODO(CompanyServiceImpl service层)
 */
@Service
public class CompanyServiceImpl extends DefaultBaseService implements CompanyService {
	protected static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
	@javax.annotation.Resource
	private CompanyMapper companyMapper;
	
	/**
	 * @Title: save
	 * @Description: (保存动作)
	 * @param record 要保存的对象
	 * @return  返回保存对象的主键
	 */
	@Override
	public Integer save(Company company){
		return companyMapper.save(company);
	}

	/**
	 * @Title: update
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	@Override
	public Integer update(Company company){
		return companyMapper.update(company);
	}
	
	/**
	 * @Title: updateSelective
	 * @Description: (更新动作)
	 * @param record 要更新的对象
	 */
	@Override
	public Integer updateSelective(Company company){
		return companyMapper.updateSelective(company);
	}

	/**
	 * @Title: delete
	 * @Description: (删除动作)
	 * @param record 要删除的对象
	 */
	@Override
	public Integer delete(java.lang.Long id){
		Company company = this.get(id);
		return companyMapper.delete(company);
	}

	/**
	 * @Title: get
	 * @Description: (查询动作)
	 * @param rowId   要删除的对象的主键
	 * @return  返回根据主键查询的对象
	 */
	@Override
	public Company get(java.lang.Long id){
		return companyMapper.get(id);
	}

	/**
	 * @Title: find
	 * @Description: 条件参数的Map形式，可为Null
	 * @param entityMap
	 * @return 返回根据条件查询的数据
	 */
	@Override
	public List<Company> find(Map<String, Object> entityMap){
		return companyMapper.find(entityMap);
	}
	
	/**
	 * @Title: findPage
	 * @Description: 条件参数的Map形式，可为Null
	 * @param pageNo 页号
	 * @param pageSize 每页记录数
	 * @param entityMap 查询条件
	 * @return 返回根据条件查询的数据
	 */
	@Override
	public Page<Company> findPage(Integer pageNo, Integer pageSize,Map<String,Object> entityMap){
		Pagination pagination = new Pagination(pageNo, pageSize, entityMap);
		return findPage(pagination);
	}
	
	/**
	 * @Title: findPage
	 * @param pagination 分页信息
	 * @return 返回根据条件查询的数据
	 */
	@Override
	public Page<Company> findPage(Pagination pagination){
		Page<Company> page = (Page<Company>)companyMapper.find(pagination);
		return page;
	}

	/**
	 * @Title: findCount
	 * @Description: (查询数量)
	 * @param map 条件参数的Map形式，可为Null
	 * @return  返回根据分页查询的数据数量
	 */
	@Override
	public Integer findCount(Map<String, Object> entityMap){
		return companyMapper.findCount(entityMap);
	}
}
