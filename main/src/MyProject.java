import java.sql.ResultSet;
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
     * Another modified BFS idk
     */
    public int[] closestInSubnet(int[][] adjlist, short[][] addrs, int src, short[][] queries) {
        int[] numHops = new int[queries.length];
        int[] minDist = minDist(adjlist, src); //min dist from src to all nodes

        
        for (int i = 0; i < queries.length; i ++) {
            short[] query = queries[i];

            int dstID = dstID(adjlist, addrs, src, query);

            if (dstID == -1) {
                numHops[i] = Integer.MAX_VALUE;
            }
            else {
                numHops[i] = minDist[dstID];
            }

        }

        return numHops;
    }


    /*  is the currentAddrs a subnet?
    *   @returns true of false
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

    /*  is the requested subnet in the network?
    *   if yes, return destination device ID
    */
    private int dstID(int[][] adjlist, short[][] addrs, int src, short[] query) {
        int deviceID = -1;
        int length = adjlist.length;
        Queue<Integer> q = new ArrayDeque<>(); 
        boolean[] checked = new boolean[length];

        q.add(src);
        
        while (!q.isEmpty()) {
            int current = q.remove(); 

            for (int branch : adjlist[current]) {
                if (!checked[branch]) {
                    q.add(branch);
                    checked[current] = true;                    
                }
                
                if (isSubnet(addrs[current], query)) { 
                    deviceID = current;
                    break;
                }
            }
            if (deviceID != -1) break;
        }


        return deviceID;
    }


    /*  returns minimum number of hops to all reachable nodes given src node
    */
    private int[] minDist(int[][] adjlist, int src) {
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

            for (int branch : adjlist[current]) {
                //add unexplored nodes to queue
                if (!checked[branch]) {
                    q.add(branch);
                    checked[branch] = true;
                    parent[branch] = current;
                    dist[branch] = dist[parent[branch]] + 1; 
                }
            }
        }
        for (int i = 0; i < dist.length;  i++) {
            System.out.println(dist[i]);
        }
        return dist; 
    }


    /**
     * BFS, finds all paths to dst node then relates paths to speeds
     */
    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
        Queue<Integer> q = new ArrayDeque<>();
        int[] paths = new int[adjlist.length];
        
        if (src == dst) {
            return -1;
        } else {
            q.add(src);
            while (!q.isEmpty()) {
                int i = 0;
            }
        }

        int maxDL = speed(adjlist, speeds, paths);

        return maxDL;
    }

    private int speed(int[][] adjlist, int[][] speeds, int[] paths){
        int maxDL = 0;

        for (int node : paths) {
            int i = 0;
        }
        return maxDL;
    }
}
