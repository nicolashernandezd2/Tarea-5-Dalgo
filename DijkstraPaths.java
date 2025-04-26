// PUNTO NUMERO 3 DE LA TAREA
//Basado en Dijkstra y Bellman Ford del repositorio del curso

import java.util.ArrayList;

public class DijkstraPaths {

    private static class Edge {

        private int source;
        private int target;
        private int weight;

        public Edge(int source, int target, int weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

    }

    private static class DiGraph {

        private int order;
        private Edge[] edges;

        public DiGraph(Edge[] edges, int order) {
            this.order = order;
            this.edges = edges;
        }

    }

    public static ArrayList<Integer>[] pathsDijkstra(DiGraph gg, int source) {
        int n = gg.order;
        int[] distances = new int[n];
        ArrayList<Integer>[] paths = new ArrayList[n];
        fillArray(distances, Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            paths[i] = new ArrayList<>();
        }
        distances[source] = 0;
        paths[source].add(source); // The path to the source is just the source itself.
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            // Find closest node
            int u = -1;
            int minDist = Integer.MAX_VALUE;
            for (int v = 0; v < n; v++) {
                if (!visited[v] && distances[v] < minDist) {
                    minDist = distances[v];
                    u = v;
                }
            }

            visited[u] = true;

            if (u == -1) {
                break;
            }

            // Relax the edges
            for (Edge edge : gg.edges) {
                if (edge.source == u) {
                    int v = edge.target;
                    int weight = edge.weight;
                    int newDist = distances[u] + weight;
                    if (newDist < distances[v]) {
                        distances[v] = newDist;
                        // Update the path
                        paths[v].clear();
                        paths[v].addAll(paths[u]);
                        paths[v].add(v);
                    }
                }
            }
        }

        return paths;
    }


    public static ArrayList<Integer>[] pathsBellmanFord(DiGraph gg, int source) {
        int n = gg.order;
        int[] distances = new int[n];
        ArrayList<Integer>[] paths = new ArrayList[n];
        fillArray(distances, Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            paths[i] = new ArrayList<>();
        }
        distances[source] = 0;
        paths[source].add(source);

        // Relax edges (V-1) times
        for (int i = 1; i < n; i++) {
            for (Edge edge : gg.edges) {
                int u = edge.source;
                int v = edge.target;
                int weight = edge.weight;
                int newDist = distances[u] + weight;
                if (distances[u] != Integer.MAX_VALUE && newDist < distances[v]) {
                    distances[v] = newDist;
                    paths[v].clear();
                    paths[v].addAll(paths[u]);
                    paths[v].add(v);
                }
            }
        }

        // Check for negative weight cycles
        for (Edge edge : gg.edges) {
            int u = edge.source;
            int v = edge.target;
            int weight = edge.weight;
            int newDist = distances[u] + weight;
            if (distances[u] != Integer.MAX_VALUE && newDist < distances[v]) {
                System.out.println("Graph contains a negative weight cycle.");
                return null;  // Return null if a negative cycle is detected
            }
        }

        return paths;
    }

   
    public static void fillArray(int[] aa, int value) {
        for (int i = 0; i < aa.length; i++) {
            aa[i] = value;
        }
    }


}