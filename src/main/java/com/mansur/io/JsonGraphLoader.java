package com.mansur.io;

import com.mansur.graph.model.Graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class JsonGraphLoader {

    public static class LoaderResult {
        public final Graph graph;
        public final int source;

        public LoaderResult(Graph graph, int source) {
            this.graph = graph;
            this.source = source;
        }
    }

    public static LoaderResult load(String filename) {
        try {

            String json = Files.readString(Path.of(filename));


            int n = getIntField(json, "\"n\"");
            Graph g = new Graph(n);


            int edgesStart = json.indexOf("\"edges\"");
            edgesStart = json.indexOf("[", edgesStart) + 1;
            int edgesEnd = json.indexOf("]", edgesStart);
            String edgesPart = json.substring(edgesStart, edgesEnd).trim();

            if (!edgesPart.isEmpty()) {

                String[] items = edgesPart.split("\\},");
                for (String raw : items) {
                    String item = raw.replace("{", "")
                            .replace("}", "")
                            .trim();
                    if (item.isEmpty()) continue;

                    int u = getIntField(item, "\"u\"");
                    int v = getIntField(item, "\"v\"");
                    int w = getIntField(item, "\"w\"");

                    g.addEdge(u, v, w);
                }
            }


            int source = getIntField(json, "\"source\"");

            return new LoaderResult(g, source);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read json file: " + filename, e);
        }
    }


    private static int getIntField(String json, String key) {
        int idx = json.indexOf(key);
        if (idx == -1) {
            throw new RuntimeException("Field " + key + " not found in json");
        }
        idx = json.indexOf(":", idx) + 1;


        while (idx < json.length() && Character.isWhitespace(json.charAt(idx))) {
            idx++;
        }


        StringBuilder sb = new StringBuilder();
        while (idx < json.length()) {
            char c = json.charAt(idx);
            if (c == '-' || Character.isDigit(c)) {
                sb.append(c);
                idx++;
            } else {
                break;
            }
        }
        return Integer.parseInt(sb.toString());
    }
}
