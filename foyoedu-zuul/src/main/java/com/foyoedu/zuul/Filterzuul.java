package com.foyoedu.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

//@Component
public class Filterzuul extends ZuulFilter {
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String A = request.getHeader("Cookie").toString();
//        System.out.println(String.format("%s AccessUserNameFilter request to %s", request.getMethod(), request.getRequestURL().toString()));
//        // 获取请求的参数
//        String username = request.getParameter("username");
//        // 如果请求的参数不为空，且值为chhliu时，则通过
//        if(null != username && username.equals("chhliu")) {
//            // 对该请求进行路由
//            ctx.setSendZuulResponse(true);
//            ctx.setResponseStatusCode(200);
//            // 设值，让下一个Filter看到上一个Filter的状态
//            ctx.set("isSuccess", true);
//            return null;
//        }else{
//            // 过滤该请求，不对其进行路由
//            ctx.setSendZuulResponse(false);
//            // 返回错误码
//            ctx.setResponseStatusCode(401);
//            // 返回错误内容
//            ctx.setResponseBody("{\"result\":\"username is not correct!\"}");
//            ctx.set("isSuccess", false);
//            return null;
//        }
        return null;
    }

    @Override
    public boolean shouldFilter() {
        // 是否执行该过滤器，此处为true，说明需要过滤
        return true;
    }

    @Override
    public int filterOrder() {
        // 优先级为0，数字越大，优先级越低
        return 0;
    }

    @Override
    public String filterType() {
        // 前置过滤器
        return "pre";
    }
}
