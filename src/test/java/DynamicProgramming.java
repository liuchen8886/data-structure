import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/5/12
 */
class DynamicProgramming {

    @Test
    void testNormalPackage() {
        int[] weight = {2, 2, 4, 6, 3};
        int[] value = {3, 4, 8, 9, 6};
        int total = 8;
        System.out.println("max package weight is: " + getMaxPackageWeight(total, weight, null));
        System.out.println("max package weight with value is: " + getMaxPackageWeight(total, weight, value));
    }

    private int getMaxPackageWeight(int total, int[] weight, int[] value) {
        int[] state = new int[total+1];
        for (int i = 1; i <= weight.length; i++) {
            for (int j = 1; j <= total; j++) {
                packageFunc(i, j, weight, value, state);
            }
        }

        return state[total];
    }

    private void packageFunc(int n, int w, int[] weight, int[] value, int[] state) {
        if (n == 1 && w < weight[0]) {
            state[w] = 0;
        } else if (n == 1) {
            state[w] = value == null ? weight[0] : value[0];
        } else {
            int wIn = -1;
            if (w >= weight[n-1]) {
                wIn = state[w-weight[n-1]] + (value == null ? weight[n-1] : value[n-1]);
            }
            int wOut = state[w];
            state[w] = wIn > wOut ? wIn : wOut;
        }
    }

    /**
     * 改造版杨辉三角求最短路径
     * https://time.geekbang.org/column/article/74788
     */
    @Test
    void testYHTriangle() {
        int[] weight = {5, 7, 8, 2, 3, 4, 4, 9, 6, 1, 2, 7, 9, 4, 5};
        int[] state = new int[5];
        int level = 5;
        int pathWeight = -1;
        int start = 0;
        Map<Integer, LinkedList<Integer>> pathMap = new HashMap<>();

        for (int i = 1; i <= level; start+=i++) {
            pathWeight = yhTriangleFunc(start, i, weight, state, pathMap);
        }
        System.out.println(format("the shortest path to level %d weight is: %d", level, pathWeight));

//        System.out.println(pathMap);
        pathMap.forEach((k,v) -> {
            StringBuilder sb = new StringBuilder(v.toString()).append(" --- ");
            AtomicInteger total = new AtomicInteger();
            AtomicInteger s = new AtomicInteger();
            AtomicInteger l = new AtomicInteger();
            v.forEach(i -> {
                s.addAndGet(l.getAndIncrement());
                total.addAndGet(weight[s.get() + i]);
            });
            sb.append(total);
            System.out.println(sb);
        });
    }

    private int yhTriangleFunc(int start, int level, int[] weight, int[] state, Map<Integer, LinkedList<Integer>> pathMap) {
        if (level == 1) {
            state[0] = weight[0];
            LinkedList<Integer> p = new LinkedList<>();
            p.add(0);
            pathMap.put(0, p);
            return weight[0];
        }

        state[level-1] = weight[start+level-1] + state[level-2];
        int min = state[level - 1];
        LinkedList<Integer> p = (LinkedList<Integer>) pathMap.get(level-2).clone();
        p.add(level-1);
        pathMap.put(level-1, p);

        for (int i = 1; i < level-1; i++) {
            int s1 = state[i-1];
            int s2 = state[i];
            state[i] = weight[start + i] + (s1 < s2 ? s1 : s2);
            if (min > state[i]) {
                min = state[i];
            }
            if (s1 < s2) {
                p = (LinkedList<Integer>) pathMap.get(i-1).clone();
            } else {
                p = pathMap.get(i);
            }
            p.add(i);
            pathMap.put(i, p);
        }

        state[0] = weight[start] + state[0];
        if (min > state[0]) {
            min = state[0];
        }
        pathMap.get(0).add(0);

        return min;
    }

