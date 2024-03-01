package com.fdlv.gmd.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import com.fdlv.gmd.api.security.AuthoritiesConstants;
import com.fdlv.gmd.api.security.jwt.JWTConfigurer;
import com.fdlv.gmd.api.security.jwt.TokenProvider;

import tech.jhipster.config.JHipsterProperties;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final JHipsterProperties jHipsterProperties;

	private final TokenProvider tokenProvider;

	private final CorsFilter corsFilter;
	private final SecurityProblemSupport problemSupport;

	public SecurityConfiguration(
			TokenProvider tokenProvider,
			CorsFilter corsFilter,
			JHipsterProperties jHipsterProperties,
			SecurityProblemSupport problemSupport) {
		this.tokenProvider = tokenProvider;
		this.corsFilter = corsFilter;
		this.problemSupport = problemSupport;
		this.jHipsterProperties = jHipsterProperties;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.authenticationProvider(new AdminAuthenticationProvider())
		.authenticationProvider(new FdlvAuthenticationProvider());
	}

	@Override
	public void configure(WebSecurity web) {
		web
		.ignoring()
		.antMatchers(HttpMethod.OPTIONS, "/**")
		.antMatchers("/app/**/*.{js,html}")
		.antMatchers("/i18n/**")
		.antMatchers("/content/**")
		.antMatchers("/swagger-ui/**")
		.antMatchers("/test/**");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.csrf()
		.disable()
		.addFilterBefore(this.corsFilter, UsernamePasswordAuthenticationFilter.class)
		.exceptionHandling()
		.authenticationEntryPoint(this.problemSupport)
		.accessDeniedHandler(this.problemSupport)
		.and()
		.headers()
		.contentSecurityPolicy(this.jHipsterProperties.getSecurity().getContentSecurityPolicy())
		.and()
		.referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
		.and()
		.featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; fullscreen 'self'; payment 'none'")
		.and()
		.frameOptions()
		.sameOrigin()
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
		.antMatchers("/api/authenticate").permitAll()
				//.antMatchers("/api/choix-organisateur/**").permitAll()
		.antMatchers("/api/user/login").permitAll()
		.antMatchers("/api/register").permitAll()
		.antMatchers("/api/activate").permitAll()
		.antMatchers("/api/scenario-etape/**").permitAll()
				.antMatchers("/api/mobile-stat-parcours/**").permitAll()
				.antMatchers("/api/mobile-track-parcours/**").permitAll()
		.antMatchers("/api/account/reset-password/init").permitAll()
		.antMatchers("/api/account/reset-password/finish").permitAll()
		.antMatchers(HttpMethod.POST,"/fdlv-user").permitAll()
		.antMatchers("/api/fdlv-user/forgot").permitAll()
		.antMatchers(HttpMethod.POST,"/api/fdlv-user/").permitAll()
		.antMatchers("/api/fdlv-user/resetPassword").permitAll()
		.antMatchers("/api/liste-videos/**").permitAll()
		.antMatchers("/api/logo-teaser/**").permitAll()
		.antMatchers("/api/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)
		.antMatchers("/api/mobile-user/**").permitAll()
		.antMatchers(HttpMethod.POST,"/api/event/add").permitAll()
		.antMatchers(HttpMethod.POST,"/api/partenaires/module").permitAll()
		.antMatchers(HttpMethod.GET, "/api/**").permitAll()
		.antMatchers(HttpMethod.POST, "/api/**").authenticated()
		//		.antMatchers(HttpMethod.POST, "/api/**").permitAll()
		.antMatchers(HttpMethod.PUT, "/api/**").authenticated()
		.antMatchers(HttpMethod.DELETE, "/api/**").authenticated()
		.antMatchers(HttpMethod.PATCH, "/api/**").authenticated()
		.antMatchers("/management/health").permitAll()
		.antMatchers("/management/health/**").permitAll()
		.antMatchers("/management/info").permitAll()
		.antMatchers("/management/prometheus").permitAll()
		.antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
		.antMatchers("/console/**").permitAll()
		.and()
		.httpBasic()
		.and()
		.apply(this.securityConfigurerAdapter());

	}

	private JWTConfigurer securityConfigurerAdapter() {
		return new JWTConfigurer(this.tokenProvider);
	}
}
