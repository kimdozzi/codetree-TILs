// https://www.codetree.ai/training-field/frequent-problems/problems/maze-runner

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, M, K; // 격자 크기, 참가자 수, 게임 시간
	static int[][] board; // 0:빈칸, 1~9 벽의 내구도 (0이되면 빈칸이 됨)
	static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 상하좌우 우선순위
	static int answer; // 모든 참가자들의 이동거리 합
	static Pair exits; // 출구 좌표
	static Pair[] travelers;
	static int sx, sy, squareSize;

	public static void main(String[] args) throws IOException {
		// init
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		board = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		travelers = new Pair[M];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			travelers[i] = new Pair(x - 1, y - 1);
		}

		st = new StringTokenizer(br.readLine());
		int x = Integer.parseInt(st.nextToken()) - 1;
		int y = Integer.parseInt(st.nextToken()) - 1;
		exits = new Pair(x, y);

		for (int t = 1; t <= K; t++) {

			// 1. 각 참가자들을 동시에 출구와 가까운 쪽으로 이동시킵니다.
			move();

			// 모든 사람이 출구로 탈출했는지 판단합니다.
			boolean isAllEscaped = true;
			for (int i = 0; i < M; i++) {
				if (!(travelers[i].x == exits.x && travelers[i].y == exits.y)) {
					isAllEscaped = false;
				}
			}
			// 만약 모든 사람이 출구로 탈출했으면 바로 종료합니다.
			if (isAllEscaped)
				break;

			// 2. 한 명 이상의 참가자와 출구를 포함한 가장 작은 정사각형을 돌리기 위해 기준이 되는 좌표 구합니다.
			findPos();

			// 3. 정사각형 내부의 벽을 회전시킵니다.
			rotateSquare();

			// 4. 모든 참가자들 및 출구를 회전시킵니다.
			rotateTravelerAndExit();

		}
		System.out.println(answer);
		System.out.println((exits.x + 1) + " " + (exits.y + 1));
	}

	// 모든 참가자들 및 출구를 회전시킵니다.
	public static void rotateTravelerAndExit() {
		// m명의 참가자들을 모두 확인합니다.
		for (int i = 0; i < M; i++) {
			int x = travelers[i].x;
			int y = travelers[i].y;
			// 해당 참가자가 정사각형 안에 포함되어 있을 때에만 회전시킵니다.
			if (sx <= x && x < sx + squareSize && sy <= y && y < sy + squareSize) {
				// Step 1. (sx, sy)를 (0, 0)으로 옮겨주는 변환을 진행합니다.
				int ox = x - sx, oy = y - sy;

				// Step 2. 변환된 상태에서는 회전 이후의 좌표가 (x, y) -> (y, squareN - x - 1)가 됩니다.
				int rx = oy, ry = squareSize - ox - 1;

				// Step 3. 다시 (sx, sy)를 더해줍니다.
				travelers[i].x = rx + sx;
				travelers[i].y = ry + sy;
			}
		}

		// 출구에도 회전을 진행합니다.
		int x = exits.x;
		int y = exits.y;
		if (sx <= x && x < sx + squareSize && sy <= y && y < sy + squareSize) {
			// Step 1. (sx, sy)를 (0, 0)으로 옮겨주는 변환을 진행합니다.
			int ox = x - sx, oy = y - sy;
			// Step 2. 변환된 상태에서는 회전 이후의 좌표가 (x, y) -> (y, squareN - x - 1)가 됩니다.
			int rx = oy, ry = squareSize - ox - 1;
			// Step 3. 다시 (sx, sy)를 더해줍니다.
			exits.x = rx + sx;
			exits.y = ry + sy;
		}
	}

	private static void rotateSquare() {

		int[][] tempBoard = new int[N][N];

		for (int x = sx; x < sx + squareSize; x++) {
			for (int y = sy; y < sy + squareSize; y++) {
				// 1. (0,0)으로 옮깁니다.
				int ox = x - sx, oy = y - sy;

				// 2. 90도 회전했을 때의 좌표로 위치를 옮깁니다.
				int rx = oy, ry = squareSize - ox - 1;

				// 3. 다시 원래 좌표로 바꿉니다.
				int nx = sx + rx, ny = sy + ry;
				tempBoard[nx][ny] = board[x][y];
			}
		}

		for (int x = sx; x < sx + squareSize; x++) {
			for (int y = sy; y < sy + squareSize; y++) {
				board[x][y] = tempBoard[x][y];
				// 벽의 내구도를 1 내립니다.
				if (board[x][y] > 0)
					board[x][y] = Math.max(board[x][y] - 1, 0);
			}
		}
	}

	private static void findPos() {
		// 가장 작은 정사각형부터 모든 정사각형을 만들어봅니다.
		for (int sz = 2; sz <= N; sz++) {
			// 가장 좌상단 r 좌표가 작은 것부터 하나씩 만들어봅니다.
			for (int x1 = 0; x1 < N - sz + 1; x1++) {
				// 가장 좌상단 c 좌표가 작은 것부터 하나씩 만들어봅니다.
				for (int y1 = 0; y1 < N - sz + 1; y1++) {
					int x2 = x1 + sz - 1;
					int y2 = y1 + sz - 1;

					// 만약 출구가 해당 정사각형 안에 없다면 스킵합니다.
					if (!(x1 <= exits.x && exits.x <= x2 && y1 <= exits.y && exits.y <= y2)) {
						continue;
					}

					// 한 명 이상의 참가자가 해당 정사각형 안에 있는지 판단합니다.
					boolean isTravelerIn = false;
					for (int l = 0; l < M; l++) {
						if (x1 <= travelers[l].x && travelers[l].x <= x2 && y1 <= travelers[l].y
							&& travelers[l].y <= y2) {
							// 출구에 있는 참가자는 제외합니다.
							if (!(travelers[l].x == exits.x && travelers[l].y == exits.y)) {
								isTravelerIn = true;
							}
						}
					}

					// 만약 한 명 이상의 참가자가 해당 정사각형 안에 있다면
					// sx, sy, sqaureSize 정보를 갱신하고 종료합니다.
					if (isTravelerIn) {
						sx = x1;
						sy = y1;
						squareSize = sz;

						return;
					}
				}
			}
		}
	}

	private static void move() {
		// m명의 모든 참가자들에 대해 이동을 진행합니다.
		for (int i = 0; i < M; i++) {
			// 이미 출구에 있는 경우 스킵합니다.
			if (travelers[i].x == exits.x && travelers[i].y == exits.y)
				continue;

			// 행이 다른 경우 행을 이동시켜봅니다.
			if (travelers[i].x != exits.x) {
				int nx = travelers[i].x;
				int ny = travelers[i].y;

				if (exits.x > nx)
					nx++;
				else
					nx--;

				// 벽이 없다면 행을 이동시킬 수 있습니다.
				// 이 경우 행을 이동시키고 바로 다음 참가자로 넘어갑니다.
				if (board[nx][ny] == 0) {
					travelers[i].x = nx;
					travelers[i].y = ny;
					answer++;
					continue;
				}
			}

			// 열이 다른 경우 열을 이동시켜봅니다.
			if (travelers[i].y != exits.y) {
				int nx = travelers[i].x;
				int ny = travelers[i].y;

				if (exits.y > ny)
					ny++;
				else
					ny--;

				// 벽이 없다면 행을 이동시킬 수 있습니다.
				// 이 경우 열을 이동시킵니다.
				if (board[nx][ny] == 0) {
					travelers[i].x = nx;
					travelers[i].y = ny;
					answer++;
					continue;
				}
			}
		}
	}

	private static boolean inRange(int nx, int ny) {
		return 0 <= nx && nx < N && 0 <= ny && ny < N;
	}

	static class Pair {
		int x;
		int y;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}