
package com.example03;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * @author yulei
 * <p>
 * job 任务，打印当前时间
 */

public class SimpleJob implements Job {

    private static Logger log = LoggerFactory.getLogger(SimpleJob.class);

    public SimpleJob() {
    }


    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobKey jobKey = context.getJobDetail().getKey();
        log.info("SimpleJob says: " + jobKey + " executing at " + new Date());
    }

}
