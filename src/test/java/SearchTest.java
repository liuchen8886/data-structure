import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static algorithm.search.Searchs.binarySearchFirst;
import static algorithm.search.Searchs.binarySearchLast;
import static algorithm.search.Searchs.binarySearchRotatedSortedArray;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/2/16
 */
public class SearchTest {

    int[] input;

    @BeforeEach
    void beforeEach() {
        input = new int[] {1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8};
    }

    @Test
    void testBiSearchFirst() {
        for (int i = 0; i < 3; i++) {
            int random = ThreadLocalRandom.current().nextInt(1, 9);
            int actual = binarySearchFirst(input, random, false);
            assertEquals(2*(random-1), actual);
        }
    }

    @Test
    void testBiSearchLast() {
        for (int i = 0; i < 3; i++) {
            int random = ThreadLocalRandom.current().nextInt(1, 9);
            int actual = binarySearchLast(input, random, false);
            assertEquals(2*(random-1)+1, actual);
        }
    }

    @Test
    void testBiSearchFirstGE() {
        int actual = binarySearchFirst(input, 0, false);
        assertEquals(-1, actual);
        actual = binarySearchFirst(input, 0, true);
        assertEquals(0, actual);
    }

    @Test
    void testBiSearchLastLE() {
        int actual = binarySearchLast(input, 10, false);
        assertEquals(-1, actual);
        actual = binarySearchLast(input, 10, true);
        assertEquals(15, actual);
    }

    @Test
    void testBiSearchRotatedSortedArray() {
        int[] input = {4,5,6,7,0,1,2};
        for (int i = 0; i < 3; i++) {
            int index = ThreadLocalRandom.current().nextInt(0, 6);
            int actual = binarySearchRotatedSortedArray(input, input[index]);
            assertEquals(index, actual);
        }
    }

}
