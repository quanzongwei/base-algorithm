package com.qzw.demo.algorithm.基本数据结构.头条;

import java.util.ArrayList;
import java.util.List;

/**
 * 给出一个区间的集合，请合并所有重叠的区间。
 * 输入: [[1,3],[2,6],[8,10],[15,18]]
 * 输出: [[1,6],[8,10],[15,18]]
 * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 * <p>
 * 思路:
 * 1.从前往后
 * 2.对于每一个,都循环向前比较,大的不动,小的往前移,合并的统一合并到前面去,后面的设置为null
 * <p>
 * 优化思路:
 * 排序的问题
 */
public class A8_合并数组区间 {

    public List<Interval> merge(List<Interval> intervals) {
        ArrayList<Interval> list = new ArrayList<>(intervals);

        for (int i = 1; i < list.size(); i++) {
            int iCopy = i;
            int j = iCopy - 1;
            while (j >= 0) {
                Interval after = list.get(iCopy);
                Interval before = list.get(j);
                if (before == null) {
                    j--;
                    continue;
                }
                //区间比前面的大
                if (after.start > before.end) {
                    break;
                }
                //区间和前面的重合,四种情况
                ///3
                if (after.start <= before.start && after.end >= before.end) {
                    list.set(j, list.get(iCopy));
                    list.set(iCopy, null);
                    iCopy = j;
                    j--;
                    continue;
                }
                ///1
                if (after.start >= before.start && after.start <= before.end) {
                    before.end = before.end > after.end ? before.end : after.end;
//                    after = null;
                    list.set(iCopy, null);
                    iCopy = j;
                    j--;
                    continue;
                }
                ///2
                if (after.end >= before.start && after.end <= before.end) {
                    before.start = before.start > after.start ? after.start : before.start;
//                    after = null;
                    list.set(iCopy, null);
                    iCopy = j;
                    j--;
                    continue;
                }
                //区间比前面的小
                Interval tmp = after;
                list.set(iCopy, before);
                list.set(j, tmp);
                iCopy = j;
                j--;
            }
        }
        List<Interval> result = new ArrayList<>();
        for (Interval one : list) {
            if (one != null) {
                result.add(one);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Interval> list = new ArrayList<>();
        list.add(new Interval(1, 2));
        list.add(new Interval(2, 3));
        list.add(new Interval(5, 6));
        System.out.println(new A8_合并数组区间().merge(list));
    }
}

class Interval {
    int start;
    int end;

    Interval() {
        start = 0;
        end = 0;
    }

    Interval(int s, int e) {
        start = s;
        end = e;
    }

    @Override
    public String toString() {
        return "Interval{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}