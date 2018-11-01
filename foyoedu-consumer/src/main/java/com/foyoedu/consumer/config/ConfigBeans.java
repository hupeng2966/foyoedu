package com.foyoedu.consumer.config;

import com.foyoedu.consumer.component.TokenAuthorFilter;
import com.netflix.loadbalancer.*;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.servlet.ErrorPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ConfigBeans {
//    /*
//    * 用Feign的情况下就不用手动开启restTemplate
//    * */
//    @Bean
//    @LoadBalanced
//    public RestTemplate getRestTemplate()
//    {
//        return new RestTemplate();
//    }

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
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
                ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/400.html");
                container.addErrorPages( error404Page,error400Page);
            }
        };
    }

    /**
     *zuul访问帐号及密码设置
     */
    @Bean
    public BasicAuthRequestInterceptor getBasicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("foyoedu","foyoedu");
    }

    /**
     * 拦截器注册
     */
    @Bean
    public FilterRegistrationBean myFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TokenAuthorFilter());
        registration.addUrlPatterns("/foyo/*");// 拦截路径
        registration.setName("tokenAuthorFilter");// 拦截器名称
        //registration.setOrder(1);// 顺序
        return registration;
    }
}
