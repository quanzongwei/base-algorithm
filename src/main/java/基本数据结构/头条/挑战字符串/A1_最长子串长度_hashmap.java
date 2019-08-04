package 基本数据结构.头条.挑战字符串;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 1. [外层]遍历所有字符串
 * 2. [内层]不重复的放入hashmap, 一旦重复则返回该位置的最长串数
 * 3. 获取最大的子串数
 * 4. 输出
 */
public class A1_最长子串长度_hashmap {
    /**
     * hashMap版本
     * <p/>
     * 1. [外层]遍历所有字符串
     * 2. [内层]不重复的放入hashmap, 一旦重复则返回该位置的最长串数
     * 3. 获取最大的子串数
     * 4. 输出
     */
    public static int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();
        int maxLength = 0;
        if (s == null || s.length() == 0) {
            return 0;
        }
        for (int i = 0; i < s.length(); i++) {
            Map map = new HashMap();
            for (int j = i; j < s.length(); j++) {
                Integer intVal = (Integer) map.get(chars[j]);
                if (intVal == null) {
                    map.put(chars[j], 1);
                } else {
                    break;
                }
            }
            int length = map.size();
            if (maxLength < length) {
                maxLength = length;
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("qwqqeasdzxc"));
    }
}
