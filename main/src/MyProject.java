import java.util.ArrayDeque;
import java.util.Queue;
import java.util.*;
import java.io.*;

/**
 * @author Adrian Bedford 22973676
 * @author Siwei Lin 22967534
 */

public class MyProject implements Project {
    /**
     * Breadth-first search
     * Change for loops to iterate over elements, maybe improves quality
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
     * Algorithm used?
     */
    public int numPaths(int[][] adjlist, int src, int dst) {
        int length = adjlist.length;
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] results = new boolean[length];
        int[] dist = new int[length];
        int[] path = new int[length];

        for (int i = 0; i < length; i++) {
            results[i] = false;
        }
        dist[src] = 0;
        path[src] = 1;

        q.add(src);
        results[src] = true;
        while (!q.isEmpty()) {
            int current = q.remove();
            results[current] = true;

            for (int x : adjlist[current]) {
                if (!results[x]) {
                    q.add(x);
                    results[x] = true;
                }
                if (dist[x] > dist[current] + 1) {
                    dist[x] = dist[current] + 1;
                    path[x] = path[current];
                }
                else if (dist[x] == dist[current]) {
                    path[x] += path[current];
                }
            }   
        }

        return 0;
    }

    public int[] closestInSubnet(int[][] adjlist, short[][] addrs, int src, short[][] queries) {
        // TODO
        return null;
    }

    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
        // TODO
        return 0;
    }
}
