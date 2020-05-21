package com.example06;

import static org.quartz.DateBuilder.nextGivenSecondDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author :  yulei
 * @data :  2020/5/21 21:30
 * @Version :  1.0
 **/

public class MisfireExample {

    public void run() throws Exception {
        Logger log = LoggerFactory.getLogger(MisfireExample.class);
        log.info("------- Initializing -------------------");
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();
        log.info("------- Initialization Complete -----------");

        Date startTime = nextGivenSecondDate(null, 15);

        //statefulJob1 3秒钟执行一次 (但是会延迟10秒)
        JobDetail job = newJob(StatefulDumbJob.class).withIdentity("statefulJob1", "group1")
                .usingJobData(StatefulDumbJob.EXECUTION_DELAY, 10000L).build();

        SimpleTrigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(3).repeatForever()).build();

        Date ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");


        // statefulJob2每三秒钟运行一次
        //(但是它会延迟10秒—因此在几次迭代之后故意失火)
        job = newJob(StatefulDumbJob.class).withIdentity("statefulJob2", "group1")
                .usingJobData(StatefulDumbJob.EXECUTION_DELAY, 10000L).build();

        trigger = newTrigger()
                .withIdentity("trigger2", "group1")
                .startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(3).repeatForever()
                        .withMisfireHandlingInstructionNowWithExistingCount()) // set misfire instructions
                .build();

        ft = sched.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        log.info("------- Starting Scheduler ----------------");
        sched.start();
        log.info("------- Started Complete -----------------");
        try {
            // sleep for ten minutes for triggers to file....
            Thread.sleep(600L * 1000L);
        } catch (Exception e) {
            //
        }
        log.info("------- Shutting Down ---------------------");

        sched.shutdown(true);

        log.info("------- Shutdown Complete -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");

    }

    public static void main(String[] args) throws Exception {
        MisfireExample example = new MisfireExample();
        example.run();

    }

}
