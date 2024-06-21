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


        List<Person> res = new ArrayList<>();
        for(int i=0; i<D; i++) {
            Person eatPerson = Alist.get(i);
            for(int j=0; j<S; j++) {
                Person hurtPerson = Blist.get(j);
                if(hurtPerson.personNumber == eatPerson.personNumber && hurtPerson.time > eatPerson.time)
                    res.add(eatPerson);
            }
        }
        for(int i=0; i<res.size(); i++){
            // System.out.println(res.get(i).personNumber + " " + res.get(i).eatCheeseNumber + " " + res.get(i).time);
        }

        HashSet<Integer> set = new HashSet<>();
        if (res.size() == 1) {
            set.add(res.get(0).eatCheeseNumber);
        }
        else {
            for(int i=0; i<res.size(); i++) {
                Person A = res.get(i);
                boolean flag = false;
                for(int j=0; j<res.size(); j++) {
                    if(i==j) continue;

                    Person B = res.get(j);
                    if(A.personNumber == B.personNumber) continue;

                    if(A.eatCheeseNumber == B.eatCheeseNumber) {
                        // System.out.println("Cheese: " + A.eatCheeseNumber);
                        flag = true;
                    }
                }
                if(flag) set.add(A.eatCheeseNumber);
            }
        }
        int ans = 0;
        Iterator iter = set.iterator();
        while(iter.hasNext()) {
            int num = (int) iter.next();
            int cnt = 0;
            for (int i=0; i<D; i++) {
                if (Alist.get(i).eatCheeseNumber == num && !answer.contains(Alist.get(i).personNumber)) {
                    answer.add(Alist.get(i).personNumber);
                    cnt++;
                }
            }
            ans = Math.max(ans, cnt);
        }

        System.out.println(ans);


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