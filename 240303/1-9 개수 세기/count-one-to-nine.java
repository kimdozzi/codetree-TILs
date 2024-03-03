import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] countingArr = new int[10];
        for (int i=0; i<n; i++) {
            countingArr[sc.nextInt()]++;
        }

        for (int i=1; i<countingArr.length; i++) {
            System.out.println(countingArr[i]);
        }
    }
}