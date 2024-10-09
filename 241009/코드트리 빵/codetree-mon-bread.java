import java.util.*;
import java.io.*;
public class Main {
    static int[] dx = {-1, 0, 0, 1};
    static int[] dy = {0, -1, 1, 0};
    static int n, m, t = 0;
    static boolean[][] visited;
    static int[][] map;
    static class Node implements Comparable<Node>{
        int x, y;
        int len = 0;
        Node(int x, int y){
            this.x = x;
            this.y = y;
        }
        Node(int x, int y, int len){
            this.x = x;
            this.y = y;
            this.len = len;
        }
        @Override
        public int compareTo(Node t) {
            if(this.len == t.len) {
                if(this.x == t.x) {
                    return Integer.compare(this.y, t.y);
                }
                return Integer.compare(this.x, t.x);
            }
            return Integer.compare(this.len, t.len);
        }
    }
    static ArrayList<Node> basecamp = new ArrayList<>();
    static ArrayList<Node> combini = new ArrayList<>();
    static ArrayList<Node> people = new ArrayList<>();
    static Queue<Node> queue = new LinkedList<>();
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        n = Integer.parseInt(st.nextToken()); m = Integer.parseInt(st.nextToken());
        //맵과 방문 초기화
        visited = new boolean[n][n];
        //BFS용 거리 체크
        map = new int[n][n];
        //베이스캠프
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for(int j = 0; j < n; j++) {
                if(Integer.parseInt(st.nextToken()) == 1)
                    basecamp.add(new Node(i,j));
            }
        }
        //편의점
        for(int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            combini.add(new Node(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1));
        }
        while(true) {
            //1
            for(int i = 0; i < people.size(); i++) {
                Node tmp = people.get(i);
                if(tmp.x == combini.get(i).x && tmp.y == combini.get(i).y)
                    continue;
                else {
                    queue.add(combini.get(i));
                    bfs2(tmp);
                    int ln = Integer.MAX_VALUE;
                    for(int j = 0; j < 4; j++) {
                        int nx = tmp.x + dx[j];
                        int ny = tmp.y + dy[j];
                        if(0 <= nx && nx < n && 0 <= ny && ny < n) {
                            if(map[nx][ny] == 2 && !visited[nx][ny]) {
                                ln = 2;
                                break;
                            }
                            else if(map[nx][ny] != 0 && map[nx][ny] < ln && !visited[nx][ny]) {
                                ln = map[nx][ny];
                            }
                        }
                    }
                    for(int j = 0; j < 4; j++) {
                        int nx = tmp.x + dx[j];
                        int ny = tmp.y + dy[j];
                        if(0 <= nx && nx < n && 0 <= ny && ny < n)
                            if(map[nx][ny] == ln && !visited[nx][ny]) {
                                people.get(i).x = nx;
                                people.get(i).y = ny;
                                break;
                            }
                    }
                }
            }
            //2
            for(int i = 0; i < people.size(); i++) {
                Node tmp = people.get(i);
                if(tmp.x == combini.get(i).x && tmp.y == combini.get(i).y) {
                    visited[tmp.x][tmp.y] = true;
                }
            }
            //3
            if(t < m) {
                Node tmp = combini.get(t);
                queue.add(tmp);
                bfs();
                ArrayList<Node> tmparr = new ArrayList<>();
                for(Node a : basecamp) {
                    if(map[a.x][a.y] != 0)
                        tmparr.add(new Node(a.x, a.y, map[a.x][a.y]));
                }
                Collections.sort(tmparr);
                visited[tmparr.get(0).x][tmparr.get(0).y] = true;
                people.add(new Node(tmparr.get(0).x,tmparr.get(0).y));
            }
            if(check()) {
                System.out.println(t+1);
                return;
            }
            t++;
        }
    }
    static boolean check() {
        for(int i = 0; i < combini.size(); i++) {
            Node com = combini.get(i);
            if(!visited[com.x][com.y])
                return false;
        }
        return true;
    }
    static void bfs2(Node p) {
        boolean[][] visited2 = new boolean[n][n];
        map = new int[n][n];
        for(int i = 0; i < n; i++)
            System.arraycopy(visited[i], 0, visited2[i], 0, visited[i].length);
        visited2[queue.peek().x][queue.peek().y] = true;
        map[queue.peek().x][queue.peek().y] = 2;
        while(!queue.isEmpty()) {
            Node tmp = queue.poll();
            for(int i = 0; i < 4; i++) {
                int nx = tmp.x + dx[i];
                int ny = tmp.y + dy[i];
                if(0 <= nx && nx < n && 0 <= ny && ny < n) {
                    if(!visited2[nx][ny]) {
                        visited2[nx][ny] = true;
                        queue.add(new Node(nx,ny));
                        map[nx][ny] = map[tmp.x][tmp.y] + 1;
                        if(nx == p.x && ny == p.y)
                            queue.clear();
                    }
                }
            }
        }
    }
    static void bfs() {
        boolean[][] visited2 = new boolean[n][n];
        map = new int[n][n];
        //arraycopy를 줄여야하나?
        for(int i = 0; i < n; i++)
            System.arraycopy(visited[i], 0, visited2[i], 0, visited[i].length);
        visited2[queue.peek().x][queue.peek().y] = true;
        map[queue.peek().x][queue.peek().y] = 2;
        while(!queue.isEmpty()) {
            Node tmp = queue.poll();
            for(int i = 0; i < 4; i++) {
                int nx = tmp.x + dx[i];
                int ny = tmp.y + dy[i];
                if(0 <= nx && nx < n && 0 <= ny && ny < n) {
                    if(!visited2[nx][ny]) {
                        visited2[nx][ny] = true;
                        queue.add(new Node(nx,ny));
                        map[nx][ny] = map[tmp.x][tmp.y] + 1;
                    }
                }
            }
        }
    }
}