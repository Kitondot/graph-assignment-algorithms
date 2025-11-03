# Assignment 4 – Graph Algorithms (SCC + Topological Sort + DAG Shortest/Longest Paths)

## 🧩 Overview

This project implements a full pipeline of graph algorithms for directed graphs (DAGs and cyclic graphs).  
The algorithms identify **Strongly Connected Components (SCC)**, build a **condensation DAG**, perform **Topological Sorting**,  
and compute both **Shortest** and **Longest Paths** on the resulting DAG.  

The goal is to analyze performance (time and operation counts) across multiple datasets of different sizes.

---

## ⚙️ How to Run

### Run the main program
```bash
mvn -q -DskipTests exec:java
```
Run tests
```
mvn test
```
The program will:
```
Read all datasets from the /data/ folder

For each graph:
Find SCCs using Tarjan’s algorithm
Build the condensation graph
Perform Topological Sort (Kahn’s algorithm)
Compute Shortest Path and Longest Path on the DAG
Print metrics and a summary line for results
```
## 📁 Datasets
| File                | Vertices (n) | Description                 | Source Node |
| ------------------- | ------------ | --------------------------- | ----------- |
| tasks-small-1.json  | 8            | Mixed graph (1 SCC + chain) | 4           |
| tasks-small-2.json  | 6            | Pure DAG (no cycles)        | 0           |
| tasks-small-3.json  | 7            | Two SCCs + chain            | 2           |
| tasks-medium-1.json | 10           | One SCC + tail structure    | 3           |
| tasks-medium-2.json | 9            | Two SCCs connected          | 0           |
| tasks-medium-3.json | 12           | DAG with branches           | 0           |
| tasks-large-1.json  | 15           | Multiple SCCs               | 3           |
| tasks-large-2.json  | 15           | Multiple SCCs               | 0           |
| tasks-large-3.json  | 15           | Pure DAG, longest chain     | 0           |

Weight model:

The project uses edge weights from the input JSONs (as allowed in the task).

## ⏱️ Metrics Collected
```
Each algorithm measures execution time (ns) and counts operations:

Tarjan (SCC): DFS calls, DFS edges
Kahn (Topo): queue pushes and pops
DAG Shortest/Longest: number of relaxations
All metrics are printed automatically after each step.
```

📊 Results Table
| Dataset                  | n  | SCC count | DAG nodes | Time SCC (ns) | Time Topo (ns) | Time Shortest (ns) | Time Longest (ns) |
| ------------------------ | -- | --------- | --------- | ------------- | -------------- | ------------------ | ----------------- |
| data/tasks-small-1.json  | 8  | 6         | 6         | 141600        | 213000         | 93700              | 56000             |
| data/tasks-small-2.json  | 6  | 6         | 6         | 35600         | 25200          | 542500             | 21700             |
| data/tasks-small-3.json  | 7  | 4         | 4         | 35800         | 75700          | 19500              | 13000             |
| data/tasks-medium-1.json | 10 | 8         | 8         | 43000         | 211632600      | 83900              | 10900             |
| data/tasks-medium-2.json | 9  | 5         | 5         | 56700         | 28200          | 20500              | 21100             |
| data/tasks-medium-3.json | 12 | 12        | 12        | 130100        | 57900          | 28500              | 33192100          |
| data/tasks-large-1.json  | 15 | 12        | 12        | 63500         | 78900          | 25100              | 13600             |
| data/tasks-large-2.json  | 15 | 11        | 11        | 97100         | 59400          | 11800              | 502200            |
| data/tasks-large-3.json  | 15 | 15        | 15        | 62900         | 33400          | 13300              | 12700             |

## Derived Orders (Original Task Order)
These lines show the order of original task IDs after SCC compression and topological sorting:
```
data/tasks-small-1.json: 0 4 1 2 3 5 6 7
data/tasks-small-2.json: 0 1 2 3 4 5
data/tasks-small-3.json: 0 1 2 3 4 5 6
data/tasks-medium-1.json: 0 1 2 3 4 8 5 9 6 7
data/tasks-medium-2.json: 0 1 2 3 4 5 6 7 8
data/tasks-medium-3.json: 0 1 2 3 4 5 6 7 8 9 10 11
data/tasks-large-1.json: 0 1 2 3 4 5 9 6 10 7 11 8 12 13 14
data/tasks-large-2.json: 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14
data/tasks-large-3.json: 0 1 6 2 7 3 10 8 4 11 9 5 12 13 14
```
## Observations and Analysis
```
The runtime of SCC (Tarjan) grows roughly linearly with the number of vertices and edges.

For pure DAGs (small-2, medium-3, large-3), topological sorting time remains stable.

Some outlier values (e.g., 211 ms for medium-1, 33 ms for medium-3) are caused by JVM timing fluctuations, not algorithmic complexity.

SCC count equals 1 for DAGs and increases for cyclic graphs, as expected.

Shortest and Longest Path algorithms on DAGs show similar performance.
```
## Testing

Unit tests are included under /src/test/java/:
---
SccTest.java – verifies Tarjan’s SCC correctness
---
TopoTest.java – verifies topological order
---
DagSpTest.java – verifies shortest path results on a small DAG
---
To run all tests:
```
mvn test
```
## Project Structure
```
src/
 ├─ main/
 │   ├─ java/com/mansur/
 │   │   ├─ graph/
 │   │   │   ├─ model/ (Graph, Edge)
 │   │   │   ├─ scc/ (TarjanSCC)
 │   │   │   ├─ topo/ (TopoSort)
 │   │   │   └─ dagsp/ (Shortest/Longest)
 │   │   ├─ io/ (JsonGraphLoader)
 │   │   └─ metrics/ (Metrics, SimpleMetrics)
 │   └─ resources/
 ├─ test/
 │   └─ java/com/mansur/graph/ (JUnit tests)
 └─ data/ (9 JSON graph datasets)
```

## 📚 References

Tarjan, R. E. (1972). Depth-first search and linear graph algorithms.
---
Kahn, A. B. (1962). Topological sorting of large networks.
---
CLRS, Introduction to Algorithms, 3rd Edition – Graph chapters.
---
