package 基本数据结构.头条.挑战字符串;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 1. 使用hashmap保存s1, key为元素值, value为元素的个数
 * 2. 对于每个起始位置, 对于hashMap进行处理,remove或者减一
 * 3. 根据hashMap的size是否为0,判断是否完全匹配
 *
 * 此处将hashMap改成int数组.利用字符的值[97,122]
 * 同时判断空值优化
 */
public class A4_B包含A的排列_数组版本_empty优化V3_1s {

    public static final int INT_27 = 27;

    //    s1 s2元素个数[1,1000]
    public static boolean checkInclusion(String s1, String s2) {

        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        int[] arr = new int[INT_27];

        for (int i = 0; i < c1.length; i++) {
            char c = c1[i];
            arr[c - 97]++;// 这个很巧
            arr[INT_27 - 1]++;
        }

        //
        int[] tmp = new int[arr.length];
        for (int i = 0; i < c2.length; i++) {//
            for (int m = 0; m < arr.length; m++) {
                tmp[m] = arr[m];
            }
            for (int j = i; j < c2.length; j++) {
                //
                boolean isEmpty = tmp[INT_27 - 1] == 0 ? true : false;

                //此时肯定不匹配
                Integer amount = tmp[c2[j] - 97];
                if (!isEmpty && amount == 0) {
                    break;
                }

                tmp[c2[j] - 97] --;//数组这种减法不对吗
                tmp[INT_27 - 1] --;
                //判断空
                //
                isEmpty = tmp[INT_27 - 1] == 0 ? true : false;
                if (isEmpty) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(checkInclusion("ab", "eidbaooo"));
        System.out.println('a' - 1);
    }
}
