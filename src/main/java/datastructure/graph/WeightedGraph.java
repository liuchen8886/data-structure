package datastructure.graph;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/5/17
 */
public class WeightedGraph {
    private int v;
    private LinkedList<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public WeightedGraph(int v) {
        this.v = v;
        this.adj = new LinkedList[v];
    }

    private static class Edge {
        private int to;
        private int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private static class Vertex {
        int val;
        int dist;
        Vertex prev;

        Vertex(int val, int dist, Vertex prev) {
            this.val = val;
            this.dist = dist;
            this.prev = prev;
        }

        int getDist() {
            return dist;
        }
    }

    public void addEdge(int from, int to, int weight) {
        if (adj[from] == null) {
            adj[from] = new LinkedList<>();
        }
        adj[from].add(new Edge(to, weight));
    }

    public void getShortestPath(int from, int to) {
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getDist));
        priorityQueue.offer(new Vertex(from, 0, null));
        boolean[] visited = new boolean[v];

        Vertex vertex;
        while ((vertex = priorityQueue.poll()) != null) {
            if (visited[vertex.val]) {
                continue;
            } else {
                if (vertex.val == to) {
                    break;
                }
                visited[vertex.val] = true;
            }

            for (Edge edge : adj[vertex.val]) {
                if (!visited[edge.to]) {
                    priorityQueue.offer(new Vertex(edge.to, vertex.dist + edge.weight, vertex));
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        while (vertex != null) {
            sb.insert(0, vertex.val).insert(0, " -> ");
            vertex = vertex.prev;
        }
        sb.insert(0, "the shortest path: start");
        System.out.println(sb);
    }

}
