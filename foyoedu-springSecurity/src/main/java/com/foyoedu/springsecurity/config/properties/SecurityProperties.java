package com.foyoedu.springsecurity.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "foyo.security")
public class SecurityProperties {
	
	private BrowserProperties browser = new BrowserProperties();
	
	private ValidateCodeProperties code = new ValidateCodeProperties();
	
	//private SocialProperties social = new SocialProperties();
}

