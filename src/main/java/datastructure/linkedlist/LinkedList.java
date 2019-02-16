package datastructure.linkedlist;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/2/13
 */
public class LinkedList<T> {

    private static class Node<T> {
        T value;
        Node<T> next;

        private Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;

    private LinkedList<T> reverse() {
        if (head != null) {
            Node<T> node = head;
            Node<T> previous = null;
            Node<T> next;
            while (node != null) {
                next = node.next;
                node.next = previous;
                previous = node;
                node = next;
            }
            head = previous;
        }
        return this;
    }

    private void reverseRecursively(Node<T> node) {
        if (node == null || node.next == null) {
            head = node;
            return;
        }
        reverseRecursively(node.next);
        node.next.next = node;
        node.next = null;
    }

    private LinkedList<T> add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            return this;
        } else {
            Node<T> node = head;
            while (node.next != null) {
                node = node.next;
            }
            node.next = newNode;
            return this;
        }
    }

    private void print() {
        if (head == null) {
            System.out.println("linked list is null");
        } else {
            Node<T> node = head;
            StringBuilder stringBuilder = new StringBuilder();
            while (node != null) {
                stringBuilder.append(node.value.toString().concat(" --> "));
                node = node.next;
            }
            System.out.println(stringBuilder.substring(0, stringBuilder.length() - 5));
        }
    }

    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList
                .add(1)
                .add(2)
                .add(3)
                .add(4);
        linkedList.print();
        System.out.println("**************");
        linkedList.reverse().print();
        linkedList.reverseRecursively(linkedList.head);
        linkedList.print();
    }

}
