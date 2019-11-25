package com.qzw.demo.algorithm.基本数据结构.头条;

import org.junit.Test;

/**
 * Created by Administrator on 2019/3/11 0011.
 */
public class A4_最长连续递增序列 {
    /**
     * 不是等值递增的情况
     */
    public int findLengthOfLCIS2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return 1;
        }
        Integer maxLen = 1;
        Integer currLen = 1;
        for (int i = 1; i < nums.length; i++) {
            int currInternal = nums[i] - nums[i - 1];
            if (currInternal > 0) {
                currLen++;
                if (currLen > maxLen) {
                    maxLen = currLen;
                }
            } else {
                currLen = 1;
            }
        }
        return maxLen;
    }


    /**
     * 最长连续递增1357900000这种情况,只是算1,3,5,9才是一个递增的话
     * 等值递增的情况
     */
    public int findLengthOfLCIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return 1;
        }
        Integer maxLen = 1;
        Integer currLen = 1;
        Integer lastInterval = null;
        for (int i = 1; i < nums.length; i++) {
            int currInterval = nums[i] - nums[i - 1];
            if (currInterval <= 0) {//1. 小于等于0, 从1开始计算
                currLen = 1;
                lastInterval = null;
            } else {//2. >0
                if (lastInterval == null) {//3. lastInterval为空, currLen+1, 且lastInterval=currentInterval
                    currLen++;//这里一定会大于2
                    if (currLen > maxLen) {
                        maxLen = currLen;
                    }
                    lastInterval = currInterval;
                } else {//3. lastInterval不为空
                    if (currInterval == lastInterval) {//4. 相等, 这个简单,+1比较就好了
                        currLen++;
                        if (currLen > maxLen) {
                            maxLen = currLen;
                        }
                    } else {//5 如果lastInternal!=null&&currenInternal!=lastInternal,此时起始值是2(比如5 1 3, 3这个位置,他前面有一个1)
                        currLen = 2;
                        lastInterval = currInterval;
                    }
                }

            }
        }
        return maxLen;
    }

    @Test
    public void main() {
//        int[] nums = new int[]{5,1,3,5,7};
        int[] nums = new int[]{1,3,5};
//        int[] nums = new int[]{1,2,5,8,0};
        int len = findLengthOfLCIS(nums);
        System.out.println(len);
    }
}
