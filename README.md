# TrackMint

## Elevator Pitch
TrackMint is a lightweight Java application demonstrating graph-based route optimization using Dijkstra's algorithm. It models a network of nodes (e.g., cities) with weighted edges (e.g., distances) and computes the shortest paths from a source node, outputting both distances and step-by-step routes. Perfect for learning DSA concepts or as a foundation for more advanced pathfinding tools.

## Features
- Graph representation with nodes and weighted edges.
- Dijkstra's shortest-path algorithm implementation using a priority queue.
- CLI demo with a sample graph.
- Programmatic addition of nodes and edges.
- Simple HTTP backend for REST API access (using built-in Java HttpServer).
- No external dependencies; runs on Java 11+.

## Technical Explanation
Dijkstra's algorithm finds the shortest path from a source node to all other nodes in a weighted graph with non-negative weights. It uses a priority queue to always expand the node with the smallest known distance. In TrackMint:
- Distances are initialized to infinity except for the source (0).
- A priority queue selects the next node to process.
- For each neighbor, update distances if a shorter path is found.
- Paths are reconstructed using a "previous" map.
This ensures O((V+E) log V) time complexity with a binary heap priority queue.

## Usage
### CLI Mode
1. Compile: `javac -d build src/b/*.java`
2. Run: `java -cp build b.Main`

### Backend Mode
1. Compile: `javac -d build src/b/*.java`
2. Run: `java -cp build b.BackendServer`

The backend starts a server on port 8080. Use tools like curl or Postman to interact.

#### API Endpoints
- `GET /graph`: Returns the current graph as JSON.
- `POST /addEdge?from=A&to=B&weight=4`: Adds an edge.
- `GET /shortestPath?from=A&to=D`: Returns shortest path from A to D as JSON.

Example curl: `curl "http://localhost:8080/shortestPath?from=A&to=D"`

### Example Graph
- Nodes: A, B, C, D
- Edges: A->B(4), A->C(2), B->C(5), B->D(10), C->D(3)
