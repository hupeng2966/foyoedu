package com.foyoedu.consumer.component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class FoyoServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {


//        // 使用properties对象加载输入流
//        Properties properties = new Properties();
//        // 使用ClassLoader加载properties配置文件生成对应的输入流
//        InputStream in = this.getClass().getClassLoader().getResourceAsStream("application.properties");
//        // 使用properties对象加载输入流
//        try {
//            properties.load(in);
//            ServletContext context = servletContextEvent.getServletContext();
//            properties.forEach((k,v) -> {
//                context.setAttribute(k.toString(), v);
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
