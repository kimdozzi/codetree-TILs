import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static final int INT_MAX = Integer.MAX_VALUE;
    public static final int MAX_N = 10;
    
    // 변수 선언
    public static int n;
    public static int[][] cost = new int[MAX_N][MAX_N];
    public static boolean[] visited = new boolean[MAX_N];
    public static ArrayList<Integer> picked = new ArrayList<>();
    
    public static int ans = INT_MAX;
    
    // 지금까지 방문한 지점의 수를 cnt라 했을 때
    // 계속 탐색을 이어서 진행합니다.
    public static void findMin(int cnt) {
        // 모든 지점를 방문했을 때
        // 가능한 지금까지의 비용 합 중
        // 최솟값을 갱신합니다.
        if(cnt == n) {
            int totalCost = 0;
            for(int i = 0; i < picked.size() - 1; i++) {
                int currCost = cost[picked.get(i)][picked.get(i + 1)];
                // 만약 비용이 0이라면 불가능한 경우이므로 
                // 바로 함수를 빠져나옵니다.
                if(currCost == 0)
                    return;
                
                totalCost += currCost;
                
            }
            // 최종적으로 1번 지점으로 다시 돌아오는 것 까지 고려해서 계산해줍니다.
            int lastPos = picked.get(picked.size() - 1);
            int additionalCost = cost[lastPos][0];
            if(additionalCost == 0)
                return;
    
            // 답을 갱신해줍니다.
            ans = Math.min(ans, totalCost + additionalCost);
            return;
        }
    
        // 방문할 지점을 선택합니다.
        for(int i = 0; i < n; i++) {
            if(visited[i]) continue;
            visited[i] = true;
            picked.add(i);
    
            findMin(cnt + 1);
    
            visited[i] = false;
            picked.remove(picked.size() - 1);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 입력:
        n = sc.nextInt();

        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                cost[i][j] = sc.nextInt();

        // 1번 지점을 시작으로
        // 탐색을 진행합니다.
        visited[0] = true;
        picked.add(0);
        findMin(1);

        System.out.print(ans);
    }
}