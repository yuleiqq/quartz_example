package com.example01;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author :  yulei
 * @data :  2020/1/10 14:25
 * @Version :  1.0
 **/

public class SchedulerTest {

   public static Logger log = LoggerFactory.getLogger(SchedulerTest.class);

    public static void main(String[] args) throws SchedulerException {
        //调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        log.debug("调度器名称："+scheduler.getSchedulerName());

        scheduler.start();
    }


}
