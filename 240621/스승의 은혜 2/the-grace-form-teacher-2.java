import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 여기에 코드를 작성해주세요.
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int B = sc.nextInt();
        int[] arr = new int[1001];
        int maxCnt = 0;
        
        for(int i=0; i<N; i++) 
            arr[i] = (int) (sc.nextInt() / 2);

        for (int i=0; i<N; i++) {
            int sum = B;
            int cnt = 0;

            if(sum-arr[i] >= 0) {
                    sum -= arr[i];
                    cnt++;
            }
            for (int j=0; j<N; j++) {
                if (i==j) continue;

                if(sum-arr[j] >= 0) {
                    sum -= arr[j];
                    cnt++;
                }
            }

            maxCnt = Math.max(cnt, maxCnt);

        }
        System.out.print(maxCnt);
    }
}