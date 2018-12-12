# 项目介绍
The standard framework of spring cloud projects, this framework will make a perfect integration of JavaEE Technology.  

本人开源的综指是用优美的代码实现一个全家桶式的JavaEE项目，其中涉猎到Java项目中绝大部分最炙手可热的技术。  
主要技术框架springcloud里程碑版本Finchley.SR2，springboot最新版2.0.6。  

一、项目中已经实现的技术要素：  
1.SpringCloud整合了Eureka、Zuul、Ribbo、Feign、Hystrix、Config分布式配置中心等技术框架，实现了微服务的Clients与Severs完美兼容；  
2.中间件：Nosql用的是Redis、消息队列用的是Rabbitmq、分布式文件服务器FastDFS、文件缓存服务器Varnish、反向代理Nginx；  
3.数据库用的是Mysql8.0、持久层框架用的Mybatis、数据库连接池用的Druid；  
4.应用容器引擎用的Docker，管理着Redis、Rabbitmq、Fastdfs、Mysql、all微服务；  
5.安全框架用的SpringSecurity,实现了用户名密码/验证码/短信验证码/微信扫二维码登录/Scheduled定时清理冗余数据；  
6.CAS或OAuth2实现SSO(计划)。  

二、各项技术实现细节：  
1.foyoedu-eureka技术要点：  
    1.1 用户认证访问Eureka；  
    1.2 从云端配置中心(Github)读取项目配置文件；  
    1.3 支持eureka集群。  
    
2.foyoedu-zuul技术要点：  
    2.1 用户认证访问Zuul；  
    2.2 从云端配置中心(Github)读取项目配置文件；  
    2.3 微服务Zuul支持自身集群化；  
    2.4 敏感词过滤，HTML特殊字符过滤。  
    
3.Ribbo技术要点：  
    3.1 负载均衡采用 ZoneAvoidanceRule()算法（复合判断servers所在区域的性能和servers的可用性选择server）  

4.Feign技术要点：  
    4.1 面向接口调用Servers；  
    4.2 Client端HTTP请求头穿透。  

5.Hystrix技术要点：  
    5.1 Servers端异常的全局捕获；  
    5.2 异常输出格式的统一封装。  
    
6.Config分布式配置中心技术要点：  
    6.1 支持微服务从配置中心读取配置；  
    6.2 支持微服务根据dev/test/release读取相关配置。  

7.foyoedu-consumer技术要点：  
    7.1 用Filter实现用户统一认证、上传文件的类型、上传文件大小的过滤；  
    7.2 用AOP与Async实现了用户访问日志的记录（访问地址、类名方法名、访问IP、方法参数、函数执行时间）；  
    7.3 用HttpSessionListener与redis实现了在线用户数、ip地址、首次访问时间的记录；  
    7.4 实现了Response数据格式及异常信息的统一处理；  
    7.5 用WebSocket实现了消息发送接收；  
    7.6 实现了CRUD、文件上传的Demo；  
    7.7 实现了400、404、405报错的统一页面跳转；  
    7.8 用Swagger实现了Rest接口访问说明及Rest接口的模拟调用。  
    
8.foyoedu-provider-base技术要点：  
    8.1 用户认证Token的统一捕获；  
    8.2 redis存取用户信息替代session；  
    8.3 用AOP对Transaction做统一管理；  
    8.4 使用FastDFS保存上传文件；  
    8.5 使用POI实现excel的导入导出功能；  
    8.6 对单表的CRUD及分页实现了统一封装处理；  
    8.7 写了Callable异步调用demo，提高Tomcat的线程使用率，增加系统的并发访问量；  
    8.8 用Redis+Rabbitmq+DeferredResult实现了学生秒杀抢课；  
    
9.foyoedu-common技术要点：  
    9.1 自定义手机号验证注解；  
    9.2 Tomcat的性能优化；  
    9.3 封装了Cookie读写工具类CookieUtils；   
    9.4 封装了POI对excel操作的工具类ExcelUtils；  
    9.5 封装了redis加解锁工具类RedisLock；  
    9.6 封装了SQL防注入工具类SqlUtils。  
