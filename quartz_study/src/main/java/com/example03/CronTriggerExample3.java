package com.example03;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author :  yulei
 * @data :  2020/1/17 10:23
 * @Version :  1.0
 *
 *  上午9点到10点之间每5分钟一次，下午1点到10点之间每20分钟一次
 *
 * 参考: https://www.iteye.com/problems/47143
 **/

public class CronTriggerExample3 {

    public void run () throws SchedulerException {

        //实例化调度器
        Scheduler scheduler  =StdSchedulerFactory.getDefaultScheduler();

        //实例化job
        JobDetail job1 = JobBuilder.newJob(SimpleJob.class)
                       .withIdentity("job1","group1")
                       // 当job 没有绑定trigger时，继续保存在调度器中
                       .storeDurably()
                       .build();

        //若有相同名称的job ，是否替换，否则抛出异常, 使用这个方法，必须保证 isDurable=true
        scheduler.addJob(job1,false);

        //定义Contrigger
        CronTrigger cronTrigger1 = TriggerBuilder.newTrigger()
                            .withIdentity("trigger1","group1")
                            .withSchedule(CronScheduleBuilder.cronSchedule(" 0/10 47 * * * ? "))
                            .forJob(job1)
                            .build();

        CronTrigger cronTrigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger2","group1")
                .withSchedule(CronScheduleBuilder.cronSchedule(" 0/20 48 * * * ? "))
                //绑定Jobdetail
                .forJob(job1)
                .build();


        CronTrigger cronTrigger3 = TriggerBuilder.newTrigger()
                .withIdentity("trigger3","group1")
                .withSchedule(CronScheduleBuilder.cronSchedule(" 0/30 49 * * * ? "))
                //绑定Jobdetail
                .forJob(job1)
                .build();


//        scheduler.addJob(job1,true);
        //调度器绑定Trigger
        scheduler.scheduleJob(cronTrigger1);
        scheduler.scheduleJob(cronTrigger2);
        scheduler.scheduleJob(cronTrigger3);

        scheduler.start();

    }

    public static void main(String[] args) throws SchedulerException {
        CronTriggerExample3 example = new CronTriggerExample3();
        example.run();

    }
}
