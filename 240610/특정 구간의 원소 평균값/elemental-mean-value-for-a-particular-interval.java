import java.util.*;
public class Main {
    public static final int MAX_N = 100;
    public static int n; 
    public static int[] arr = new int[MAX_N];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n=sc.nextInt();
        for(int i=0; i<n; i++) {
            arr[i] = sc.nextInt();
        }    

        int ans = 0;
        for(int i=0; i<n; i++) {
            for(int j=i;j<n;j++) {
                int sum = 0;
                for(int k=i; k<=j; k++) {
                    sum += arr[k];
                }

                double avg = (double)sum / (j-i+1);
                boolean exists = false;
                for (int k=i; k<=j; k++) {
                    if (arr[k] == avg) exists = true;
                }

                if(exists) ans++;
            }
        }
        System.out.println(ans);
    }
}