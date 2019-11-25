package com.qzw.demo.algorithm.基本数据结构.多线程;

/**
 * 1. 两个线程循环打印数组,主要是用synchronized关键字
 *
 * 2.使用runnable.run如果有死循环,后面的runnable不会执行,不知道为啥
 */
public class A1两个线程循环输出一个数组 {
    public static volatile Boolean odd = true;//这里可以不交除非是自定义的对象类型类型
    static int i = 0;
    static int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

    public void twoThreadSequentExecute() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (int.class) {
                        if (odd == null) {
                            break;
                        }
                        if (odd == true) {
                            System.out.println(arr[i++]);
                            odd = false;
                            if (i >= arr.length) {
                                odd = null;
                            }
                        }
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (int.class) {
                        if (odd == null) {
                            break;
                        }
                        if (odd == false) {
                            System.out.println(arr[i++]);
                            odd = true;
                            if (i >= arr.length) {
                                odd = null;
                            }
                        }
                    }
                }
            }
        }).start();
    }

    public void testThreadStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.print("1");
                    Thread.yield();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.print("2");
                    Thread.yield();
                }
            }
        }).start();
    }
    //twoThreadSequentExecute: 111111111222222222221111122222

    public void testRunnableRun() {
        new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.print("1");
                    Thread.yield();
                }
            }
        }.run();

        new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.print("2");
                    Thread.yield();
                }
            }
        }.run();
    }//twoThreadSequentExecute: 1111111111111111 区别:new Runnable 如果有死循环,不会主线程不会往后执行,记住就好了

    public static void main(String[] args) {
        new A1两个线程循环输出一个数组().twoThreadSequentExecute();
        new A1两个线程循环输出一个数组().testRunnableRun();
    }
}
