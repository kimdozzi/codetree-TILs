import java.util.*;
import java.io.*;
public class Main {
    static int n;
    static int[][] board;
    static boolean[][] visited;
    static int answer;
    public static void main(String[] args) throws IOException {
        // 여기에 코드를 작성해주세요.
        init();

        solve();

        System.out.println(answer);
    }

    static void solve() {
        for (int i=0; i<n; i++) {
            for (int j=0; j<n-2; j++) {
                int cnt = 0;
                for (int k=j; k<j+3; k++) { // 1*3 크기만큼 동전 저장
                    visited[i][k] = true; // 방문표시
                    if (board[i][k]>0) cnt++;
                }

                for (int a=0; a<n; a++) {
                    int ccnt = 0;
                    for (int b=0; b<n-2; b++) {
                        if (visited[a][b]) continue;
                        if (i==a && Math.abs(j-b)<=2) continue;
                        if (board[a][b] > 0) ccnt++;
                    }
                    answer = Math.max(answer, cnt+ccnt);
                }

                for (int k=j; k<j+3; k++) {
                    visited[i][k] = false; // 방문표시 해제
                }
            }
        }
    }

    static void init() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        board = new int[n][n];
        visited = new boolean[n][n];
        for (int i=0; i<n; i++) {
            Arrays.fill(visited[i], false);
        }

        StringTokenizer st;
        for (int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        answer = 0;
    }
}