package test;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Created by 972536780 on 2017/8/5.
 */
class Node {
    int a, b;
}

public class Main {
    static int n, m;
    static Node[] A = new Node[100005];
    static Node[] B = new Node[1005];
    static int[][] dp = new int[1005][15];

    public static void init(int max) {
        for (int i = 0; i <= max; i++) {
            for (int j = 0; j < dp[i].length; j++) dp[i][j] = 1 << 30;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner (new BufferedInputStream(System.in));
        for (int i = 0; i < 100000; i++) A[i] = new Node();
        for (int i = 0; i < 1000; i++) B[i] = new Node();
        while (in.hasNext()) {
            int sum = 0, max = 0;
            n = in.nextInt();
            m = in.nextInt();

            for (int i = 0; i < n; i++) {
                A[i].a = in.nextInt();
                A[i].b = in.nextInt();
                max = Math.max(max, A[i].a);
            }
            for (int i = 0; i < m; i++) {
                B[i].a = in.nextInt();
                B[i].b = in.nextInt();
            }
            init(max);
            for (int i = 0; i <= 10; i++) dp[0][i] = 0;
            for (int i = 0; i <= max; i++) {
                for (int j = 0; j <= 10; j++) {
                    for (int k = 0; k < m; k++) {
                        int temp = B[k].b - j;
                        if (temp > 0 && i >= temp) {
                            dp[i][j] = Math.min(dp[i][j], dp[i - temp][j] + B[k].a);
                        }
                    }
                }
            }
            for (int i = 0; i < n; i++) sum += dp[A[i].a][A[i].b];
            if (sum >= 1 << 30) System.out.println(-1);
            else System.out.println(sum);
        }
        in.close();
    }
}
