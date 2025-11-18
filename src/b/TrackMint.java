package b;

import java.util.*;

public class TrackMint {
    private final Graph graph;

    public TrackMint(Graph graph) {
        this.graph = graph;
    }

    public Map<String, PathResult> computeShortestPaths(String source) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (String node : graph.getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        pq.add(source);

        while (!pq.isEmpty()) {
            String current = pq.poll();
            int currentDist = distances.get(current);

            for (Edge edge : graph.getEdges(current)) {
                String neighbor = edge.getTo();
                int newDist = currentDist + edge.getWeight();
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, current);
                    pq.add(neighbor);
                }
            }
        }

        Map<String, PathResult> results = new HashMap<>();
        for (String node : graph.getNodes()) {
            if (distances.get(node) == Integer.MAX_VALUE) continue; 
            List<String> path = reconstructPath(previous, source, node);
            results.put(node, new PathResult(distances.get(node), path));
        }
        return results;
    }

    public PathResult computeShortestPath(String source, String target) {
        Map<String, PathResult> all = computeShortestPaths(source);
        return all.get(target);
    }

    private List<String> reconstructPath(Map<String, String> previous, String source, String target) {
        List<String> path = new ArrayList<>();
        String current = target;
        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }
        if (!path.get(0).equals(source)) return Collections.emptyList();
        return path;
    }
}
