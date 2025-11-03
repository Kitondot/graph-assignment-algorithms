package com.mansur.graph.scc;

import com.mansur.graph.model.Edge;
import com.mansur.graph.model.Graph;
import com.mansur.metrics.Metrics;

import java.util.*;

public class TarjanSCC {

    private final Graph g;
    private final Metrics metrics;  // может быть null

    private int time = 0;
    private final int[] disc;
    private final int[] low;
    private final boolean[] onStack;
    private final Deque<Integer> stack = new ArrayDeque<>();
    private int compCount = 0;
    private final int[] compOf;
    private final List<List<Integer>> components = new ArrayList<>();


    public TarjanSCC(Graph g) {
        this(g, null);
    }


    public TarjanSCC(Graph g, Metrics metrics) {
        this.g = g;
        this.metrics = metrics;
        int n = g.size();
        disc = new int[n];
        low = new int[n];
        onStack = new boolean[n];
        compOf = new int[n];
        for (int i = 0; i < n; i++) {
            disc[i] = -1;
        }
    }

    public Result run() {
        if (metrics != null) metrics.start();

        for (int v = 0; v < g.size(); v++) {
            if (disc[v] == -1) {
                dfs(v);
            }
        }

        if (metrics != null) {
            metrics.stop();
            metrics.print("SCC (Tarjan)");
        }
        return new Result(components, compOf);
    }

    private void dfs(int v) {
        if (metrics != null) metrics.inc("dfs_calls");

        disc[v] = low[v] = time++;
        stack.push(v);
        onStack[v] = true;

        for (Edge e : g.neighbors(v)) {
            if (metrics != null) metrics.inc("dfs_edges");

            int to = e.to;
            if (disc[to] == -1) {
                dfs(to);
                low[v] = Math.min(low[v], low[to]);
            } else if (onStack[to]) {
                low[v] = Math.min(low[v], disc[to]);
            }
        }

        if (low[v] == disc[v]) {
            List<Integer> comp = new ArrayList<>();
            while (true) {
                int u = stack.pop();
                onStack[u] = false;
                compOf[u] = compCount;
                comp.add(u);
                if (u == v) break;
            }
            components.add(comp);
            compCount++;
        }
    }


    public static Graph buildCondensation(Graph g, int[] compOf, int compCount) {
        Graph dag = new Graph(compCount);
        int n = g.size();
        for (int u = 0; u < n; u++) {
            int cu = compOf[u];
            for (Edge e : g.neighbors(u)) {
                int v = e.to;
                int cv = compOf[v];
                if (cu != cv) {
                    dag.addEdge(cu, cv, e.weight);
                }
            }
        }
        return dag;
    }

    public static class Result {
        public final List<List<Integer>> components;
        public final int[] compOf;

        public Result(List<List<Integer>> components, int[] compOf) {
            this.components = components;
            this.compOf = compOf;
        }
    }
}
