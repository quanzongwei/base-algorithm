package 基本数据结构.排序算法;

import sun.java2d.cmm.CMSManager;

import java.util.Arrays;

/**
 * Created by Administrator on 2019/3/3 0003.
 */
public class 二路归并排序 {
    /**
     * 包含关系
     */
    public static void mergeSortRecursion(int[] arr, int left, int right) {
        if (left == right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSortRecursion(arr, left, mid);
        mergeSortRecursion(arr, mid + 1, right);//加一操作应该放在左值中,约定
        arrayMerge(arr, left, mid, right);
    }

    /**
     * 已排序数组的合并,通用解答方法
     */
    private static void arrayMerge(int[] arr, int left, int mid, int right) {
        int length = (right - left) + 1;
        int[] tmp = new int[length];//辅助空间
        int k = 0;
        int i = left;
        int j = mid + 1;
        while (i <= mid && j <= right) {
            tmp[k++] = arr[i] > arr[j] ? arr[j++] : arr[i++];//这句话决定了升序还是降序
        }
        while (i <= mid) {
            tmp[k++] = arr[i++];
        }
        while (j <= right) {
            tmp[k++] = arr[j++];
        }
        for (int m = 0; m < tmp.length; m++) {
            arr[m + left] = tmp[m];
        }
    }


    public static void main(String[] args) {
        int[] arr = RandomArrayGenerator.generate(10, 0, 10);
        System.out.println(Arrays.toString(arr));
        mergeSortRecursion(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));

    }
}
