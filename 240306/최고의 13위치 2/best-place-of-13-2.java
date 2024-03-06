import java.util.*;
import java.io.*;
public class Main {
    static int n;
    static int[][] board;
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
                for (int a=0; a<n; a++) {
                    for (int b=0; b<n-2; b++) {
                        if (i==a && Math.abs(j-b) <= 2) continue;

                        int cnt1 = board[i][j] + board[i][j+1] + board[i][j+2];
                        int cnt2 = board[a][b] + board[a][b+1] + board[a][b+2];
                        answer = Math.max(answer, cnt1+cnt2);
                    }   
                }
            }
        }
    }

    static void init() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        board = new int[n][n];
      
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