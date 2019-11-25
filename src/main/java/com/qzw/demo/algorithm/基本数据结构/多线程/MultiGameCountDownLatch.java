package com.qzw.demo.algorithm.基本数据结构.多线程;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 闭锁-赛跑,发令枪和总计时
 * 1. newCountDownLatch(5)
 * 2. countDownLatch.await()//阻塞
 * 3. countDownLatch.countDown()
 * Created by Administrator on 2019/2/26 0026.
 */
public class MultiGameCountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch startCD = new CountDownLatch(1);
        final CountDownLatch endCD = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            final int num = i;
             new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(num + "号选手已就绪");
                    try {
                        //等待裁判开枪
                        startCD.await();
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println(num+"号到达终点");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        //选手跑完
                        endCD.countDown();
                    }
                }
            }).start();
        }
        //发令枪响起
        startCD.countDown();
        System.out.println("裁判哨声响起");
        long begin = System.currentTimeMillis();
        //等待所有选手跑完
        endCD.await();
        long end = System.currentTimeMillis();
        System.out.println("总耗时: "+(end-begin)/1000+"s");
    }
}
