import java.util.ArrayList;
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

class Group {
	int num;
	int cnt;
	int adjacent;

	public Group(int num, int cnt, int adjacent) {
		this.num = num;
		this.cnt = cnt;
		this.adjacent = adjacent;
	}
}

public class Main {
	static int N, K = 2;
	static int[][] board; // 격자판 정보
	static List<List<Pair>> groups; // 각 그룹의 좌표를 담는 리스트 (조합 사용할 예정)
	static boolean[] groupVisit; // 조합을 위한 방문 배열
	static int[] groupCombination;
	static int answer;
	static boolean[][] visit;
	static int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		N = sc.nextInt();
		board = new int[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = sc.nextInt();
			}
		}

		// 초기 예술 점수
		simulation();

		for (int t = 1; t <= 3; t++) {
			// 회전.
			rotate();

			simulation();
		}
		System.out.println(answer);

	}

	private static void simulation() {
		visit = new boolean[N][N];
		groups = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (!visit[i][j]) {
					findGroup(i, j);
				}
			}
		}
		groupVisit = new boolean[groups.size()];
		groupCombination = new int[K];
		rec_func(0, 0);
	}

	private static void rotate() {
		int[][] rotate270 = new int[N][N]; // 시계방향 회전 (십자 모양)

		// 십자 모양 회전
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				rotate270[N - j - 1][i] = board[i][j];
			}
		}

		int centerX = N / 2;
		int centerY = N / 2;

		rotate90(0, 0, N / 2);
		rotate90(0, centerY + 1, N / 2);
		rotate90(centerX + 1, 0, N / 2);
		rotate90(centerX + 1, centerY + 1, N / 2);

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == centerX || j == centerY) {
					board[i][j] = rotate270[i][j];
				}
			}
		}
	}

	private static void rotate90(int sx, int sy, int length) {
		int[][] temp = new int[N][N];

		for (int x = sx; x < sx + length; x++) {
			for (int y = sy; y < sy + length; y++) {
				// 1. (0,0) 좌표 구함.
				int ox = x - sx, oy = y - sy;

				// 2. rotate 90 좌표 구함
				int rx = oy, ry = length - ox - 1;

				temp[sx + rx][sy + ry] = board[x][y];
			}
		}

		for (int x = sx; x < sx + length; x++) {
			for (int y = sy; y < sy + length; y++) {
				board[x][y] = temp[x][y];
			}
		}
	}

	private static void printBoard(int[][] board) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	private static void printGroup() {
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i) == null)
				continue;
			List<Pair> group = groups.get(i);
			for (int j = 0; j < group.size(); j++) {
				System.out.println(group.get(j).x + " " + group.get(j).y);
			}
			System.out.println();
		}
	}

	private static void findGroup(int x, int y) {
		List<Pair> list = new ArrayList<>();
		list.add(new Pair(x, y));
		int groupNumber = board[x][y];
		Queue<Pair> q = new LinkedList<>();
		q.add(new Pair(x, y));
		visit[x][y] = true;

		while (!q.isEmpty()) {
			Pair current = q.poll();
			for (int d = 0; d < 4; d++) {
				int nx = current.x + dirs[d][0];
				int ny = current.y + dirs[d][1];

				if (!inRange(nx, ny))
					continue;

				if (board[nx][ny] != groupNumber)
					continue;

				if (visit[nx][ny])
					continue;

				visit[nx][ny] = true;

				list.add(new Pair(nx, ny));
				q.add(new Pair(nx, ny));
			}
		}

		groups.add(list);
	}

	private static boolean inRange(int nx, int ny) {
		return 0 <= nx && nx < N && 0 <= ny && ny < N;
	}

	private static void rec_func(int start, int cnt) {
		if (cnt == K) {
			getScore(groupCombination[0], groupCombination[1]);
			return;
		}
		for (int i = start; i < groups.size(); i++) {
			if (groupVisit[i])
				continue;
			groupVisit[i] = true;
			groupCombination[cnt] = i;
			rec_func(i + 1, cnt + 1);
			groupVisit[i] = false;
		}
	}

	private static void getScore(int groupA, int groupB) {
		List<Pair> A = groups.get(groupA);
		List<Pair> B = groups.get(groupB);

		int numberA = board[A.get(0).x][A.get(0).y];
		int numberB = board[B.get(0).x][B.get(0).y];

		int countA = A.size();
		int countB = B.size();

		int adjacentSpaceCount = 0;

		for (int i = 0; i < A.size(); i++) {
			Pair pairA = A.get(i);
			for (int j = 0; j < B.size(); j++) {
				Pair pairB = B.get(j);
				if (Math.abs(pairA.x - pairB.x) + Math.abs(pairA.y - pairB.y) == 1) {
					adjacentSpaceCount++;
				}
			}
		}
		if (adjacentSpaceCount == 0)
			return;

		int score = calculate(countA, countB, numberA, numberB, adjacentSpaceCount);
		answer += score;
	}

	private static int calculate(int countA, int countB, int numberA, int numberB, int adjacentSpaceCount) {
		return (countA + countB) * numberA * numberB * adjacentSpaceCount;
	}
}