import java.util.*;
import java.io.*;

public class Main {
    static class Santa { // 산타 
        int number; // 번호 
        int r;
        int c;
        int score; // 점수 
        int status; // 기절 정보 
        int[] moveInfo; // 이동한 방향 정보 

        Santa(int number, int r, int c) {
            this.number = number;
            this.r = r;
            this.c = c;
            this.score = 0;
            this.status = -1;
            moveInfo = new int[2];
        }
    }
    static class Loo { // 루돌프
            int r;
            int c;
            int[] moveInfo; // 이동한 방향 정보 

            Loo(int r, int c) {
                this.r = r;
                this.c = c;
                moveInfo = new int[2];
            }
        }


    static int N; // 격자 크기
    static int M; // 게임 턴 수
    static int P; // 산타의 수 
    static int C; // 루돌프의 힘
    static int D; // 산타의 힘 
    static int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1},
                            {-1,-1}, {-1,1}, {1,-1}, {1,1}}; // 상 하 좌 우 좌상 우상 좌하 우하 

    static int[][] board; // 게임판
    static Santa[] sandaList; // 산타들 정보  

    public static void main(String[] args) throws IOException {
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        // 루돌프 초기 위치
        st = new StringTokenizer(br.readLine());
        int looX = Integer.parseInt(st.nextToken());
        int looY = Integer.parseInt(st.nextToken());
        Loo loo = new Loo(looX,looY);

        // 산타 리스트 초기화
        sandaList = new Santa[P];
        for(int i=0; i<P; i++) {
            st = new StringTokenizer(br.readLine());
            int pn = Integer.parseInt(st.nextToken());
            int px = Integer.parseInt(st.nextToken());
            int py = Integer.parseInt(st.nextToken());

            sandaList[i] = new Santa(pn, px, py);
        }
    }
}