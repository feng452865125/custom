package com.chunhe.custom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableAsync
//@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = {"com.chunhe.custom.mapper"})
public class CustomApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomApplication.class, args);
//        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");
    }

}

