package com.chunhe.custom.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ThreadAsyncConfig implements AsyncConfigurer {

    static Logger logger = LogManager.getLogger(ThreadAsyncConfig.class);

    @Value("${async.executor.thread.corePoolSize}")
    private int corePoolSize;
    @Value("${async.executor.thread.maxPoolSize}")
    private int maxPoolSize;
    @Value("${async.executor.thread.queueCapacity}")
    private int queueCapacity;
    @Value("${async.executor.thread.waitSecond}")
    private int waitSecond;
    @Value("${async.executor.thread.namePrefix}")
    private String namePrefix;

    @Override
    @Bean("asyncService")
    public Executor getAsyncExecutor() {
        logger.info("异步线程配置 加载ing...");
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        //设置核心线程数
        threadPool.setCorePoolSize(corePoolSize);
        //设置最大线程数
        threadPool.setMaxPoolSize(maxPoolSize);
        //线程池所使用的缓冲队列
        threadPool.setQueueCapacity(queueCapacity);
        //等待任务在关机时完成--表明等待所有线程执行完
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        // 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        threadPool.setAwaitTerminationSeconds(waitSecond);
        //  线程名称前缀
        threadPool.setThreadNamePrefix(namePrefix);
        // 初始化线程
        threadPool.initialize();
        return threadPool;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }

}
