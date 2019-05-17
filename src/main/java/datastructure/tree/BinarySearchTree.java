package datastructure.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/2/28
 */
public class BinarySearchTree {

    public static class Node {
        private int value;
        private Node left;
        private Node right;
        private Node parent;
        private int code;

        private Node(int value, Node parent) {
            if (parent == null) {
                this.code = 1;
            } else if (parent.value < value){
                this.code = (parent.code << 1) + 1;
            } else {
                this.code = parent.code << 1;
            }
            this.value = value;
            this.parent = parent;
        }

        public int getCodeLength() {
            return Integer.toBinaryString(code).length();
        }

        private Node find(int value) {
            System.out.print(this.value + ", ");
            if (this.value == value) {
                return this;
            }
            if (this.value < value && this.right != null) {
                return right.find(value);
            } else if (this.left != null) {
                return left.find(value);
            }
            return null;
        }

        private int insert(int value) {
            if (this.value < value) {
                if (this.right == null) {
                    this.right = new Node(value, this);
                    return getCodeLength();
                } else {
                    return this.right.insert(value);
                }
            } else {
                if (this.left == null) {
                    this.left = new Node(value, this);
                    return getCodeLength();
                } else {
                    return this.left.insert(value);
                }
            }
        }

        public int getValue() {
            return value;
        }
    }

    private Node root;
    private int height;

    public Node find(int value) {
        if (root == null) {
            return null;
        }
        System.out.println();
        return root.find(value);
    }

    public void insert(int value) {
        if (root == null) {
            root = new Node(value, null);
            height = 1;
            return;
        }

        int level = root.insert(value);
        if (height < level) {
            height = level;
        }
    }

    public void delete(int value) {
        Node node = find(value);
        if (node != null) {
            //node is leaf
            if (node.left == null && node.right == null) {
                //node is root
                if (node.parent == null) {
                    root = null;
                } else if (node.parent.value < value) {
                    node.parent.right = null;
                } else {
                    root.parent.left = null;
                }
            } else if (node.left == null) {
                if (node.parent.value < value) {
                    node.parent.right = node.right;
                } else {
                    root.parent.left = node.right;
                }
            } else if (node.right == null) {
                if (node.parent.value < value) {
                    node.parent.right = node.left;
                } else {
                    root.parent.left = node.left;
                }
            } else {
                if (node.parent.value < value) {
                    Node n = node;
                    while (n.left != null) {
                        n = n.left;
                    }
                    node.value = n.value;
                    n.parent.left = null;
                } else {
                    Node n = node;
                    while (n.right != null) {
                        n = n.right;
                    }
                    node.value = n.value;
                    n.parent.right = null;
                }
            }
        }
    }

    public void traverseByLevel() {
        if (root == null) {
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            sb.append("    ");
        }

        int currentLevel = 0;
        int currentX = 0;
        while (!queue.isEmpty()) {
            Node n = queue.poll();
            if (currentLevel < n.getCodeLength()) {
                currentLevel++;
                System.out.println();
                if (sb.length() > 0) {
                    sb.setLength(sb.length() -1);
                    System.out.print(sb.toString());
                }
            }

            int m = n.code & ((1<<currentLevel-1) -1);
            for (int i = 0; i < ((m+1)/2 - currentX); i++) {
                System.out.print("\t\t");
            }
            currentX = (m+1)/2;
            System.out.print(n.value);

            if (n.left != null) {
                queue.offer(n.left);
            }
            if (n.right != null) {
                queue.offer(n.right);
            }
        }
    }

}
