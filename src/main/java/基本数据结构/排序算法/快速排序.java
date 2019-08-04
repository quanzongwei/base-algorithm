package 基本数据结构.排序算法;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. 选取[中间位置的值]pivot
 * 2. i,j分别从head和tail开始向中间走
 * 3. i停在>=pivot值的地方,j停在第一个<=pivot的地方
 * <p/>
 * 4.
 * if(i==j){//结束本次循环,开始递归调用
 * i++;
 * }
 * else if(i>j){//结束本次循环,开始递归调用
 * //数据已经符合条件,ij也已经[交叉错位]
 * }
 * else if(i<j){
 * swap();
 * i++;
 * j--;
 * }
 * <p/>
 * 注意i和j的值,最终i-j=1,同时注意递归参数
 * <p/>
 * 背下来重写
 */
public class 快速排序 {
    /**
     * @param arr
     * @param head 包含
     * @param tail 包含
     */
    public static void quickSort(int[] arr, int head, int tail) {
        if (head >= tail || arr == null || arr.length <= 1) {//结束条件,最后一个就不用比较了
            return;
        }
        int i = head, j = tail, pivot = arr[(head + tail) / 2];
        while (i <= j) {
            while (arr[i] < pivot) {//找>=povot的值,一定能够存在
                ++i;
            }
            while (arr[j] > pivot) {//找<=povot的值,一定能够存在
                --j;
            }
            if (i < j) {//对于两个值, i和j会错开, j<i恒成立
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
                ++i;
                --j;
            } else if (i == j) {//对于奇数值,最终i=j,于是i++,让i只大于j一个值
                i++;//i==j只会执行这最后一行代码,为什么是i++不是j--,因为求余的值是偏左的,arr[(head + tail) / 2],
                // 假如最后的递归有(1,2)两个数据,中值为1,i停在1,j也停在1,此时只能是i+1,这个非常经典
            }
        }
        quickSort(arr, head, j);
        quickSort(arr, i, tail);//对于两个值, i和j会错开,所以是head到j, i到tail
    }

    public static void main(String[] args) {
//        int[] arr = RandomArrayGenerator.generate(10, 0, 10);
        int[] arr = new int[]{3,2,1,5,6,4};
        System.out.println(Arrays.toString(arr));
        qs(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 一遍直接默写下来
     * @param arr
     * @param head
     * @param tail
     */
    public static void qs(int[] arr, int head, int tail) {
        if (arr == null || arr.length <= 1 || head >= tail) {
            return;
        }
        int i = head;
        int j = tail;
        int pivot = arr[(head + tail) / 2];
        while (i <= j) {
            while (arr[i] < pivot) {
                i++;
            }
            while (arr[j] > pivot) {
                j--;
            }

            if (i < j) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            } else if (i == j) {
                i++;
            }

            qs(arr, head, j);
            qs(arr, i, tail);
        }
    }
}
