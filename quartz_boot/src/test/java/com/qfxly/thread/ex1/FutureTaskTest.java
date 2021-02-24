package com.qfxly.thread.ex1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author yulei
 * @date 2021/2/23
 */
public class FutureTaskTest {

    public static void main(String[] args) {

        //创建线程池
        ExecutorService es = Executors.newSingleThreadExecutor();
        //创建Callable对象任务
        CallableDemo callTask = new CallableDemo();
        FutureTask<Integer> futureTask=new FutureTask<>(callTask);
        es.submit(futureTask);

        //关闭线程池
        es.shutdown();
        try {
//            TimeUnit.SECONDS.sleep(20);
            System.out.println("主线程在执行其他任务");
            if (futureTask.get() != null) {
                //输出获取到的结果
                System.out.println("future.get()-->" + futureTask.get());
            } else {
                //输出获取到的结果
                System.out.println("future.get()未获取到结果");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("主线程在执行完成");


    }



}
