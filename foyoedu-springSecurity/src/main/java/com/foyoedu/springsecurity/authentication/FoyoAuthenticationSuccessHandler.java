package com.foyoedu.springsecurity.authentication;

import com.alibaba.fastjson.JSON;
import com.foyoedu.springsecurity.dao.LoginValidateDao;
import com.foyoedu.springsecurity.pojo.FoyoResult;
import com.foyoedu.springsecurity.utils.CookieUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;

@Component("foyoAuthenticationSuccessHandler")
public class FoyoAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler /*implements AuthenticationSuccessHandler*/ {
	private RequestCache requestCache = new HttpSessionRequestCache();

	@Autowired
	private LoginValidateDao dao;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if(savedRequest != null && !StringUtils.contains(savedRequest.getRedirectUrl(),"signIn") && !StringUtils.contains(savedRequest.getRedirectUrl(),"qrSignIn")) {
			super.onAuthenticationSuccess(request, response, authentication);
		} else {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(JSON.toJSONString(FoyoResult.ok(authentication)));
		}
		if(!StringUtils.isEmpty(request.getParameter("qrtoken"))) {
			if (dao.updateqrlogin(request.getParameter("qrtoken"),true)) {
				String openId = RandomStringUtils.randomAlphanumeric(30);
				String loginId = authentication.getName();
				if (dao.addweixin(authentication.getName(),openId, request.getParameter("qrtoken"), new Date().getTime())) {
					CookieUtils.setCookie(request,response,"weixin",openId,21900);
				}
			}
		}
	}
}
