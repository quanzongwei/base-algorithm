package 基本数据结构.头条.挑战字符串;

import sun.security.util.Length;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class A2_最长子串长度_for循环_超时_算法必看 {
    /**
     * 循环比较版本(30000个字符串会超时,性能很差, 但是理论很实用)
     * <p/>
     * 这个是循环嵌套最复杂的情况
     * <p/>
     * 1. [i层]遍历所有字符串
     * 2. [j层]获取以E[j]开头的的最长字符串
     * 3. [k层]k往后移动
     * 4. [l层]E[k]和E[j]到E[k-1]进行重复性校验, 一旦重复则k停止往后移动,
     * 5. k-j就是E[j]的最长字符串长度
     * 6. [i层]比较所有结果,得到最终最长子串这个结果
     * <p/>
     * <p/>
     * <p/>
     * 注意嵌套循环结束标志的问题
     * <p/>
     * for循环[i层]问题
     * 1. [i层] 边界[0, len]
     * 2. [j层] 边界[i, len]
     * 3. [k层] 边界[k+1, len]
     * 4. [l层] 边界[j, k+1]
     * 最终结果是k-j, 就是i对应的最长字符串
     */
    public static int lengthOfLongestSubstring2(String s) {
        char[] chars = s.toCharArray();
        int maxLength = 0;
        if (s == null || s.length() == 0) {
            return 0;
        }
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                int k = j + 1;
                for (; k < s.length(); k++) {
                    //结束标志,这个用的也很巧
                    boolean end = false;
                    // char[k]和j到k-1位置的元素不等, K索引则继续往下
                    for (int l = j; l <= k - 1; l++) {
                        if (Objects.equals(chars[l], chars[k])) {
                            //只要其中一个相等, 则k索引不在往下
                            end = true;
                            break;
                        }
                    }
                    if (end) {
                        break;
                    }
                }
                int jPoslength = k - j;
                if (jPoslength > maxLength) {
                    maxLength = jPoslength;
                }
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring2("q2aaaq"));
    }
}
