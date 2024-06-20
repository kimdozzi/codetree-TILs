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
            arr[i] = sc.nextInt();

        Arrays.sort(arr);
        
        for (int i=0; i<1001; i++) {
            int sum = B;
            int cnt = 0;
            
            for (int j=0; j<1001; j++) {
                if (i==j) {
                    if(arr[j] > 0 && sum-(int)(arr[j]/2) >= 0) {
                    sum -= (int)(arr[j]/2);
                    cnt++;
                    }
                }
                else if(arr[j] > 0 && sum-arr[j] >= 0) {
                    sum -= arr[j];
                    cnt++;
                }
            }
            maxCnt = Math.max(cnt, maxCnt);
        }
        System.out.print(maxCnt);
    }
}