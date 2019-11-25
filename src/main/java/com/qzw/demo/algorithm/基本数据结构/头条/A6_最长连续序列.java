package com.qzw.demo.algorithm.基本数据结构.头条;

import java.util.HashMap;
import java.util.Map;

/**
 1. hashMap保存所有数据
 2. value的值为true和false
 3.true:便是这个值用过了
 4. false表示这个值没用过
 6.null表示没有这个值
 7.这个是一次性用品
 * Created by Administrator on 2019/3/11 0011.
 */
public class A6_最长连续序列 {
    public int longestConsecutive(int[] nums) {
        Map<Integer, Boolean> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, false);
        }
        int max = 0;
        for (int num : nums) {
            if (map.get(num) != null && map.get(num) == true) {
                continue;//这个值使用过了
            }
            int num1 = num-1;
            int num2 = num+1;
            int total = 1;
            while (true) {
                Boolean bool = map.get(num2);
                if (bool != null) {
                    total++;
                    map.put(num2, true);
                    num2++;
                } else {
                    break;
                }
            }

            while (true) {
                Boolean bool = map.get(num1);
                if (bool != null) {
                    total++;
                    map.put(num1, true);
                    num1--;
                } else {
                    break;
                }
            }
            if (total > max) {
                max = total;
            }
        }
        return max;
    }
}
