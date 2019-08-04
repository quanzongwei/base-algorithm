package 基本数据结构.多线程;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 栅栏-一起出游
 *
 1. new CyclicBarrier(5, new Runnable() {
@Override
public void run() {
System.out.println("我们都准备好了,开始出发吧");
}
});
 * 2. cyclicBarrier.await()//两个作用, 1 表示我到达了 2. 等待大家都到达,在执行构造方法中的出游方法
 *
 * Created by Administrator on 2019/2/27 0027.
 */
public class CyclicbarrierSpringAction {
    public static void main(String[] args) {
        final CyclicBarrier cb = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("我们都准备好了,开始出发吧");
            }
        });

        for (int i = 0; i < 5; i++) {
            final int num = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(num + "号同学已经到达出发点");
                        cb.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
