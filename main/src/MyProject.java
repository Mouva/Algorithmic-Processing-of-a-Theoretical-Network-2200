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
        
        for (int i = 0; i < queries.length; i ++) {
            short[] query = queries[i];
            numHops[i] = numHops(adjlist, addrs, src, query);
        }

        return numHops;
    }
    

    /*  is the currentAddrs a subnet?
    *   @returns true of false
    */  
    private boolean inSubnet(short[] currentAddrs, short[] query) {
        boolean addrsInSubnet = true;
        for (int i = 0; i < query.length; i++) {
            if (query[i] != currentAddrs[i]) {
                addrsInSubnet = false;
            }
        }
        return addrsInSubnet;
    }

    /*  assuming the requested subnet is part of the network
    *   run this piece of code to get numHops
    */
    private int numHops(int[][] adjlist, short[][] addrs, int src, short[] query) {
        int length = adjlist.length; 
        int numHops = -1;
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] checked = new boolean[length]; 
        boolean foundSubnet = false;

        for (int i = 0; i < length; i ++) {
            checked[i] = false;
        }

        q.add(src);
        checked[src] = true;
        while (!q.isEmpty()) {
            int current = q.remove();
            numHops ++;
            for (int branch : adjlist[current]) {
                //add unexplored nodes to queue
                if (!checked[branch]) {
                    q.add(branch);
                    checked[branch] = true;
                }

                if (inSubnet(addrs[current], query)) {
                    foundSubnet = true;
                    break;
                }
            }
        }

        if (!foundSubnet) {
            return Integer.MAX_VALUE; 
        }
        return numHops;
    }


    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
        Queue<Integer> q = new ArrayDeque<>();
        
        if (src == dst) {
            return -1;
        } else {
            
        }
        return 0;
    }
}
