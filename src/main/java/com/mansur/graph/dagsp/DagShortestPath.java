package com.mansur.graph.dagsp;

import com.mansur.graph.model.Edge;
import com.mansur.graph.model.Graph;
import com.mansur.metrics.Metrics;

import java.util.Arrays;
import java.util.List;

public class DagShortestPath {

    public static class ShortestResult {
        public final int[] dist;
        public final int[] parent;

        public ShortestResult(int[] dist, int[] parent) {
            this.dist = dist;
            this.parent = parent;
        }
    }

    public static ShortestResult shortestPathDAG(Graph dag, int source, List<Integer> topo, Metrics metrics) {
        if (metrics != null) metrics.start();

        int n = dag.size();
        int[] dist = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[source] = 0;

        for (int u : topo) {
            if (dist[u] == Integer.MAX_VALUE) continue;
            for (Edge e : dag.neighbors(u)) {
                int v = e.to;
                int nd = dist[u] + e.weight;
                if (nd < dist[v]) {
                    dist[v] = nd;
                    parent[v] = u;
                    if (metrics != null) metrics.inc("relax");
                }
            }
        }

        if (metrics != null) {
            metrics.stop();
            metrics.print("DAG shortest");
        }

        return new ShortestResult(dist, parent);
    }


    public static ShortestResult shortestPathDAG(Graph dag, int source, List<Integer> topo) {
        return shortestPathDAG(dag, source, topo, null);
    }
}
