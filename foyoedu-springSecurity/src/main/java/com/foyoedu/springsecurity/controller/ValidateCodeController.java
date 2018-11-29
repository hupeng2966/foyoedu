package com.foyoedu.springsecurity.controller;

import com.foyoedu.springsecurity.configBean.SecurityConstants;
import com.foyoedu.springsecurity.pojo.validate.ImageCode;
import com.foyoedu.springsecurity.pojo.validate.ValidateCode;
import com.foyoedu.springsecurity.service.SmsCodeSender;
import com.foyoedu.springsecurity.service.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ValidateCodeController {

    public static final String SESSION_IMAGE_KEY = "SESSION_KEY_IMAGE_CODE";

    public static final String SESSION_SMS_KEY = "SESSION_KEY_SMS_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;

    @Autowired
    private SmsCodeSender smsCodeSender;

    /*@Autowired    通过类名获取spring容器中的对象
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;*/

    @GetMapping("/code/image")
    public void creatImageCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletWebRequest req = new ServletWebRequest(request);

        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(req);
        sessionStrategy.setAttribute(req, SESSION_IMAGE_KEY, imageCode);
        ImageIO.write(imageCode.getImage(),"png", response.getOutputStream());
    }

    @GetMapping("/code/sms")
    public void creatSmsCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletWebRequest req = new ServletWebRequest(request);
        ValidateCode smsCode = smsCodeGenerator.generate(req);
        sessionStrategy.setAttribute(req, SESSION_SMS_KEY, smsCode);
        String mobile = ServletRequestUtils.getStringParameter(request, SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE,"13813865429");//getRequiredStringParameter();
        smsCodeSender.send(mobile, smsCode.getCode());
    }

}
