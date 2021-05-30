package com.chunhe.custom.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    static Logger logger = LogManager.getLogger(CorsConfig.class);

    @Bean
    public CorsFilter corsFilter(CorsProperties corsProperties) {
        logger.info("跨域配置 加载ing...");
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1) 允许的域,不要写*，否则cookie就无法使用了
        for (String allowedOrigin : corsProperties.getAllowedOrigins()) {
            config.addAllowedOrigin(allowedOrigin);
        }
        //2) 是否发送Cookie信息
        config.setAllowCredentials(corsProperties.getAllowCredentials());
        //3) 允许的请求方式
        for (String allowedMethod : corsProperties.getAllowedMethods()) {
            config.addAllowedMethod(allowedMethod);
        }
        // 4）允许的头信息
        for (String allowedHeader : corsProperties.getAllowedHeaders()) {
            config.addAllowedHeader(allowedHeader);
        }
        // 5) 允许暴露的头信息
        for (String exposedHeader : corsProperties.getExposedHeaders()) {
            config.addExposedHeader(exposedHeader);
        }
        // 6）设置有效期
        config.setMaxAge(corsProperties.getMaxAge());

        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration(corsProperties.getFilterPath(), config);

        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);
    }
}
