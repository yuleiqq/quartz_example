
package com.example04;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author  yulei
 */

public class SimpleJob implements Job {

    private static Logger log = LoggerFactory.getLogger(SimpleJob.class);

    public SimpleJob() {

    }

    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobKey jobKey = context.getJobDetail().getKey();

        JobDataMap jobDataMap = context.getMergedJobDataMap();

        log.info("name: "+jobDataMap.getString("name")+", email: "+jobDataMap.getString("email"));
        log.info("SimpleJob says: " + jobKey + " executing at " + new Date());
    }

}
