package com.foyoedu.oauth2.configBean;

import lombok.Data;

@Data
public class BrowserProperties {
	
	//private SessionProperties session = new SessionProperties();
	
	//private String signUpUrl = "/signUp.html";
	
	private String loginPage = "/signIn.html";
	
	private LoginResponseType loginType = LoginResponseType.REDIRECT;
	
	//private int rememberMeSeconds = 3600;

}
