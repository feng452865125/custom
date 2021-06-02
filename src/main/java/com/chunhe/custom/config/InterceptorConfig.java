package com.chunhe.custom.config;

import com.chunhe.custom.utils.DictUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * @author:fengzhiyuan
 * @date:2020/10/10 9:10
 * @description: 拦截器
 * @param: null
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    static Logger logger = LogManager.getLogger(InterceptorConfig.class);

    @Bean
    public Interceptor getMyInterceptor() {
        DictUtils.init("dict.xml");
        return new Interceptor();
    }

    /**
     * 添加静态资源文件，外部可以直接访问地址
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("拦截器 资源 加载ing...");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/templates/")
                .addResourceLocations("classpath:/public/");
        super.addResourceHandlers(registry);
    }

}
