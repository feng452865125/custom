//package com.chunhe.custom.config;
//
//import com.chunhe.custom.fmService.FmSysUserService;
//import com.chunhe.custom.secuity.*;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
///**
// * Created with IntelliJ IDEA.
// *
// * @Author: white
// * @Date: 2021/05/16/15:12
// * @Description:
// */
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    static Logger logger = LogManager.getLogger(SecurityConfig.class);
//
//    /**
//     * Spring Boot 2 配置，这里要bean 注入
//     */
////    @Bean
////    @Override
////    public AuthenticationManager authenticationManagerBean() throws Exception {
////        AuthenticationManager manager = super.authenticationManagerBean();
////        return manager;
////    }
//
////    自定义认证
////    @Autowired
////    private LoginValidateAuthenticationProvider loginValidateAuthenticationProvider;
//
//    @Autowired
//    private FmSysUserService fmSysUserService;
//
//    /**
//     * 权限核心配置
//     *
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        logger.info("security5配置 加载ing...");
//        //基础设置
//        http
////                .httpBasic()//配置HTTP基本身份验证
////                .and()
//                .authorizeRequests()
//                //配置不拦截路由
//                .antMatchers("/500").permitAll()
//                .antMatchers("/403").permitAll()
//                .antMatchers("/404").permitAll()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/login2").permitAll()
//                .anyRequest().authenticated()//所有请求都需要认证
//                .and()
//                .formLogin() //登录表单
//                .loginPage("/login")//登录页面url
////                .loginProcessingUrl("/login")//登录验证url
////                .defaultSuccessUrl("/index")//成功登录跳转
////                .successForwardUrl("/home")
////                .failureForwardUrl("/500")
//                .permitAll()//登录成功后有权限访问所有页面
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .permitAll();
////
////        //关闭csrf跨域攻击防御
////        http.csrf().disable();
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/login");
//    }
//
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        //这里要设置自定义认证
////        auth.authenticationProvider(loginValidateAuthenticationProvider);
////    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(fmSysUserService).passwordEncoder(new BCryptPasswordEncoder());
//    }
//
//}
