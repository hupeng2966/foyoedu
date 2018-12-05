package com.foyoedu.springsecurity.config.properties;

import lombok.Data;

@Data
public class BrowserProperties {
	
	//private SessionProperties session = new SessionProperties();
	
	//private String signUpUrl = "/signUp.html";
	
	private String loginPage = "/signIn.html";

	private int rememberMeSeconds = 604800;

}
