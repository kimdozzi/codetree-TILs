import java.util.*;
import java.io.*;

class Pair {
    int start;
    int end; 

    public Pair(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

public class Main {
    static final int MAX_N = 100;
    static int maxCnt = 0;
    public static void main(String[] args) throws IOException{
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int[] arr;
        List<Pair> list = new ArrayList<>();

        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            list.add(new Pair(a,b));
        }

        for(int i=0; i<list.size(); i++) {
            arr = new int[1001];
            for(int j=0; j<list.size(); j++) {
                if (i==j) continue;
                Pair pair = list.get(j);
                arr[pair.start] = 1;
                arr[pair.end] = -1;
            }

            for(int j=1; j<1001; j++) {
                arr[j] += arr[j-1];
            }

            int cnt = 0;
            for(int j=0; j<1001; j++) 
                if (arr[j] >= 1) cnt++;
            
            maxCnt = Math.max(maxCnt, cnt);
        }
        System.out.print(maxCnt);

    }
}