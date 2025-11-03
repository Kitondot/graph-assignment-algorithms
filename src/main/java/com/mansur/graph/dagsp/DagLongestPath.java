package com.mansur.graph.dagsp;

import com.mansur.graph.model.Edge;
import com.mansur.graph.model.Graph;
import com.mansur.metrics.Metrics;

import java.util.Arrays;
import java.util.List;

public class DagLongestPath {

    public static class LongestResult {
        public final int[] dist;
        public final int[] parent;
        public final int bestVertex;

        public LongestResult(int[] dist, int[] parent, int bestVertex) {
            this.dist = dist;
            this.parent = parent;
            this.bestVertex = bestVertex;
        }

        public String reconstructPath(int target) {
            StringBuilder sb = new StringBuilder();
            int cur = target;
            while (cur != -1) {
                sb.insert(0, cur + " ");
                cur = parent[cur];
            }
            return sb.toString();
        }
    }

    public static LongestResult longestPathDAG(Graph dag, int source, List<Integer> topo, Metrics metrics) {
        if (metrics != null) metrics.start();

        int n = dag.size();
        int[] dist = new int[n];
        int[] parent = new int[n];
        Arrays.fill(dist, Integer.MIN_VALUE);
        Arrays.fill(parent, -1);
        dist[source] = 0;

        for (int u : topo) {
            if (dist[u] == Integer.MIN_VALUE) continue;
            for (Edge e : dag.neighbors(u)) {
                int v = e.to;
                int nd = dist[u] + e.weight;
                if (nd > dist[v]) {
                    dist[v] = nd;
                    parent[v] = u;
                    if (metrics != null) metrics.inc("relax");
                }
            }
        }

        int bestV = source;
        for (int i = 0; i < n; i++) {
            if (dist[i] > dist[bestV]) bestV = i;
        }

        if (metrics != null) {
            metrics.stop();
            metrics.print("DAG longest");
        }

        return new LongestResult(dist, parent, bestV);
    }

    public static LongestResult longestPathDAG(Graph dag, int source, List<Integer> topo) {
        return longestPathDAG(dag, source, topo, null);
    }
}
