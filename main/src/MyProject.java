import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Adrian Bedford 22973676
 * @author Siwei Lin 22967534
 */

public class MyProject implements Project {
    /**
     * Breadth-first search
     */ 
    public boolean allDevicesConnected(int[][] adjlist) {
        int length = adjlist.length;
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] results = new boolean[length];
        boolean connected = true;

        for (int i = 0; i < length; i++) {
            results[i] = false;
        }

        q.add(0);
        while (!q.isEmpty()) {
            int current = q.remove();
            results[current] = true;
            for (int i = 0; i < adjlist[current].length ; i++) {
                if (!results[adjlist[current][i]]) {
                    q.add(adjlist[current][i]);
                }
            }   
        }
        
        for (int i = 0; i < length; i++) {
            if (!results[i]) {
                connected = false;
                break;
            }
        }
        
        return connected;
    }

    /**
     * Modified BFS
     */
    public int numPaths(int[][] adjlist, int src, int dst) {
        int length = adjlist.length;
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] checked = new boolean[length];
        int[] distance = new int[length];
        int[] numPaths = new int[length];

        if (src == dst) {
            return 1;
        }

        for (int i = 0; i < length; i++) {
            checked[i] = false;
            numPaths[i] = 1;
            distance[i] = Integer.MAX_VALUE;
        }

        q.add(src);
        checked[src] = true;
        while (!q.isEmpty()) {
            int current = q.remove();

            for (int branch : adjlist[current]) {
            
                // adds unexplored branch to the queue
                if (!checked[branch]) {
                    q.add(branch);
                    checked[branch] = true;
                }

                /*  adds the previous number of shortest paths
                */
                if (distance[branch] > distance[current] + 1) {
                    distance[branch] = distance[current] + 1;
                    numPaths[branch] = numPaths[current];
                }

                /*  branch is on the shortest path 
                *   ie. equals to dist[current] + 1
                */
                else if (distance[branch] == distance[current] + 1) {
                    numPaths[branch] += numPaths[current];
                }

            } 
        }
        /*  did the BFS reach dst node?
        *   if yes then numPaths[dst] will contain the correct value
        */
        if (checked[dst]) {
            return numPaths[dst];
        }
        return 0;
    }


    /**
     * Uses BFS to determine the minium hops required to reach destination given source
     * node and subnet address.
     * @param adjlist decribes how devices are linked based on deviceID
     * @param addrs contains the network address of the device based on deviceID
     * @param src deviceID at source node
     * @param queries array of destination subnet addresses
     * 
     * @return an array of minimum number of hops
     */
    public int[] closestInSubnet(int[][] adjlist, short[][] addrs, int src, short[][] queries) {
        int[] numHops = new int[queries.length];
        
        for (int i = 0; i < queries.length; i ++) {
            short[] query = queries[i];

            numHops[i] = minDist(adjlist, addrs, src, query);
        }

        return numHops;
    }


    /** is the currentAddrs a subnet?
     * @return true of false
     */  
    private boolean isSubnet(short[] currentAddrs, short[] query) {
        boolean addrsIsSubnet = true;
        for (int i = 0; i < query.length; i++) {
            if (query[i] != currentAddrs[i]) {
                addrsIsSubnet = false;
            }
        }
        return addrsIsSubnet;
    }


    /**  returns minimum number of hops to a reachable nodes given src node
     *   if the destination node is not reachable, returns integer max value
     */
    private int minDist(int[][] adjlist, short[][] addrs, int src, short[] query) {
        int deviceID = -1;
        int length = adjlist.length; 
        int[] parent = new int[length];
        int[] dist = new int[length];
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] checked = new boolean[length]; 

        for (int i = 0; i < length; i ++) {
            checked[i] = false;
            dist[i] = 0;
        }

        q.add(src);
        checked[src] = true;
        
        while (!q.isEmpty()) {
            int current = q.remove();

            if (isSubnet(addrs[current], query)) { 
                deviceID = current;
                break;
            }

            for (int branch : adjlist[current]) {
                //add unexplored nodes to queue
                if (!checked[branch]) {
                    q.add(branch);
                    checked[branch] = true;

                    //tracks distance from src
                    parent[branch] = current;
                    dist[branch] = dist[parent[branch]] + 1; 
                }
            }
        }

        if (deviceID != -1) {
            //if destination device is reachable, dist is returned immediately
            return dist[deviceID];
        }
        return Integer.MAX_VALUE; 
    }


    public boolean dstReachable(int flowGraph[][], int src, int dst, int parent[]) {
        int length = flowGraph.length;
        boolean checked[] = new boolean[length];
        Queue<Integer> q = new ArrayDeque<>();

        for (int i = 0; i < length; i ++) {
            checked[i] = false;
        }

        q.add(src); 
        checked[src] = true;
        parent[src] = -1;

        while (!q.isEmpty()) {
            int u = q.remove(); 


            for (int v = 0; v < length; v ++) {

                
                if (checked[v] == false  && flowGraph[u][v] > 0) {
                    //return immediately when dst node is reached
                    if (v == dst) {
                        parent[v] = u;
                        return true;
                    }
                    q.add(v);
                    parent[v] = u;
                    checked[v] = true;
                }
            }
        }
        return checked[dst];
    } 

    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
        int u;
        int v;
        int length = adjlist.length;
        int parent[] = new int[length];
        int[][] flowCap = new int[length][length];
        int max_flow = 0; 

        if (src == dst) {
            return -1;
        }

        //create graph that stores the capacity of flow form vector u to v
        for (u = 0; u < length; u ++) {
            for (v = 0; v < length; v ++) {
                flowCap[u][v] = 0;
            }
        }

        for (u = 0; u < length; u ++) { 
            for (int i = 0; i < adjlist[u].length; i ++) {
                v = adjlist[u][i];
                flowCap[u][v] = speeds[u][i];
            }
        }



        while (dstReachable(flowCap, src, dst, parent)) {
            int path_flow = Integer.MAX_VALUE;

            for (v = dst; v != src; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, flowCap[u][v]);
            }

            //update remaining capacity of the edges
            for (v = dst; v != src; v = parent[v]) {
                u = parent[v]; 
                flowCap[u][v] -= path_flow;
                flowCap[v][u] += path_flow;
            }
            
            max_flow += path_flow;
        }

        
        return max_flow;
    }

}
