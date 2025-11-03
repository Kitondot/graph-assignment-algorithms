package com.mansur.graph.topo;

import com.mansur.graph.model.Edge;
import com.mansur.graph.model.Graph;
import com.mansur.metrics.Metrics;

import java.util.*;

public class TopoSort {


    public static List<Integer> kahn(Graph g, Metrics metrics) {
        if (metrics != null) metrics.start();

        int n = g.size();
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++) {
            for (Edge e : g.neighbors(u)) {
                indeg[e.to]++;
            }
        }

        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indeg[i] == 0) q.add(i);
        }

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.remove();
            order.add(u);
            if (metrics != null) metrics.inc("kahn_pops");

            for (Edge e : g.neighbors(u)) {
                indeg[e.to]--;
                if (indeg[e.to] == 0) {
                    q.add(e.to);
                    if (metrics != null) metrics.inc("kahn_pushes");
                }
            }
        }

        if (metrics != null) {
            metrics.stop();
            metrics.print("Topo (Kahn)");
        }

        return order;
    }


    public static List<Integer> kahn(Graph g) {
        return kahn(g, null);
    }
}
