package datastructure.tree;

import java.util.Arrays;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/4/26
 */
public class Heap {

    private int[] vals;
    private int size;
    private boolean isLittleTop;

    public Heap() {
        this(true);
    }

    public Heap(boolean isLittleTop) {
        this.vals = new int[16];
        this.size = 0;
        this.isLittleTop = isLittleTop;
    }

    public Heap(int[] vals, boolean isLittleTop) {
        this.vals = vals;
        this.isLittleTop = isLittleTop;
        size = vals.length - 1;
        for (int x = size/2; x > 0; x--) {
            heapify(x);
        }
    }

    private void ensureCapacity() {
        if (vals.length == size + 1) {
            vals = Arrays.copyOf(vals, size << 1);
        }
    }

    private void heapify(int i) {
        int a = i;
        int x = i;
        while (i <= size/2) {
            if (i*2 <= size
                    && ((isLittleTop && vals[i*2] < vals[i]) || (!isLittleTop && vals[i*2] > vals[i]))) {
                x = i*2;
            }
            if (i*2+1 <= size
                    && ((isLittleTop && vals[i*2+1] < vals[x]) || (!isLittleTop && vals[i*2+1] > vals[x]))) {
                x = i*2+1;
            }

            if (x != i) {
                swap(i, x);
                i = x;
            } else {
                break;
            }
        }
//        return x != a;
    }

    private void add(int n) {
        ensureCapacity();
        vals[++size] = n;
        for (int i = size/2; i > 0; i/=2) {
            heapify(i);
        }
    }

    private void swap(int a, int b) {
        int temp = vals[a];
        vals[a] = vals[b];
        vals[b] = temp;
    }

    public static void heapOrder(int[] arr, boolean isLittleTop) {
        Heap heap = new Heap(arr, isLittleTop);

        while (heap.size > 1) {
           heap.swap(1, heap.size--);
           heap.heapify(1);
        }
    }

    public static void topK(int[] arr, int k, boolean isLittleTop) {
        Heap heap = new Heap(arr, isLittleTop);

        while (k-- > 0) {
            heap.swap(1, heap.size--);
            heap.heapify(1);
        }
    }

    public static class MiddleFinder {
        Heap littleHeap = new Heap(true);
        Heap bigHeap = new Heap(false);

        public void add(int n) {
            if (littleHeap.size == bigHeap.size) {
                if (n >= littleHeap.vals[1]) {
                    littleHeap.add(n);
                } else {
                    bigHeap.add(n);
                }
            }else if (littleHeap.size > bigHeap.size && n >= littleHeap.vals[1]) {
                bigHeap.add(littleHeap.vals[1]);
                littleHeap.vals[1] = n;
                littleHeap.heapify(1);
            } else if (littleHeap.size < bigHeap.size && n >= bigHeap.vals[1]) {
                littleHeap.add(n);
            } else if (littleHeap.size < bigHeap.size) {
                littleHeap.add(bigHeap.vals[1]);
                bigHeap.vals[1] = n;
                bigHeap.heapify(1);
            } else {
                bigHeap.add(n);
            }
        }

        public void add2(int n) {
            if (n >= littleHeap.vals[1]) {
                littleHeap.add(n);
            } else {
                bigHeap.add(n);
            }

            if (littleHeap.size - bigHeap.size == 2) {
                bigHeap.add(littleHeap.vals[1]);
                littleHeap.swap(1, littleHeap.size--);
                littleHeap.heapify(1);
            } else if (bigHeap.size - littleHeap.size == 2) {
                littleHeap.add(bigHeap.vals[1]);
                bigHeap.swap(1, bigHeap.size--);
                bigHeap.heapify(1);
            }
        }

        public int getMiddle() {
            return littleHeap.size > bigHeap.size ? littleHeap.vals[1]
                    : littleHeap.size < bigHeap.size ? bigHeap.vals[1]
                    : littleHeap.vals[1];
        }

    }

}
