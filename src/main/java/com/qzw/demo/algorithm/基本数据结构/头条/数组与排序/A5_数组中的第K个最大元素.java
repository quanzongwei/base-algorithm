package com.qzw.demo.algorithm.基本数据结构.头条.数组与排序;

/**
 * Created by Administrator on 2019/3/11 0011.
 */
public class A5_数组中的第K个最大元素 {
    public int findKthLargest(int[] nums, int k) {
        quickSort(nums, 0, nums.length - 1);
        return nums[k - 1];

    }
    private void quickSort(int[] nums, int low, int high) {
        if (nums == null || high <= low) {
            return;
        }
        int i = low;
        int j = high;
        int pivot = nums[(low + high) / 2];
        while (i <= j) {
            while (nums[i] > pivot) {
                i++;
            }
            while (nums[j] < pivot) {
                j--;
            }
            if (i < j) {
                // swap
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
                i++;
                j--;
            } else if (i == j) {
                i++;
            }
            quickSort(nums, low, j);
            quickSort(nums, i, high);
        }
    }
}
