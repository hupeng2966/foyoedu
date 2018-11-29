package com.foyoedu.springsecurity.authentication;

import com.alibaba.fastjson.JSON;
import com.foyoedu.springsecurity.pojo.FoyoResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("foyoAuthenctiationFailureHandler")
public class FoyoAuthenticationFailureHandler implements AuthenticationFailureHandler /*extends SimpleUrlAuthenticationFailureHandler*/ {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException exception) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(JSON.toJSONString(FoyoResult.error(HttpStatus.UNAUTHORIZED.value(), exception.getMessage())));
	}
}
