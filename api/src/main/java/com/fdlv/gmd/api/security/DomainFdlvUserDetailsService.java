package com.fdlv.gmd.api.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.fdlv.FdlvUser;
import com.fdlv.gmd.api.repository.fdlv.FdlvUserRepository;

@Component("userFdlvDetailsService")
public class DomainFdlvUserDetailsService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(DomainFdlvUserDetailsService.class);

	private final FdlvUserRepository fdlvUserRepository;

	public DomainFdlvUserDetailsService(FdlvUserRepository fdlvUserRepository) {
		this.fdlvUserRepository = fdlvUserRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String login) {
		log.debug("Authenticating fdlv {}", login);

		if (new EmailValidator().isValid(login, null))
			return fdlvUserRepository
					.findOneWithAuthoritiesByEmailIgnoreCase(login)
					.map(user -> createSpringSecurityUser(login, user))
					.orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));

		final String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
		return fdlvUserRepository
				.findOneWithAuthoritiesByLogin(lowercaseLogin)
				.map(user -> createSpringSecurityUser(lowercaseLogin, user))
				.orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
	}

	private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, FdlvUser user) {
		if (!user.getActif())
			throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
		final List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.FDLV));
		//				user
		//				.getAuthorities()
		//				.stream()
		//				.map(authority -> )
		//				.collect(Collectors.toList());
		return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getMdpHash(), grantedAuthorities);
	}
}