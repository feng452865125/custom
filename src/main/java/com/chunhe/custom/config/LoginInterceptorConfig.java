//package com.chunhe.custom.config;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//
///**
// * @author:fengzhiyuan
// * @date:2020/10/10 9:10
// * @description: 拦截器
// * @param: null
// */
//@Configuration
//public class LoginInterceptorConfig extends WebMvcConfigurationSupport {
//
//    static Logger logger = LogManager.getLogger(LoginInterceptorConfig.class);
//
//    @Bean
//    public LoginInterceptor getMyInterceptor() {
//        return new LoginInterceptor();
//    }
//
//    /**
//     * 添加静态资源文件，外部可以直接访问地址
//     *
//     * @param registry
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/resources/")
//                .addResourceLocations("classpath:/static/")
//                .addResourceLocations("classpath:/templates/")
//                .addResourceLocations("classpath:/public/");
//        super.addResourceHandlers(registry);
//    }
//
////    /**
////     * @author:fengzhiyuan
////     * @date:2020/12/12 14:43
////     * @description: 自定义WebMvcConfigur(这里改名LoginInterceptorConfig)之后，原有properties中的jackson配置会失效
////     * @param:
////     */
////    @Bean
////    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
////        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
////        ObjectMapper objectMapper = new ObjectMapper();
////        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
////        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
////        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
////        objectMapper.setLocale(Locale.SIMPLIFIED_CHINESE);
////        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
////        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
////        jsonConverter.setObjectMapper(objectMapper);
////        return jsonConverter;
////    }
////
////    @Override
////    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
////        converters.add(customJackson2HttpMessageConverter());
////        super.addDefaultHttpMessageConverters(converters);
////    }
//
//}
