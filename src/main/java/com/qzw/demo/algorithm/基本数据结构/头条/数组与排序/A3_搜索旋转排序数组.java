package com.qzw.demo.algorithm.基本数据结构.头条.数组与排序;


import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

/**
 * Created by Administrator on 2019/3/10 0010.
 */
public class A3_搜索旋转排序数组 {

    public static int search(int[] nums, int target) {
        if (nums == null||nums.length==0) {
            return -1;
        }
        if (nums[0] <= nums[nums.length - 1]) {
            return binarySearch(nums, 0, nums.length - 1, target);
        }

        // 二份查找最大值和最小值的位置, 这个问题有一个特点,234 0123,我们找到0的位置和9的位置在进行二分查找就好了
        //E中要么大于Elow要么小于Ehigh,这个很重要啊
        int low = 0;
        int high = nums.length - 1;//null deal,最终high指向全局最小值得位置
        if (nums[low]==target) {
            return low;
        }if (nums[high]==target) {
            return high;
        }
        int mid = (high + low) / 2;
        while (high!=low){
            mid = (high + low) / 2;
            if (nums[mid]==target) {
                return mid;
            }if (nums[low]==target) {
                return low;
            }if (nums[high]==target) {
                return high;
            }
            if (nums[mid] > nums[high]) {
                low = mid+1;
            }  else if(nums[mid] < nums[high]){
                high = mid;
            }

        }
        System.out.println(high);
        System.out.println(low);
        if (target >= nums[high] && target <= nums[nums.length - 1]) {
            return binarySearch(nums, high, nums.length - 1, target);
        }else if (target >= nums[0] && target <= nums[low-1]) {
            return binarySearch(nums, 0, high-1, target);

        } else {
            return -1;
        }
    }

    public static int searchOrigin(int[] nums, int target) {
        if (nums == null||nums.length==0) {
            return -1;
        }

        // 二份查找最大值和最小值的位置, 这个问题有一个特点,234 0123,我们找到0的位置和9的位置在进行二分查找就好了
        //E中要么大于Elow要么小于Ehigh,这个很重要啊
        int low = 0;
        int high = nums.length - 1;//null deal,最终high指向全局最小值得位置
        int mid;
        while (high - low > 1) {
            mid = (low + high) / 2;
            if (target==nums[low]) {
                return low;
            }
            if (target==nums[mid]) {
                return mid;
            }
            if (target==nums[high]) {
                return high;
            }
            //target在Elow和Ehigh之间直接返回
            if (nums[low] > target && target > nums[high]) {
                return -1;
            }
            if (target > nums[mid]) {
                    low = mid+1;
            } else if (target < nums[mid]) {
                if (target > nums[low]) {
                    high = mid-1;
                } else if (target < nums[low]) {
                    low = mid+1;
                } else {
                    return low;
                }
            } else {
                return mid;
            }
        }
        System.out.println(high);
        high++;
        System.out.println(low);
        if (target >= nums[high] && target <= nums[nums.length - 1]) {
            return binarySearch(nums, high, nums.length - 1, target);
        }else if (target >= nums[0] && target <= nums[low]) {
            return binarySearch(nums, 0, high, target);

        } else {
            return -1;
        }
    }

    public static int binarySearch(int[] arr, int i, int j, int target) {
        int high = j;
        int low = i;
        int mid;
        while (low <= high) {
            mid = (low + high) / 2;
            if (target<arr[mid]) {
                high = mid-1;
            } else {
                low = mid+1;
            }
        }
        if (low-1>=0&&target==arr[low-1]) {
            return low-1;
        }
        return -1;
    }

    public  static void main(String[] args) {

        int[] arr = new int[]{4,5,1,2,3};
        int search = search(arr, 2);
        System.out.println(search);

    }
}
