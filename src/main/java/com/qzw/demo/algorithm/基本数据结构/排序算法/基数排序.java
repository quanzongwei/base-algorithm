package com.qzw.demo.algorithm.基本数据结构.排序算法;

import java.util.Arrays;

/**
 * Created by Administrator on 2019/3/4 0004.
 */
public class 基数排序 {
    /*
    计数排序-修改版
     */
    private static void countSort(int[] arr, int d) {
        int[] A = arr;
        int[] C = new int[10];//基数为10,注意是10, 保存[0-99]范围的数据
        int[] B = new int[arr.length];//A的数据的临时存放位置
        for (int i = 0; i < A.length; i++) {
            C[getDigit(A[i], d)]++;
        }
        for (int i = 1; i < C.length; i++) {
            C[i] = C[i] + C[i - 1];
        }
        //逆序保证稳定性,我就说哪里稳定了
        for (int i = A.length - 1; i >= 0; i--) {
            B[--C[getDigit(A[i], d)]] = A[i];// 这里要注意, 调试了一次,吧A中的值赋给B, 位置是从C找, C用了一次之后要减一
        }
        for (int i = 0; i < B.length; i++) {
            A[i] = B[i];
        }
    }

    private static int getDigit(int value, int d) {
        int[] radix = {1, 1, 10, 100};//index=0没有用到
        return (value / radix[d]) % 10;
    }

    private static void lsdRadixSort(int[] arr, int d) {
        for (int i = 1; i <= d; i++) {//从1开始,所以上面0好索引无效
            countSort(arr, i);//这个i携程d了,要注意啊
        }
    }

    public static void main(String[] args) {
        int[] arr = RandomArrayGenerator.generate(10, 0, 10);
        System.out.println(Arrays.toString(arr));
        lsdRadixSort(arr, 3);
        System.out.println(Arrays.toString(arr));
    }
}
