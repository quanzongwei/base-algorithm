package com.qzw.demo.algorithm.基本数据结构.头条.挑战字符串;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 1. 使用hashmap保存s1, key为元素值, value为元素的个数
 * 2. 对于每个起始位置, 对于hashMap进行处理,remove或者减一
 * 3. 根据hashMap的size是否为0,判断是否完全匹配
 *
 * 此处将hashMap改成int数组.利用字符的值[97,122]
 */
public class A4_B包含A的排列_数组版本V2_1s {
    //    s1 s2元素个数[1,1000]
    public static boolean checkInclusion(String s1, String s2) {
        // 合法性校验
        if (s1 == null && s1 == null) {
            return true;
        } else if (s1 == null || s2 == null) {
            return false;
        }
        if (s1.length() > s2.length()) {
            return false;
        }

        HashMap<Character, Integer> map = new HashMap();
        int[] arr = new int[26];

        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            arr[c - 97]++;// 这个很巧
        }
        //
        for (int i = 0; i < s2.length(); i++) {//
            int[] tmp = Arrays.copyOf(arr, arr.length);
            for (int j = i; j < s2.length(); j++) {
                //
                boolean isEmpty = true;
                for (int k = 0; k < tmp.length; k++) {
                    if (tmp[k]>0) {
                        isEmpty = false;
                        break;
                    }
                }
                //此时肯定不匹配
                Integer amount = tmp[s2.charAt(j)-97];
                if (!isEmpty && amount == 0) {
                    break;
                }
                //map数据处理
                tmp[s2.charAt(j) - 97]--;
                //判断空
                //
                isEmpty = true;
                for (int k = 0; k < tmp.length; k++) {
                    if (tmp[k]>0) {
                        isEmpty = false;
                        break;
                    }
                }
                if (isEmpty) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(checkInclusion("ab", "eidbaooo"));
        System.out.println('a'-1);
    }
}
