package com.example02;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author :  yulei
 * @data :  2020/1/16 8:36
 * @Version :  1.0
 *
 * 这个示例将演示使用简单触发器实现Quartz的所有基本调度功能
 **/

public class SimpleTriggerExample {

     Logger log = LoggerFactory.getLogger(SimpleTriggerExample.class);

     public void run () throws Exception {

         log.info("------- Initializing -------------------");
         // 实例化调度器
         SchedulerFactory sf = new StdSchedulerFactory();
         Scheduler sched = sf.getScheduler();
         log.info("------- Initialization Complete --------");

         log.info("------- Scheduling Jobs ----------------");

         //可以在调用sched.start() 之前 调用jobs
         //定义任务的执行时间
         Date startTime = DateBuilder.nextGivenSecondDate(null, 15);

         /** job1**/
         //job1 只会执行一次
         JobDetail job = newJob(SimpleJob.class).withIdentity("job1", "group1").build();

         //定义一个触发器
         SimpleTrigger trigger = (SimpleTrigger) newTrigger().withIdentity("trigger1", "group1").startAt(startTime).build();

         //使用调度器将job1 和 trigger1 绑定起来，并运行 .
         Date ft = sched.scheduleJob(job, trigger);
         log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                 + trigger.getRepeatInterval() / 1000 + " seconds");

         /** job2**/
         //job2 和job1 一样，归属在同一个组中
         job = newJob(SimpleJob.class).withIdentity("job2", "group1").build();
         trigger = (SimpleTrigger) newTrigger().withIdentity("trigger2", "group1").startAt(startTime).build();

         ft = sched.scheduleJob(job, trigger);
         log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                 + trigger.getRepeatInterval() / 1000 + " seconds");

         /***job3***/
         //运行11次 (1次立即运行；之后，每个10秒运行一次,共10次)
         job = newJob(SimpleJob.class).withIdentity("job3", "group1").build();
         trigger = newTrigger().withIdentity("trigger3", "group1").startAt(startTime)
                 .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10)).build();

         ft = sched.scheduleJob(job, trigger);
         log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                 + trigger.getRepeatInterval() / 1000 + " seconds");
         // 所有的任务已经被添加到调度器，但是任务都不会运行，直到调用start 方法之后.
         sched.start();

//         //在sched.start() 之后，jobs 仍然可以被调用.
//         job = newJob(SimpleJob.class).withIdentity("job4", "group1").build();
//         trigger = newTrigger().withIdentity("trigger4", "group1").startAt(startTime)
//                 .withSchedule(simpleSchedule().withIntervalInMinutes(5).withRepeatCount(20)).build();
//
//         ft = sched.scheduleJob(job, trigger);
//         log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
//                 + trigger.getRepeatInterval() / 1000 + " seconds");
//
//         //job 也可以立即执行，不用触发器
//         job = newJob(SimpleJob.class).withIdentity("job5", "group1").storeDurably().build();
//         log.info("'Manually' triggering job5...");
//         sched.addJob(job, true);
//
//         //job可以被再度调用
//         log.info("------- Rescheduling... --------------------");
//         trigger = newTrigger().withIdentity("trigger3", "group1").startAt(startTime)
//                 .withSchedule(simpleSchedule().withIntervalInMinutes(5).withRepeatCount(20)).build();
//         ft = sched.rescheduleJob(trigger.getKey(), trigger);
//         log.info("job3 rescheduled to run at: " + ft);

         TimeUnit.MINUTES.sleep(1);
         //获取当前已经执行的任务个数
         SchedulerMetaData metaData = sched.getMetaData();
         log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");

     }


    public static void main(String[] args) throws Exception {

        SimpleTriggerExample example = new SimpleTriggerExample();
        example.run();
    }



}
