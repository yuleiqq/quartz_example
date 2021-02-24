package com.qfxly.quartz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author yulei
 * @date 2021/2/23
 */

@Service
public class TaskService {
    private Logger logger = LoggerFactory.getLogger(TaskService.class);

    /**
     * 异步调用
     * @param countDownLatch
     */
    @Async
    public void test1(CountDownLatch countDownLatch){
        try {

            logger.debug("调用TaskService 的 test1()方法....start...");
            TimeUnit.SECONDS.sleep(10);
            logger.debug("调用TaskService 的 test1()方法...end...");

            countDownLatch.countDown();
            logger.debug("剩余数:"+ countDownLatch.getCount());


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 串行调用
     */
    public void test2(){
        try {
            TimeUnit.SECONDS.sleep(10);
            logger.debug("调用TaskService 的 test()2方法....");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步调用，带返回值 AsyncResult
     */

    @Async
    public Future<String> test3(){

        try {
            System.out.println("test3()调用开始....");
            TimeUnit.SECONDS.sleep(100);
            System.out.println("test3()调用结束....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AsyncResult result = new AsyncResult("success");

        result.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("调动失败");
            }
            @Override
            public void onSuccess(String result) {
                System.out.println("结果值:"+result);
            }
        });

        return  result;

    }

}
