package com.mansur.metrics;

import java.util.HashMap;
import java.util.Map;

public class SimpleMetrics implements Metrics {

    private long start;
    private long end;
    private final Map<String, Integer> counters = new HashMap<>();

    @Override
    public void start() {
        start = System.nanoTime();
    }

    @Override
    public void stop() {
        end = System.nanoTime();
    }

    @Override
    public void inc(String name) {
        counters.put(name, counters.getOrDefault(name, 0) + 1);
    }

    @Override
    public void print(String title) {
        System.out.println("---- METRICS: " + title + " ----");
        System.out.println("time (ns): " + getElapsedNs());
        for (var e : counters.entrySet()) {
            System.out.println(e.getKey() + " = " + e.getValue());
        }
        System.out.println("------------------------------");
    }


    public long getElapsedNs() {
        return end - start;
    }
}
