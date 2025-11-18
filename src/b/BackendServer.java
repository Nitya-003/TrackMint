package b;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Map;

public class BackendServer {
    private static Graph graph = new Graph();
    private static TrackMint planner = new TrackMint(graph);

    static {
        graph.addEdge("A", "B", 4);
        graph.addEdge("A", "C", 2);
        graph.addEdge("B", "C", 5);
        graph.addEdge("B", "D", 10);
        graph.addEdge("C", "D", 3);
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/graph", new GraphHandler());
        server.createContext("/addEdge", new AddEdgeHandler());
        server.createContext("/shortestPath", new ShortestPathHandler());
        server.setExecutor(null); 
        server.start();
        System.out.println("Server started on port 8080");
    }

    static class GraphHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = graph.toJson();
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    static class AddEdgeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                URI uri = exchange.getRequestURI();
                Map<String, String> params = parseQuery(uri.getQuery());
                String from = params.get("from");
                String to = params.get("to");
                String weightStr = params.get("weight");
                if (from != null && to != null && weightStr != null) {
                    try {
                        int weight = Integer.parseInt(weightStr);
                        graph.addEdge(from, to, weight);
                        String response = "{\"status\":\"Edge added\"}";
                        exchange.getResponseHeaders().set("Content-Type", "application/json");
                        exchange.sendResponseHeaders(200, response.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    } catch (NumberFormatException e) {
                        exchange.sendResponseHeaders(400, -1); 
                    }
                } else {
                    exchange.sendResponseHeaders(400, -1); 
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    static class ShortestPathHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                URI uri = exchange.getRequestURI();
                Map<String, String> params = parseQuery(uri.getQuery());
                String from = params.get("from");
                String to = params.get("to");
                if (from != null && to != null) {
                    PathResult result = planner.computeShortestPath(from, to);
                    String response = result != null ? result.toJson() : "{\"error\":\"No path found\"}";
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } else {
                    exchange.sendResponseHeaders(400, -1); 
                }
            } else {
                exchange.sendResponseHeaders(405, -1); 
            }
        }
    }

    private static Map<String, String> parseQuery(String query) {
        Map<String, String> params = new java.util.HashMap<>();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                if (pair.length == 2) {
                    params.put(pair[0], pair[1]);
                }
            }
        }
        return params;
    }
}
