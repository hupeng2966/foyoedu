package com.foyoedu.consumer.config;

import com.foyoedu.common.pojo.CommonConfig;
import com.foyoedu.common.utils.FoyoUtils;
import com.foyoedu.consumer.component.TokenAuthorFilter;
import com.netflix.loadbalancer.*;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class ConfigBeans {

    /**
     *Ribbon负载均衡算法
     */
    @Bean
    public IRule myRule()
    {
        //return new RandomRule();
        //return new RoundRobinRule();
        //return new BestAvailableRule();     //选择一个最小的并发请求的server
        //return new WeightedResponseTimeRule();  //根据响应时间分配一个weight，响应时间越长，weight越小，被选中的可能性越低。
        return new ZoneAvoidanceRule();         //复合判断server所在区域的性能和server的可用性选择server
    }

    /**
     * 统一页码处理配置
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html");
                ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400.html");
                ErrorPage error405Page = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error/405.html");
                container.addErrorPages( error404Page, error400Page, error405Page);
            }
        };
    }

    /**
     *zuul访问帐号及密码设置
     */
    @Autowired
    private CommonConfig commonConfig;
    @Bean
    public BasicAuthRequestInterceptor getBasicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(commonConfig.getZUUL_USERNAME(),commonConfig.getZUUL_PWD());
    }

    /**
     * Feign 超时设置
     * @return
     */
    @Bean
    public Request.Options options() {
        int connectTimeOutMillis = 30000;//超时时间
        int readTimeOutMillis = 30000;
        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
    }

    /**
     * Feign 重试设置
     * @return
     */
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100, SECONDS.toMillis(1), 3);
    }

}
