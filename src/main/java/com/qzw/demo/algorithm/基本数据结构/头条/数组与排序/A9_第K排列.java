package com.qzw.demo.algorithm.基本数据结构.头条.数组与排序;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BG388892
 * @date 2019/11/25
 */
public class A9_第K排列 {

    @Test
    public void test() {
        String res = getPermutationStr(3, 3);
        Assert.assertEquals("213", res);
    }


    /**
     * @param n n阶乘=n!
     * @param k 第K个排列
     */
    public String getPermutationStr(int n, int k) {
        List<Integer> nList = new ArrayList<>();
        int i = 0;
        while (true) {
            nList.add(++i);
            if (i >= n) {
                break;
            }
        }

        List<Integer> rList = new ArrayList<>();
        getSerialList(n, nList, k, rList);

        String rtnStr = "";
        for (Integer one : rList) {
            rtnStr = rtnStr + one;
        }

        return rtnStr;
    }


    /**
     * @param n     本组值个数, 等于nList.size()
     * @param nList 升序排列的组中数据
     * @param k     第K个排列,每个递归, k指的都是当前层级下,目标值所在的序号(定义序号和索引的区别,序号从1开始,索引从0开始)
     * @param rList 返回的数据
     * @return
     */
    private void getSerialList(int n, List<Integer> nList, int k, List<Integer> rList) {
        if (1 == n) {
            rList.add(nList.get(0));
            return;
        }

        int eachGroupAmount = getJieCheng(n - 1);
        int num = k / eachGroupAmount;
        int last = k % eachGroupAmount;
        if (last == 0) {
            num--;
        }
        num = num; //得到第几组的索引,第一组的所以是0

        Integer groupNum = nList.get(num);
        nList.remove(num);
        rList.add(groupNum);
        getSerialList(n - 1, nList, k - num * eachGroupAmount, rList);
    }

    /**
     * 获取阶乘的值
     */
    private int getJieCheng(int input) {
        if (1 == input) {
            return 1;
        }
        return input * getJieCheng(input - 1);
    }
}
