package com.foyoedu.consumer.config;

import com.foyoedu.common.config.CommonConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import feign.Request;
import feign.Retryer;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

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
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html");
                //没传参数
                ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400.html");
                //提交类型不匹配（比如:post接收却用get请求）
                ErrorPage error405Page = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error/405.html");
                factory.addErrorPages( error404Page, error400Page, error405Page);
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
        int connectTimeOutMillis = commonConfig.getTIMEOUT();//超时时间
        int readTimeOutMillis = commonConfig.getTIMEOUT();
        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
    }

    /**
     * Feign 重试设置
     * @return
     */
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(60, SECONDS.toMillis(3), 3);
    }

    /**
     * websocket
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
