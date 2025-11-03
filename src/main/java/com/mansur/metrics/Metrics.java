package com.mansur.metrics;

public interface Metrics {
    void start();
    void stop();
    void inc(String name);
    void print(String title);
}
