package com.foyoedu.springsecurity.config;

import com.foyoedu.springsecurity.configBean.SecurityProperties;
import com.foyoedu.springsecurity.service.SmsCodeSender;
import com.foyoedu.springsecurity.service.ValidateCodeGenerator;
import com.foyoedu.springsecurity.service.impl.ImageCodeGenerator;
import com.foyoedu.springsecurity.service.impl.SmsCodeSenderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringSecurityConfig implements WebMvcConfigurer {

	@Bean
	@ConditionalOnMissingBean(name = "imageCodeGenerator")
	public ValidateCodeGenerator imageValidateCodeGenerator() {
		return new ImageCodeGenerator();
	}

	@Bean
	@ConditionalOnMissingBean(SmsCodeSender.class)
	public SmsCodeSender smsCodeSender() {
		return new SmsCodeSenderImpl();
	}

	@Autowired
	private SecurityProperties securityProperties;
	@Override
	public void addViewControllers( ViewControllerRegistry registry ) {
		registry.addViewController("/").setViewName("forward:/" + securityProperties.getBrowser().getLoginPage());
	}
}
