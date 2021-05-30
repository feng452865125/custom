package com.chunhe.custom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableCaching
//@EnableAsync
//@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = {"com.chunhe.custom.framework.mapper", "com.chunhe.custom.pc.mapper"})
///**
// * 方法一
// * spring boot jar启动的写法
// * 2018年10月26日10:51:48
// */
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");
    }

}


///**
// * 方法二
// * tomcat启动的写法
// * 2018年10月26日10:51:48
// */
//public class Application extends SpringBootServletInitializer {
//
//	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
//	}
//
//	/**
//	 * 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
//	 */
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		// 注意这里要指向原先用main方法执行的Application启动类
//		return application.sources(Application.class);
//	}
//
//}