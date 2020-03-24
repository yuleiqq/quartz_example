package com.example04;

        import org.quartz.*;
        import org.quartz.impl.StdSchedulerFactory;

/**
 * 1442554244
 * JobExecutionContext例子
 **/

public class CronTriggerExample {

    public void run () throws SchedulerException {

        //实例化调度器
        Scheduler scheduler  =StdSchedulerFactory.getDefaultScheduler();

        //实例化job
        JobDetail job1 = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job1","group1")
                //设置jobDetail值
                .usingJobData("name","tom")
                .usingJobData("email","123@qq.com")
                // 当job 没有绑定trigger时，继续保存在调度器中
                .storeDurably()
                .build();

        //若有相同名称的job ，是否替换，否则抛出异常, 使用这个方法，必须保证 isDurable=true
        scheduler.addJob(job1,false);

        //定义Contrigger
        CronTrigger cronTrigger1 = TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1")
                //设置JobDatail值
                .usingJobData("email","456@qq.com")
                .withSchedule(CronScheduleBuilder.cronSchedule(" 0/5 * * * * ? "))
                .forJob(job1)
                .build();

        scheduler.scheduleJob(cronTrigger1);
        scheduler.start();

    }

    public static void main(String[] args) throws SchedulerException {
        CronTriggerExample example = new CronTriggerExample();
        example.run();

    }
}
