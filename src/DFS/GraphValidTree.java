package DFS;

import java.util.*;

public class GraphValidTree {

    public boolean validTree(int n, int[][] edges) {
        if (edges == null ){
            return false;
        }
        if (edges.length != n-1 ){ //no enough edge for nodes
            return false;
        }

        //build adjacency list
        Map<Integer, List<Integer>> out = new HashMap<>();
        for (int i=0; i<n; i++){
            out.put(i, new ArrayList<Integer>());
        }
        for (int i=0; i < edges.length; i++){
            out.get(edges[i][0]).add(edges[i][1]);
            out.get(edges[i][1]).add(edges[i][0]); //since undirected graph, need to add to both directions
        }

        //Run DFS using out map, randomly start from node 0
        boolean[] visited = new boolean[n];
        visited[0] = true;
        if (!validTreeHelper(out, 0, -1, visited)){ //if cycle, false
            return false;
        }
        for (int i=0; i<n; i++){ //if found unconnected component, false
            if (!visited[i]){
                return false;
            }
        }
        return true;
    }

    private boolean validTreeHelper(Map<Integer, List<Integer>>map, int root, int parent, boolean[] visited){

        for (Integer nei : map.get(root)){
            if (visited[nei]){
                if( nei != parent){ //found a cycle
                    return false;
                }
            }else {
                visited[nei] = true;
                if (!validTreeHelper(map, nei, root, visited)){
                    return false;
                }
            }
        }
        return true;
    }
}

//clarification: graph valid tree
//M1:
//A tree is consider valid if there's no cycle in it + connected -> each child can only have one parent that generates it -> there shouldn't be another root that generates it that is not its parent -> need to pass parent node to its child's recursion function as input
//Since we have undirected edge, we can start from any node to run a BFS?

//1.build adjacency list for out map
//2.run BFS1 using out map, use boolean[]visited to dedupe
//3.If current has a neighbor that has been visited but is not its parent, means a cycle, return false

//TC:O(V+E) for traverse all nodes and their edges
//SC:O(V) for visited and queue


//M2: Better
//for no-cycle, it must have edges count == n-1, no more, no less. So can just use this to determine whether it has cycle.
//Then use BFS to check whether it is fully connected

//TC:O(V+E) for traverse all nodes and edges
//SC:O(V) for visited