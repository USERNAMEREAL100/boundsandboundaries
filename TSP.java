import java.util.Arrays;

public class TSP {

    public static void main(String[] args) {

        int[][] adj = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };

        tsp(adj);
        System.out.printf("Максимальна вартість: %d%n", finalRes);
        System.out.print("Обраний маршрут: ");
        for (int i = 0; i <= N; i++) {
            System.out.printf("%d ", finalPath[i]);
        }
    }

    static final int N = 4;
    static int[] finalPath = new int[N + 1];
    static boolean[] visited = new boolean[N];
    static int finalRes = Integer.MIN_VALUE;

    static void tsp(int[][] adj) {
        int[] currPath = new int[N + 1];
        int currBound = 0;
        Arrays.fill(finalPath, -1);
        Arrays.fill(visited, false);

        for (int i = 0; i < N; i++) {
            currBound += (firstMax(adj, i) + secondMax(adj, i));
        }
        currBound = (currBound % 2 == 1) ? currBound / 2 + 1 : currBound / 2;

        visited[0] = true;
        currPath[0] = 0;

        tspRec(adj, currBound, 0, 1, currPath);
    }

    static void tspRec(int[][] adj, int currBound, int currWeight, int level, int[] currPath) {

        if (level == N) {
            if (adj[currPath[level - 1]][currPath[0]] != 0) {
                int curRes = currWeight + adj[currPath[level - 1]][currPath[0]];

                if (curRes > finalRes) {
                    copyToFinal(currPath);
                    finalRes = curRes;
                }
            }
            return;
        }

        for (int i = 0; i < N; i++) {

            if (adj[currPath[level - 1]][i] != 0 && !visited[i]) {
                int tempBound = currBound;
                int tempWeight = currWeight;

                currWeight += adj[currPath[level - 1]][i];

                if (level == 1) {
                    currBound -= ((firstMax(adj, currPath[level - 1]) + firstMax(adj, i)) / 2);
                } else {
                    currBound -= ((secondMax(adj, currPath[level - 1]) + firstMax(adj, i)) / 2);
                }

                if (currBound + currWeight > finalRes) {
                    currPath[level] = i;
                    visited[i] = true;

                    tspRec(adj, currBound, currWeight, level + 1, currPath);
                }

                currWeight = tempWeight;
                currBound = tempBound;

                Arrays.fill(visited, false);
                for (int j = 0; j <= level - 1; j++) {
                    visited[currPath[j]] = true;
                }
            }
        }
    }

    static int firstMax(int[][] adj, int i) {
        int max = Integer.MIN_VALUE;
        for (int k = 0; k < N; k++) {
            if (adj[i][k] > max && i != k) {
                max = adj[i][k];
            }
        }
        return max;
    }

    static int secondMax(int[][] adj, int i) {
        int first = Integer.MIN_VALUE;
        int second = Integer.MIN_VALUE;
        for (int j = 0; j < N; j++) {
            if (i == j) continue;

            if (adj[i][j] >= first) {
                second = first;
                first = adj[i][j];
            } else if (adj[i][j] >= second && adj[i][j] != first) {
                second = adj[i][j];
            }
        }
        return second;
    }

    static void copyToFinal(int[] currPath) {
        for (int i = 0; i < N; i++) {
            finalPath[i] = currPath[i];
        }
        finalPath[N] = currPath[0];
    }
}