package com.foyoedu.springsecurity.configBean;

import lombok.Data;

@Data
public class SmsCodeProperties {
	private int length = 6;
	private int expireIn = 60;
	private String url;
}
