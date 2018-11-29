package com.foyoedu.springsecurity.controller;


import com.foyoedu.springsecurity.configBean.SecurityProperties;
import com.foyoedu.springsecurity.pojo.FoyoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BrowserSecurityController {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private SecurityProperties securityProperties;

//	@Autowired
//	private ProviderSignInUtils providerSignInUtils;

	/**
	 * 当需要身份认证时，跳转到这里
	 */
	@RequestMapping("/authentication/require")
	public FoyoResult requireAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
//		FoyoResult result = new FoyoResult();
//		result.setStatus(HttpStatus.UNAUTHORIZED.value());
//		result.setErrMsg("访问的服务需要身份认证，请引导用户到登录页");
//		result.setData(securityProperties.getBrowser().getLoginPage());
//		return result;
		return null;
	}

//	@GetMapping("/social/user")
//	public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
//		SocialUserInfo userInfo = new SocialUserInfo();
//		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
//		userInfo.setProviderId(connection.getKey().getProviderId());
//		userInfo.setProviderUserId(connection.getKey().getProviderUserId());
//		userInfo.setNickname(connection.getDisplayName());
//		userInfo.setHeadimg(connection.getImageUrl());
//		return userInfo;
//	}

}
