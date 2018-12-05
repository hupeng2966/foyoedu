package com.foyoedu.common.utils;

import com.alibaba.fastjson.JSON;
import com.foyoedu.common.config.CommonConfig;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.pojo.PageResult;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class FoyoUtils {
    @Autowired
    private CommonConfig commonConfig;
    private static FoyoUtils foyoUtils;

    @Autowired
    private FastFileStorageClient storageClient;

    @PostConstruct
    public void init() {
        foyoUtils = this;
    }

    public static FoyoResult errorMessage(Throwable throwable) {
        String msg = throwable.getMessage();
        log.error(msg);
        Integer status = Integer.parseInt(msg.split(" ")[1]);
        String msgfunction = FoyoUtils.getRequest().getRequestURI()+" → " + msg.split(" ")[3];
        String content = msg.substring(msg.indexOf("content:") + 9);
        try{
            Document doc = Jsoup.parse(content);
            Element msgContent = doc.getElementsByTag("div").get(2);
            return error(status, msgfunction+" → "+msgContent.text());
        }catch (Exception e){ }
        return error(status, msgfunction+" → "+ JSON.parseObject(content).getString("message"));
    }

    public static <T> FoyoResult ok(T data) {
        return new FoyoResult<T>(data);
    }

    public static FoyoResult error(Integer status, String errMsg) {
        return  new FoyoResult<String>(status, errMsg);
    }

    public static PageResult pageResult(Integer total, Object data) {
        return new PageResult(total, data);
    }

    public static Object getSevletContextByArg(String arg) {
        return getRequest().getServletContext().getAttribute(arg);
    }

    public static Object getSevletContextByArg(String arg, ServletContext servletContext) {
        return servletContext.getAttribute(arg);
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static String getToken() {
        return  CookieUtils.getCookieValue(getRequest(), foyoUtils.commonConfig.getTOKEN_KEY());
    }

    public static String getCookie(String cookieName) {
        return CookieUtils.getCookieValue(getRequest(), cookieName);
    }

    public static String saveFdfsFile(MultipartFile file) throws Throwable {
        String originalFilename = file.getOriginalFilename();
        String extName = "";
        if(originalFilename.lastIndexOf(".") != -1) {
            extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        StorePath storePath = foyoUtils.storageClient.uploadFile(file.getBytes(), extName);
        //响应上传图片的url
        return foyoUtils.commonConfig.getFASTDFS_STORAGE_URL() + storePath.getFullPath();
    }

    public static <T> void validatePojo(T obj) throws Throwable {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        List list = new ArrayList(constraintViolations);
        if (list.size() > 0) {
            ConstraintViolation<T> constraintViolation = (ConstraintViolation<T>) list.get(0);
            throw new Exception(constraintViolation.getMessage());
        }
    }

    public static boolean isIP(String addr) {
        if(addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean ipAddress = mat.find();

        return ipAddress;
    }

    public static void outPutResponse(FoyoResult result) {
        outPutResponse(FoyoUtils.getResponse(), result);
    }
    public static void outPutResponse(HttpServletResponse response, FoyoResult result) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(result.toString());
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        PrintWriter writer = null;
//        OutputStreamWriter osw = null;
//        try {
//            osw = new OutputStreamWriter(response.getOutputStream() , "UTF-8");
//            writer = new PrintWriter(osw, true);
//            String jsonStr = JsonUtils.objectToJson(result);
//            writer.write(jsonStr);
//            writer.flush();
//            writer.close();
//            osw.close();
//        } catch (UnsupportedEncodingException e) {
//            log.error("过滤器返回信息失败:" + e.getMessage(), e);
//        } catch (IOException e) {
//            log.error("过滤器返回信息失败:" + e.getMessage(), e);
//        } finally {
//            if (null != writer) {
//                writer.close();
//            }
//            if(null != osw){
//                osw.close();
//            }
//        }
    }

}
