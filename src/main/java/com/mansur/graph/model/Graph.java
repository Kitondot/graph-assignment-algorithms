package com.mansur.graph.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int n;
    private final List<List<Edge>> adj;

    public Graph(int n) {
        this.n = n;
        this.adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new Edge(v, w));
    }

    public int size() {
        return n;
    }

    public List<Edge> neighbors(int u) {
        return adj.get(u);
    }
}
