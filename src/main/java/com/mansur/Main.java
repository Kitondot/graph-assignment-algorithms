package com.mansur;

import com.mansur.graph.dagsp.DagLongestPath;
import com.mansur.graph.dagsp.DagShortestPath;
import com.mansur.graph.model.Graph;
import com.mansur.graph.scc.TarjanSCC;
import com.mansur.graph.topo.TopoSort;
import com.mansur.io.JsonGraphLoader;
import com.mansur.metrics.SimpleMetrics;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        String[] files = {
                "data/tasks-small-1.json",
                "data/tasks-small-2.json",
                "data/tasks-small-3.json",
                "data/tasks-medium-1.json",
                "data/tasks-medium-2.json",
                "data/tasks-medium-3.json",
                "data/tasks-large-1.json",
                "data/tasks-large-2.json",
                "data/tasks-large-3.json"
        };


        System.out.println("dataset;n;sccCount;dagNodes;timeSccNs;timeTopoNs;timeSpNs;timeLpNs");

        for (String filename : files) {

            var loaded = JsonGraphLoader.load(filename);
            Graph g = loaded.graph;
            int source = loaded.source;
            int n = g.size();


            var sccMetrics = new SimpleMetrics();
            TarjanSCC tarjan = new TarjanSCC(g, sccMetrics);
            TarjanSCC.Result res = tarjan.run();
            int sccCount = res.components.size();
            long timeScc = sccMetrics.getElapsedNs();


            Graph dag = TarjanSCC.buildCondensation(g, res.compOf, sccCount);


            var topoMetrics = new SimpleMetrics();
            List<Integer> topo = TopoSort.kahn(dag, topoMetrics);
            long timeTopo = topoMetrics.getElapsedNs();


            int sourceComp = res.compOf[source];
            var spMetrics = new SimpleMetrics();
            DagShortestPath.ShortestResult sp =
                    DagShortestPath.shortestPathDAG(dag, sourceComp, topo, spMetrics);
            long timeSp = spMetrics.getElapsedNs();


            var lpMetrics = new SimpleMetrics();
            DagLongestPath.LongestResult lp =
                    DagLongestPath.longestPathDAG(dag, sourceComp, topo, lpMetrics);
            long timeLp = lpMetrics.getElapsedNs();


            System.out.print("Derived order for " + filename + ": ");
            for (int compId : topo) {
                for (int v = 0; v < res.compOf.length; v++) {
                    if (res.compOf[v] == compId) {
                        System.out.print(v + " ");
                    }
                }
            }
            System.out.println();


            System.out.println(filename + ";" +
                    n + ";" +
                    sccCount + ";" +
                    dag.size() + ";" +
                    timeScc + ";" +
                    timeTopo + ";" +
                    timeSp + ";" +
                    timeLp);
        }
    }
}
