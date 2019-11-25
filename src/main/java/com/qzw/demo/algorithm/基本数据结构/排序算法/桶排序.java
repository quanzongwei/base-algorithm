package com.qzw.demo.algorithm.基本数据结构.排序算法;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2019/3/3 0003.
 */
public class 桶排序 {
    public static final int BASE_NUM_10 = 10;//预计一个同种的元素个数是10个

    public static void bucketSort(int[] arr) {
        int min = arr[0];
        int max = arr[0];

        for (int one : arr) {
            if (one > max) {
                max = one;
            }
            if (min > one) {
                min = one;
            }
        }

        int bucketNum = (max - min) / BASE_NUM_10 + 1;// 19/10得到索引是1, 1/10得到索引是0,所以这里要加1

        List<ArrayList<Integer>> list = new ArrayList(bucketNum);
        for (int i = 0; i < bucketNum; i++) {
            list.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < arr.length; i++) {//各个桶数据初始化
            int index = getIndex(arr[i], min);
            list.get(index).add(arr[i]);
        }

        for (List<Integer> one : list) {
            insertSort(one);
            for (int i = 0; i < one.size(); i++) {
                System.out.print(one.get(i));
                System.out.print(" ");
            }
            System.out.println();
        }

    }

    /**
     * 从数组改过来到list很简单
     */
    private static void insertSort(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            int j = i - 1;
            int tmp = list.get(i);
            //while (j >= 0 && arr[j] > arr[j + 1]) { //坑
            while (j >= 0 && list.get(j) > tmp) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, tmp);
        }

    }

    private static int getIndex(int value, int min) {
        return (value - min) / BASE_NUM_10;
    }

    public static void main(String[] args) {
        int[] arr = RandomArrayGenerator.generate(10, 0, 10);
        System.out.println(Arrays.toString(arr));
        bucketSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
