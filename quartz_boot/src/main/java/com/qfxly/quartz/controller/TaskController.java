package com.qfxly.quartz.controller;

import com.qfxly.quartz.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * @author yulei
 * @date 2021/2/23
 */

@RestController
public class TaskController {
    private  Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    TaskService taskService;


    @RequestMapping("/test1")
    public void test1() throws InterruptedException {
        LocalDateTime start = LocalDateTime.now();

        CountDownLatch countDownLatch = new CountDownLatch(50);

        for( int i =0 ;i <50;i++){
            taskService.test1(countDownLatch);
        }
        countDownLatch.await();
        LocalDateTime end  = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        logger.error("总耗时："+duration.toMillis());

    }


    @RequestMapping("/test2")
    public void test2(){
        LocalDateTime start = LocalDateTime.now();
        for( int i =0 ;i <10;i++){
            taskService.test2();
        }
        LocalDateTime end  = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        System.out.println("总耗时："+duration.toMillis());

    }

    @RequestMapping("/test3")
    public void test3(){

        List<Future<String>> resultList = new ArrayList<>();
        Future<String> result  = taskService.test3();
        resultList.add(result);
        System.out.println("异步调用直接返回...");

        for(Future<String> future : resultList){
            try {
                System.out.println("获取返回值："+future.get());
            } catch (Exception e) {
                logger.error("获取返回值发生异常!"+e.getMessage());
            }
        }


    }
}
