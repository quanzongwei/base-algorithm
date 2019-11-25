package com.qzw.demo.algorithm.基本数据结构.排序算法;

import java.util.Arrays;

/**
 * Created by Administrator on 2019/3/3 0003.
 */
public class 计数排序 {
    private static void countSort(int[] arr) {
        int[] A = arr;
        int[] C = new int[100];//基数为100, 保存[0-99]范围的数据
        int[] B = new int[arr.length];//A的数据的临时存放位置
        for (int i = 0; i < A.length; i++) {
            C[A[i]]++;
        }
        for (int i = 1; i < C.length; i++) {
            C[i] = C[i] + C[i - 1];
        }
        //逆序保证稳定性,我就说哪里稳定了
        for (int i = A.length - 1; i >= 0; i--) {
            B[--C[A[i]]] = A[i];// 这里要注意, 调试了一次,吧A中的值赋给B, 位置是从C找, C用了一次之后要减一
        }
        for (int i = 0; i < B.length; i++) {
            A[i] = B[i];
        }
    }

    public static void main(String[] args) {
        int[] arr = RandomArrayGenerator.generate(10, 0, 10);
        System.out.println(Arrays.toString(arr));
        countSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}