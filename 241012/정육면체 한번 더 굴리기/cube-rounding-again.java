import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Pair {
	int x;
	int y;
	int d;

	public Pair(int x, int y, int d) {
		this.x = x;
		this.y = y;
		this.d = d;
	}

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

public class Main {
	static int[] dice = {5, 1, 2, 6, 4, 3};
	static int answer;
	static Pair dicePos;
	static int N, M;
	static int[][] board;
	static int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		board = new int[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = sc.nextInt();
			}
		}

		dicePos = new Pair(0, 0, 0);

		for (int t = 1; t <= M; t++) {
			simulation();
		}

		System.out.println(answer);
	}

	private static void simulation() {
		// 1. 벽이면 방향 전환 후 1칸 이동, 아니면 그냥 1칸 이동.
		rollDice();

		// 2. 이동 후 주사위 모양 변환.
		updateDice();

		rotate();

		int score = getScore();
		answer += score;
	}

	private static void rollDice() {
		int x = dicePos.x;
		int y = dicePos.y;
		int d = dicePos.d;

		int nx = x + dirs[d][0];
		int ny = y + dirs[d][1];

		if (!inRange(nx, ny)) {
			d = (d + 2) % 4;

			nx = x + dirs[d][0];
			ny = y + dirs[d][1];
		}

		dicePos = new Pair(nx, ny, d);
	}

	private static void rotate() {
		int x = dicePos.x;
		int y = dicePos.y;
		int d = dicePos.d;

		// 만약, 주사위의 아랫면이 보드의 해당 칸에 있는 숫자보다 크면, 진행방향 90도 회전.
		if (dice[3] > board[x][y]) {
			d = (d + 1) % 4;
		} else if (dice[3] < board[x][y]) {
			d = (d + 3) % 4;
		}
		dicePos = new Pair(x, y, d);
	}

	private static int getScore() {
		int x = dicePos.x;
		int y = dicePos.y;

		Queue<Pair> q = new LinkedList<>();
		q.add(new Pair(x, y));
		boolean[][] visit = new boolean[N][N];
		visit[x][y] = true;

		int sum = board[x][y];
		while (!q.isEmpty()) {
			Pair current = q.poll();

			for (int d = 0; d < 4; d++) {
				int nx = current.x + dirs[d][0];
				int ny = current.y + dirs[d][1];

				if (!inRange(nx, ny))
					continue;

				if (board[nx][ny] != board[x][y])
					continue;

				if (visit[nx][ny])
					continue;

				visit[nx][ny] = true;

				sum += board[nx][ny];
				q.add(new Pair(nx, ny));
			}
		}
		return sum;
	}

	private static boolean inRange(int nx, int ny) {
		return 0 <= nx && nx < N && 0 <= ny && ny < N;
	}

	public static void updateDice() {
		int dir = dicePos.d;
		int[] copyDice = dice.clone();

		// 동 서 남 북 순서.
		switch (dir) {
			case 0: // 동
				dice[1] = copyDice[4];
				dice[4] = copyDice[3];
				dice[3] = copyDice[5];
				dice[5] = copyDice[1];
				break;
			case 1:
				dice[0] = copyDice[3];
				dice[1] = copyDice[0];
				dice[2] = copyDice[1];
				dice[3] = copyDice[2];
				break;
			case 2: // 서
				dice[1] = copyDice[5];
				dice[5] = copyDice[3];
				dice[3] = copyDice[4];
				dice[4] = copyDice[1];
				break;
			case 3:
				dice[0] = copyDice[1];
				dice[1] = copyDice[2];
				dice[2] = copyDice[3];
				dice[3] = copyDice[0];
				break;
		}
	}
}