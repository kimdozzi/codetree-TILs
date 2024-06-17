import java.util.*;
public class Main {
    static int N,H,T, answer = 0;
    static int[] arr; 
    public static void main(String[] args) {
        // 여기에 코드를 작성해주세요.
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        H = sc.nextInt();
        T = Math.min(10,sc.nextInt());

        arr = new int[N];

        for(int i=0; i<N; i++) 
            arr[i] = sc.nextInt();
        
        answer = find();
        System.out.println(answer);
        
    }

    private static int find() {
        int mn = 2000001;
        for (int i=0; i<N-T; i++) {
            int sum = 0;
            for (int j=i; j<i+T; j++) {
                sum += Math.abs(H-arr[j]);
            }
            mn = Math.min(sum, mn);
        }
        return mn;
    }
}