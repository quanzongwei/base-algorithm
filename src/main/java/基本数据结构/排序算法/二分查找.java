package 基本数据结构.排序算法;

import com.sun.rowset.internal.Row;

import java.util.Arrays;

/**
 * Created by Administrator on 2019/3/3 0003.
 */
public class 二分查找 {//? 这个和二分查找稍微有点区别啊, 这个是给二分插入排序定制化的

    /*
    套公式就好了
    例子
       1 2 3 4 5   value
    -1 0 1 2 3 4 5 index
    如果target=-1, 则arr[mid] > target一直成立,high值一直左移, 结束时high=-1,low的位置不变,还是0,这就是要插入数据的位置
    如果target=5, 则arr[mid] < target一直成立,low值一直右移, 结束时low=5,这就是要插入数据的位置

    如果在中间, 则low最终会移动到target(多个则一道最后一个target)或者target+1的位置,high的位置最终会和他重合,在根据arr[mid]=target,导致low右移
    最终找到他应该插入的位置上

    如果全部值都相同则根据arr[mid]=target,low值一直移动到index=5得到位置

     */
    public static void binSearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        int mid = -1;
        while (low <= high) {
            mid = low + (high - low) / 2;
            if (arr[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;// 元素相同时，也插入在后面的位置
            }
        }
        //最终low的值就是要插入的位置
        System.out.println(low);
    }

    public static void main(String[] args) {
        int[] arr = RandomArrayGenerator.generate(10, 0, 10);
        System.out.println(Arrays.toString(arr));
        冒泡排序.bubbleSort(arr);
        binSearch(arr, 5);
        System.out.println(Arrays.toString(arr));
    }
}
