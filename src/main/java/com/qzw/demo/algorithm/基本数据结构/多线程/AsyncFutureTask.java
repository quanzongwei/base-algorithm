package com.qzw.demo.algorithm.基本数据结构.多线程;

import java.util.concurrent.*;

/**
 * feature配合Executors.newCachedThreadPool.submit(Callable{callable()})来使用
 * 1. Feature<String> feature = executorservice.submit{new Callable()}
 * 2. feature.isDone()//判断线程是否处理完毕
 * 3. feature.get();//阻塞获取返回结果
 * Created by Administrator on 2019/2/27 0027.
 */
public class AsyncFutureTask {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();//jDK推荐方式
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("学生开始做作业");
                Thread.sleep(3000);
                return "作业完成,作文:[我是一个好学生......]";
            }
        });
        if (!future.isDone()) {//判断是否成功
            System.out.println("作业未完成");
        }
        //阻塞等待学生做作业的结果
        try {
            System.out.println(future.get());//同步等待结果
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (future.isDone()) {
            System.out.println("学生作业上交完成");
        }
    }
}
