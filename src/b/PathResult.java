package b;

import java.util.List;

public class PathResult {
    private final int distance;
    private final List<String> path;

    public PathResult(int distance, List<String> path) {
        this.distance = distance;
        this.path = path;
    }

    public int getDistance() { return distance; }
    public List<String> getPath() { return path; }

    public String toJson() {
        return "{\"distance\":" + distance + ",\"path\":" + path.toString().replace(" ", "") + "}";
    }

    @Override
    public String toString() {
        return "Distance: " + distance + ", Path: " + path;
    }
}
