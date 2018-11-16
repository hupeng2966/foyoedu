package com.foyoedu.zuul;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

@Component
@PropertySource( name="sensitive-word-dict.properties",value={"classpath:sensitive-word-dict.properties"},ignoreResourceNotFound=false,encoding="UTF-8")
public class SensitiveWordFilter extends ZuulFilter {
    @Value("#{'${filterWord}'.split(',')}")
    private List<String> list;

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
        HttpServletRequest request = ctx.getRequest();
        String ct = request.getHeader("Content-Type");
        if(!StringUtils.isEmpty(ct)) {
            if(request.getHeader("Content-Type").contains("multipart/form-data")) {
                return null;
            }
        }

        InputStream in = null;
        String body = "";
        if (!ctx.isChunkedRequestBody()) {
            try {
                in = request.getInputStream();
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
                            String val = v;

                            if(val.contains("<")) {
                                val = val.replaceAll("<","&lt;");
                            }
                            if(v.contains(">")) {
                                val = val.replaceAll(">","&gt;");
                            }
                            if(v.contains("\"")) {
                                val = val.replaceAll("\"","&quot;");
                            }
                            if(v.contains("'")) {
                                val = val.replaceAll("'","&#39;");
                            }

                            //遍历list集合，看看获取得到的数据有没有敏感词汇
                            for (String s : list) {
                                if (v.contains(s)) {
                                    val = val.replaceAll(s,"***");
                                }
                            }
                            map.replace(k,v,val);
                        }
                    });
                    final byte[] reqBodyBytes = MAPPER.writeValueAsString(map).getBytes();
                    ctx.setRequest(new HttpServletRequestWrapper(request) {
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
