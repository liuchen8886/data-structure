package algorithm.search;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/2/16
 */
public class Searchs {

    public static int binarySearchFirst(int[] input, int item, boolean greatEquals) {
        int start = 0;
        int end = input.length - 1;
        int middle;

        while (start <= end) {
            middle = start + ((end - start) >> 1);
            if (input[middle] >= item) {
                end = middle - 1;
            } else {
                start = middle + 1;
            }
        }

        boolean get = start < input.length && (greatEquals || input[start] == item);
        if (get) {
            return start;
        }
        return -1;
    }

    public static int binarySearchLast(int[] input, int item, boolean lessEquals) {
        int start = 0;
        int end = input.length - 1;
        int middle;

        while (start <= end) {
            middle = start + ((end - start) >> 1);
            if (input[middle] > item) {
                end = middle - 1;
            } else {
                start = middle + 1;
            }
        }

        boolean get = end > -1 && (lessEquals || input[end] == item);
        if (get) {
            return end;
        }
        return -1;
    }

    public static int binarySearchRotatedSortedArray(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start <= end) {
            int mid = start + ((end - start) >> 1);
            if (nums[mid] == target) {
                return mid;
            }
            boolean inLeft =
                    (nums[start] < nums[end] && nums[mid] > target)
                            || (nums[start] > nums[end]
                                && (nums[mid] < nums[end] && (nums[mid] > target || nums[end] < target))
                                    || (nums[mid] > nums[end] && nums[start] < target && nums[mid] > target));
            if (inLeft) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        return -1;
    }

}
