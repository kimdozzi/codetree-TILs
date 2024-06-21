import java.util.*;
import java.io.*;

class Pair {
    int x;
    int y;
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    static int k,n;
    static int[][] person;
    static Set<Pair> set = new HashSet<>();
    public static void main(String[] args) throws IOException{
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        k = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        person = new int[n+1][n+1];

        for(int i=1; i<=k; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=n; j++) {
                person[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // for(int i=1; i<=k; i++) {
        //     for(int j=1; j<=n; j++) {
        //         System.out.print(person[i][j]+ " ");
        //     }
        //     System.out.println();
        // }

        // i번째 개발자보다 항상 높은 개발자 찾기 
        for(int i=1; i<=n; i++) {
            int[] arr = new int[n+1];
            List<Integer> highPerson = new ArrayList<>();
            
            
            for(int x=1; x<=k; x++) {
                for(int y=1; y<=n; y++) {
                    // 자기 자신의 순위가 오면 루프 탈출 
                    if (i==person[x][y]) break;
                    highPerson.add(person[x][y]);
                }
            }

            // i번 개발자보다 높은 순위였던 개발자들의 번호 갱신 
            for(int j=0; j<highPerson.size(); j++) {
                arr[highPerson.get(j)] += 1;
            }

            // 그 중에서 k번 모두 자신을 이겼다면 set에 저장
            for(int j=0; j<arr.length; j++) {
                if(arr[j] == k) {
                    if(!set.contains(new HashSet<>(i,arr[j])))
                        set.add(new Pair(i, arr[j]));
                }
            }
        }
        System.out.print(set.size());
    }
}