package com.qzw.demo.algorithm.基本数据结构.头条.挑战字符串;

import com.sun.javafx.image.IntPixelGetter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 核心思想: 以中垂线翻转, 然后在处理空格
 * Created by Administrator on 2019/3/6 0006.
 */
public class A6_反转字符串里的单词_三种解法 {

    /**
     * 直接使用collection.reverse(String[])来实现
     */
    public static String reverseWords(String s) {
        String[] strs = s.trim().split(" +");
        Collections.reverse(Arrays.asList(strs));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strs.length; i++) {
            sb.append(strs[i]);
            if (i != strs.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * 字节去翻转数组,算法很精巧,jdk中参考的
     */
    public static String reverseWords2(String s) {
        String[] strs = s.trim().split(" +");//string最常见的方法
        //这个写的很巧
        for (int i = 0, mid = (strs.length) >> 1, j = strs.length - 1; i < mid; i++, j--) {
            //swap
            String tmp = strs[i];
            strs[i] = strs[j];
            strs[j] = tmp;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strs.length; i++) {
            sb.append(strs[i]);
            if (i != strs.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }


    /**
     * 原地翻转
     * 空间复杂度是O(1)
     * <p/>
     * 思路:
     * 1. 先翻转整个字符串
     * 2. 翻转其中的每一个单词
     * 3. 去除前后空格
     * 4. 中间的空格变成一个
     */
    public static String reverseWords3(String s) {
        //可以用string.charAt来替代
        char[] chars = s.toCharArray();
        reverseArray(chars, 0, s.length());
        Integer wordStart = null;//单词开始的位置
        for (int i = 0; i < chars.length; i++) {//这四种条件写的很好, 有用笔
            if (wordStart == null && chars[i] == ' ') {
                continue;
            }
            //
            else if (wordStart == null && chars[i] != ' ') {
                wordStart = i;
            }
            //
            else if (wordStart != null && chars[i] == ' ') {
                reverseArray(chars, wordStart, i);
                wordStart = null;
            }
            //
            else if (wordStart != null && chars[i] != ' ') {

                continue;
            }
        }
        if (wordStart != null) {
            reverseArray(chars, wordStart, chars.length);
        }
        //去掉前后空格
        return String.valueOf(chars).trim().replaceAll(" +", " ");
    }

    /**
     * @param arr
     * @param begin inclusive
     * @param end   exclusive
     */
    public static void reverseArray(char[] arr, int begin, int end) {
        int mid = ((end - begin) >> 1) + begin;// len/2
        for (int i = begin, j = end - 1; i < mid; i++, j--) {
            //swap
            char tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    public static void main(String[] args) {
        System.out.println(reverseWords3("the sky  is blue"));
    }
}
