import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

class Pair {
	int x;
	int y;

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class Camp implements Comparable<Camp> {
	int x;
	int y;
	int dist;

	public Camp(int x, int y, int dist) {
		this.x = x;
		this.y = y;
		this.dist = dist;
	}

	@Override
	public int compareTo(Camp c) {
		if (this.dist != c.dist)
			return this.dist - c.dist;
		if (this.x != c.x)
			return this.x - c.x;
		return this.y - c.y;
	}
}

public class Main {
	static int[][] dirs = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}}; // 상 좌 우 하

	static int N, M;
	static int time;
	static int[][] board;
	static boolean[][] cannotMove; // 이동가능 격지인지 확인
	static Pair[] people; // 사람들의 좌표 정보
	static List<Pair> campList;
	static Queue<Pair> willNotUse;
	static Pair[] markets;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();

		board = new int[N][N];
		cannotMove = new boolean[N][N];
		people = new Pair[M + 1];
		markets = new Pair[M + 1];
		campList = new ArrayList<>();
		willNotUse = new LinkedList<>();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = sc.nextInt();
				if (board[i][j] == 1) {
					campList.add(new Pair(i, j));
				}
			}
		}

		for (int i = 1; i <= M; i++) {
			int x = sc.nextInt() - 1;
			int y = sc.nextInt() - 1;
			markets[i] = new Pair(x, y);
		}

		// 초기에 사람들은 모두 격자 밖에 존재함.
		for (int i = 0; i <= M; i++) {
			people[i] = new Pair(-1, -1);
		}

		while (true) {
			time++;

			// 1. 격자에 있는 사람들 모두 본인이 가고 싶은 편의점 방향으로 1칸 이동
			move();

			// 2. 편의점에 도착여부 확인.
			destination();

			// 3. 베이스 캠프로 이동.
			if (time <= M)
				goBaseCamp();

			// 모든 사람이 편의점에 도착했는지 확인
			if (allArrived()) {
				break;
			}
		}
		System.out.println(time);
	}

	private static boolean allArrived() {
		for (int i = 1; i <= M; i++) {
			if (people[i].x != markets[i].x || people[i].y != markets[i].y) {
				return false;
			}
		}
		return true;
	}

	private static void goBaseCamp() {
		List<Camp> shortestCamp = new ArrayList<>();
		boolean[][] visit = new boolean[N][N];

		//t번 사람은 자신이 가고 싶은 편의점과 가장 가까운 베이스 캠프로 이동.
		Pair marketPos = markets[time];
		Queue<Camp> q = new LinkedList<>();
		q.add(new Camp(marketPos.x, marketPos.y, 0));
		visit[marketPos.x][marketPos.y] = true;

		while (!q.isEmpty()) {
			Camp camp = q.poll();
			int x = camp.x;
			int y = camp.y;

			// 해당 위치가 베이스캠프라면 가까운 캠프의 후보지에 넣음.
			if (board[x][y] == 1) {
				shortestCamp.add(new Camp(x, y, camp.dist));
				continue;
			}

			for (int d = 0; d < 4; d++) {
				int nx = x + dirs[d][0];
				int ny = y + dirs[d][1];

				if (!inRange(nx, ny))
					continue;

				if (visit[nx][ny])
					continue;

				if (cannotMove[nx][ny])
					continue;

				visit[nx][ny] = true;
				q.add(new Camp(nx, ny, camp.dist + 1));
			}
		}

		// 우선순위에 맞게 정렬.
		Collections.sort(shortestCamp);

		// 가장 가까운 베이스 캠프를 구함.
		Camp camp = shortestCamp.get(0);

		people[time].x = camp.x;
		people[time].y = camp.y;
		cannotMove[camp.x][camp.y] = true;
	}

	private static void destination() {

		for (int i = 1; i <= M; i++) {
			Pair market = markets[i];
			Pair person = people[i];

			if (market.x == person.x && market.y == person.y) {
				cannotMove[market.x][market.y] = true;
			}
		}
	}

	private static void move() {
		// 모든 사람들의 좌표를 살펴보고, 격자 내에 존재한다면 1칸 이동.
		for (int idx = 1; idx <= M; idx++) {
			Pair person = people[idx];
			int x = person.x;
			int y = person.y;

			// 격자에 있지 않는 사람들은 패스.
			if (x == -1 && y == -1)
				continue;

			// 본인이 가고 싶은 편의점 좌표
			Pair market = markets[idx];
			int ex = market.x;
			int ey = market.y;

			if (x == ex && y == ey)
				continue;

			Queue<Pair> q = new LinkedList<>();
			boolean[][] vis = new boolean[N][N];
			Pair[][] parent = new Pair[N][N];

			q.add(new Pair(x, y));
			vis[x][y] = true;

			while (!q.isEmpty()) {
				Pair current = q.poll();

				if (current.x == ex && current.y == ey) {
					Pair next = current;
					while (parent[next.x][next.y] != null && (
						parent[next.x][next.y].x != x && parent[next.x][next.y].y != y)) {
						next = parent[next.x][next.y];
					}
					people[idx] = next;
					break;
				}

				for (int d = 0; d < 4; d++) {
					int nx = current.x + dirs[d][0];
					int ny = current.y + dirs[d][1];

					if (!inRange(nx, ny) || vis[nx][ny] || cannotMove[nx][ny])
						continue;

					q.add(new Pair(nx, ny));
					vis[nx][ny] = true;
					parent[nx][ny] = current;
				}
			}
		}
	}

	private static boolean inRange(int nx, int ny) {
		return 0 <= nx && nx < N && 0 <= ny && ny < N;
	}
}