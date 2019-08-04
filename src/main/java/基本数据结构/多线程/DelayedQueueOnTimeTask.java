package 基本数据结构.多线程;

import java.util.Currency;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列
 * 1. delayedQueue.take()
 * 2. DelayTask implements Delayed
 * 3. 重写detDelay()方法,返回值是几秒后执行,默认保存的是执行的的时间点,毫秒计时,返回对应TimeUnit的转换值
 * 3. 重写compareTo方法, getDelay方法详见就好了
 * Created by Administrator on 2019/2/27 0027.
 */
public class DelayedQueueOnTimeTask {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayTask> delayQueue = new DelayQueue<>();
        delayQueue.add(new DelayTask(5000, "1"));
        delayQueue.add(new DelayTask(1000, "2"));// 最先输出
        delayQueue.add(new DelayTask(8000, "3"));
        while (true) {
            DelayTask one = delayQueue.take();
            System.out.println(one.getName());
        }
    }

    public static class DelayTask implements Delayed {
        private long exeTimeMilliseconds = 0L;

        public long getExeTimeMilliseconds() {
            return exeTimeMilliseconds;
        }

        public void setExeTimeMilliseconds(long exeTimeMilliseconds) {
            this.exeTimeMilliseconds = exeTimeMilliseconds;
        }

        private String name;

        public DelayTask(long milliseconds, String name) {
            exeTimeMilliseconds = milliseconds + System.currentTimeMillis();
            this.name = name;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(exeTimeMilliseconds - System.currentTimeMillis(), TimeUnit.MICROSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            //未考虑0的情况, 底层的优先级队列是越小越优先的
            //结果是2, 1, 3
            return this.getDelay(TimeUnit.MICROSECONDS) - o.getDelay(TimeUnit.MICROSECONDS) > 0 ? 1 : -1;
        }

        public String getName() {
            return name;
        }
    }
}
