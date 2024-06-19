import java.util.*;

public class Main {

    private static int diff(int i, int j, int k, int q) {
        int sum = 0;
        for(int x=0; x<5; x++)
            sum += arr[x];
        
        // 세번째 팀
        int team01 = arr[i]+arr[j];
        int team02 = arr[k]+arr[q];
        int team03 = sum - (team01 + team02);

        if (team01 == team02 || team01 == team03 || team02 == team03) return -1;
        int mx = Math.max(team01, Math.max(team02, team03));
        int mn = Math.min(team01, Math.min(team02, team03));

        return Math.abs(mx-mn);
    }

    static int[] arr = new int[5]; // 능력 리스트 
    static int ans = 5000;

    public static void main(String[] args) {
        // 여기에 코드를 작성해주세요.
        Scanner sc = new Scanner(System.in);
        for(int i=0; i<5; i++)
            arr[i] = sc.nextInt();
        
        // 첫번째 팀
        for(int i=0; i<5; i++) {
            for (int j=i+1; j<5; j++) {

                // 두번째 팀 
                for(int k=0; k<5; k++) {
                    for(int q=k+1; q<5; q++) {
                        if(i==k || i==q || j==k || j==q) continue;


                        int temp = diff(i,j,k,q);
                        if (temp == -1) continue;

                        ans = Math.min(ans, temp);
                    }
                }
            }
        }
        System.out.println(ans == 5000 ? - 1 : ans);
    }
}