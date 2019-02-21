import datastructure.linkedlist.OptimizedSkipList;
import datastructure.linkedlist.SkipList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/2/20
 */
class DataStructureTest {

    @Test
    void testSkipLinkedList() {
        SkipList<Character> skipList = new SkipList<>();
        skipList.add('a', 1);
        skipList.add('b', 2);
        skipList.add('c', 3);

        assertEquals('b', skipList.query(2, null, -1).charValue());
    }

    @Test
    void testOptimizedSkipList() {
        OptimizedSkipList<Character> skipList = new OptimizedSkipList<>();
        skipList.insert(new OptimizedSkipList.Node<>('a', 1));
        skipList.insert(new OptimizedSkipList.Node<>('b', 2));
        skipList.insert(new OptimizedSkipList.Node<>('c', 3));
        skipList.insert(new OptimizedSkipList.Node<>('d', 4));
        skipList.insert(new OptimizedSkipList.Node<>('e', 5));
        skipList.insert(new OptimizedSkipList.Node<>('f', 6));
        skipList.insert(new OptimizedSkipList.Node<>('g', 7));
        skipList.insert(new OptimizedSkipList.Node<>('h', 8));
        skipList.insert(new OptimizedSkipList.Node<>('i', 9));


        assertEquals('b', skipList.find(2).getObj().charValue());
        assertEquals('e', skipList.find(5).getObj().charValue());
        assertEquals('h', skipList.find(8).getObj().charValue());

        skipList.delete(5);
        assertNull(skipList.find(5));

    }

}
