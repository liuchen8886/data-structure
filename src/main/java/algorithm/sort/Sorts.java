package algorithm.sort;

import java.util.stream.IntStream;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/2/13
 */
public class Sorts {

    public static int[] insertSort(int[] input) {
        int cur;
        int j;
        for (int i = 1; i < input.length; i++) {
            cur = input[i];
            j = i - 1;
            for (; j >= 0 && cur < input[j]; j--) {
                input[j + 1] = input[j];
            }
            input[j + 1] = cur;
        }

        return input;
    }

    public static int[] bubbleSort(int[] input) {
        int tmp;
        boolean swapFlag = false;
        for (int i=0; i<input.length; i++) {
            for (int j=0; j<input.length-i-1; j++) {
                if (input[j] > input[j+1]) {
                    tmp = input[j];
                    input[j] = input[j+1];
                    input[j+1] = tmp;
                    swapFlag = true;
                }
            }
            if (!swapFlag) {
                return input;
            }
        }

        return input;
    }

    public static int[] selectSort(int[] input) {
        for (int i=0; i< input.length; i++) {
            int minIndex = i;
            for (int j=i+1; j < input.length; j++) {
                if (input[minIndex] > input[j]) {
                    minIndex = j;
                }
            }
            int tmp = input[i];
            input[i] = input[minIndex];
            input[minIndex] = tmp;
        }

        return input;
    }

    public static int[] mergeSort(int[] input, int start, int end) {
        if (start == end) {
            return null;
        }
        int q = (end+start)/2;
        mergeSort(input, start, q);
        mergeSort(input, q+1, end);
        merge(input, start, q, end);
        return input;
    }

    private static void merge(int[] arr, int start, int q, int end) {
        int[] result = new int[end - start + 1];
        int resultIndex = 0, leftIndex = start, rightIndex = q+1;
        while (leftIndex <= q && rightIndex <= end) {
            if (arr[leftIndex] <= arr[rightIndex]) {
                result[resultIndex++] = arr[leftIndex++];
            } else {
                result[resultIndex++] = arr[rightIndex++];
            }
        }
        if (leftIndex <= q) {
            for (int i=leftIndex; i <= q; i++) {
                result[resultIndex++] = arr[leftIndex++];
            }
        }
        if (rightIndex <= end) {
            for (int i=rightIndex; i <= end; i++) {
                result[resultIndex++] = arr[rightIndex++];
            }
        }

        IntStream.range(0, result.length).forEach(i -> arr[i + start] = result[i]);

    }

    public static int[] quickSort(int[] input, int start, int end) {
        if (start >= end) {
            return null;
        }

        int partitionIndex = partition(input, start, end);

        quickSort(input, start, partitionIndex - 1);
        quickSort(input, partitionIndex + 1, end);

        return input;
    }

    private static int partition(int[] arr, int start, int end) {
        int left = start, right = end, index = start, value = arr[index];
        boolean leftFlag = false;
        while (left <= right) {
            if (leftFlag && arr[left++] >= value) {
                arr[index] = arr[left-1];
                index = left - 1;
                leftFlag = false;
            }
            if (!leftFlag && arr[right--] < value) {
                arr[index] = arr[right+1];
                index = right + 1;
                leftFlag = true;
            }
        }
        arr[index] = value;

        return index;
    }

    private static int partition2(int[] arr, int start, int end) {
        int value = arr[end], splitIndex = start, tmp;
        for (int i = start; i < end; i++) {
            if (arr[i] < value) {
                tmp = arr[splitIndex];
                arr[splitIndex++] = arr[i];
                arr[i] = tmp;
            }
        }
        tmp = arr[splitIndex];
        arr[splitIndex] = value;
        arr[end] = tmp;

        return splitIndex;
    }

    public static int maxKthNum(int[] input, int k) {
        int start = 0, end = input.length -1, p = -1;
        while (p != k - 1) {
            if (p < k - 1) {
                start = p + 1;
            } else {
                end = p - 1;
            }
            p = partition(input, start, end);
        }

        return input[p];
    }

    public static int[] bucketSort(int[] input) {


        return input;
    }

    public static int[] countSort(int[] input) {
        int max = 0;
        for (int anInput : input) {
            if (max < anInput) {
                max = anInput;
            }
        }

        int[] counter = new int[max];
        for (int anInput : input) {
            counter[anInput - 1]++;
        }

        for (int i=1; i<counter.length; i++) {
            counter[i] += counter[i-1];
        }

        int[] result = new int[input.length];
        for (int i=input.length-1; i>=0; i--) {
            result[counter[input[i]-1]-1] = input[i];
            counter[input[i]-1] -= 1;
        }

        return result;
    }

    public static int[] radixSort(int[] input) {

        return input;
    }

}
