package com.foyoedu.common.utils;

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
import java.io.IOException;

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

    public static String errorMessage(Throwable throwable) {
        String msg = throwable.getMessage();
        log.error(msg);
        Integer status = Integer.parseInt(msg.split(" ")[1]);
        String msgfunction = msg.split(" ")[3];
        String content = msg.substring(msg.indexOf("content:")+9);
        Document doc = Jsoup.parse(content);
        Element msgContent = doc.getElementsByTag("div").get(2);
        return error(status, msgfunction+" → "+msgContent.text());
    }

    public static String ok(Object data) {
        return new FoyoResult(data).toString();
    }

    public static String error(Integer status, String errMsg) {
        return  new FoyoResult(status, errMsg).toString();
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

    public static void outPutResponse(HttpServletResponse response, String result) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain;charset=utf-8");
            response.getWriter().write(result);
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
