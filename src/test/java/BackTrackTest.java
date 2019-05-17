import org.junit.jupiter.api.Test;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/5/9
 */
public class BackTrackTest {

    @Test
    void testPackage() {
        int[] items = new int[10];
        for (int i = 0; i < 10; i++) {
//            items[i] = 10 - i;
            items[i] = i + 1;
        }

        BitSet bitSet = putItemIn2(items, 0, 0, 50, new BitSet());
        System.out.println("max weight is: " + getTotalWeight(items, bitSet));
        StringBuilder sb = new StringBuilder("result arr: \r\n");
        bitSet.stream().forEach(i -> sb.append(items[i]).append(", "));
        sb.setLength(sb.length() - 2);
        System.out.println(sb);
    }

    private int putItemIn(int[] items, int i, int weight, int limit) {
        if (i == items.length - 1) {
            if (weight + items[i] <= limit) {
                return weight + items[i];
            } else {
                return weight;
            }
        }

        int wIn = weight, wOut;
        if (weight + items[i] <= limit) {
            wIn = putItemIn(items, i+1, weight+items[i], limit);
        }
        wOut = putItemIn(items, i+1, weight, limit);
        if (wIn > wOut) {
            return wIn;
        }
        return wOut;
    }

    private static int getTotalWeight(int[] items, BitSet result) {
        int total = 0;
        for (int i = 0; i < items.length; i++) {
            if (result.get(i)) {
                total += items[i];
            }
        }
        return total;
    }

    private BitSet putItemIn2(int[] items, int i, int weight, int limit, BitSet result) {
        if (i == items.length - 1) {
            if (weight + items[i] <= limit) {
                result.set(i);
            }
            return result;
        }

        BitSet wIn = null, wOut;
        if (weight + items[i] <= limit) {
            BitSet inResult = (BitSet) result.clone();
            inResult.set(i);
            wIn = putItemIn2(items, i+1, weight+items[i], limit, inResult);
        }
        wOut = putItemIn2(items, i+1, weight, limit, result);
        if (wIn != null && getTotalWeight(items, wIn) > getTotalWeight(items, wOut)) {
            return wIn;
        }
        return wOut;
    }


    @Test
    void eightQueenCount() {
        int[] state = new int[8];
        AtomicInteger count = new AtomicInteger();
        queenFunc(state, 0, count);
        System.out.println("eight queen count: " + count.get());
    }

    private void queenFunc(int[] state, int n, AtomicInteger count) {
        for (int i = 0; i < 8; i++) {
            if (!isLineConflict(state, i, n)) {
                state[n] = i;
                if (n == 7) {
                    count.incrementAndGet();
                    printArray(state);
                } else {
                    queenFunc(state, n+1, count);
                }
            }
        }
    }

    private boolean isLineConflict(int[] state, int line, int column) {
        for (int i = 0; i < column; i++) {
            if (state[i] == line) {
                return true;
            }
            if (line - column + i >= 0 && state[i] == line - column + i) {
                return true;
            }
            if (line + column - i < 8 && state[i] == line + column - i) {
                return true;
            }
        }

        return false;
    }

    private void printArray(int[] state) {
        System.out.println("********* array start *********");
        for (int i = 0; i < state.length; i++) {
            StringBuilder sb = new StringBuilder("[");
            for (int j = 0; j < 8; j++) {
                sb.append(i == state[j] ? 1 : 0).append(", ");
            }
            sb.setLength(sb.length() - 2);
            sb.append("]");
            System.out.println(sb);
        }
        System.out.println("********* array finish *********");
    }

}
