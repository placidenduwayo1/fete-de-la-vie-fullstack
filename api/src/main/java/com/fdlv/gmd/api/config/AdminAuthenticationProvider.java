package com.fdlv.gmd.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fdlv.gmd.api.security.DomainUserDetailsService;

@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private DomainUserDetailsService adminUserDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final String username = authentication.getName();
		final String password = (String) authentication.getCredentials();

		final UserDetails adminUserDetails =  adminUserDetailsService.loadUserByUsername(username);

		if (!passwordEncoder.matches(password,adminUserDetails.getPassword()))
			throw new BadCredentialsException("Invalid credentials");

		return new UsernamePasswordAuthenticationToken(adminUserDetails, password, adminUserDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}

