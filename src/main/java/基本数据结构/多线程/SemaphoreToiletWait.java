package 基本数据结构.多线程;

import java.util.concurrent.Semaphore;

/**
 * 信号量-上厕所
 * 1. semaphore = new Semaphore(5)
 * 2. semaphore.acquire();//获取资源
 * 3. semaphore.release()//释放资源
 * Created by Administrator on 2019/2/27 0027.
 */
public class SemaphoreToiletWait {
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(5);//最多允许同时有5个人上厕所
        for (int i = 0; i < 10; i++) {
            final int num = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println(num + "号同学已经获取到坑位,正在上厕所");
                        Thread.sleep((long) (3000 * Math.random()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                        System.out.println(num + "号同学上厕所完毕");
                    }
                }
                //ad
            }).start();
        }
    }
}
