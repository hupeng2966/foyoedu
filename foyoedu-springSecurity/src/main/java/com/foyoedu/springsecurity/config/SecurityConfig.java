package com.foyoedu.springsecurity.config;

import com.foyoedu.springsecurity.authentication.FoyoAuthenticationFailureHandler;
import com.foyoedu.springsecurity.authentication.FoyoAuthenticationSuccessHandler;
import com.foyoedu.springsecurity.authentication.FoyoUserDetailsService;
import com.foyoedu.springsecurity.authentication.ValidateCodeFilter;
import com.foyoedu.springsecurity.config.properties.SecurityConstants;
import com.foyoedu.springsecurity.config.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private FoyoAuthenticationSuccessHandler foyoAuthenticationSuccessHandler;

	@Autowired
	private FoyoAuthenticationFailureHandler foyoAuthenticationFailureHandler;

	@Autowired
	private ValidateCodeFilter validateCodeFilter;

	@Autowired
	private FoyoUserDetailsService foyoUserDetailsService;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
				.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
				.successHandler(foyoAuthenticationSuccessHandler)
				.failureHandler(foyoAuthenticationFailureHandler)
				.and()
			.apply(validateCodeSecurityConfig)
				.and()
			.apply(smsCodeAuthenticationSecurityConfig)
				.and()
			.rememberMe()
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
				.userDetailsService(foyoUserDetailsService)
				.and()
			.authorizeRequests()
				.antMatchers("/**/*.html","/js/**/*.js","/css/**/*.css","/images/**/*.png","/images/**/*.jpg","/favicon.ico","/**/*.woff","/**/*.woff2","/validate/**/*","/authentication/require","/code/**/*").permitAll()
				.anyRequest()
				.authenticated()
				.and()
			.csrf().disable();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		//tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;
	}

}
