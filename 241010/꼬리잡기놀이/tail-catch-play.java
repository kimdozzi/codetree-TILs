import java.util.ArrayList;
import java.util.Deque;
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

public class Main {
	static int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
	static int N, M, K;
	static int answer = 0;
	static int[][] board;
	static List<Pair> headList;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		K = sc.nextInt();

		board = new int[N][N];
		headList = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = sc.nextInt();
				if (board[i][j] == 1) {
					headList.add(new Pair(i, j));
				}
			}
		}

		for (int t = 0; t < K; t++) {
			move();

			ball(t);
		}
        System.out.println(answer);
	}

	private static void ball(int t) {
        t %= N;
		if (t == N) {
			// 90도 회전.
			int[][] temp = new int[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					temp[j][N - i - 1] = board[i][j];
				}
			}
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					board[i][j] = temp[i][j];
				}
			}
		}
		int r = t;
		for (int c = 0; c < N; c++) {
			// 해당 선에 사람이 있으면.
			if (1 <= board[r][c] && board[r][c] <= 3) {
				// 다시 머리사람 찾기
				Deque<Pair> linkedPeopleQueue = new LinkedList<>();
				int x = r;
				int y = c;

				int[] headPos = findHead(x, y);

				linkedPeopleQueue.add(new Pair(headPos[0], headPos[1]));

				// 머리사람과 연결된 사람들의 경로를 찾습니다.
				findPath(new Pair(headPos[0], headPos[1]), linkedPeopleQueue);

				// 머리사람과 공을 받은 사람의 거리를 구하고, 거리만큼 제곱 점수를 받습니다.
				int score = getScore(linkedPeopleQueue, x, y) + 1;
				answer += (score * score);
				break;
			}
		}
	}

	private static int getScore(Queue<Pair> q, int sx, int sy) {
		boolean[][] visit = new boolean[N][N];

		int[][] temp = new int[N][N];

		int ex = 0, ey = 0;
		while (!q.isEmpty()) {
			Pair current = q.poll();

			for (int d = 0; d < 4; d++) {
				int nx = current.x + dirs[d][0];
				int ny = current.y + dirs[d][1];
				if (!inRange(nx, ny))
					continue;

				if (board[nx][ny] == 0 || board[nx][ny] == 4)
					continue;

				if (visit[nx][ny])
					continue;

				visit[nx][ny] = true;

				if (board[nx][ny] == 1) {
					ex = nx;
					ey = ny;
					temp[nx][ny] = 1;
					continue;
				} else if (board[nx][ny] == 3) {
					temp[nx][ny] = 3;
					continue;
				}
				q.add(new Pair(nx, ny));
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (temp[i][j] == 3) {
					board[i][j] = 1;
				} else if (temp[i][j] == 1) {
					board[i][j] = 3;
				}
			}
		}

		return Math.abs(sx - ex) + Math.abs(sy - ey);
	}

	private static int[] findHead(int x, int y) {
		Queue<Pair> q = new LinkedList<>();
		q.add(new Pair(x, y));
		boolean[][] visit = new boolean[N][N];
		visit[x][y] = true;
		while (!q.isEmpty()) {
			Pair pair = q.poll();
			if (board[pair.x][pair.y] == 1) {
				return new int[] {pair.x, pair.y};
			}

			for (int d = 0; d < 4; d++) {
				int nx = pair.x + dirs[d][0];
				int ny = pair.y + dirs[d][1];
				if (!inRange(nx, ny))
					continue;

				if (board[nx][ny] == 0 || board[nx][ny] == 4)
					continue;

				if (visit[nx][ny])
					continue;

				visit[nx][ny] = true;

				q.add(new Pair(nx, ny));
			}
		}
		return new int[] {0, 0};
	}

	private static void print(int[][] board) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	private static void move() {
		int[][] newBoard = new int[N][N];
		newInit(newBoard);

		for (int i = 0; i < headList.size(); i++) {
			Deque<Pair> linkedPeopleQueue = new LinkedList<>();

			Pair head = headList.get(i);
			linkedPeopleQueue.add(new Pair(head.x, head.y));

			// 머리사람과 연결된 사람들의 경로를 찾습니다.
			findPath(head, linkedPeopleQueue);

			// 1칸 이동합니다.
			boolean[][] visit = new boolean[N][N];
			while (!linkedPeopleQueue.isEmpty()) {
				Pair current = linkedPeopleQueue.poll();
				for (int d = 0; d < 4; d++) {
					int nx = current.x + dirs[d][0];
					int ny = current.y + dirs[d][1];
					if (!inRange(nx, ny))
						continue;

					if (board[nx][ny] == 0)
						continue;

					if (visit[nx][ny])
						continue;

					visit[nx][ny] = true;
					if (board[current.x][current.y] > 1 && board[current.x][current.y] >= board[nx][ny]) {
						newBoard[nx][ny] = board[current.x][current.y];
					} else if (board[current.x][current.y] == 1 && board[nx][ny] == 4) {
						newBoard[nx][ny] = board[current.x][current.y];
					}
				}
			}
		}

		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++) {
				board[x][y] = newBoard[x][y];
			}
		}
	}

	private static void findPath(Pair head, Deque<Pair> linkedPeopleQueue) {
		boolean[][] visit = new boolean[N][N];
		visit[head.x][head.y] = true;
		Deque<Pair> findPeopleQueue = new LinkedList<>();
		findPeopleQueue.add(new Pair(head.x, head.y));

		// 머리사람과 연결된 사람들의 좌표를 구합니다.
		while (!findPeopleQueue.isEmpty()) {
			Pair pair = findPeopleQueue.poll();
			for (int d = 0; d < 4; d++) {
				int nx = pair.x + dirs[d][0];
				int ny = pair.y + dirs[d][1];
				if (!inRange(nx, ny))
					continue;

				if (visit[nx][ny])
					continue;

				if (board[nx][ny] == 0 || board[nx][ny] == 4)
					continue;

				visit[nx][ny] = true;
				linkedPeopleQueue.addFirst(new Pair(nx, ny));
				findPeopleQueue.add(new Pair(nx, ny));
			}
		}
	}

	private static void newInit(int[][] newBoard) {
		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++) {
				if (board[x][y] == 0) {
					newBoard[x][y] = 0;
				} else {
					newBoard[x][y] = 4;
				}
			}
		}
	}

	private static boolean inRange(int nx, int ny) {
		return 0 <= nx && nx < N && 0 <= ny && ny < N;
	}
}