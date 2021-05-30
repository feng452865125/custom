package com.chunhe.custom.config;

import com.chunhe.custom.service.fm.FmSysUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Created with IntelliJ IDEA.
 *
 * @Author: white
 * @Date: 2021/05/16/15:12
 * @Description:
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    static Logger logger = LogManager.getLogger(SecurityConfig.class);

    @Autowired
    private FmSysUserService fmSysUserService;

    /**
     * Spring Boot 2 配置，这里要bean 注入
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    /**
     * 权限核心配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("security5配置 加载ing...");
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/bootstrap/**", "/build/**", "/dist/**", "/plugins/**", "/**/favicon.ico", "/druid/**", "/error/**", "/test/**", "/app/**", "/thirdSupplier/api");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(fmSysUserService).passwordEncoder(new BCryptPasswordEncoder());
    }


}
