package com.example01.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


/**
 * @author :  yulei
 * @data :  2020/1/15 16:07
 * @Version :  1.0
 **/

public class HelloJob  implements Job {
    private static Logger log = LoggerFactory.getLogger(HelloJob.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        //打印当前日期
        log.debug("Hello World! - " + new Date());
    }
}
