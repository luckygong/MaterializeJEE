package com.materialize.jee.platform.authorization.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.materialize.jee.platform.authorization.domain.User;
import com.materialize.jee.platform.utils.Base64Utils;
import com.materialize.jee.platform.utils.MD5Utils;

@SuppressWarnings("deprecation")
public abstract class AbstractAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	protected final Log logger = LogFactory.getLog(getClass());
	private IMyUserDetailsService userDetailsService;
	protected PasswordEncoder passwordEncoder;
	protected SaltSource saltSource;
	
	@Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		Object salt = null;
		if (this.saltSource != null) {
			salt = this.saltSource.getSalt(userDetails);
		}

		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: 用户名错误");
			throw new BadCredentialsException("用户名错误");
		}
		
        Boolean isPasswdTypeAuthCode = ((MyCustomAuthenticationToken) authentication).getIsPasswdTypeAuthCode();
        if(isPasswdTypeAuthCode){
        	Boolean isAuthCodePassed = ((MyCustomAuthenticationToken) authentication).getIsAuthCodePassed();
        	if(!isAuthCodePassed){
        		logger.debug("Authentication failed: 验证码错误");
    			throw new BadCredentialsException("验证码错误");
        	}
        }else{
        	String presentedPassword = authentication.getCredentials().toString();
        	if(MD5Utils.encode(Base64Utils.encode(presentedPassword+salt)).equals(userDetails.getPassword())){
        		logger.debug("Authentication failed: 密码错误");
        		throw new BadCredentialsException("密码错误");
        	}
        }

		
		User user = (User)userDetails;
		if(!user.isEnabled()){
			throw new BadCredentialsException("用户已禁用");
		}
		if(user.getIsLock()){
			throw new BadCredentialsException("用户已被锁定");
		}
    }
	
	public IMyUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(IMyUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public SaltSource getSaltSource() {
		return saltSource;
	}

	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}

}
