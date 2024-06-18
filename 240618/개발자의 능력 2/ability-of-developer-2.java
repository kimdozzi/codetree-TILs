import java.util.*;
public class Main {
    public static final int INT_MAX = Integer.MAX_VALUE;
    public static final int MAX_N = 6;
    
    public static int n = MAX_N;
    public static int[] arr = new int[MAX_N];

    public static int diff(int i, int j, int k, int l) {
        int totalSum = 0;
        for(int idx = 0; idx < n; idx++)
            totalSum += arr[idx];
        
        int sum1 = arr[i] + arr[j];
        int sum2 = arr[k] + arr[l];

        int sum3 = totalSum - sum1 - sum2;

        int ret = Math.abs(sum1-sum2);
        ret = Math.max(ret, Math.abs(sum2-sum3));
        ret = Math.max(ret, Math.abs(sum3-sum1));

        return ret;

    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for (int i=0; i<n; i++)
            arr[i] = sc.nextInt();


        // 첫 번째 팀원을 만들어줍니다.
        int minDiff = INT_MAX;
        for(int i = 0; i < n; i++)
            for(int j = i + 1; j < n; j++)
                
                // 두 번째 팀원을 만들어줍니다.
                for(int k = 0; k < n; k++)
                    for(int l = k + 1; l < n; l++) {
                        //첫 번째 팀원과 두 번째 팀원이 겹치는지 여부를 확인합니다.
                        if(k == i || k == j || l == i || l == j)
                            continue;
                        minDiff = Math.min(minDiff, diff(i, j, k, l));
                    }
        
        System.out.print(minDiff);

    }
}