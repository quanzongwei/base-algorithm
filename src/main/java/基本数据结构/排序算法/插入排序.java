package 基本数据结构.排序算法;

import java.util.Arrays;

/**
 * 扑克牌例子
 * Created by Administrator on 2019/3/3 0003.
 */
public class 插入排序 {
    public static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j >= 1; j--) {
                if (arr[j] < arr[j - 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = tmp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = RandomArrayGenerator.generate(10, 0, 10);
        System.out.println(Arrays.toString(arr));
        insertSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
