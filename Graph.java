// PUNTO NUMERO 2 DE LA TAREA

import java.util.*;

public class Graph {
    private final int V;
    private final boolean directed;
    private final List<List<Integer>> adj; //las listas de adyacencia de cada vértice

    /*
      Crea un grafo no dirigido con V vértices (0..V-1)
     */
    public Graph(int V) {
        this(V, false);
    }

    /*
      Crea un grafo con V vértices. Si directed==true, es dirigido; 
      si no, es no dirigido.
     */
    public Graph(int V, boolean directed) {
        this.V = V;
        this.directed = directed;
        this.adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    /**
     * Añade una arista u→v. Si el grafo es no dirigido, también añade v→u.
     */
    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        if (!directed) {
            adj.get(v).add(u);
        }
    }

    
    public boolean hasCyclesDFS() {
        boolean[] visited = new boolean[V];
        Deque<int[]> stack = new ArrayDeque<>();
        // cada elemento es [vértice, padre]
        stack.push(new int[]{0, -1});

        while (!stack.isEmpty()) {
            int[] cur = stack.pop();
            int u = cur[0], parent = cur[1];
            if (!visited[u]) {
                visited[u] = true;
                for (int v : adj.get(u)) {
                    if (!visited[v]) {
                        stack.push(new int[]{v, u});
                    } else if (v != parent) {
                        // encontramos un vecino ya visitado que no es el padre
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean hasCyclesBFS() {
        boolean[] visited = new boolean[V];
        int[] parent = new int[V];
        Arrays.fill(parent, -1);

        Queue<Integer> q = new LinkedList<>();
        visited[0] = true;
        q.add(0);

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : adj.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    parent[v] = u;
                    q.add(v);
                } else if (parent[u] != v) {
                    // vecino visitado y no es el padre → ciclo
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasDirCyclesDFS() {
        boolean[] visited = new boolean[V];
        boolean[] inStack = new boolean[V];

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                if (dfsCycleDir(i, visited, inStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsCycleDir(int u, boolean[] visited, boolean[] inStack) {
        visited[u] = true;
        inStack[u] = true;
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                if (dfsCycleDir(v, visited, inStack)) {
                    return true;
                }
            } else if (inStack[v]) {
                // regreso a un vértice aún en la pila de recursión
                return true;
            }
        }
        inStack[u] = false;
        return false;
    }

    
    public boolean hasDirCyclesBFS() {
        int[] indegree = new int[V];
        for (int u = 0; u < V; u++) {
            for (int v : adj.get(u)) {
                indegree[v]++;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                q.add(i);
            }
        }

        int visitedCount = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            visitedCount++;
            for (int v : adj.get(u)) {
                if (--indegree[v] == 0) {
                    q.add(v);
                }
            }
        }
        // si no procesamos todos los vértices, hay ciclo
        return visitedCount != V;
    }

    public int connectedComponents() {
        boolean[] visited = new boolean[V];
        int count = 0;

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                count++;
                // BFS para marcar toda la componente
                Queue<Integer> q = new LinkedList<>();
                visited[i] = true;
                q.add(i);
                while (!q.isEmpty()) {
                    int u = q.poll();
                    for (int v : adj.get(u)) {
                        if (!visited[v]) {
                            visited[v] = true;
                            q.add(v);
                        }
                    }
                }
            }
        }
        return count;
    }

}