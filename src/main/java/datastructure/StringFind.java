package datastructure;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/5/8
 */
public class StringFind {

    public static int[] getNext(char[] s, int m) {
        int[] next = new int[m];

        next[0] = -1;
        int k = -1;
        for (int i = 1; i < m; i++) {
            while (k != -1 && s[i] != s[k+1]) {
                k = next[k];
            }
            if (s[i] == s[k+1]) {
                k++;
            }
            next[i] = k;
        }

        return next;
    }


    public static int kmp(char[] a, int n, char[] b, int m) {
        int[] next = getNext(b, m);
        int j = 0;
        for (int i = 0; i < n; i++) {
            while (j >= 0) {
                if (a[i] == b[j]) {
                    j++;
                    break;
                } else {
                    j = next[j-1] + 1;
                }
            }
            if (j == m) {
                return i-m+1;
            }
        }

        return -1;
    }

    public static int kmp2(char[] a, int n, char[] b, int m) {
        int[] next = getNext(b, m);
        int j = 0;
        for (int i = 0; i < n; i++) {
            while (j > 0 && a[i] != b[j]) {
                j = next[j-1] + 1;
            }
            if (a[i] == b[j]) {
                j++;
            }
            if (j == m) {
                return i-m+1;
            }
        }
        return -1;
    }

}
