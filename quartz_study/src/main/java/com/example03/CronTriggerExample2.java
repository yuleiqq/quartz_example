package com.example03;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author :  yulei
 * @data :  2020/1/17 10:23
 * @Version :  1.0
 * <p>
 * 上午9点到10点之间每5分钟一次，下午1点到10点之间每20分钟一次
 **/

public class CronTriggerExample2 {

    public void run() throws SchedulerException {

        //实例化调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        //实例化job
        JobDetail job1 = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job1", "group1")
                .build();


        //定义Contrigger
        CronTrigger cronTrigger1 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule(" 0/10 45 * * * ? "))
                .build();

        CronTrigger cronTrigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule(" 0/3 42 * * * ? "))
                .build();

        //调度器绑定Trigger
        scheduler.scheduleJob(job1, cronTrigger1);
        scheduler.scheduleJob(job1, cronTrigger2);

        scheduler.start();

    }

    public static void main(String[] args) throws SchedulerException {
        CronTriggerExample2 example = new CronTriggerExample2();
        example.run();

    }

}
