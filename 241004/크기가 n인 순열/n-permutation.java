import java.util.*;

public class Main {
    static int[] arr;
    static boolean[] visit;
    static int n;
    public static void main(String[] args) {
        // 여기에 코드를 작성해주세요.
        // 중복 x 
        // 순서 o 

        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        arr = new int[n+1];
        visit = new boolean[n+1];

        backtrack(0);
        
    }
    private static void backtrack(int cnt) {
        if (cnt == n) {
            print();
            return;
        }

        for(int i=1; i<=n; i++) {
            if (visit[i]) continue;
            visit[i] = true;
            arr[cnt] = i;
            backtrack(cnt+1);
            visit[i] = false;
        }
    }
    private static void print() {
        for(int i=0; i<n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}