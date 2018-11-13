package com.foyoedu.consumer.component;

import com.foyoedu.common.config.CommonConfig;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.pojo.User;
import com.foyoedu.common.utils.CookieUtils;
import com.foyoedu.common.utils.FoyoUtils;
import com.foyoedu.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


@Slf4j
//@Component
//@WebFilter(urlPatterns = "/foyo/*", filterName = "tokenAuthorFilter")
public class TokenAuthorFilter implements Filter {

    @Value("${cookie.token_key}")
    private String TOKEN_KEY;

    @Value("${login.uri}")
    private String LOGIN_URI;

    @Autowired
    private CommonConfig config;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        HttpSession session= req.getSession();

        res.setCharacterEncoding("UTF-8");
        //res.setContentType("application/json; charset=utf-8");

        String token = CookieUtils.getCookieValue(req, TOKEN_KEY);
        String result = "";

        // 以下都是需要验证URI的流程
        if (StringUtils.isEmpty(token)) {
            result = FoyoUtils.error(403,"token没有认证通过!原因为：客户端请求参数中无token信息");
        } else {
            String json = "";
            try {
                json = redisTemplate.opsForValue().get(token);
            }catch (Exception e) {
                log.error(e.toString());
                result = FoyoUtils.error(403,"token没有认证通过!原因为：客户端请求中认证的token信息无效");
            }
            if (StringUtils.isEmpty(json)) {
                result = FoyoUtils.error(403,"token没有认证通过!原因为：客户端请求中认证的token信息无效");
            }else if(JsonUtils.jsonToPojo(json.toString(), User.class).isDelete()){
                result = FoyoUtils.error(401,"该token目前已处于停用状态，请联系邮件系统管理员确认!");
            }else{
                redisTemplate.expire(token, config.getREDIS_SESSION_EXPIRE(), TimeUnit.MINUTES);
                // 如果登录成功，则跳转到登录前浏览的页面，如果登录前是从login.jsp过来的，则不跳转
                Object uri = session.getAttribute("requestURI");
                if(uri != null) {
                    session.removeAttribute("requestURI");
                    res.sendRedirect(uri.toString());
                }else {
                    chain.doFilter(request, response);
                }
                return;
            }
        }
        // token验证不通过的，全部跳转到登录页面
        FoyoResult foyoResult = JsonUtils.jsonToPojo(result, FoyoResult.class);
        if(foyoResult.getStatus() == 403) {
            // 记录用户未登录状态下的访问URI
            String requestURI = req.getRequestURI();
            session.setAttribute("requestURI", requestURI);
            res.sendRedirect(req.getContextPath() + "/" + LOGIN_URI);
            return;
        }

        if(foyoResult.getStatus() == 401){
            outPutResponse(res, foyoResult);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void outPutResponse(HttpServletResponse response, FoyoResult result) throws IOException {
        String jsonStr = JsonUtils.objectToJson(result);
        response.getWriter().write(jsonStr);
        response.getWriter().flush();
        response.getWriter().close();
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

