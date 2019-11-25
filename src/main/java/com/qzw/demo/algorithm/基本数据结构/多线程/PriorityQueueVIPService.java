package com.qzw.demo.algorithm.基本数据结构.多线程;

import java.util.PriorityQueue;

/**
 * 优先级队列-vip服务
 * 1. priorityQueue = new PriorityQueue
 * 2. Vip implements Comparable接口
 * 3. 越小越优先
 * Created by Administrator on 2019/2/27 0027.
 */
public class PriorityQueueVIPService {
    public static void main(String[] args) throws InterruptedException {
        PriorityQueue<VIP> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new VIP(5));
        priorityQueue.add(new VIP(1));// 最先输出,优先级队列越小越优先
        priorityQueue.add(new VIP(8));
        while (true) {
            VIP one = priorityQueue.poll();
            if (one == null) {
                break;
            }
            System.out.println(one.getVipValue());
        }
    }

    public static class VIP implements Comparable<VIP> {
        private int vipValue;

        public int getVipValue() {
            return vipValue;
        }

        public VIP(int vipValue) {
            this.vipValue = vipValue;
        }

        @Override
        public int compareTo(VIP o) {
            //暂不考虑0的情况
            return this.vipValue - o.getVipValue() > 0 ? 1 : -1;
        }
    }
}
