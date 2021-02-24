package com.qfxly.thread.ex1;

import sun.applet.Main;

import java.util.concurrent.CompletableFuture;

/**
 * @author yulei
 * @date 2021/2/23
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws InterruptedException {
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(CompletableFutureTest::fetchPrice);

        // 如果执行成功:
        cf.thenAccept((result) ->{
            System.out.println("price: "+result);
        });

        // 如果执行异常:
        cf.exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });

        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);

    }

    static  Double fetchPrice(){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Math.random() < 0.3) {
            throw new RuntimeException("fetch price failed!");
        }

        return 5 + Math.random() * 20;

    }
}
