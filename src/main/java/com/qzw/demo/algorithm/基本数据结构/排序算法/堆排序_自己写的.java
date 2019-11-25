package com.qzw.demo.algorithm.基本数据结构.排序算法;

import java.util.Arrays;

/**
 * 大顶堆
 * <p/>
 * Created by Administrator on 2019/3/3 0003.
 */
public class 堆排序_自己写的 {

    public static void heapSort(int[] arr) {
        buildMaxHeap(arr);
        //建完之后的大顶堆
        System.out.println(Arrays.toString(arr));
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, i, 0);
            maxHeapAdjust(arr, 0, i);//此处要的是一个length值,并且需要重新堆化的是0节点(根节点)
        }
    }

    private static void buildMaxHeap(int[] arr) {
        int n = arr.length;
        int beginIndex = (n - 1 - 1) >> 1;//floor((i-1)/2) 取父节点
        for (int i = beginIndex; i >= 0; i--) {
            maxHeapAdjust(arr, i, n);
        }

    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[j];
        arr[j] = arr[i];
        arr[i] = tmp;
    }

    private static void maxHeapAdjust(int[] arr, int i, int n) {
        int left = (i << 1) + 1;
        int right = (i << 1) + 2;//右子树可能存可能不存在
        int max = left;
        if (left > n - 1) {
            return;
        }
        if (right <= n - 1 && arr[left] < arr[right]) {
            max = right;
        }
        if (arr[max] > arr[i]) {
            //swap
            swap(arr, max, i);
            maxHeapAdjust(arr, max, n);//左子树或者右子树需要重新堆化
        }
    }

    public static void main(String[] args) {
        int[] arr = RandomArrayGenerator.generate(10, 0, 10);
        System.out.println(Arrays.toString(arr));
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
