import java.util.*;

public class Main {
    static int[] arr;
    static boolean[] visit;
    public static void main(String[] args) {
        // 여기에 코드를 작성해주세요.
        // 중복 x 
        // 순서 o 

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        arr = new int[n];
        visit = new boolean[n];

        backtrack(0,n);
        
    }
    private static void backtrack(int start, int k) {
        if (start == k) {
            print(k);
            return;
        }

        for(int i=0; i<k; i++) {
            if (visit[i]) continue;
            visit[i] = true;
            arr[start] = i+1;
            backtrack(start+1, k);
            visit[i] = false;

        }
    }
    private static void print(int n) {
        for(int i=0; i<n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}