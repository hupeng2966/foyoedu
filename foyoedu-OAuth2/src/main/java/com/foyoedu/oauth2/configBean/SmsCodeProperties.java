package com.foyoedu.oauth2.configBean;

import lombok.Data;

@Data
public class SmsCodeProperties {
	private int length = 6;
	private int expireIn = 60;
	private String url;
}
