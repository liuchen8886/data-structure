import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/5/21
 */
public class LeetCodeTest {

    /**
     * #1
     */
    @Test
    void testTwoSum() {
        int[] result = twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println(result);
    }

    private int[] twoSum(int[] nums, int target) {
        int[] temp = new int[target];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = -1;
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                continue;
            }
            if (temp[nums[i]] == -1) {
                temp[target-nums[i]] = i;
            } else {
                int[] result = new int[2];
                result[0] = temp[nums[i]];
                result[1] = i;
                return result;
            }
        }
        return null;
    }

    /**
     * #2
     */
    @Test
    void testAddTwoNumbers() {
        ListNode l1 = new ListNode(2);
        l1.setNext(4).setNext(3);
        ListNode l2 = new ListNode(5);
        l2.setNext(6).setNext(4);
        ListNode node = addTwoNumbers(l1, l2);
        StringBuilder sb = new StringBuilder();
        while (node != null) {
            sb.append(node.val).append(" -> ");
            node = node.next;
        }
        sb.setLength(sb.length() - 4);
        System.out.println(sb);
    }

    private ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode node = result;
        int t, t1 = 0, t2 = 0;
        while (true) {
            if (l1 != null) {
                t1 = l1.val;
            }
            if (l2 != null) {
                t2 = l2.val;
            }
            t = t1 + t2 + node.val;
            if (t < 10) {
                node.val = t;
                node.next = new ListNode(0);
            } else {
                node.val = t - 10;
                node.next = new ListNode(1);
            }

            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
            if (l1 != null || l2 != null) {
                t1 = 0;
                t2 = 0;
                node = node.next;
            } else {
                node.next = null;
                break;
            }
        }

        return result;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode setNext(int next) {
            this.next = new ListNode(next);
            return this.next;
        }
    }

    /**
     * #3
     */
    @Test
    void testLengthOfLongestSubstring() {
        System.out.println("max length of longest substring is: " + lengthOfLongestSubstring("pwwkew".toCharArray()));
    }

    private  int lengthOfLongestSubstring(char[] s) {
        int maxLength = 0, offset = 0, length = 0;
        int[] record = new int[128];
        for (int i = 0; i < s.length; i++) {
            //character repeat
            if (record[s[i]] >= offset) {
                if (maxLength < length) {
                    maxLength = length;
                }
                length = length - record[s[i]] + offset;
                offset = i;
            } else {
                record[s[i]] = i;
                length++;
            }
        }

        if (maxLength < length) {
            maxLength = length;
        }

        return maxLength;
    }

    /**
     * #4
     */
    @Test
    void testFindMedianSortedArrays() {
        int[] a = {1, 2, 8, 10, 12, 13};
        int[] b = {3, 7, 9, 15};
        System.out.println("median of sorted arrays is: " + findMedianSortedArrays(a, b));
    }

    private double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] more, less;
        if (nums1.length < nums2.length) {
            more = nums2;
            less = nums1;
        } else {
            more = nums1;
            less = nums2;
        }

        int moreBegin = 0, moreEnd = more.length - 1, lessBegin = 0, lessEnd = less.length - 1;
        double moreMid, lessMid;
        while (lessBegin < lessEnd) {
            moreMid = getMid(more, moreBegin, moreEnd);
            lessMid = getMid(less, lessBegin, lessEnd);
            if (moreMid == lessMid) {
                return moreMid;
            }

            int x = lessEnd - lessBegin;
            if (moreMid < lessMid) {
                moreBegin += (x+1)/2;
                lessEnd -= (x+1)/2;
            } else {
                moreEnd -= (x+1)/2;
                lessBegin += (x+1)/2;
            }
        }

        moreMid = getMid(more, moreBegin, moreEnd);
        double l = less[lessBegin];
        int i = moreBegin + (moreEnd-moreBegin)/2;
        if (moreMid == l) {
            return moreMid;
        }
        if (moreMid < l) {
            if ((moreEnd - moreBegin)%2==0) {
                double a = more[i];
                double b = more[i+1];
                if (b > l) {
                    b = l;
                }
                return (a+b)/2;
            } else {
                double b = more[i+1];
                return b > l ? l : b;
            }
        } else {
            if ((moreEnd - moreBegin)%2==0) {
                double a = more[i];
                double b = more[i+1];
                if (b < l) {
                    b = l;
                }
                return (a+b)/2;
            } else {
                double b = more[i+1];
                return b < l ? l : b;
            }
        }

    }

    private static double getMid(int[] nums, int begin, int end) {
        if ((end - begin) % 2 == 0) {
            return nums[begin + (end-begin)/2];
        } else {
            return (nums[begin + (end-begin)/2] + nums[begin + (end-begin)/2 + 1])/2;
        }
    }


    /**
     * #5
     */
    @Test
    void testLongestPalindrome() {
        System.out.println(format("the longest palindrome str of %s is : %s", "cbbd", longestPalindrome("cbbd")));
        System.out.println(format("the longest palindrome str of %s is : %s", "abcbabcba", longestPalindrome("abcbabcba")));
    }

    private String longestPalindrome(String s) {
        char[] chars = s.toCharArray();
        int start = 0;
        int end = 0;
        for (int i = 0; i < chars.length; i++) {
            int l1 = getPalindromeLength(chars, i, i);
            int l2 = getPalindromeLength(chars, i, i+1);
            if (end-start+1 < l1) {
                start = i - l1/2;
                end = i + l1/2;
            }
            if (end-start+1 < l2) {
                start = i - l2/2 + 1;
                end = i + l2/2;
            }
        }

        return s.substring(start, end+1);
    }

    private int getPalindromeLength(char[] chars, int i, int j) {
        while (i >= 0 && j < chars.length && chars[i] == chars[j]) {
            i--;
            j++;
        }

        return j-i-1;
    }

    /**
     * #6
     */
    @Test
    void testConvertZigZag() {
        System.out.println(format("zig zag converted string of %s is: %s", "PAYPALISHIRING", convertZigZag("PAYPALISHIRING", 4)));
    }

    private String convertZigZag(String s, int numRows) {
        char[] c0 = s.toCharArray();
        char[] c1 = new char[s.length()];
        int i = 0;
        int j = numRows - 1;
        int x = 0;
        while (i < numRows) {
            int y = i;
            boolean addJ = true;
            while (y < s.length()) {
                c1[x++] = c0[y];
                if ((i != 0 && !addJ) || j == 0) {
                    y += 2*i;
                    addJ = true;
                } else {
                    y += 2*j;
                    addJ = false;
                }
            }
            i++;
            j--;
        }

        return new String(c1);
    }

    /**
     * #7
     */
    @Test
    void testInteger() {
        System.out.println(format("reverse integer of %d is: %d", 123, reverseInteger(123)));
        System.out.println(format("reverse integer of %d is: %d", -123, reverseInteger(-123)));
    }

    private int reverseInteger(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x%10;
            x /= 10;
            rev = rev * 10 + pop;
        }
        return rev;
    }

    @Test
    void testIntPalindrome() {
        assertTrue(isIntPalindrome(1221));
        assertTrue(isIntPalindrome(12321));
        assertFalse(isIntPalindrome(12211));
    }

    private boolean isIntPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int revert = 0;
        while (x > revert) {
            revert = revert * 10 + x%10;
            x /= 10;
        }
        return x == revert || x == revert/10;
    }

    /**
     * #10
     */
    @Test
    void testPatternMatch() {
        assertTrue(isPatternMatch("aab", "c*a*ab"));
        assertFalse(isPatternMatch("mississippi", "mis*is*p*."));
        assertTrue(isPatternMatch("ab", ".*"));
    }

    private boolean isPatternMatch(String s, String p) {
        char[] chars = s.toCharArray();
        char[] charp = p.toCharArray();
        return isCharMatch(chars, 0, charp, 0);
    }

    private boolean isCharMatch(char[] chars, int i, char[] charp, int j) {
        if (i == chars.length) {
            return true;
        }
        if (j >= charp.length) {
            return false;
        }
        if (charp[j] == '*') {
            return false;
        }

        boolean isFirstMatch = chars[i] == charp[j] || charp[j] == '.';
        if (j < charp.length-1 && charp[j+1] == '*') {
            if (!isFirstMatch) {
                return isCharMatch(chars, i, charp, j+2);
            }
            return  isCharMatch(chars, i+1, charp, j) || isCharMatch(chars, i+1, charp, j+2);
        }else {
            return isFirstMatch && isCharMatch(chars, i+1, charp, j+1);
        }
    }

    @Test
    void testMaxArea() {
        int[] height = {1,8,6,2,5,4,8,3,7};
        assertEquals(49, maxArea(height));
        assertEquals(49, maxArea2(height));
    }

    /**
     * 只降低了单边的复杂度
     */
    private int maxArea(int[] height) {
        int maxHeight = -1;
        int maxArea = -1;
        int area;
        for (int i = 0; i < height.length-1; i++) {
            if (height[i] > maxHeight) {
                for (int j = i+1; j < height.length; j++) {
                    area = (j-i) * Math.min(height[i], height[j]);
                    if (area > maxArea) {
                        maxArea = area;
                    }
                }
            }
        }
        return maxArea;
    }

    private int maxArea2(int[] height) {
        int maxArea = -1;
        int area;
        int i = 0, j = height.length-1;
        while (i < j) {
            if (height[i] > height[j]) {
                area = (j-i) * height[j];
                j--;
            } else {
                area = (j-i) * height[i];
                i++;
            }
            if (maxArea < area) {
                maxArea = area;
            }
        }
        return maxArea;
    }

    /**
     * #12
     */
    @Test
    void testIntToRoman() {
        assertEquals("III", intToRoman(3));
        assertEquals("IV", intToRoman(4));
        assertEquals("IX", intToRoman(9));
        assertEquals("LVIII", intToRoman(58));
        assertEquals("MCMXCIV", intToRoman(1994));
    }

    private String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        char[] alphabet = {'-', 'M', 'D', 'C', 'L', 'X', 'V', 'I'};
        for (int i = 0, j = 1_000; num > 0; num%=j, j/=10, i+=2) {
            int n = num/j;
            if (n == 0) {
                continue;
            }
            if (n < 4) {
                for (int m = n; m > 0; m--) {
                    sb.append(alphabet[i+1]);
                }
            } else if (n == 4) {
                sb.append(alphabet[i+1]).append(alphabet[i]);
            } else if (n < 9) {
                sb.append(alphabet[i]);
                for (int m = n-5; m > 0; m--) {
                    sb.append(alphabet[i+1]);
                }
            } else if (n == 9) {
                sb.append(alphabet[i+1]).append(alphabet[i-1]);
            }
        }

        return sb.toString();
    }

    /**
     * #15
     */
    @Test
    void testThreeSum() {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        System.out.println(threeSum(nums));
    }

    private List<List<Integer>> threeSum(int[] nums) {
        return threeSumFunc(nums, 0, 0, 1);
    }

    private List<List<Integer>> threeSumFunc(int[] nums, int i, int expect, int j) {
        if (i == nums.length) {
            return null;
        }
        if (j == 3) {
            List<List<Integer>> result = new ArrayList<>();
            for (int x = i; x < nums.length; x++) {
                if (nums[x] == expect) {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(expect);
                    result.add(list);
                }
            }
            return result.size() == 0 ? null : result;
        }

        List<List<Integer>> l1 = threeSumFunc(nums, i+1, expect, j);
        List<List<Integer>> l2 = threeSumFunc(nums, i+1, expect-nums[i], j+1);
        if (l1 == null && l2 == null) {
            return null;
        } else if (l1 == null) {
            l2.forEach(l -> l.add(nums[i]));
            return l2;
        } else if (l2 == null) {
            return l1;
        } else {
            l2.forEach(l -> l.add(nums[i]));
            l1.addAll(l2);
            return l1;
        }
    }

    /**
     * #24
     */
    @Test
    void testSwapPairs() {
        ListNode head = new ListNode(1);
        head.setNext(2).setNext(3).setNext(4);
        head = swapPairs(head);
//        head = swapPairs2(head);
        printListNode(head);

    }

    private void printListNode(ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val).append(" -> ");
            head = head.next;
        }
        sb.setLength(sb.length() - 4);
        System.out.println("result: " + sb);
    }

    private ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode node1 = head;
        ListNode node2 = head.next;
        head = node2;
        while (node2 != null) {
            node1.next = node2.next;
            node2.next = node1;
            node1 = node1.next;
            if (node1 == null) {
                break;
            }
            if (node1.next != null) {
                node2.next.next = node1.next;
            }
            node2 = node1.next;
        }
        return head;
    }

    private ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        head.next = swapPairs(head.next.next);
        next.next = head;
        return next;
    }

    @Test
    void testReverseListNode() {
        ListNode head = new ListNode(1);
        head.setNext(2).setNext(3).setNext(4).setNext(5);
        head = reverseListNode(head);
        printListNode(head);
    }

    private ListNode reverseListNode(ListNode node) {
        if (node == null || node.next == null) {
            return node;
        }
        ListNode next = node.next;
        ListNode head = reverseListNode(next);
        next.next = node;
        node.next = null;
        return head;
    }

    private ListNode reverseListNode2(ListNode node) {
        ListNode prev = null;
        ListNode next;
        while (node != null) {
            next = node.next;
            node.next = prev;
            prev = node;
            node = next;
        }

        return prev;
    }

    /**
     * #25
     */
    @Test
    void testReverseKGroup() {
        ListNode head = new ListNode(1);
        head.setNext(2).setNext(3).setNext(4).setNext(5).setNext(6).setNext(7).setNext(8);
        head = reverseKGroup(head, 3, 1);
        printListNode(head);
    }

    private ListNode reverseKGroup(ListNode node, int k, int i) {
        if (node == null || node.next == null) {
            return null;
        }

        int m = i%k;
        ListNode next = node.next;
        ListNode subHead = reverseKGroup(node.next, k, i+1);
        if (subHead == null) {
            return m > 1 ? null : node;
        }
        if (m == 0) {
            node.next = subHead;
            return node;
        } else {
            node.next = next.next;
            next.next = node;
            return subHead;
        }
    }

    @Test
    void testNextPermutation() {
        int[] nums = {1,2,3};
        nextPermutation(nums);
        assertEquals(1, nums[0]);
        assertEquals(3, nums[1]);
        assertEquals(2, nums[2]);

        nextPermutation(nums);
        assertEquals(2, nums[0]);
        assertEquals(1, nums[1]);
        assertEquals(3, nums[2]);

        nextPermutation(nums);
        assertEquals(2, nums[0]);
        assertEquals(3, nums[1]);
        assertEquals(1, nums[2]);

        nextPermutation(nums);
        assertEquals(3, nums[0]);
        assertEquals(1, nums[1]);
        assertEquals(2, nums[2]);

        nextPermutation(nums);
        assertEquals(3, nums[0]);
        assertEquals(2, nums[1]);
        assertEquals(1, nums[2]);

        nextPermutation(nums);
        assertEquals(1, nums[0]);
        assertEquals(2, nums[1]);
        assertEquals(3, nums[2]);
    }

    private void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i+1]) {
            i--;
        }
        if (i >= 0) {
            int j = i+1;
            while (j < nums.length && nums[j] > nums[i]) {
                j++;
            }
            swap(nums, i, j-1);
        }
        reverse(nums, i+1);

    }

    private static void reverse(int[] nums, int i) {
        int j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    @Test
    void testLongestValidParentheses() {
        assertEquals(2, longestValidParentheses3("(()"));
        assertEquals(4, longestValidParentheses3(")()())"));
        assertEquals(4, longestValidParentheses3("((())("));
        assertEquals(4, longestValidParentheses3(")()()("));
        assertEquals(6, longestValidParentheses3(")())((()))"));
        assertEquals(8, longestValidParentheses3("(()(()))"));
    }

    private int longestValidParentheses(String s) {
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        int maxLength = 0;
        int length = 0;

        for (char c : chars) {
            if (c == '(') {
                stack.push(c);
            } else if (stack.empty()) {
                length = 0;
            } else {
                stack.pop();
                if (++length > maxLength) {
                    maxLength = length;
                }
            }
        }

        return maxLength*2;
    }

    private int longestValidParentheses2(String s) {
        char[] chars = s.toCharArray();
        int maxLength = 0;
        int length = 0;
        int lCnt = 0;

        for (char c : chars) {
            if (c == '(') {
                lCnt++;
            } else if (lCnt == 0) {
                length = 0;
            } else {
                lCnt--;
                if (++length > maxLength) {
                    maxLength = length;
                }
            }
        }

        return maxLength*2;
    }

    private int longestValidParentheses3(String s) {
        int[] state = new int[s.length()];
        longestValidParenthesesFunc(s.toCharArray(), 0, state);

        int max = 0;
        for (int m : state) {
            if (max < m) {
                max = m;
            }
        }

        return max;
    }

    private void longestValidParenthesesFunc(char[] chars, int i, int[] state) {

        if (chars[i] == ')' && i < chars.length - 1) {
            longestValidParenthesesFunc(chars, i+1, state);
        } else if (i == chars.length - 1) {
            state[i] = 0;
        }else if (chars[i+1] == ')') {
            if (i == chars.length - 2 || chars[i+2] == ')') {
                state[i] = 2;
                if (i < chars.length-3) {
                    longestValidParenthesesFunc(chars, i+3, state);
                }
            } else {
                longestValidParenthesesFunc(chars, i+2, state);
                state[i] = state[i+2] + 2;
            }
        } else {
            int ans;
            longestValidParenthesesFunc(chars, i+1, state);
            int j = state[i+1];
            if (i+j+1 >= chars.length) {
                ans = j;
            } else {
                ans = chars[i+j+1] == ')' ? j+2 : j;
            }
            state[i] = ans;
        }
    }

    @Test
    void testSearchRotated() {
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        assertEquals(4, searchRotated(nums, 0));

        nums = new int[]{4, 5, 6, 7, 0, 1, 2};
        assertEquals(-1, searchRotated(nums, 3));
    }

    private int searchRotated(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid;

        while (left < right) {
            mid = (right + left)/2;
            if (target == nums[mid]) {
                return mid;
            }
            if ((nums[left] < nums[mid] && (target > nums[mid] || target <= nums[right]))
                    || (nums[left] > nums[mid] && target > nums[mid] && target <= nums[right])) {
                left = mid;
            } else {
                right = mid;
            }
        }

        return -1;
    }


    /**
     * #37
     */
    @Test
    void solveSudoku() {
        char[][] board = {{'5','3','.','.','7','.','.','.','.'},{'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},{'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},{'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},{'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}};

        char[][] result = sudokuFunc(board, 0, 0);

        if (result != null) {
            for (char[] chars : result) {
                System.out.println(chars);
            }
        } else {
            System.out.println("not a valid sudoku");
        }
    }

    private char[][] sudokuFunc(char[][] board, int i, int j) {
        out: for (; i < board.length; i++) {
            if (j == 9) {
                j = 0;
            }
            for (; j < board[i].length; j++) {
                if (board[i][j] == '.') {
                    break out;
                }
            }
        }

        if (i == 9 && j == 9) {
            return board;
        }

        boolean[] b = new boolean[9];
        for (int x = 0; x < 9; x++) {
            if (board[x][j] != '.') {
                b[board[x][j] - '1'] = true;
            }
            if (board[i][x] != '.') {
                b[board[i][x] - '1'] = true;
            }
        }
        int h = i/3*3, w = j/3*3;
        for (int x = h; x < h+3; x++) {
            for (int y = w; y < w+3; y++) {
                if (board[x][y] != '.') {
                    b[board[x][y] - '1'] = true;
                }
            }
        }

        for (int m = 0; m < 9; m++) {
            if (!b[m]) {
                char[][] newBoard = deepCopy(board, 9, 9);
                newBoard[i][j] = (char) ('1' + m);
                char[][] retBoard;
                if (j < 8) {
                    if ((retBoard = sudokuFunc(newBoard, i, j+1)) != null) {
                        return retBoard;
                    }
                } else if (i < 8) {
                    if ((retBoard = sudokuFunc(newBoard, i+1, 0)) != null) {
                        return retBoard;
                    }
                }
            }
        }

        return null;
    }

    private char[][] deepCopy(char[][] src, int m, int n) {
        char[][] newArr = new char[m][n];

        for (int i = 0; i < m; i++) {
            newArr[i] = src[i].clone();
        }

        return newArr;
    }

    /**
     * #39
     */
    @Test
    void testCombinationSum() {
        int[] candidates = {2, 3, 5};
        List<List<Integer>> result = combinationSum(candidates, 8);
        result.forEach(System.out::println);
    }

    private List<List<Integer>> combinationSum(int[] candidates, int target) {
        Map<String, List<List<Integer>>> state = new HashMap<>();
        return combinationSumFunc(candidates, target, 0, state);
    }

    private List<List<Integer>> combinationSumFunc(int[] candidates, int target, int i, Map<String, List<List<Integer>>> state) {
        if (i == candidates.length) {
            return null;
        }
        if (target == candidates[i]) {
            List<List<Integer>> result = new ArrayList<>();
            List<Integer> integerList = new ArrayList<>();
            integerList.add(candidates[i]);
            result.add(integerList);
            return result;
        }

        List<List<Integer>> l1 = state.computeIfAbsent(target + "_" + (i+1),
                k -> combinationSumFunc(candidates, target, i+1, state));
        List<List<Integer>> l2 = null;
        int nt;
        if ((nt = target - candidates[i]) > 0) {
            l2 = state.computeIfAbsent(nt + "_" + i,
                    k -> combinationSumFunc(candidates, nt, i, state));
        }

        if (l1 == null && l2 == null) {
            return null;
        }

        List<List<Integer>> result = new ArrayList<>();
        copyAndInsert(l1, result, candidates, i, false);
        copyAndInsert(l2, result, candidates, i, true);
        return result;
    }

    private void copyAndInsert(List<List<Integer>> from, List<List<Integer>> to, int[]candidates, int i, boolean include) {
        if (from != null) {
            from.forEach(l -> {
                List<Integer> integerList = new ArrayList<>(l);
                if (include) {
                    integerList.add(candidates[i]);
                }
                to.add(integerList);
            });
        }
    }

    @Test
    void test() throws InterruptedException {
        System.out.println("test.test");
        TimeUnit.SECONDS.sleep(10);
        TestStatic testStatic = new TestStatic();
    }

    /**
     * #45
     */
    @Test
    void testJumpGame() {
        int nums[] = {2,2,3,5,1,1,1,1,1};
        assertEquals(3, jump2(nums));
    }

    private int jump(int[] nums) {
        int[] state = new int[nums.length];
        return jumpFunc(nums, 0, state);
    }

    private int jumpFunc(int[] nums, int i, int[] state) {
        if (i >= nums.length - 1) {
            return 0;
        }
        if (nums[i] == 0) {
            return Integer.MAX_VALUE;
        }

        int min = Integer.MAX_VALUE;
        int m;
        for (int n = 1; n <= nums[i]; n++) {
            if (state[i+n] != 0) {
                m = state[i+n];
            } else {
                m = jumpFunc(nums, i+n, state);
            }

            if (m < min) {
                min = m;
            }
        }
        state[i] = min+1;
        return state[i];
    }

    private int jump2(int[] nums) {
        if (nums.length <= 1) return 0;
        int step = 0;

        for (int i = 0; i < nums.length;) {
            step++;
            int range = i + nums[i];

            if (range >= nums.length - 1) {
                return step;
            }

            int farMax = 0;

            for (int j = i + 1; j <= range; j++) {
                if (j + nums[j] > farMax) {
                    farMax = j + nums[j];
                    i = j;
                }
            }
        }

        return step;
    }

    @Test
    void testPermute() {
        int[] nums = {1, 2, 1};
        permute2(nums).forEach(System.out::println);
    }

    private List<List<Integer>> permute(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        List<Integer> element = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        element.add(nums[0]);
        ans.add(element);

        List<List<Integer>> temp;
        for (int i = 1; i < nums.length; i++) {
            temp = new ArrayList<>();
            for (List<Integer> an : ans) {
                an.add(nums[i]);
                temp.addAll(rotateList(an));
            }
            ans = temp;
        }

        return ans;
    }

    private List<List<Integer>> rotateList(List<Integer> element) {
        List<List<Integer>> ans = new ArrayList<>();
        int size = element.size();
        int[][] temp = new int[size][size];
        for (int i = 0; i < size; i++) {
            int item = element.get(i);
            for (int j = 0; j < size; j++) {
                temp[j][(j+i)%size] = item;
            }
        }
        for (int i = 0; i < size; i++) {
            ans.add(IntStream.of(temp[i]).boxed().collect(toList()));
        }

        return ans;
    }

    private List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(nums, new boolean[nums.length], result, new ArrayList<>(nums.length));
        return result;
    }

    private void dfs(int[] nums, boolean[] visited, List<List<Integer>> result, List<Integer> list) {
        if (list.size() == nums.length) {
            result.add(new ArrayList<>(list));
            return;
        }

        BitSet bs = new BitSet();
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i] && !bs.get(nums[i])) {
                visited[i] = true;
                list.add(nums[i]);
                dfs(nums, visited, result, list);
                visited[i] = false;
                list.remove(list.size()-1);
                bs.set(nums[i]);
            }
        }
    }

    @Test
    void testRotate() {
        int n = 7;
        int[][] matrix = new int[n][n];
        int x = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = x++;
            }
        }

        rotate(matrix);

        for (int[] a : matrix) {
            System.out.println(Arrays.toString(a));
        }
    }

    private  void rotate(int[][] matrix) {
        int i = 0, j = 0, l, t;
        while (i < matrix.length/2) {
            l = matrix.length - i - 1;
            while (j < l) {
                t = matrix[i][j];
                matrix[i][j] = matrix[l-j+i][i];
                matrix[l-j+i][i] = matrix[l][l-j+i];
                matrix[l][l-j+i] = matrix[j][l];
                matrix[j][l] = t;
                j++;
            }
            i++;j=i;
        }
    }

    @Test
    void testMaxSubArray() {
        int[] arr = {-2,1,-3,4,-1,2,1,-5,4};
        assertEquals(6, maxSubArray(arr));
    }

    private int maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int temp = 0;
        for (int n : nums) {
            temp += n;
            if (temp + n < 0) {
                temp = 0;
            }
            if (max < temp) {
                max = temp;
            }
        }
        return max;
    }

    @Test
    void testSpiralOrder() {
        int[][] matrix = new int[][] {
                {1, 2, 3, 4, 5, 6, 7},
                {8, 9, 10, 11, 12, 13, 14},
                {15, 16, 17, 18, 19, 20, 21},
                {22, 23, 24, 25, 26, 27, 28},
        };

        System.out.println(spiralOrder(matrix));
    }

    private List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<>();

        int m = matrix[0].length - 1;
        int n = matrix.length - 1;

        int x = 0, y = 0;

        while (m > 0 && n > 0) {
            for (int i = 0; i < m; i++) {
                list.add(matrix[y][x++]);
            }
            for (int i = 0; i < n; i++) {
                list.add(matrix[y++][x]);
            }
            for (int i = 0; i < m; i++) {
                list.add(matrix[y][x--]);
            }
            for (int i = 0; i < n; i++) {
                list.add(matrix[y--][x]);
            }
            m-=2;
            n-=2;
            y++;
            x++;
        }

        return list;
    }

    /**
     * #55
     */
    @Test
    void testCanJump() {
        int[] nums = {2,3,1,1,4};
        assertTrue(canJump(nums));
        nums = new int[]{3,2,1,0,4};
        assertFalse(canJump(nums));
    }

    private boolean canJump(int[] nums) {
        out: for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] == 0) {
                for (int j = i-1; j >= 0; j--) {
                    if (nums[j] - i + j > 0) {
                        i = j;
                        continue out;
                    }
                }
                return false;
            }
        }

        return true;
    }

    /**
     * #56
     */
    @Test
    void testMerge() {
        int[][] intervals = new int[][] {
                {1, 3},
                {5, 8},
                {2, 4},
                {2, 3}
        };
        int[][] merged = merge(intervals);
        for (int[] interval : merged) {
            System.out.println(Arrays.toString(interval));
        }
    }

    private int[][] merge(int[][] intervals) {
        int[][] merged = new int[intervals.length][2];

        sortIntervals(intervals, 0, intervals.length - 1);

        int n = 0;
        for (int[] itv : intervals) {
            if (n == 0) {
                merged[0] = itv;
                n++;
            } else {
                if (merged[n-1][1] >= itv[0]) {
                    merged[n-1][1] = Math.max(merged[n-1][1], itv[1]);
                } else {
                    merged[n++] = itv;
                }
            }
        }

        return Arrays.copyOf(merged, n);
    }

    private void sortIntervals(int[][] intervals, int start, int end) {
        if (start < end) {
            int index = partition(intervals, start, end);
            sortIntervals(intervals, start, index - 1);
            sortIntervals(intervals, index + 1, end);
        }
    }

    private int partition(int[][] intervals, int start, int end) {
        int value = intervals[end][0], index = start; int[] tmp;

        for (int i = start; i < end; i++) {
            if (intervals[i][0] < value) {
                tmp = intervals[index];
                intervals[index++] = intervals[i];
                intervals[i] = tmp;
            }
        }

        tmp = intervals[end];
        intervals[end] = intervals[index];
        intervals[index] = tmp;

        return index;
    }

    @Test
    void testGenerateMatrix() {
        int[][] matrix = generateMatrix(6);
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    private int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int i = 1;

        int x = 0, y = 0, a = n - 1;
        while (i < n*n) {
            while (x < a) {
                matrix[y][x] = i++;
                x++;
            }
            while (y < a) {
                matrix[y][x] = i++;
                y++;
            }
            while (x > n-a-1) {
                matrix[y][x] = i++;
                x--;
            }
            while (y > n-a-1) {
                matrix[y][x] = i++;
                y--;
            }
            x++;
            y++;
            a--;
        }
        matrix[x][y] = i;

        return matrix;
    }

    @Test
    void testGetPermutation() {
        assertEquals("2341", getPermutation(4, 10));
    }

    private String getPermutation(int n, int k) {
        return getPermutationFunc(n, k, new AtomicInteger(), "", new boolean[n]);
    }

    private String getPermutationFunc(int n, int k, AtomicInteger c, String s, boolean[] visited) {
        if (s.length() == n) {
            System.out.println(s);
            if (c.incrementAndGet() == k) {
                return s;
            } else {
                return null;
            }
        }

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                visited[i] = true;
                String r = getPermutationFunc(n, k, c, s + (i+1), visited);
                if (c.get() == k) {
                    return r;
                }
                visited[i] = false;
            }
        }

        return null;
    }

    /**
     * #62
     */
    @Test
    void testUniquePaths() {
        assertEquals(3, uniquePaths(3, 2));
        assertEquals(28, uniquePaths(7, 3));
    }

    private int uniquePaths(int m, int n) {
        //组合问题：C(m+n-2)(n-1)
        m--;n--;
        m += n;
        int a = 1, b = 1;
        while (n > 0) {
            a *= m--;
            b *= n--;
        }
        return a/b;
    }

    /**
     * #63
     */
    @Test
    void testUniquePathsWithObstacles() {
        assertEquals(2, uniquePathsWithObstacles(new int[][]{
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        }));
    }

    private int uniquePathsWithObstacles(int[][] obstacleGrid) {
        return uniquePathsWithObstaclesFunc(obstacleGrid, obstacleGrid.length-1, obstacleGrid[0].length-1);
    }

    private int uniquePathsWithObstaclesFunc(int[][] obstacleGrid, int m, int n) {
        if (m + n == 1) {
            return 1;
        }

        int ans = 0;
        if (m > 0 && obstacleGrid[m-1][n] == 0) {
            ans += uniquePathsWithObstaclesFunc(obstacleGrid, m-1, n);
        }
        if (n > 0 && obstacleGrid[n-1][m] == 0) {
            ans += uniquePathsWithObstaclesFunc(obstacleGrid, m, n-1);
        }

        return ans;
    }

    /**
     * #68
     */
    @Test void testFullJustify() {
        String[] words = {"This", "is", "an", "example", "of", "text", "justification."};

//        String[] words =  {
//                "Science","is","what","we","understand","well","enough","to","explain",
//                "to","a","computer.","Art","is","everything","else","we","do"};

        for (String s : fullJustify(words, 16)) {
            System.out.println(s);
        }
    }

    private List<String> fullJustify(String[] words, int maxWidth) {
        List<String> ans = new ArrayList<>();
        int start = 0, wc = 0, width = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= words.length; i++) {
            if (i < words.length && width + words[i].length() <= maxWidth) {
                width += words[i].length()+1;
                wc++;
            } else {
                int spaceLength = maxWidth - width + wc;
                int spacePerWord = wc > 1
                        ? spaceLength % (wc-1) == 0 ? spaceLength / (wc-1) : spaceLength / (wc-1) + 1
                        : spaceLength;
                if (wc > 1) {
                    while (wc-- > 1) {
                        sb.append(words[start++]);
                        for (int j = spacePerWord; j > 0; j--) {
                            sb.append('-');
                        }
                    }
                    if (sb.length() > maxWidth - words[start].length()) {
                        sb.replace(maxWidth - words[start].length(), sb.length(), words[start++]);
                    } else {
                        sb.append(words[start++]);
                    }

                } else {
                    sb.append(words[start++]);
                    for (int j = spacePerWord; j > 0; j--) {
                        sb.append('-');
                    }
                }

                ans.add(sb.toString());
                sb.setLength(0);
                if (i < words.length) {
                    wc = 1;
                    width = words[i].length() + 1;
                }
            }
        }


        return ans;
    }

    @Test
    void testMinDistance() {
        assertEquals(3, minDistance("horse", "ros"));
        assertEquals(5, minDistance("intention", "execution"));
    }

    private int minDistance(String word1, String word2) {
        return minDistanceFunc(word1.toCharArray(), word2.toCharArray(), 0, 0, new int[word1.length()][word2.length()]);
    }

    private int minDistanceFunc(char[] w1, char[] w2, int i, int j, int[][] state) {
        if (i == w1.length) {
            return w2.length - j;
        }
        if (j == w2.length) {
            return w1.length - i;
        }
        if (state[i][j] != 0) {
            return state[i][j];
        }

        int min;
        if (w1[i] == w2[j]) {
            min = DynamicProgramming.min(
                    minDistanceFunc(w1, w2, i, j+1, state) + 1,
                    minDistanceFunc(w1, w2, i+1, j+1, state),
                    minDistanceFunc(w1, w2, i+1, j, state) + 1
            );
        } else {
            min = DynamicProgramming.min(
                    minDistanceFunc(w1, w2, i, j+1, state),
                    minDistanceFunc(w1, w2, i+1, j+1, state),
                    minDistanceFunc(w1, w2, i+1, j, state)
            ) + 1;
        }

        state[i][j] = min;

        return min;
    }

    /**
     * #73
     */
    @Test
    void testSetZeroes() {
        int[][] matrix = new int[][]{
                {0,1,2,0},
                {3,4,5,2},
                {1,3,1,5}
        };
        setZeroes(matrix);
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    private void setZeroes(int[][] matrix) {
        int m = -1, n = -1;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    if (m == -1) {
                        m = i; n = j;
                    } else {
                        matrix[m][j] = 0;
                        matrix[i][n] = 0;
                    }
                }
            }
        }

        for (int i = 0; i < matrix[m].length; i++) {
            if (matrix[m][i] == 0 && i!= n) {
                for (int j = 0; j < matrix.length; j++) {
                    matrix[j][i] = 0;
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][n] == 0) {
                for (int j = 0; j < matrix[i].length; j++) {
                    matrix[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            matrix[i][n] = 0;
        }
    }

    /**
     * #76
     */
    @Test
    void testMinWindow() {
        assertEquals("BANC", minWindow("ADOBECODEBANC", "ABC"));
        assertEquals("ACB", minWindow("ABAACBAB", "ABC"));
    }

    private String minWindow(String s, String t) {
        LinkedHashMap<Character, Integer> map = new LinkedHashMap<>();
        for (char c : t.toCharArray()) {
            map.put(c, -1);
        }
        int minLength = Integer.MAX_VALUE, length;
        int start = 0, end = 0, tempStart, tempEnd;
        int matchCount = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (map.containsKey(c)) {
                if (map.get(c) == -1) {
                    matchCount++;
                }
                map.remove(c);
                map.put(c, i);
                tempEnd = i+1;

                if (matchCount == t.length()) {
                    tempStart = map.values().iterator().next();
                    length = tempEnd - tempStart;
                    if (length < minLength) {
                        minLength = length;
                        start = tempStart;
                        end = tempEnd;
                    }
                }
            }
        }

        return s.substring(start, end);
    }

    /**
     * #77
     */
    @Test
    void testCombine() {
        combine(6, 3).forEach(System.out::println);
    }

    private List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        combineFunc(n, 1, k, new ArrayList<>(), result);
        return result;
    }

    private void combineFunc(int n, int i, int k, List<Integer> list, List<List<Integer>> result) {
        if (list.size() == k) {
            result.add(list);
            return;
        }

        List<Integer> nl = new ArrayList<>(list);
        nl.add(i);
        combineFunc(n, i+1, k, nl, result);

        if (k - n + i <= list.size()) {
            combineFunc(n, i+1, k, list, result);
        }
    }

    /**
     * #2575
     */
    @Test
    void testDivisibilityArray() {
        int[] ans = divisibilityArray("91221181269244172125025075166510211202115152121212341281327", 21);
        System.out.println(ans.length);
    }
    private int[] divisibilityArray(String word, int m) {
        int[] res = new int[word.length()];
        long cur = 0;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            cur = (cur * 10 + (c - '0')) % m;
            res[i] = (cur == 0) ? 1 : 0;
        }

        return res;
    }

}