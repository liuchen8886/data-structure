package datastructure.tree;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/2/28
 */
public class BinaryTree {

    private static final int DEFAULT_ELEMENT_SIZE = 15;

    int[] elements = null;
    int elementLength = 1;

    public void insert(int n) {
        ensureCapacity();
        elements[elementLength++] = n;
    }

    public void ensureCapacity() {
        if (elementLength == 1) {
            elements = new int[DEFAULT_ELEMENT_SIZE+1];
            return;
        }

        if (elements.length < elementLength+1) {
            int[] newArr = new int[elementLength * 2];
            for (int i = 0; i < elementLength; i++) {
                newArr[i] = elements[i];
            }
            elements = newArr;
        }
    }

    public void firstOrderTraverse(int i) {
        if (i >= elementLength) {
            return;
        }

        System.out.println(elements[i]);
        firstOrderTraverse(i*2);
        firstOrderTraverse(i*2+1);
    }

    public void middleOrderTraverse(int i) {
        if (i >= elementLength) {
            return;
        }

        firstOrderTraverse(i*2);
        System.out.println(elements[i]);
        firstOrderTraverse(i*2+1);
    }

    public void lastOrderTraverse(int i) {
        if (i >= elementLength) {
            return;
        }

        firstOrderTraverse(i*2);
        firstOrderTraverse(i*2+1);
        System.out.println(elements[i]);
    }

}
