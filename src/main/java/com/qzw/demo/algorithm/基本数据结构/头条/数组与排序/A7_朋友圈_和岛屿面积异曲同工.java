package com.qzw.demo.algorithm.基本数据结构.头条.数组与排序;

/**
 * 1. 和最大岛屿面积异曲同工
 * 2. 但是最大岛屿面积遇到0就停止
 * 3. 朋友圈遇到0需要继续保持同方向的删除操作
 * 4. 这个矩阵只需要看一半, 边界是包含关系
 */
public class A7_朋友圈_和岛屿面积异曲同工 {
    public int findCircleNum(int[][] M) {
        int total = 0;
        int[][] nums = M;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j <= i; j++) {
                if (nums[i][j] == 1) {
                    total++;
                    recursionRemoveFourDerection(nums, i, j, 0);
                }

            }
        }
        return total;
    }

    public void recursionRemoveFourDerection(int[][] nums, int i, int j, int direction) {
        if (i >= 0 && i <= nums.length - 1 && j >= 0 && j <= i) {
            if (nums[i][j] == 1) {
                nums[i][j] = 0;
                recursionRemoveFourDerection(nums, i - 1, j, 1);
                recursionRemoveFourDerection(nums, i, j - 1, 2);
                recursionRemoveFourDerection(nums, i + 1, j, 3);
                recursionRemoveFourDerection(nums, i, j + 1, 4);
            } else {//为0,保持同方继续清除
                switch (direction) {
                    case 1:
                        recursionRemoveFourDerection(nums, i - 1, j, 1);
                        break;
                    case 2:
                        recursionRemoveFourDerection(nums, i, j - 1, 2);

                        break;
                    case 3:
                        recursionRemoveFourDerection(nums, i + 1, j, 3);
                        break;
                    case 4:
                        recursionRemoveFourDerection(nums, i, j + 1, 4);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
