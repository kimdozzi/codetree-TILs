import java.util.*;
public class Main {
    public static int n;
    public static int[][] segments;
    public static long MIN_N = 1_600_000_000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 초기화 
        n = sc.nextInt();
        segments = new int[n][2];
        for(int i=0; i<n; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            segments[i][0] = a;
            segments[i][1] = b;
        }

        for(int i=0; i<n; i++) {
            int lx = 40000, rx = 0, uy = 0, dy = 40000;
            for(int j=0; j<n; j++) {
                if(i==j) continue;

                int x = segments[j][0];
                int y = segments[j][1];

                lx = Math.min(lx, x);
                rx = Math.max(rx, x);
                uy = Math.max(uy, y);
                dy = Math.min(dy, y);
            }
            int size = (rx-lx) * (uy-dy);
            MIN_N = Math.min(MIN_N, size);
        }

        System.out.println(MIN_N);
    }
}