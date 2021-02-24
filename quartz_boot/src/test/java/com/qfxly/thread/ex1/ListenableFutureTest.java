package com.qfxly.thread.ex1;

import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author yulei
 * @date 2021/2/23
 */
public class ListenableFutureTest {

    public static void main(String[] args) {

        ListenableFutureTask<String> task = new ListenableFutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("call() 开始...");
                TimeUnit.SECONDS.sleep(20);
                System.out.println("call() 结束...");
                return "success";
            }
        });


        task.addCallback(new ListenableFutureCallback<String>() {

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("调用失败");
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(String result) {
                System.out.println("调用成功：" + result);
            }

        });

        Executors.newSingleThreadExecutor().submit(task);

    }

}
