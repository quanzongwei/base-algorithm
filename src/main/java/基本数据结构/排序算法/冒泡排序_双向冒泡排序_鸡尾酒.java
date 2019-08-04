package 基本数据结构.排序算法;


import java.util.Arrays;

/**
 * Created by Administrator on 2019/3/3 0003.
 */
public class 冒泡排序_双向冒泡排序_鸡尾酒 {
    public static void two_way_bubbleSort(int[] arr) {
        int n = arr.length;
        int left = 0;
        int right = n - 1;
        while (left < n) {
            // 最大位置是right, 要进行比较只需要取到right-1, 之后+1就恩能够和right比较
            for (int i = 0; i <= right - 1; i++) {//前半部分排序大的值向右移动,到right当前位置即可
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                }
            }
            left++;
            for (int j = right; j >= left + 1; j--) {//后半部分小的值向左移动.到left当前位置即可
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                }
            }
            right--;
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = RandomArrayGenerator.generate(10, 0, 10);
        System.out.println(Arrays.toString(arr));
        two_way_bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
