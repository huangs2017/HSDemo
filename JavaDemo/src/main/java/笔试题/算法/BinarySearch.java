package 笔试题.算法;

// 有序数组中查找目标值，如果存在返回下标，否则返回 -1。
public class BinarySearch {

    public int binarySearch(int[] nums, int target) {
        int mid;
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] == target)    return mid;
            if (target < nums[mid])     right = mid - 1;
            else                        left = mid + 1;
        }
        return -1;
    }

}


/*
（力扣 704题 二分查找）
计算 mid 时需要技巧防止溢出，即 mid = left + (right - left) / 2
不要写成 mid = (right + left) / 2;
*/