import java.util.*;

public class Main {
    public static final int MAX_N = 10000;
    public static void main(String[] args) {
        // 여기에 코드를 작성해주세요.
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] arr = new int[MAX_N];
        for (int i=0; i<n; i++) {
            int val = sc.nextInt();
            int pos = sc.nextInt();
            arr[pos] += val;
        }
        int mx = -1;
        for (int i=k; i<MAX_N-k; i++) {
            int sum = 0;
            for (int j=i-k; j<=i+k; j++) {
                sum += arr[j];
            }
            mx = Math.max(sum, mx);
        }

        System.out.println(mx);
    }
}