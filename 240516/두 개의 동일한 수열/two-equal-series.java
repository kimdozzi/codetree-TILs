import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr1 = new int[n];
        int[] arr2 = new int[n];

        int i = 0;
        while (st.hasMoreTokens()) {
            arr1[i++] = Integer.parseInt(st.nextToken());
        }
        i = 0;
        st = new StringTokenizer(br.readLine());
        while (st.hasMoreTokens()) {
            arr2[i++] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        
        for (i=0; i<n; i++) {
            if (arr1[i] != arr2[i]) {
                System.out.println("No");
                break;
            }
        }
        if (i != n-1) {
            System.out.println("Yes");
        }

    }
}