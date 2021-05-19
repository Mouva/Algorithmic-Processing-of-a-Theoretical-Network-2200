import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Adrian Bedford 22973676
 * @author Siwei Lin 22967534
 */

public class MyProject implements Project {
    public boolean allDevicesConnected(int[][] adjlist) {
        Queue<Integer> q = new ArrayDeque<>(0);
        int length = adjlist.length;
        boolean[] results = new boolean[length];
        boolean connected = false;

        for (int i = 0; i < length; i++) {
            results[i] = false;
        }

        while (!q.isEmpty()) {
            int current = q.remove();
            results[current] = true;
            for (int i = 0; i < length; i++) {
                if (!results[adjlist[current][i]]) {
                    q.add(adjlist[current][i]);
            }
        }
        return connected;
    }
}

    public int numPaths(int[][] adjlist, int src, int dst) {
        // TODO
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
