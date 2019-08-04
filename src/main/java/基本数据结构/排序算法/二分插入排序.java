package 基本数据结构.排序算法;

import java.util.Arrays;

/**
 * Created by Administrator on 2019/3/3 0003.
 */
public class 二分插入排序 {
    private static void binaryInsertSort(int[] arr) {
        for (int i = 1; i <= arr.length - 1; i++) {//[]
            int low = 0;
            int high = i - 1;
            int mid = -1;
            while (low <= high) {
                mid = low + (high - low) / 2;
                if (arr[mid] > arr[i]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            int tmp = arr[i];//low位置后面所有的元素后移, i位置的元素赋值给low
            for (int j = i; j >= low + 1; j--) {
                arr[j] = arr[j - 1];
            }
            arr[low] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] arr = RandomArrayGenerator.generate(10, 0, 10);
        System.out.println(Arrays.toString(arr));
        binaryInsertSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
