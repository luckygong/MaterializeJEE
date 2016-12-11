package com.materialize.jee.platform.authorization.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

public class MyUserAuthenticationProvider extends AbstractAuthenticationProvider {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();

		UserDetails user = null;
		try {
			user = retrieveUser(username, (MyCustomAuthenticationToken) authentication);
		} catch (UsernameNotFoundException notFound) {
			this.logger.debug("用户不存在");
			throw notFound;
		}
		Assert.notNull(user, "用户不存在");
		additionalAuthenticationChecks(user, (MyCustomAuthenticationToken) authentication);

		Object principalToReturn = user;
		return createSuccessAuthentication(principalToReturn, authentication, user);
	}

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    	UserDetails loadedUser;
		try {
            loadedUser = this.getUserDetailsService().loadUserByUsername(username,((MyCustomAuthenticationToken)authentication).getUserType());
        } catch (UsernameNotFoundException notFound) {
            throw notFound;
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException("用户不存在");
        }
        return loadedUser;
    }
	
}
