import java.util.*;
import java.io.*;
public class Main {
    public static final int MAX_N = 100;
    public static int N;

    public static String[] arr = new String[MAX_N+1];

    public static void main(String[] args) throws IOException{
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        if (N == 1) {
            System.out.println(0);
            System.exit(0);
        }
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            int pos = Integer.parseInt(st.nextToken());
            String alpha = st.nextToken();
            arr[pos] = alpha;
        }
        int ans = 0;
        for (int i=0; i<=100; i++) {
            for (int j=i; j<=100; j++) {
                int s = 101, e = -1;
                int g=0, h=0;
                for (int k=i; k<=j; k++) {
                    if (arr[k] != null && arr[k].equals("G")){
                        if (k < s) s = k;
                        if (k > e) e = k;
                        g++;
                    }
                    else if (arr[k] != null && arr[k].equals("H")) {
                        if (k < s) s = k;
                        if (k > e) e = k;
                        h++;
                    }
                    
                }
               //System.out.println(s + " " + e);
                if ((g>0 && h==0) || (h>0 && g==0) || h==g) ans = Math.max(ans, e-s);
            }
        }
        System.out.println(ans);

    }
}