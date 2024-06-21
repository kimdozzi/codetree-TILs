import java.util.*;

class Person {
    int personNumber;
    int eatCheeseNumber;
    int time;

    public Person(int personNumber, int eatCheeseNumber, int time) {
        this.personNumber = personNumber;
        this.eatCheeseNumber = eatCheeseNumber;
        this.time = time;
    }

    public Person(int personNumber, int time) {
        this.personNumber = personNumber;
        this.eatCheeseNumber = -1;
        this.time = time;
    }
}

public class Main {
    static int N, M, D, S;
    static List<Person> Alist, Blist;
    static List<Integer> answer = new ArrayList<>();

    public static void main(String[] args) {
       
        input();

        int ans = 0;

        // 하나의 치즈가 상했을 때 필요한 약의 수의 최대값 구하기

        for(int i=1; i<=m; i++) {
            // i번 치즈가 상했을 때 필요한 약의 수의 최대값 

            // time 배열을 만들어 각 사람이 언제 치즈를 먹었는지 저장
            int[] time = new int[51];
            for(int j=0; j<d; j++) {
                if(Alist.eatCheeseNumber != i) continue;

                // person이 i번째 치즈를 처음 먹었거나 이전보다 더 빨리 먹게 된 경우 time배열을 갱신
                int person = Alist[j].personNumber;
                if(time[person] == 0 || time[person] > Alist[j].time)
                    time[person] = Alist[j].time;
            }

            // possible: i번째 치즈가 상했을 수 있으면 true, 아니면 false
            boolean possible = true;

            for(int j=0; j<s; j++) {
                int person = Blist[j].personNumber;
                if(time[person] == 0 || time[person] >= Blist[j].time) 
                    possible = false;
            }

            // 만약 i번째 치즈가 상했을 가능성이 있다면, 몇 개의 약이 필요한지 확인
            int pill = 0;
            if(possible) {
                for (int j=1; j<=n; j++) {
                    if(time[j] != 0) {
                        pill++;
                    }
                }
            }
            ans = Math.max(ans,pill);
        }
        System.out.print(ans);
    }


    private static void input() {
        // 여기에 코드를 작성해주세요.
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();
        D = sc.nextInt();
        S = sc.nextInt();

        Alist = new ArrayList<>();
        Blist = new ArrayList<>();

        for(int i=0; i<D; i++) {
            int p = sc.nextInt();
            int c = sc.nextInt();
            int t = sc.nextInt();

            Alist.add(new Person(p,c,t));
        }

        for(int i=0; i<S; i++) {
            int p = sc.nextInt();
            int t = sc.nextInt();

            Blist.add(new Person(p,t));
        }
    }
}