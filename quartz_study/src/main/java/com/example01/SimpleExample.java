package com.example01;

import com.example01.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author :  yulei
 * @data :  2020/1/15 15:58
 * @Version :  1.0
 **/

public class SimpleExample {

    private   Logger log = LoggerFactory.getLogger(SchedulerTest.class);

    public void  run () throws Exception {
        //实例化调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();


        //定义一个JobDetail ,并绑定到我们的 Job 。 JobBuilder 用户来实例化JobDetail
        JobDetail job = newJob(HelloJob.class).withIdentity("job1", "group1").build();

        Date runTime = evenMinuteDate(new Date());


        //定一个触发器Trigger, 立即运行，之后每隔40秒一次
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();

        //告诉调度器使用trigger 调度我们的job
        scheduler.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + runTime);

        //启动调度器 , 在启动调度器之前，任务和触发器不会运行
        scheduler.start();
        log.info("------- Started Scheduler -----------------");
//        log.info("------- Waiting 65 seconds... -------------");

//        //休眠65秒，让任务有机会执行
//        TimeUnit.SECONDS.sleep(65);

        // 关闭调度器
//        log.info("------- Shutting Down ---------------------");
//        scheduler.shutdown(true);
//        log.info("------- Shutdown Complete -----------------");
    }


    public static void main(String[] args) throws Exception {
        SimpleExample  simpleExample = new SimpleExample();
        simpleExample.run();

    }

}
