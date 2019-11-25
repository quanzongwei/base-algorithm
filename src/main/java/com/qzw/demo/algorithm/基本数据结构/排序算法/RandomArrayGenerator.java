package com.qzw.demo.algorithm.基本数据结构.排序算法;

/**
 * Created by Administrator on 2019/3/3 0003.
 */
public class RandomArrayGenerator {

    /**
     * math.random返回一个大于等于0且小于1的double数据
     *
     * @param length
     * @param fromInclude
     * @param toInclude
     * @return
     */
    public static int[] generate(int length, int fromInclude, int toInclude) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = fromInclude + (int) (Math.random() * (toInclude + 1 - fromInclude));
        }
        return arr;
    }
}
