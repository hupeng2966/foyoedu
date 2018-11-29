package com.foyoedu.springsecurity.service;


import com.foyoedu.springsecurity.pojo.validate.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {
	ValidateCode generate(ServletWebRequest request);
}
