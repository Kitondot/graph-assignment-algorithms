package com.mansur.graph;

import com.mansur.graph.dagsp.DagShortestPath;
import com.mansur.graph.model.Graph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DagSpTest {

    @Test
    void shortest_on_dag() {
        Graph g = new Graph(4);
        g.addEdge(0,1,1);
        g.addEdge(1,2,2);
        g.addEdge(0,2,5);
        g.addEdge(2,3,1);

        List<Integer> topo = List.of(0,1,2,3);
        DagShortestPath.ShortestResult r =
                DagShortestPath.shortestPathDAG(g, 0, topo);

        assertEquals(0, r.dist[0]);
        assertEquals(1, r.dist[1]);
        assertEquals(3, r.dist[2]); // 0->1->2 лучше чем 0->2
        assertEquals(4, r.dist[3]);
    }
}
