package com.qzw.demo.algorithm.基本数据结构.头条.数组与排序;

import org.junit.Test;

import java.util.jar.JarEntry;

/**
 * Created by Administrator on 2019/3/10 0010.
 */
public class A2_岛屿问题 {


    public int islandSum(int[][] arr, int i, int j) {
        if (arr == null || arr[0] == null || i < 0 || i >= arr.length || j < 0 || j >= arr[0].length || arr[i][j] != 1) {
            return 0;
        }
        int sum = 1;
        arr[i][j] = 0;//算过的就可以删了
        sum += islandSum(arr, i, j - 1);
        sum += islandSum(arr, i - 1, j);
        sum += islandSum(arr, i, j + 1);
        sum += islandSum(arr, i + 1, j);
        return sum;
    }

    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid[0] == null) {
            return 0;
        }
        int max = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int total = islandSum(grid, i, j);
                if (total > max) {
                    max = total;
                }
            }
        }
        return max;
    }


    @Test
    public static void mainTest() {
//        new A2_岛屿问题().maxAreaOfIsland();

    }
}
