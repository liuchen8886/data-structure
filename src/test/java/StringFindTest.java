import datastructure.StringFind;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/5/8
 */

public class StringFindTest {

    @Test
    void testGetNext() {
        int[] next = StringFind.getNext("ababac".toCharArray(), 6);
        StringBuilder sb = new StringBuilder();
        for (int i : next) {
            sb.append(i).append(", ");
        }
        sb.setLength(sb.length() - 2);
        System.out.println(sb);
    }

    @Test
    void testSearch() {
        String t = "ababdabcababacabaababac";
        String s = "ababac";
        System.out.println(format("t: %s, s: %s, result: %d", t, s,
                StringFind.kmp2(t.toCharArray() ,t.length(), s.toCharArray(), s.length())));
    }

}
