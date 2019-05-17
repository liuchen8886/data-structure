package datastructure.graph;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.String.format;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/5/1
 */
public class Graph<T> {

    private Map<T, LinkedList<T>> nodeMap;

    public Graph() {
        nodeMap = new HashMap<>();
    }

    public void addEdge(T a, T b) {
        addNode(a).add(b);
        addNode(b).add(a);
    }

    private LinkedList<T> addNode(T val) {
        LinkedList<T> list = nodeMap.get(val);
        if (list == null) {
            list = new LinkedList<>();
            nodeMap.putIfAbsent(val, list);
        }
        return list;
    }

    public void print() {
        nodeMap.forEach((k,v) -> {
            StringBuilder sb = new StringBuilder();
            v.forEach(n -> sb.append(n).append(" -> "));
            sb.insert(0, ": ").insert(0, k).setLength(sb.length() - 4);
            System.out.println(sb);
        });
    }

    /**
     * 广度优先搜索
     * @param start 搜索起始位置的值
     * @param val 搜索目标值
     */
    public void bfs(T start, T val) {
        if (start.equals(val)) {
            System.out.println("search result: " + val);
            return;
        }

        Map<T, T> pathMap = new HashMap<>();
        Set<T> visited = new HashSet<>();
        Queue<T> queue = new ArrayBlockingQueue<>(nodeMap.size());

        T t = start;
        visited.add(start);
        out: while (t != null) {
            System.out.println("t: " + t);
            for (T x : nodeMap.get(t)) {
                if (!visited.contains(x)) {
                    visited.add(x);
                    queue.offer(x);
                    pathMap.put(x, t);
                }
                if (val.equals(x)) {
                    break out;
                }
            }
            t = queue.poll();
        }

        printSearchPath(val, pathMap);

    }

    /**
     * 深度优先搜索
     * @param start 搜索起始位置的值
     * @param val 搜索目标值
     */
    public void dfs(T start, T val) {
        if (start.equals(val)) {
            System.out.println("search result: " + val);
            return;
        }

        Map<T, T> pathMap = new HashMap<>();
        Set<T> visited = new HashSet<>();
        Stack<T> stack = new Stack<>();

        T t = start;
        visited.add(t);
        out: while (t != null) {
            System.out.println("t: " + t);
            for (T x : nodeMap.get(t)) {
                if (!visited.contains(x)) {
                    visited.add(x);
                    pathMap.put(x, t);
                    if (val.equals(x)) {
                        break out;
                    } else {
                        stack.push(t);
                        t = x;
                        continue out;
                    }
                }
            }
            t = stack.pop();
        }

        printSearchPath(val, pathMap);
    }

    private void printSearchPath(T val, Map<T, T> pathMap) {
        T t = val;
        if (!pathMap.containsKey(val)) {
            System.out.println(format("search result: %s is not found", val.toString()));
            return;
        }
        StringBuilder sb = new StringBuilder();
        while (t != null) {
            sb.insert(0, ", ").insert(0, t);
            t = pathMap.get(t);
        }
        sb.insert(0, "search result: ").setLength(sb.length() - 2);
        System.out.println(sb.toString());
    }

}
