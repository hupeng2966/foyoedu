package com.foyoedu.consumer.config;

import com.foyoedu.common.config.CommonConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import feign.Request;
import feign.Retryer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class ConfigBeans {
    @Autowired
    private CommonConfig commonConfig;

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
