package com.mansur.graph;

import com.mansur.graph.model.Graph;
import com.mansur.graph.scc.TarjanSCC;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SccTest {

    @Test
    void triangle_is_one_scc() {
        Graph g = new Graph(3);
        g.addEdge(0,1,1);
        g.addEdge(1,2,1);
        g.addEdge(2,0,1);

        TarjanSCC t = new TarjanSCC(g);
        var res = t.run();

        assertEquals(1, res.components.size());
    }
}
