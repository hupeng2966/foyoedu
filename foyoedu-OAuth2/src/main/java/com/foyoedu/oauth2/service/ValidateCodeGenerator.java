package com.foyoedu.oauth2.service;

import com.foyoedu.oauth2.pojo.validate.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {

	ImageCode generate(ServletWebRequest request);
	
}
