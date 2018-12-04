package com.foyoedu.springsecurity.controller;

import com.foyoedu.springsecurity.configBean.SecurityConstants;
import com.foyoedu.springsecurity.dao.LoginValidateDao;
import com.foyoedu.springsecurity.pojo.FoyoResult;
import com.foyoedu.springsecurity.pojo.validate.ImageCode;
import com.foyoedu.springsecurity.pojo.validate.ValidateCode;
import com.foyoedu.springsecurity.service.SmsCodeSender;
import com.foyoedu.springsecurity.service.ValidateCodeGenerator;
import com.foyoedu.springsecurity.utils.FoyoUtils;
import com.foyoedu.springsecurity.utils.HttpRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private LoginValidateDao dao;

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

    @GetMapping("/code/qrcode")
    public void creatQrCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletOutputStream stream = null;
        String sessionId = request.getSession().getId();
        boolean result = dao.addqrlogin(sessionId,false, new Date().getTime());
        if (result){
            try {
                stream = response.getOutputStream();
                Map<EncodeHintType, Object> hints = new HashMap<>();
                //编码
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                //边框距
                hints.put(EncodeHintType.MARGIN, 0);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
                BitMatrix bm = qrCodeWriter.encode(basePath + "/qrSignIn.html?qrtoken="+sessionId, BarcodeFormat.QR_CODE, 150, 150, hints);
                MatrixToImageWriter.writeToStream(bm, "png", stream);
            } catch (Exception e) {
                e.getStackTrace();
            }
            finally {
                if (stream != null) {
                    stream.flush();
                    stream.close();
                }
            }
        }
    }

    @GetMapping(value = "/validate/qrtoken/{qrtoken}")
    public FoyoResult validateQrToken(@PathVariable("qrtoken") String qrtoken, HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean result = dao.findqrlogin(qrtoken);
        if (result) {
            //UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = dao.findweixin(qrtoken);
            return FoyoResult.ok(username);
        }
        return FoyoResult.ok(result);
    }

    @GetMapping(value = "/validate/weixin")
    public boolean validateWeixin(@RequestParam("weixin") String openId, @RequestParam("qrtoken") String qrtoken) {
        boolean result = dao.isExistsOpenId(openId);
        if (result) {
            dao.updateweixin(openId, qrtoken);
            dao.updateqrlogin(qrtoken,true);
        }
        return result;
    }
}
