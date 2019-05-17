import datastructure.graph.Graph;
import datastructure.graph.WeightedGraph;
import org.junit.jupiter.api.Test;

/**
 * Desc:
 * -------------------------------
 *
 * @author liuchen11@meituan.com
 * @date 2019/5/1
 */
class GraphTest {

    @Test
    void testPrint() {
        Graph<Character> graph = new Graph<>();
        graph.addEdge('a', 'b');
        graph.addEdge('a', 'c');
        graph.addEdge('a', 'd');
        graph.addEdge('b', 'c');
        graph.addEdge('c', 'd');

        graph.print();
    }

    @Test
    void testSearch() {
        Graph<Integer> graph = new Graph<>();
        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(4, 6);
        graph.addEdge(5, 7);
        graph.addEdge(6, 7);

        System.out.println("************* BFS  *************");
        graph.bfs(0, 7);
        System.out.println("************* DFS  *************");
        graph.dfs(0, 7);
    }

    @Test
    void testGetShortestPath() {
        WeightedGraph graph = new WeightedGraph(6);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 4, 15);
        graph.addEdge(1, 2, 15);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 5, 5);
        graph.addEdge(3, 2, 1);
        graph.addEdge(3, 5, 12);
        graph.addEdge(4, 5, 10);
        graph.getShortestPath(0, 5);
    }

}
