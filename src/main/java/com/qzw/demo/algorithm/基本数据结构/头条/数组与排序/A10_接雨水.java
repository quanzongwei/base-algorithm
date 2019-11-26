package com.qzw.demo.algorithm.基本数据结构.头条.数组与排序;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author BG388892
 * @date 2019/11/25
 */
public class A10_接雨水 {

    @Test
    public void test() {
        int result = trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1});
        Assert.assertEquals(result, 6);
    }

    @Test
    public void test2() {
        int result = trap(new int[]{4, 2, 3});
        Assert.assertEquals(result, 1);
    }

    public int trap(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return trapInternal(0, arr[0], arr);
    }

    /**
     * @param i      边界条件, 正方形开始的位置必须是倒数第三个, 如果正方形开始的是倒数第二个, 那么就不会积水了
     * @param fValue fromValue 索引i对应的高度, 或者降级之后的高度
     * @param arr
     * @return 返回本正方形的积水+之前的积水总和
     */
    public int trapInternal(int i, int fValue, int[] arr) {
        int j = i + 1;
        if (j >= arr.length - 1) {// i j已经是最后两个了,直接返回
            return 0;
        }
        int nextHeight = arr[j];
        if (nextHeight >= fValue) {//右边的值比左边值大,往右走一步
            return trapInternal(j, nextHeight, arr);
        }
        // 右值比左边小, 找到下一个大于等于fValue的索引
        Integer higherIndex = null;
        while (++j <= arr.length - 1 && higherIndex == null) {
            if (arr[j] >= fValue) {
                higherIndex = j;
                break;
            }
        }
        if (higherIndex == null) {//没找到降级处理
            return trapInternal(i, --fValue, arr);
        }
        //找到了, 则计算容水量; 降级的情况,不能使用降级后的高度, 需要使用降级前的高度,不管是否降级, 使用原高度都是没有问题的
        //int minHeight = Math.min(fValue, arr[higherIndex]);
        //int maxHeight = Math.max(fValue, arr[higherIndex]);
        int minHeight = Math.min(arr[i], arr[higherIndex]);
        int maxHeight = Math.max(arr[i], arr[higherIndex]);
        int square = minHeight * (j - i + 1);
        int result = square - sumRange(i, j, arr) + (maxHeight - minHeight);// 计算正方形面积的时候, 多减了高出来的砖块的数量

        return result + trapInternal(higherIndex, arr[higherIndex], arr);// 从更高的索引开始寻找下一个长方形
    }

    /**
     * 计算索引区间内的砖块数
     *
     * @param fInclude from
     * @param tInclude to
     * @param arr
     */
    private int sumRange(int fInclude, int tInclude, int[] arr) {
        int sum = 0;
        while (fInclude <= tInclude) {
            sum += arr[fInclude];
            ++fInclude;
        }
        return sum;
    }
}