    @Test
    void testGetEditDistance() {
        char[] a = "mtacnu".toCharArray();
        char[] b = "mitcmu".toCharArray();
        int[][] state = new int[a.length][b.length];

        state[0][0] = a[0] == b[0] ? 0 : 1;
        for (int i = 1; i < a.length; i++) {
            state[i][0] = a[i] == b[0] ? i : state[i-1][0]+1;
        }
        for (int j = 1; j < b.length; j++) {
            state[0][j] = a[0] == b[j] ? j : state[0][j-1]+1;
        }

        editDistFunc(a, b, 1, 1, state);
        System.out.println("edit distance is: " + state[a.length-1][b.length-1]);
        printArray(state);
    }

    private void editDistFunc(char[] a, char[] b, int i, int j, int[][] state) {
        if (a[i] == b[j]) {
            state[i][j] = min(state[i-1][j]+1, state[i][j-1]+1, state[i-1][j-1]);
        } else {
            state[i][j] = min(state[i-1][j], state[i][j-1], state[i-1][j-1]) + 1;
        }

        if (j != b.length - 1) {
            editDistFunc(a, b, i, j+1, state);
        } else if (i != a.length - 1) {
            editDistFunc(a, b, i+1, 1, state);
        }

    }

    public static int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    private void printArray(int[][] state) {
        System.out.println("********* array start *********");
        for (int i = 0; i < state.length; i++) {
            StringBuilder sb = new StringBuilder("[");
            for (int j = 0; j < state[i].length; j++) {
                sb.append(state[i][j]).append(", ");
            }
            sb.setLength(sb.length() - 2);
            sb.append("]");
            System.out.println(sb);
        }
        System.out.println("********* array finish *********");
    }

    /**
     * 获取最长递增子序列
     * 例：2，9，3，6，5，1，7
     * 解：2，3，5，7
     */
    @Test
    void testGetMaxIncreasementLength() {
        int[] a = {2, 9, 3, 6, 5, 1, 7};
        int[][] state = new int[7][7];
        System.out.println("max increasement length is: " + maxIncreasementLengthFunc(a, 0, 0, state));
    }

    private int maxIncreasementLengthFunc(int[] a, int i, int n, int[][] state) {
        if (i == a.length - 1) {
            return 1;
        }

        if (a[i] <= n) {
            return maxIncreasementLengthFunc(a, i+1, n, state);
        } else {
            int m1 = maxIncreasementLengthFunc(a, i+1, n, state);
            int m2 = maxIncreasementLengthFunc(a, i+1, a[i], state) + 1;
            m1 = Math.max(m1, m2);
            state[i][n] = m1;
            return m1;
        }
    }

    /**
     * 获取最长递增子序列（同上）
     * 回溯法
     */
    @Test
    void testLongestIncreaseSubArray() {
        int[] a = {2, 9, 3, 6, 5, 1, 7};
        System.out.println("max increasement length is: " + longestIncreaseSubArrayDP(a));
    }

    private int longestIncreaseSubArrayDP(int[] array) {
        if (array.length < 2) return array.length;
        int[] state = new int[array.length];
        state[0] = 1;
        for (int i = 1; i < state.length; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                if (array[j] < array[i]) {
                    if (state[j] > max) max = state[j];
                }
            }
            state[i] = max + 1;
        }
        int result = 0;
        for (int aState : state) {
            if (aState > result) result = aState;
        }
        return result;
    }

    /**
     * 爬楼梯问题
     * 单步步长为：1, 3, 5
     * 求到达某高度，需要的最少步数
     */
    @Test
    void testMountStairs() {
        System.out.println("min step to mount to 9 is: " + mountStairFunc(9));
    }

    private int mountStairFunc(int n) {
        int[] state = {1, 2, 1, 2, 1};

        if (n < 6) {
            return state[n-1];
        }

        int j = 0;
        for (int i = 6; i <= n; i++, j++) {
            state[j] = min(state[j%5], state[(j+2)%5], state[(j+4)%5]) + 1;
        }

        return state[j-1];
    }

}
