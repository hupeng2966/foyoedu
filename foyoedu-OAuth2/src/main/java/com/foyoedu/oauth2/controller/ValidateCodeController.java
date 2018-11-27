package com.foyoedu.oauth2.controller;

import com.foyoedu.oauth2.pojo.validate.ImageCode;
import com.foyoedu.oauth2.service.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @GetMapping("/code/image")
    public void creatCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletWebRequest req = new ServletWebRequest(request);
        ImageCode imageCode = imageCodeGenerator.generate(req);
        sessionStrategy.setAttribute(req, SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(),"png", response.getOutputStream());
    }

}
