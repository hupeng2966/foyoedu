package com.foyoedu.springsecurity.service;

public interface SmsCodeSender {
	
	void send(String mobile, String code);

}
