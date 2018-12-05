
package com.foyoedu.springsecurity.service.impl;


import com.foyoedu.springsecurity.config.properties.SecurityProperties;
import com.foyoedu.springsecurity.pojo.validate.ValidateCode;
import com.foyoedu.springsecurity.service.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public ValidateCode generate(ServletWebRequest request) {
		String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
		System.out.print(code);
		return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
	}

}
