package datastructure.linkedlist;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/2/20
 */
@SuppressWarnings("unchecked")
public class OptimizedSkipList<T> {
    private static final int MAX_LEVEL = 16;
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    private Node<T> head = new Node<>(null, Integer.MIN_VALUE);
    private int levelCount;

    public Node<T> find(int score) {
        Node<T> p = head;
        for (int i = levelCount-1; i >= 0; i--) {
            while (p.forwards[i] != null && p.forwards[i].score < score) {
                p = p.forwards[i];
            }
        }

        if (p.forwards[0] != null && p.forwards[0].score == score) {
            return p.forwards[0];
        }

        return null;
    }

    public void insert(Node<T> node) {
        int maxLevel = random.nextInt(1, MAX_LEVEL);
        node.maxLevel = maxLevel;
        Node<T> p = head;
        Node<T>[] update = new Node[maxLevel];
        for (int i = 0; i < maxLevel; ++i) {
            update[i] = head;
        }
        for (int i = maxLevel-1; i >= 0; i--) {
            while (p.forwards[i] != null && p.forwards[i].score < node.score) {
                p = p.forwards[i];
            }
            update[i] = p;
        }

        for (int i = 0; i < maxLevel; i++) {
            node.forwards[i] = update[i].forwards[i];
            update[i].forwards[i] = node;
        }

        if (levelCount < maxLevel) {
            levelCount = maxLevel;
        }
    }

    public void delete(int score) {
        Node<T> p = head;
        Node<T>[] update = new Node[levelCount];
        Node<T> node = null;
        for (int i = levelCount-1; i >= 0; i--) {
            while (p.forwards[i] != null && p.forwards[i].score < score) {
                p = p.forwards[i];
            }
            if (p.forwards[i] != null && p.forwards[i].score == score) {
                update[i] = p;
                node = p.forwards[i];
            }
        }

        if (node == null) {
            return;
        }

        for (int i = 0; i < node.maxLevel; i++) {
            update[i].forwards[i] = node.forwards[i];
            if (update[i] == head && node.forwards[i] == null) {
                levelCount--;
            }
        }

    }

    public static class Node<T> {
        private T obj;
        private int score;
        private Node<T>[] forwards = new Node[MAX_LEVEL];
        private int maxLevel;

        public Node(T obj, int score) {
            this.obj = obj;
            this.score = score;
        }

        public T getObj() {
            return obj;
        }

        public int getScore() {
            return score;
        }
    }

}
