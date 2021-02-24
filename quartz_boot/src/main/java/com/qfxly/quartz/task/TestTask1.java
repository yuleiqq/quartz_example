package com.qfxly.quartz.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author yulei
 * @date 2021/2/22
 */

@Component
public class TestTask1 {

    private static Logger logger = LoggerFactory.getLogger(TestTask1.class);

//    @Scheduled(cron = "0/10 * * * * ?")
    @Async
    public void method1( )  {
        try {
            logger.debug("method1: ");
            int a = 20/0;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("数值异常",e);
        }

    }

//    @Scheduled(cron = "0/10 * * * * ?")
    @Async
    public void method2(){
        logger.debug("method2: ");

    }


    @Async
    public void method3(String name){

    }


}
