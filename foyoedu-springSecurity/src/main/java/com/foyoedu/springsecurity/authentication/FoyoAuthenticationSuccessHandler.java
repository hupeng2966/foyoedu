package com.foyoedu.springsecurity.authentication;

import com.alibaba.fastjson.JSON;
import com.foyoedu.springsecurity.pojo.FoyoResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("foyoAuthenticationSuccessHandler")
public class FoyoAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler /*implements AuthenticationSuccessHandler*/ {
	private RequestCache requestCache = new HttpSessionRequestCache();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if(savedRequest != null) {
			super.onAuthenticationSuccess(request, response, authentication);
		} else {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(JSON.toJSONString(FoyoResult.ok(authentication)));
		}
	}
}
