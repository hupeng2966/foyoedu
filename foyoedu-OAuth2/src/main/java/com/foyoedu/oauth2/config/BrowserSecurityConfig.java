package com.foyoedu.oauth2.config;

import com.foyoedu.oauth2.authentication.FoyoAuthenticationFailureHandler;
import com.foyoedu.oauth2.authentication.FoyoAuthenticationSuccessHandler;
import com.foyoedu.oauth2.authentication.ValidateCodeFilter;
import com.foyoedu.oauth2.configBean.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private FoyoAuthenticationSuccessHandler foyoAuthenticationSuccessHandler;

	@Autowired
	private FoyoAuthenticationFailureHandler foyoAuthenticationFailureHandler;

	@Autowired
	private ValidateCodeFilter validateCodeFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
			.formLogin()
			.loginPage("/authentication/require")
			.loginProcessingUrl("/authentication/form")
			.successHandler(foyoAuthenticationSuccessHandler)
			.failureHandler(foyoAuthenticationFailureHandler)
			.and()
			.authorizeRequests()
			.antMatchers(securityProperties.getBrowser().getLoginPage(), "/authentication/require","/code/image").permitAll()
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

}
