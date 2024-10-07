// https://www.codetree.ai/training-field/frequent-problems/problems/maze-runner

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int N, M, K; // 격자 크기, 참가자 수, 게임 시간
	static int[][] board; // 0:빈칸, 1~9 벽의 내구도 (0이되면 빈칸이 됨)
	static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 상하좌우 우선순위
	static int answer; // 모든 참가자들의 이동거리 합
	static int[] entrance; // 출구 좌표
	static boolean[] isOut; // 참가자의 탈출 여부
	static Queue<int[]> peopleList; // 참가자들의 좌표

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

		peopleList = new LinkedList<>();
		isOut = new boolean[M];

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			peopleList.add(new int[] {x - 1, y - 1});
			isOut[i] = false;
		}
		st = new StringTokenizer(br.readLine());
		int x = Integer.parseInt(st.nextToken()) - 1;
		int y = Integer.parseInt(st.nextToken()) - 1;
		entrance = new int[] {x, y};

		for (int t = 1; t <= K; t++) {
			// 1. 먼저 보드에 아직 탈출하지 못한 참가자와 출구를 표시합니다.
			makeBoard();

			// 2. 각 참가자들을 동시에 출구와 가까운 쪽으로 이동시킵니다.
			move();

			// 3. 한 명 이상의 참가자와 출구를 포함한 가장 작은 정사각형을 돌리기 위해 기준이 되는 좌표 구합니다.
			int[] pos = findPos();

			rotate90(pos[0], pos[1], pos[2]);


		}
		System.out.println(answer);
		System.out.println((entrance[0] + 1) + " " + (entrance[1] + 1));
	}

	private static void rotate90(int sx, int sy, int length) {

		int[][] tempBoard = new int[N][N];

		for (int x = sx; x < sx + length; x++) {
			for (int y = sy; y < sy + length; y++) {
				// 1. (0,0)으로 옮깁니다.
				int ox = x - sx, oy = y - sy;

				// 2. 90도 회전했을 때의 좌표로 위치를 옮깁니다.
				int rx = oy, ry = length - ox - 1;

				// 3. 다시 원래 좌표로 바꿉니다.
				int nx = sx + rx, ny = sy + ry;
				tempBoard[nx][ny] = board[x][y];
			}
		}

		for (int x = sx; x < sx + length; x++) {
			for (int y = sy; y < sy + length; y++) {
				board[x][y] = tempBoard[x][y];
				// 벽의 내구도를 1 내립니다.
				if (board[x][y] > 0)
					board[x][y] = Math.max(board[x][y] - 1, 0);
			}
		}

		// 돌리고 난 후 입구 정보와 참가자의 정보를 갱신해줍니다.
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j] == -1) {
					peopleList.add(new int[] {i, j});
				} else if (board[i][j] == -2) {
					entrance[0] = i;
					entrance[1] = j;
				}
			}
		}
	}

	private static int[] findPos() {
		int size = 2; // 2*2 정사각형부터 10*10(최대) 사이즈까지 완전탐색을 수행합니다.
		boolean isExistPeople = false;
		boolean isExistEntrance = false;

		while (size <= 10) {
			for (int i = 0; i < N - size; i++) {
				for (int j = 0; j < N - size; j++) {
					for (int r = i; r < i + size; r++) {
						for (int c = j; c < j + size; c++) {
							if (board[r][c] == -1) {
								isExistPeople = true;
							}
							if (board[r][c] == -2) {
								isExistEntrance = true;
							}
						}
					}
					if (isExistEntrance && isExistPeople) {
						return new int[] {i, j, size};
					} else {
						isExistPeople = false;
						isExistEntrance = false;
					}

				}
			}
			size++;
		}

		return new int[] {0, 0, 0};
	}

	private static void printPeoplePosition() {
		Iterator<int[]> iterator = peopleList.iterator();
		while (iterator.hasNext()) {
			int[] next = iterator.next();
			System.out.println("위치: " + next[0] + " " + next[1]);
		}
		System.out.println();
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

	private static void move() {
		int ex = entrance[0];
		int ey = entrance[1];

		Queue<int[]> q = new LinkedList<>();
		while (!peopleList.isEmpty()) {
			q.add(peopleList.poll());
		}

		while (!q.isEmpty()) {
			int[] xyPos = q.poll();

			int x = xyPos[0];
			int y = xyPos[1];

			for (int d = 0; d < 4; d++) {
				int nx = x + dirs[d][0];
				int ny = y + dirs[d][1];

				// 참가자가 탈출구에 도착했다면
				// 이전 위치는 빈칸(0)으로 만들고, 탈출 여부를 표시합니다.
				// 그리고, 나머지 단계는 스킵합니다.
				if (nx == entrance[0] && ny == entrance[1]) {
					answer++;
					board[x][y] = 0;
					continue;
				}

				// 격자를 벗어난다면 continue
				if (!inRange(nx, ny))
					continue;

				// 해당 위치가 벽이라면 continue;
				if (1 <= board[nx][ny] && board[nx][ny] <= 9)
					continue;

				// 기존 위치에서 출구 까지의 거리와 이동하려는 위치에서 출구 까지의 거리를 구합니다.
				int originalDist = Math.abs(x - ex) + Math.abs(y - ey);
				int MoveDist = Math.abs(nx - ex) + Math.abs(ny - ey);

				// 기존 거리보다 이동할 거리의 거리가 짧다면, 해당 위치로 이동합니다.
				if (MoveDist < originalDist) {
					// 맵에 반영.
					board[x][y] = 0;
					board[nx][ny] = -1;
					answer++;
					//peopleList.add(new int[] {nx, ny});
					break;
				}
			}
			//peopleList.add(new int[] {x, y});
		}
	}

	private static boolean inRange(int nx, int ny) {
		return 0 <= nx && nx < N && 0 <= ny && ny < N;
	}

	private static void makeBoard() {
		// 탈출하지 못한 참가자 표시
		Iterator<int[]> iterator = peopleList.iterator();
		while (iterator.hasNext()) {
			int[] next = iterator.next();
			board[next[0]][next[1]] = -1;
		}

		// 입구 표시
		board[entrance[0]][entrance[1]] = -2;
	}
}