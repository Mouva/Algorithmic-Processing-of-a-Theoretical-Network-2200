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
     * Lacks ability to recognise what the present query is, need to add a loop to track this
     */
    public int[] closestInSubnet(int[][] adjlist, short[][] addrs, int src, short[][] queries) {
        int count = 0;
        int length = adjlist.length;
        int[] distances = new int[length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (queries[j] != addrs[j]) {
                    count++;
                } else {
                    distances[queries[j][j]] = count;
                    count = 0;
                    break;
                }
            }
        }
        for (int i = 0; i < length; i++) {
            System.out.println(queries[i][i] + "and" + addrs[i][i]);
            System.out.println(queries[i] + "and" + addrs[i]);
        }  
        return distances;
    }

    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
        // TODO
        return 0;
    }
}
