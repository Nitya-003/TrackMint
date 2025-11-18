package b;

import java.util.*;

public class Graph {
    private final Map<String, List<Edge>> adjacencyList = new HashMap<>();

    public void addNode(String node) {
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(String from, String to, int weight) {
        addNode(from);
        addNode(to);
        adjacencyList.get(from).add(new Edge(from, to, weight));
    }

    public List<Edge> getEdges(String node) {
        return adjacencyList.getOrDefault(node, Collections.emptyList());
    }

    public Set<String> getNodes() {
        return adjacencyList.keySet();
    }

    public String toJson() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (String node : adjacencyList.keySet()) {
            if (!first) sb.append(",");
            sb.append("\"").append(node).append("\":[");
            List<Edge> edges = adjacencyList.get(node);
            for (int i = 0; i < edges.size(); i++) {
                if (i > 0) sb.append(",");
                Edge e = edges.get(i);
                sb.append("{\"to\":\"").append(e.getTo()).append("\",\"weight\":").append(e.getWeight()).append("}");
            }
            sb.append("]");
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String node : adjacencyList.keySet()) {
            sb.append(node).append(": ").append(adjacencyList.get(node)).append("\n");
        }
        return sb.toString();
    }
}
