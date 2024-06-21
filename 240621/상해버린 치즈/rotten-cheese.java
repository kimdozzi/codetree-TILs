import java.util.Scanner;

// 치즈를 먹은 정보를 나타내는 클래스 선언
class Info1{
    int p, m, t;

    public Info1(int p, int m, int t) {
        this.p = p;
        this.m = m;
        this.t = t;
    }
};

// 치즈를 먹은 정보를 나타내는 클래스 선언
class Info2{
    int p, t;

    public Info2(int p, int t) {
        this.p = p;
        this.t = t;
    }
};

public class Main {
    public static final int MAX_N = 50;
    public static final int MAX_D = 1000;
    
    public static int n, m, d, s;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 입력
        n = sc.nextInt();
        m = sc.nextInt();
        d = sc.nextInt();
        s = sc.nextInt();

        Info1[] info1 = new Info1[MAX_D];
        Info2[] info2 = new Info2[MAX_N];

        for(int i = 0; i < d; i++) {
            int p = sc.nextInt();
            int m = sc.nextInt();
            int t = sc.nextInt();
            info1[i] = new Info1(p, m, t);
        }
        
        for(int i = 0; i < s; i++) {
            int p = sc.nextInt();
            int t = sc.nextInt();
            info2[i] = new Info2(p, t);
        }

        int ans = 0;
        
        // 하나의 치즈가 상했을 때 필요한 약의 수의 최댓값을 구합니다.
        for(int i = 1; i <= m; i++) {
            // i번째 치즈가 상했을 때 필요한 약의 수를 구합니다.

            // 우선 i번째 치즈가 상했다고 가정할 때 모순이 발생하지 않는지 확인합니다.
            // time배열을 만들어 각 사람이 언제 치즈를 먹었는지 저장합니다.
            int[] time = new int[MAX_N + 1];
            for(int j = 0; j < d; j++) {
                // i번째 치즈에 대한 정보가 아닌 경우 넘어갑니다.
                if(info1[j].m != i)
                    continue;

                // person이 i번째 치즈를 처음 먹었거나
                // 이전보다 더 빨리 먹게 된 경우 time배열을 갱신합니다.
                int person = info1[j].p;
                if(time[person] == 0)
                    time[person] = info1[j].t;
                else if(time[person] > info1[j].t)
                    time[person] = info1[j].t;
            }

            // possible : i번째 치즈가 상했을 수 있으면 true, 아니면 false
            boolean possible = true;

            for(int j = 0; j < s; j++) {
                // person이 i번째 치즈를 먹지 않았거나
                // i번째 치즈를 먹은 시간보다 먼저 아픈 경우 모순이 생깁니다.
                int person = info2[j].p;
                if(time[person] == 0)
                    possible = false;
                if(time[person] >= info2[j].t)
                    possible = false;
            }

            // 만약 i번째 치즈가 상했을 가능성이 있다면, 몇 개의 약이 필요한지 확인합니다.
            int pill = 0;
            if(possible) {
                // 한번이라도 i번째 치즈를 먹은 적이 있다면, 약이 필요합니다.
                for(int j = 1; j <= n; j++) {
                    if(time[j] != 0)
                        pill++;
                }
            }

            ans = Math.max(ans, pill);
        }

        System.out.print(ans);
    }
}