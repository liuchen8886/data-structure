/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/6/12
 */
public class TestStatic {

    private static String s = "abc";

    static void run() {
        System.out.println("static run...");
    }


    static {
        System.out.println("static s is: ".concat(s));
    }

}
