package com.qzw.demo.algorithm.基本数据结构.头条.挑战字符串;


import java.util.Objects;

/**
 * 1. 将乘数倒序成两个数组
 * 2. 然后迭代相乘,递归相加
 * 3. 将结果逆序输出就是0001230这种形式
 * 4. 去除前面的0值
 */
public class A5_字符串乘积 {

    /**
     * @param arr   存放总数的数组
     * @param begin 开始位置
     * @param value 存放值(最多两位数)
     */
    public static void addValueToArray(int[] arr, int begin, int value) {

        int sum = arr[begin] + value;
        int nextNum = sum / 10;
        int currentNum = sum % 10;

        arr[begin] = currentNum;
        if (nextNum > 0) {
            addValueToArray(arr, begin + 1, nextNum);
        }
    }

    public static String multiply(String num1, String num2) {
        int[] n1 = new int[num1.length()];
        int[] n2 = new int[num2.length()];
        for (int i = 0; i < num1.length(); i++) {
            //n1[i] = Integer.valueOf(num1.charAt(i)); 这里有一个坑, char值到int会对应到ascii值
            n1[num1.length() - i - 1] = Integer.valueOf(String.valueOf(num1.charAt(i)));
        }
        for (int i = 0; i < num2.length(); i++) {
            //n2[i] = Integer.valueOf(num2.charAt(i));
            n2[num2.length() - i - 1] = Integer.valueOf(String.valueOf(num2.charAt(i)));
        }
        int[] sum = new int[n1.length + n2.length];
        for (int i = 0; i < n1.length; i++) {
            for (int j = 0; j < n2.length; j++) {
                int begin = j + i;
                int value = 1 * n1[i] * n2[j];
                addValueToArray(sum, begin, value);
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sum.length; i++) {
            builder.append(sum[sum.length - 1 - i]);
        }
        String str = builder.toString();
        str = str.replaceFirst("^0+", "");
        //if (str == "") {//这个要注意啊,居然不相等,涉及到常量池了,这个是编程习惯问题,
        if (Objects.equals(str, "")) {
            str = "0";
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(multiply("0", "0"));
        System.out.println(Integer.valueOf("2"));
    }
}
