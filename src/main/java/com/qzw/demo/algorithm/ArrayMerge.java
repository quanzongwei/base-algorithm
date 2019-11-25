package com.qzw.demo.algorithm;

/**
 * Created by Administrator on 2019/2/28 0028.
 */
public class ArrayMerge {

    public static void main(String[] args) {
        int[] a=new int[]{2,10,14,19,51,71};
        int[] b = new int[]{2, 9, 10, 14, 19, 40, 51};
        int[] merge = merge(a, b);
        for (int i : merge) {
            System.out.print(i);
            System.out.print(" ");
        }
    }

    public static int[] merge(int a[], int b[]) {
        if (a == null) {
            a = new int[0];
        }
        if (b == null) {
            b = new int[0];
        }
        int result[];
        result = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length)
            if (a[i] <= b[j]) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        while (i < a.length)
            result[k++] = a[i++];
        while (j < b.length)
            result[k++] = b[j++];
        return result;
    }
}
