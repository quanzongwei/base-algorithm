package com.qzw.demo.algorithm.基本数据结构.排序算法;


import java.util.Arrays;

/**
 * Created by Administrator on 2019/3/3 0003.
 */
public class 冒泡排序 {
    public static void bubbleSort(int[] arr) {

        for (int i = 0; i <= arr.length - 2; i++) {
            for (int j = 0; j <= arr.length - 2 - i; j++) {// 注意要减i, 已经是最大的值,就不用比较了,no有道
                compareAndSwap(arr, j, j + 1);// 注意这里是j和j+1比较交换
            }
        }
    }


    public static void compareAndSwap(int[] arr, int i, int j) {
        if (arr[i] > arr[j]) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public static void main(String[] args) {
        int[] arr = RandomArrayGenerator.generate(10, 0, 10);
        System.out.println(Arrays.toString(arr));
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
