package com.mansur.graph;

import com.mansur.graph.model.Graph;
import com.mansur.graph.topo.TopoSort;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TopoTest {

    @Test
    void simple_dag_topo_ok() {
        Graph g = new Graph(4);
        g.addEdge(0,1,1);
        g.addEdge(1,2,1);
        g.addEdge(0,3,1);

        List<Integer> order = TopoSort.kahn(g);
        // 0 должен быть раньше 1 и 3
        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(0) < order.indexOf(3));
    }
}
