import datastructure.tree.BinarySearchTree;
import datastructure.tree.BinaryTree;
import datastructure.tree.Heap;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/2/28
 */
class TreeTest {

    @Test
    void testBinaryTreeTraverse() {
        BinaryTree binaryTree = new BinaryTree();
        for (int i = 1; i <= 20; i++) {
            binaryTree.insert(i);
        }

        binaryTree.firstOrderTraverse(1);
        binaryTree.middleOrderTraverse(1);
        binaryTree.lastOrderTraverse(1);
    }

    @Test
    void testBinarySearchTreeFind() {
        BinarySearchTree searchTree = new BinarySearchTree();
        searchTree.insert(10);
        searchTree.insert(5);
        searchTree.insert(15);
        searchTree.insert(3);
        searchTree.insert(7);
        searchTree.insert(12);
        searchTree.insert(18);

        searchTree.traverseByLevel();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();


        BinarySearchTree.Node node = searchTree.find(10);
        assertNotNull(node);
        assertEquals(10, node.getValue());
        assertNull(searchTree.find(11));
    }

    @Test
    void testHeapSort() {
        int elementSize = 100;
        int arrSize = elementSize + 1;
        int[] arr = new int[arrSize];
        for (int i = 1; i < arrSize; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(10000);
        }

        Heap.heapOrder(arr, true);

        for (int i = 1; i < elementSize; i++) {
            assertTrue(arr[i] >= arr[i+1]);
            System.out.println(arr[i]);
        }
        System.out.println(arr[elementSize]);

        System.out.println("array heap sort successfully, array length is: " + arr.length);
    }

    @Test
    void testTopK() {
        int elementSize = 10;
        int arrSize = elementSize + 1;
        int[] arr = new int[arrSize];
        for (int i = 1; i < arrSize; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(10000);
        }

        Heap.topK(arr, 5, false);

        for (int i = 1; i < arrSize; i++) {
            System.out.println(arr[i]);
        }

        System.out.println("array heap sort successfully, array length is: " + arr.length);
    }

    @Test
    void testGetMiddle() {
        int elementSize = 100000;
        int arrSize = elementSize + 1;
        int[] arr = new int[arrSize];
        Heap.MiddleFinder middleFinder1 = new Heap.MiddleFinder();
        Heap.MiddleFinder middleFinder2 = new Heap.MiddleFinder();
        for (int i = 1; i < arrSize; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(10000);
        }

        long startTime1 = System.currentTimeMillis();
        for (int i = 1; i < arrSize; i++) {
            middleFinder1.add2(arr[i]);
        }
        long finishTime1 = System.currentTimeMillis();

        long startTime2 = System.currentTimeMillis();
        for (int i = 1; i < arrSize; i++) {
            middleFinder2.add2(arr[i]);
        }
        long finishTime2 = System.currentTimeMillis();

        int mid1 = middleFinder1.getMiddle();
        int mid2 = middleFinder2.getMiddle();

        int greater1 = 0, lesser1 = 0, equals1 = 0, greater2 = 0, lesser2 = 0, equals2 = 0;
        for (int i = 1; i < arrSize; i++) {
            if (arr[i] > mid1) {
                greater1++;
            }
            if (arr[i] < mid1) {
                lesser1++;
            }
            if (arr[i] == mid1) {
                equals1++;
            }
            if (arr[i] > mid2) {
                greater2++;
            }
            if (arr[i] < mid2) {
                lesser2++;
            }
            if (arr[i] == mid2) {
                equals2++;
            }
        }

        assertTrue(abs(greater1 - lesser1) < equals1);
        assertTrue(abs(greater2 - lesser2) < equals2);

        System.out.println(format("add1 spend: %d, add2 spend: %d", finishTime1 - startTime1, finishTime2 - startTime2));

    }

}
