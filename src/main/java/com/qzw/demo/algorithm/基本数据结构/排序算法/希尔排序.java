package com.qzw.demo.algorithm.基本数据结构.排序算法;

import java.util.Arrays;

/**
 * Created by Administrator on 2019/3/3 0003.
 */
public class 希尔排序 {

    /**
     * 插入排序,希尔版本
     *
     * @param arr
     */
    private static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            int tmp = arr[i];
            //while (j >= 0 && arr[j] > arr[j + 1]) { //坑
            while (j >= 0 && arr[j] > tmp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = tmp;
        }

    }

    /**
     * 希尔排序,将所有的1换成h就行了
     *
     * @param arr
     */
    private static void shellSort(int[] arr) {
        int h = arr.length / 2;
        while (h >= 1) {
            for (int i = h; i < arr.length; i++) {
                int j = i - h;
                int tmp = arr[i];
                while (j >= 0 && arr[j] > tmp) {// 其中tmp = arr[j+1],错了后面j被修改了
                    arr[j + h] = arr[j];
                    j = j - h;
                }
                arr[j + h] = tmp;
            }
            h = h / 2;
        }

    }

    public static void main(String[] args) {
        int[] arr = RandomArrayGenerator.generate(10, 0, 10);
        System.out.println(Arrays.toString(arr));
        shellSort(arr);
        System.out.println(Arrays.toString(arr));

    }
}
