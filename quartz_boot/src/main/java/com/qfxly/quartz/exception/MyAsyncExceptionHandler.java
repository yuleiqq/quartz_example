package com.qfxly.quartz.exception;

import com.qfxly.quartz.task.TestTask1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * @author yulei
 * @date 2021/2/23
 */
public class MyAsyncExceptionHandler  implements AsyncUncaughtExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(TestTask1.class);



    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {

        logger.error("----统一捕获线程异常信息----");
        logger.error("异常信息: " + throwable.getMessage());
        logger.error("方法名: " + method.getName());
        for (Object param : objects) {
            logger.error("异常参数值: " + param);
        }

    }
}
