import java.util.*;
import java.io.*;
public class Main {
    public static int n,m;
    public static int[] A,B,temp;

    public static void main(String[] args) throws IOException{
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        A = new int[n];
        B = new int[m];
        temp = new int[m];

        st = new StringTokenizer(br.readLine());
        for (int i=0; i<n; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for (int i=0; i<m; i++) {
            B[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(B);

        int cnt = 0;
        for (int i=0; i <= n-m; i++) {
            for (int j=0; j<m; j++) {
                temp[j] = A[i+j];
            }
            Arrays.sort(temp);

            boolean isSame = true;
            for(int j=0; j<m; j++) {
                if (temp[j] != B[j]) {
                    isSame = false;
                    break;
                }
            }
            if (isSame) cnt++;
        }

        System.out.println(cnt);
    }
}