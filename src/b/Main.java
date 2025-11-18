package b;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge("A", "B", 4);
        graph.addEdge("A", "C", 2);
        graph.addEdge("B", "C", 5);
        graph.addEdge("B", "D", 10);
        graph.addEdge("C", "D", 3);

        TrackMint planner = new TrackMint(graph);
        Map<String, PathResult> results = planner.computeShortestPaths("A");

        System.out.println("Shortest paths from A:");
        for (Map.Entry<String, PathResult> entry : results.entrySet()) {
            System.out.println("To " + entry.getKey() + ": " + entry.getValue());
        }
    }
}
