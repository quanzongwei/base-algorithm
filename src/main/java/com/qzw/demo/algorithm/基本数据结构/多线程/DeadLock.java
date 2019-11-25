package com.qzw.demo.algorithm.基本数据结构.多线程;

import java.awt.*;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2019/2/27 0027.
 */
public class DeadLock {

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (int.class){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (long.class) {
                        System.out.println("线程A方法执行成功");
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (long.class){
                    synchronized (int.class) {
                        System.out.println("线程B方法执行成功");
                    }
                }
            }
        }).start();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.toPattern();
    }
}
