import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static final int MAX_N = 1000;
    
    public static int n, b;
    public static int[] p = new int[MAX_N];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 입력
        n = sc.nextInt();
        b = sc.nextInt();

        for(int i = 0; i < n; i++)
            p[i] = sc.nextInt();

        int ans = 0;
        
        // 한 명의 학생에 선물 쿠폰을 쓸 때 선물 가능한 학생의 최대 명수를 구합니다.
        for(int i = 0; i < n; i++) {
            // i번째 학생의 선물에 쿠폰을 쓸 때 선물 가능한 학생 수를 구합니다.

            // tmp배열을 만들어 i번째 학생의 선물에 쿠폰을 쓸 때
            // 각 학생의 원하는 선물 가격을 저장합니다.
            int[] tmp = new int[MAX_N];
            for(int j = 0; j < n; j++)
                tmp[j] = p[j];
            tmp[i] /= 2;

            // 학생을 선물 가격 순으로 정렬합니다.
            // 선물 가격이 가장 작은 학생부터 선물을 사 줄 때,
            // 반드시 가장 많은 학생에게 선물을 줄 수 있습니다.
            Arrays.sort(tmp, 0, n);

            int student = 0;
            int cnt = 0;

            // 가장 많은 학생에게 선물을 줄 때, 그 학생 수를 구합니다.
            // student : 선물받은 학생 수 / cnt : 현재까지 쓴 돈
            for(int j = 0; j < n; j++) {
                if(cnt + tmp[j] > b)
                    break;
                cnt += tmp[j];
                student++;
            }

            ans = Math.max(ans, student);
        }

        System.out.print(ans);
    }
}