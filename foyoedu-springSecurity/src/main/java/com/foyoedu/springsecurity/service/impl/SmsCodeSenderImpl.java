package com.foyoedu.springsecurity.service.impl;


import com.foyoedu.springsecurity.service.SmsCodeSender;

public class SmsCodeSenderImpl implements SmsCodeSender {

	@Override
	public void send(String mobile, String code) {
		System.out.println("向手机"+mobile+"发送短信验证码"+code);
	}

}
