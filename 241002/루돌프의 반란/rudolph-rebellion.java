import java.util.*;
import java.io.*;

public class Main {
    static class Santa implements Comparable<Santa>{ // 산타 
        int number; // 번호 
        int r;
        int c;
        int score;
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

        @Override
        public int compareTo(Santa o1) {
            if (this.r == o1.r) {
                return o1.c - this.c;
            } else {
                return o1.r - this.r;
            }
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
    static int[][] dirs = {{-1,-1}, {-1,1}, {1,-1}, {1,1}, {-1,0}, {1,0}, {0,-1}, {0,1}}; // 상 하 좌 우 좌상 우상 좌하 우하 

    // static int[] santaScore; 
    static int[][] board; // 게임판
    static Queue<Santa> santaQueue; // 산타들 정보  
    static Loo loo = null;

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
        int looX = Integer.parseInt(st.nextToken())-1;
        int looY = Integer.parseInt(st.nextToken())-1;
        loo = new Loo(looX,looY);

        // 산타 리스트 초기화
        // santaScore = new int[N];
        santaQueue = new PriorityQueue<>();
        for(int i=0; i<P; i++) {
            st = new StringTokenizer(br.readLine());
            int pn = Integer.parseInt(st.nextToken());
            int px = Integer.parseInt(st.nextToken())-1;
            int py = Integer.parseInt(st.nextToken())-1;

            santaQueue.add(new Santa(pn, px, py));
        }

        // santa는 r이 큰 순, c가 큰 순서대로 저장되어 있음. 
        Iterator<Santa> iter = santaQueue.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next().number);
        }


        // M번의 턴 
        for(int tc=1; tc<=M; tc++) {
            // 루돌프 이동
            looMove();
            System.out.println();

            // 루돌프와 산타 충돌 여부 확인 
            //boom(santaPosition);

            // 산타 이동 
            santaMove();

            // 만약 p명의 산타가 모두 게임에서 탈락한다면 즉시 종료.
            gameEndStatus();

            // 매 턴 이후 아직 탈락하지 않은 산타들에게 +1점씩 부여.
            addScore();

        }
    }

    private static void looMove() {
        // 1. 가장 가까운 산타 선정. 
        // 거리 계산 방법: 두 칸 사이의 거리 (r1-r2)^2 + (c1-c2)^2
        int r1 = loo.r;
        int c1 = loo.c;
        int sr2 = -1;
        int sc2 = -1;
        int shortDist = Integer.MAX_VALUE;
        int shortDistSantaNumber = -1;
        
        Iterator<Santa> iter = santaQueue.iterator();
        while(iter.hasNext()) {
            Santa santa = iter.next();
            int santaNumber = santa.number;
            int r2 = santa.r;
            int c2 = santa.c;
            
            // 격자밖에 나간 산타일 경우 
            if (!inRange(r2,c2)) {
                continue;
            }

            int resultDist = calculateDist(r1,c1,r2,c2);
            System.out.println(santaNumber + " : " + resultDist);
            if (resultDist < shortDist) {
                shortDistSantaNumber = santaNumber; // 산타 번호 (1번부터~)
                shortDist = resultDist;
                sr2 = r2;
                sc2 = c2;
            }
        }
        System.out.println("산타번호 :" + shortDistSantaNumber + " 거리 : " + shortDist);

        // 가장 가까운 산타를 향해 8방향 중 하나로 돌진. 
        for(int d = 0; d<8; d++) {
            int nr1 = r1 + dirs[d][0];
            int nc1 = c1 + dirs[d][1];
            int resultDist = calculateDist(nr1, nc1, sr2, sc2);
            if (resultDist < shortDist) {
                loo.r = nr1;
                loo.c = nc1;
                loo.moveInfo[0] = dirs[d][0];
                loo.moveInfo[1] = dirs[d][1];
                break;
            }
        }
        // 이동한 루돌프 위치 
        System.out.println(loo.r + " " + loo.c + " : d : " + loo.moveInfo[0] + " " + loo.moveInfo[1]);


        // 루돌프가 이동한 위치에 산타가 존재한다면 충돌
        if (loo.r == sr2 && loo.c == sc2) {
            // 기절 시키기 

            // 연쇄 반응
            chain();
        }
    }
    
    private static void chain() {
        
    }

    // 거리 계산.
    private static int calculateDist(int r1, int c1, int r2, int c2) {
        return (int)Math.pow(r1-r2,2) + (int)Math.pow(c1-c2,2);
    }

    // 격자 내 존재 여부 확인.
    private static boolean inRange(int x, int y) {
        return 0 <= x && x < N && 0 <= y && y < N;
    }

    private static void santaMove() {

    }

    private static void gameEndStatus() {

    }

    private static void addScore() {

    }
}