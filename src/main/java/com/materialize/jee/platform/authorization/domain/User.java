package com.materialize.jee.platform.authorization.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.materialize.jee.platform.organization.domain.Organization;

public class User implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户类型：1-注册用户;2-正式会员;3-内部员工
	 */
	public static final Integer USER_TYPE_REGEDIT  = 1;
	public static final Integer USER_TYPE_MEMBER   = 2;
	public static final Integer USER_TYPE_EMPLOYEE = 3;
	
	/**
	 * 主键
	 */
	private java.lang.Long id;
	
	/**
	 * 公司
	 */
	private java.lang.Long companyId;
	
	/**
	 * 用户ID
	 */
	private java.lang.String username;
	
	/**
	 * 用户昵称
	 */
	private java.lang.String nikeName;
	
	/**
	 * 用户姓名
	 */
	private java.lang.String realName;
	
	/**
	 * 性别，W-女；M-男；
	 */
	private java.lang.String sex;
	
	/**
	 * 密码
	 */
	private java.lang.String password;
	
	/**
	 * 头像路径
	 */
	private java.lang.String avatar;
	
	/**
	 * 联系电话
	 */
	private java.lang.String telPhone;
	
	/**
	 * 手机
	 */
	private java.lang.String phone;
	
	/**
	 * 邮箱
	 */
	private java.lang.String email;
	
	/**
	 * 用户类型：1-注册用户;2-正式会员;3-内部员工
	 */
	private Integer userType;
	
	/**
	 * 引用ID：正式会员引用member表；内部员工引用employee表
	 */
	private Long refId;
	
	/**
	 * 状态，0-禁用；1-正常；
	 */
	private Boolean isEnabled;
	
	/**
	 * 是否锁定，0-否；1-是
	 */
	private Boolean isLock;
	
	/**
	 * 创建者ID
	 */
	private java.lang.Long createId;
	
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	
	/**
	 * 用户所属机构
	 */
	private List<Organization> orgs;
	
	/**
	 * 用户所属角色
	 */
	private List<Role> roles;
	
	/**
	 * 用户所属菜单
	 */
	private List<Resource> menus;
	
	/**
	 * 用户拥有权限的方法
	 */
	private List<Resource> methods;
	
	private  Collection<GrantedAuthority> authorities;
	
	private  boolean accountNonExpired = Boolean.TRUE;
	private  boolean accountNonLocked = Boolean.TRUE;
	private  boolean credentialsNonExpired = Boolean.TRUE;
	
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public java.lang.Long getId() {
        return id;
    }
    
    public void setId(java.lang.Long id) {
        this.id = id;
    }
    
	public java.lang.String getNikeName() {
        return nikeName;
    }
    
    public void setNikeName(java.lang.String nikeName) {
        this.nikeName = nikeName;
    }
    
	public java.lang.String getRealName() {
        return realName;
    }
    
    public void setRealName(java.lang.String realName) {
        this.realName = realName;
    }
    
	public java.lang.String getSex() {
        return sex;
    }
    
    public void setSex(java.lang.String sex) {
        this.sex = sex;
    }
    
	public java.lang.String getPassword() {
        return password;
    }
    
    public void setPassword(java.lang.String password) {
        this.password = password;
    }
    
	public java.lang.String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(java.lang.String avatar) {
        this.avatar = avatar;
    }
    
	public java.lang.String getTelPhone() {
        return telPhone;
    }
    
    public void setTelPhone(java.lang.String telPhone) {
        this.telPhone = telPhone;
    }
    
	public java.lang.String getPhone() {
        return phone;
    }
    
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }
    
	public java.lang.String getEmail() {
        return email;
    }
    
    public void setEmail(java.lang.String email) {
        this.email = email;
    }
    
	public Boolean getIsLock() {
        return isLock;
    }
    
    public void setIsLock(Boolean isLock) {
        this.isLock = isLock;
    }
    
	public java.lang.Long getCreateId() {
        return createId;
    }
    
    public void setCreateId(java.lang.Long createId) {
        this.createId = createId;
    }
    
	public java.util.Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

	public List<Organization> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<Organization> orgs) {
		this.orgs = orgs;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Resource> getMenus() {
		return menus;
	}

	public void setMenus(List<Resource> menus) {
		this.menus = menus;
	}

	public List<Resource> getMethods() {
		return methods;
	}

	public void setMethods(List<Resource> methods) {
		this.methods = methods;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public java.lang.Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(java.lang.Long companyId) {
		this.companyId = companyId;
	}

}