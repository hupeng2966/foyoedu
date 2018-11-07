package com.foyoedu.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class SensitiveWordFilter extends ZuulFilter {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Override
    public String filterType() {
        // 前置过滤器
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 优先级为0，数字越大，优先级越低
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 是否执行该过滤器，此处为true，说明需要过滤
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        //定义一堆敏感词汇
        List<String> list = Arrays.asList("傻b", "尼玛", "操蛋","fuck");

        InputStream in = null;
        String body = "";
        if (!ctx.isChunkedRequestBody()) {
            try {
                in = ctx.getRequest().getInputStream();
                if (in != null) {
                    body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!StringUtils.isEmpty(body)) {
                try {
                    HashMap<String, String> map = MAPPER.readValue(body, HashMap.class);
                    map.forEach((k,v) -> {
                        if (!StringUtils.isEmpty(v)) {
                            //遍历list集合，看看获取得到的数据有没有敏感词汇
                            for (String s : list) {
                                if (v.indexOf(s) != -1) {
                                    map.replace(k,v,v.replaceAll(s,"*****"));
                                }
                            }
                        }
                    });
                    final byte[] reqBodyBytes = MAPPER.writeValueAsString(map).getBytes();
                    ctx.setRequest(new HttpServletRequestWrapper(ctx.getRequest()) {
                        @Override
                        public ServletInputStream getInputStream() throws IOException {
                            return new ServletInputStreamWrapper(reqBodyBytes);
                        }

                        @Override
                        public int getContentLength() {
                            return reqBodyBytes.length;
                        }

                        @Override
                        public long getContentLengthLong() {
                            return reqBodyBytes.length;
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
