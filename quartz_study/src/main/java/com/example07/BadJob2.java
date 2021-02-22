
package com.example07;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class BadJob2 implements Job {

    // Logging
    private static Logger _log = LoggerFactory.getLogger(BadJob2.class);
    private int calculation;

    /**
     * Empty public constructor for job initialization
     */
    public BadJob2() {
    }

    /**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a <code>{@link org.quartz.Trigger}</code>
     * fires that is associated with the <code>Job</code>.
     * </p>
     *
     * @throws JobExecutionException if there is an exception while executing the job.
     */
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        _log.info("---" + jobKey + " executing at " + new Date());

        // a contrived example of an exception that
        // will be generated by this job due to a 
        // divide by zero error
        try {
            int zero = 0;
            calculation = 4815 / zero;
        } catch (Exception e) {
            _log.info("--- Error in job!");
            JobExecutionException e2 =
                    new JobExecutionException(e);
            // Quartz will automatically unschedule
            // all triggers associated with this job
            // so that it does not run again
            e2.setUnscheduleAllTriggers(true);
            throw e2;
        }

        _log.info("---" + jobKey + " completed at " + new Date());
    }

}
