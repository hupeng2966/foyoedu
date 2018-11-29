package com.foyoedu.springsecurity.authentication;

import com.foyoedu.springsecurity.configBean.SecurityConstants;
import com.foyoedu.springsecurity.configBean.SecurityProperties;
import com.foyoedu.springsecurity.controller.ValidateCodeController;
import com.foyoedu.springsecurity.pojo.validate.ValidateCode;
import com.foyoedu.springsecurity.pojo.validate.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

	@Autowired
	private FoyoAuthenticationFailureHandler authenticationFailureHandler;

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@Autowired
	private SecurityProperties securityProperties;

	private AntPathMatcher pathMatcher = new AntPathMatcher();

	/**
	 * 存放所有需要校验验证码的url
	 */
	private Map<String, ValidateCodeType> urlMap = new HashMap<>();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		if(urlMap.isEmpty()){
			addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);
			addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
		}

		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
			Set<String> urls = urlMap.keySet();
			for (String url : urls) {
				if (pathMatcher.match(url, request.getRequestURI())) {
					try{
						validate(new ServletWebRequest(request), urlMap.get(url));
					} catch (ValidateCodeException e) {
						authenticationFailureHandler.onAuthenticationFailure(request,response,e);
						return;
					}
				}
			}
		}

		chain.doFilter(request, response);
	}

	protected void addUrlToMap(String urlString, ValidateCodeType type) {
		if (StringUtils.isNotBlank(urlString)) {
			String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
			for (String url : urls) {
				urlMap.put(url, type);
			}
		}
	}

	private void validate(ServletWebRequest request, ValidateCodeType type) throws ServletRequestBindingException {
		ValidateCode codeInSession = null;
		String codeInRequest = "";
		String SESSION_KEY = "";
		if (type.equals(ValidateCodeType.IMAGE)) {
			SESSION_KEY = ValidateCodeController.SESSION_IMAGE_KEY;

			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE);
		}else {
			SESSION_KEY = ValidateCodeController.SESSION_SMS_KEY;
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS);
		}
		codeInSession = (ValidateCode) sessionStrategy.getAttribute(request, SESSION_KEY);
		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("验证码的值不能为空");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException("验证码不存在");
		}

		if (codeInSession.isExpried()) {
			sessionStrategy.removeAttribute(request, SESSION_KEY);
			throw new ValidateCodeException("验证码已过期");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码不匹配");
		}
		sessionStrategy.removeAttribute(request, SESSION_KEY);
	}

}
