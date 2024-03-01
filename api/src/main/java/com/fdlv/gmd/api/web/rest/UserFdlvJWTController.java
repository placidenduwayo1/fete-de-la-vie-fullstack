package com.fdlv.gmd.api.web.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fdlv.gmd.api.config.FdlvAuthenticationProvider;
import com.fdlv.gmd.api.dto.fdlv.FdlvUserDTO;
import com.fdlv.gmd.api.mapper.fdlv.FdlvUserMapper;
import com.fdlv.gmd.api.repository.fdlv.FdlvUserRepository;
import com.fdlv.gmd.api.security.jwt.JWTFilter;
import com.fdlv.gmd.api.security.jwt.TokenProvider;
import com.fdlv.gmd.api.web.rest.vm.LoginVM;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@RestController
@RequestMapping("/api/user")
public class UserFdlvJWTController {

	@Autowired
	private  FdlvAuthenticationProvider fdlvAuthenticationProvider;

	@Autowired
	private  TokenProvider tokenProvider;

	@Autowired
	private  FdlvUserRepository fdlvUserRepository;

	@Autowired
	private FdlvUserMapper fdlvUserMapper;

	@PostMapping("/login")
	public ResponseEntity<?> authorizeFdlv(@Valid @RequestBody LoginVM loginVM) {
		try {
			final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					loginVM.getUsername(),
					loginVM.getPassword()
			);
			System.out.println(authenticationToken);
			final Authentication authentication = fdlvAuthenticationProvider.authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
			final HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
			final FdlvUserDTO user = fdlvUserMapper.toDto(fdlvUserRepository.findOneWithAuthoritiesByLogin(authentication.getName()).orElseThrow());
			return new ResponseEntity<>(new JWTToken(jwt,user), httpHeaders, HttpStatus.OK);
		}
		catch (Exception e){
			HashMap<String,String> returnMessage = new HashMap<>();
			returnMessage.put("message","Login ou mot de passe incorrect!");
			return ResponseEntity.status(401).body(returnMessage);
		}

	}

	/**
	 * Object to return as body in JWT Authentication.
	 */
	static class JWTToken {

		private FdlvUserDTO user;
		private String idToken;

		JWTToken(String idToken,FdlvUserDTO user) {
			this.idToken = idToken;
			this.user = user;
		}

		@JsonProperty("token")
		String getIdToken() {
			return idToken;
		}

		void setIdToken(String idToken) {
			this.idToken = idToken;
		}

		public FdlvUserDTO getUser() {
			return user;
		}

		public void setUser(FdlvUserDTO user) {
			this.user = user;
		}
	}
}
