package 基本数据结构.头条.挑战字符串;

import java.util.IdentityHashMap;

/**
 * 1. 找出第0个字符串
 * 2. i值一直往后加
 * 3. 字符串为空或索引下标越界则不匹配
 */
public class A3_字符串数组最长公共前缀 {

    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {//数组合法性校验
            return "";
        }
        int i = 0;
        while (true) {
            Character c = null;
            boolean match = true;
            //
            if (strs[0] == null) {//字符串合法性校验
                return "";
            }
            //
            if (indexOutOfRange(strs[0], i)) {//索引合法性校验
                break;
            }
            for (String str : strs) {
                if (str == null) {//字符串合法性校验
                    return "";
                }
                if (indexOutOfRange(str, i)) {//索引合法性校验
                    match = false;
                    break;
                }
                if (c != null && c != str.charAt(i)) {//值匹配校验
                    match = false;
                    break;
                } else if (c == null) {// 这里只会放进去第一个数组的值
                    c = str.charAt(i);
                }
            }
            if (!match) {
                break;
            }
            i++;
        }
        return strs[0].substring(0, i);//(incluxive, exlusive), 最终i是哪个不匹配的索引号
    }

    public static boolean indexOutOfRange(String str, int idx) {
        if (idx >= str.length()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(longestCommonPrefix(new String[]{"fl", "flow", "flight"}));
    }
}
