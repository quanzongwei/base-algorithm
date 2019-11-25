package com.qzw.demo.algorithm.基本数据结构.排序算法;

import java.util.Arrays;

/**
 * Created by Administrator on 2019/3/3 0003.
 */
public class 选择排序 {
    private static void selectSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int index = i;//假设index处对应的数组元素是最小的
            for (int j = i + 1; j < arr.length; j++) {//查找最小记录的位置
                if (arr[index] > arr[j]) {
                    index = j;
                }
            }
            if (index != i) {//若无序区第一个元素不是无序区中最小元素，则进行交换
                int tmp = arr[index];
                arr[index] = arr[i];
                arr[i] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = RandomArrayGenerator.generate(10, 0, 10);
        System.out.println(Arrays.toString(arr));
        selectSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
