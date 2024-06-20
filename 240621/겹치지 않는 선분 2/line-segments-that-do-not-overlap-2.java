import java.util.Scanner;

public class Main {
    public static final int MAX_N = 100;
    
    public static int n;
    public static int[] x1 = new int[MAX_N];
    public static int[] x2 = new int[MAX_N];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 입력
        n = sc.nextInt();

        for(int i = 0; i < n; i++) {
            x1[i] = sc.nextInt();
            x2[i] = sc.nextInt();
        }

        int ans = 0;

        // 다른 선분과 겹치지 않는 선분의 수를 구합니다.
        for(int i = 0; i < n; i++) {
            // i번째 선분이 다른 선분과 겹치지 않는지 확인합니다.

            boolean overlap = false;

            for(int j = 0; j < n; j++) {
                // 자기 자신은 제외합니다.
                if(j == i) continue;

                // x1이 큰 쪽 선분이 x2가 더 작다면 겹치게 됩니다.
                if((x1[i] <= x1[j] && x2[i] >= x2[j]) || (x1[i] >= x1[j] && x2[i] <= x2[j])) {
                    overlap = true;
                    break;
                }
            }

            // 겹치지 않았다면 정답의 개수에 하나를 추가합니다.
            if(overlap == false)
                ans++;
        }

        System.out.print(ans);
    }
}