import java.util.*;
public class Main {
    static int N,H,T, answer = 0;
    static final int MN = 20001;
    static int[] arr; 
    public static void main(String[] args) {
        // 여기에 코드를 작성해주세요.
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        H = sc.nextInt();
        T = sc.nextInt();

        arr = new int[N];

        for(int i=0; i<N; i++) 
            arr[i] = sc.nextInt();
        
        answer = find();
        System.out.println(answer);
        
    }

    private static int find() {
        int min_n = MN;
        for (int i=0; i<N-T+1; i++) {

            int sum = 0;
            for (int j=i; j<i+T; j++) {
                sum += Math.abs(H-arr[j]);
            }
            min_n = Math.min(sum, min_n);
        }
        if (min_n == MN) return 0;
        return min_n;
    }
}