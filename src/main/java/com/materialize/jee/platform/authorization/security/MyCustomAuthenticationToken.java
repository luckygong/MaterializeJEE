package com.materialize.jee.platform.authorization.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class MyCustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = 410L;
	
	/** 用户类型：1-注册用户;2-正式会员;3-内部员工     */
	private String userType;
	/** 是否是手机app客户端登录,默认网页登录     */
	private Boolean isMobileClient = Boolean.FALSE;
	/** 手机端登录密码类型是否为动态密码，默认为是     */
	private Boolean isPasswdTypeAuthCode = Boolean.TRUE;
	/** 验证码校验是否通过    */
	private Boolean isAuthCodePassed = Boolean.FALSE;
	
	/**
	 * @param principal principal  委托人为用户名
	 * @param credentials 凭证为用户密码
	 * @param mobile 邮箱
	 * @param email 手机号
	 * @param captcha 验证码
	 */
	public MyCustomAuthenticationToken(String principal, String credentials, String userType, Boolean isMobileClient, Boolean isPasswdTypeAuthCode,Boolean isAuthCodePassed) {
		super(principal, credentials);
		this.isMobileClient = isMobileClient;
		this.isPasswdTypeAuthCode = isPasswdTypeAuthCode;
		this.isAuthCodePassed = isAuthCodePassed;
		this.userType = userType;
	}

	public Boolean getIsMobileClient() {
		return isMobileClient;
	}

	public void setIsMobileClient(Boolean isMobileClient) {
		this.isMobileClient = isMobileClient;
	}

	public Boolean getIsPasswdTypeAuthCode() {
		return isPasswdTypeAuthCode;
	}

	public void setIsPasswdTypeAuthCode(Boolean isPasswdTypeAuthCode) {
		this.isPasswdTypeAuthCode = isPasswdTypeAuthCode;
	}

	public Boolean getIsAuthCodePassed() {
		return isAuthCodePassed;
	}

	public void setIsAuthCodePassed(Boolean isAuthCodePassed) {
		this.isAuthCodePassed = isAuthCodePassed;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
