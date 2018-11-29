package com.foyoedu.springsecurity.config;

import com.foyoedu.springsecurity.authentication.FoyoAuthenticationFailureHandler;
import com.foyoedu.springsecurity.authentication.FoyoAuthenticationSuccessHandler;
import com.foyoedu.springsecurity.authentication.FoyoUserDetailsService;
import com.foyoedu.springsecurity.authentication.ValidateCodeFilter;
import com.foyoedu.springsecurity.configBean.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

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
		applyPasswordAuthenticationConfig(http);
		http.apply(validateCodeSecurityConfig)
				.and()
			.apply(smsCodeAuthenticationSecurityConfig)
				.and()
			.rememberMe()
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
				.userDetailsService(foyoUserDetailsService)
				.and()
			.authorizeRequests()
				.antMatchers(securityProperties.getBrowser().getLoginPage(), "/authentication/require","/code/image","/code/sms").permitAll()
				.anyRequest()
				.authenticated()
				.and()
			.csrf()
				.disable();
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
