package com.foyoedu.zuul;

import com.alibaba.fastjson.JSON;
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
import java.util.List;
import java.util.Map;

@Component
@PropertySource(name="sensitive-word-dict.properties",value={"classpath:sensitive-word-dict.properties"},ignoreResourceNotFound=false,encoding="UTF-8")
public class SensitiveWordFilter extends ZuulFilter {
    @Value("#{'${filterWord}'.split(',')}")
    private List<String> list;

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
                    Map<String, Object> map = JSON.parseObject(body, Map.class);
                    map.forEach((k, v) -> {
                        if (!StringUtils.isEmpty(v) && v instanceof String) {
                            String val = v.toString();

                            if (val.contains("<")) {
                                val = val.replaceAll("<", "&lt;");
                            }
                            if (val.contains(">")) {
                                val = val.replaceAll(">", "&gt;");
                            }
                            if (val.contains("\"")) {
                                val = val.replaceAll("\"", "&quot;");
                            }
                            if (val.contains("'")) {
                                val = val.replaceAll("'", "&#39;");
                            }

                            //遍历list集合，看看获取得到的数据有没有敏感词汇
                            for (String s : list) {
                                if (val.contains(s)) {
                                    val = val.replaceAll(s, "***");
                                }
                            }
                            if (!v.toString().equals(val)) map.replace(k, v, val);
                        }
                    });
                    final byte[] reqBodyBytes = JSON.toJSONBytes(map);
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
                }catch (Exception e) { }
            }
        }
        return null;
    }
}
