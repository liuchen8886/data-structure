import algorithm.sort.Sorts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/2/16
 */
class SortTest {

    private int[] input;
    private int[] expected = {1,2,3,4,5,6,7,8};

    @BeforeEach
    void before() {
        input = new int[]{8,7,3,1,6,4,5,2};
    }

    @Test
    void testInsertSort() {
        int[] actual = Sorts.insertSort(input);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testBubbleSort() {
        int[] actual = Sorts.bubbleSort(input);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testSelectSort() {
        int[] actual = Sorts.selectSort(input);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testMergeSort() {
        int[] actual = Sorts.mergeSort(input, 0, input.length -1);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testQuickSort() {
        int[] actual = Sorts.quickSort(input, 0, input.length - 1);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testCountSort() {
        int[] actual = Sorts.countSort(input);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void testMaxKthNum() {
        int random = ThreadLocalRandom.current().nextInt(0, 8);
        int actual = Sorts.maxKthNum(input, random);
        Assertions.assertEquals(actual, random);
    }

}
