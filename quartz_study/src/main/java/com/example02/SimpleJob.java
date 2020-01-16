package com.example02;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author :  yulei
 * @data :  2020/1/16 8:30
 * @Version :  1.0
 *
 * 定义一个简单的任务(Job)，该任务会在调度器(Scheduler)给其绑定触发器(Trigger)后执行
 **/

public class SimpleJob implements Job {

    private static Logger log = LoggerFactory.getLogger(SimpleJob.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            JobKey jobKey = context.getJobDetail().getKey();

            log.info("SimpleJob says "+ jobKey +" executing at " +new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
