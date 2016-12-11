package com.materialize.jee.platform.authorization.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IMyUserDetailsService extends UserDetailsService {
	public abstract UserDetails loadUserByPhone(String paramString) throws UsernameNotFoundException;
	public abstract UserDetails loadUserByPhone(String paramString, String userType) throws UsernameNotFoundException;
	public abstract UserDetails loadUserByUsername(String paramString, String userType) throws UsernameNotFoundException;
}
