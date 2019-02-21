package datastructure.linkedlist;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/2/18
 */
public class SkipList<T> {

    private static class Node<T> {
        private T obj;
        private Node<T> next;
        private Node<T> down;
        private int score;

        Node(T obj, int score) {
            this.obj = obj;
            this.score = score;
        }
    }

    private Node<T>[] listArray = new Node[10];
    private int listArrayCapacity = 1;
    private int listArrayLength = 0;
    private int listLength = 0;

    public void add(T obj, int value) {
        if ((++listLength)>>listArrayCapacity > 0) {
            listArrayGrow();
        }

        query(value, obj, ThreadLocalRandom.current().nextInt(0, listArrayCapacity));
    }

    private void listArrayGrow() {
        if (listArrayCapacity == listArray.length) {
            listArray = Arrays.copyOf(listArray, listArrayCapacity*2);
        }
        listArrayCapacity++;
    }

    public T query(int score, T obj, Integer indexTopLevel) {
        Node<T> node = listArray[listArrayLength];
        if (node == null && indexTopLevel < 0) {
            return null;
        }

        Node<T> previous = null;
        Node<T> prevIndexNode = null;
        int indexLevel = listArrayLength;

        for (int i = indexTopLevel; i > indexLevel; i--) {
            Node<T> newIndexNode = new Node<>(obj, score);
            listArray[i] = newIndexNode;
            listArrayLength++;
            if (prevIndexNode != null) {
                prevIndexNode.down = newIndexNode;
            }
            prevIndexNode = newIndexNode;
        }

        while (true) {
            if (node != null && node.score < score) {
                previous = node;
                node = previous.next;
            } else if (previous != null && previous.down != null) {
                if (--indexLevel < indexTopLevel) {
                    Node<T> newIndexNode = new Node<>(obj, score);
                    newIndexNode.next = previous.next;
                    previous.next = newIndexNode;
                    if (prevIndexNode != null) {
                        prevIndexNode.down = newIndexNode;
                    }
                    prevIndexNode = newIndexNode;
                }
                previous = previous.down;
                node = previous.next;
            } else if (node == null) {
                if (indexTopLevel >= 0) {
                    Node<T> newNode = new Node<>(obj, score);
                    if (prevIndexNode != null) {
                        prevIndexNode.down = newNode;
                    }
                    if (previous != null) {
                        previous.next = newNode;
                    } else {
                        listArray[0] = newNode;
                    }
                }
                return null;
            } else if (node.score == score) {
                if (indexTopLevel >= 0) {
                    Node<T> newNode = new Node<>(obj, score);
                    while(node.down != null) {
                        node = node.down;
                    }
                    node.next = newNode;
                }
                return node.obj;
            }
        }
    }

    public List<Node> queryRange(int start, int end) {
        return null;
    }

}
