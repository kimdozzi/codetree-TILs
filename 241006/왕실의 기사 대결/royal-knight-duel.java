import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static int L, N, Q; // 정사각형 격자 정보 L, 기사의 수 N, Q개의 명령
	static int[][] board; // 기사들의 정보와 벽의 정보를 다룸.
	static int[][] trap; // 트랩 정보를 다룸.
	static int[] hp; // 기사들의 체력 정보
	static int[] originalHp; // 기사들의 처음 체력 정보
	static boolean[] alive; // 기사들의 생존 여부
	static boolean[] pushStatus; // 기사들의 밀린 여부
	static int[][] pos; // 각 인덱스에 해당하는 기사들의 좌표정보를 다룸.(좌상단 기준)
	static int[][] size;
	static int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
	static int answer; // 생존한 기사들이 총 받은 데미지의 합

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		L = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());

		board = new int[L][L];
		trap = new int[L][L];
		hp = new int[N + 1];
		originalHp = new int[N + 1];
		alive = new boolean[N + 1];
		pushStatus = new boolean[N + 1];
		pos = new int[N + 1][2];
		size = new int[N + 1][2];
		answer = 0;

		for (int i = 1; i <= N; i++) {
			alive[i] = true; // 초기에는 모두 생존.
		}

		for (int i = 0; i < L; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < L; j++) {
				int data = Integer.parseInt(st.nextToken());
				if (data == 1) {
					trap[i][j] = data; // 함정이라면 트랩 격자에 1 저장.
				} else {
					if (data == 2)
						board[i][j] = -1; // 벽이라면 -1
					else
						board[i][j] = data; // 빈칸이라면 0
				}
			}
		}

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int h = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());

			pos[i][0] = r - 1;
			pos[i][1] = c - 1;
			size[i][0] = h;
			size[i][1] = w;
			hp[i] = k;
			originalHp[i] = k;
		}

		for (int i = 1; i <= N; i++) {
			int sx = pos[i][0];
			int sy = pos[i][1];
			for (int x = sx; x < sx + size[i][0]; x++) {
				for (int y = sy; y < sy + size[i][1]; y++) {
					board[x][y] = i;
				}
			}
		}

		for (int t = 1; t <= Q; t++) {
			st = new StringTokenizer(br.readLine());
			int idx = Integer.parseInt(st.nextToken()); // 명령을 받은 기사의 번호
			int d = Integer.parseInt(st.nextToken()); // 기사가 이동할 방향 정보
			// System.out.println(idx + "번 기사 명령 받음 : 방향 -> " + d);

			// 죽은 사람이라면 넘어감.
			if (!alive[idx])
				continue;

			// 이동하기 전 기사들의 위치 정보
			int[][] prevPos = new int[N + 1][2];
			for (int i = 1; i <= N; i++) {
				prevPos[i] = pos[i].clone();
			}

			boolean isMove = rec_func(idx, d);
			// System.out.println("움직임 성공 여부 : " + isMove);
			// System.out.println();
			// 만약 이동에 실패했다면, 이전 위치 복귀
			if (!isMove) {
				for (int i = 1; i <= N; i++) {
					pos[i] = prevPos[i].clone();
					pushStatus[i] = false;
				}
				continue;
			} else {
				updatePosition(prevPos);
			}

			// print(board);
			for (int i = 1; i <= N; i++) {
				// System.out.println(i + "번 기사의 현재 좌표 : : (" + pos[i][0] + " " + pos[i][1] + ")");
			}

			damage(idx);

			isAlive();

			for (int i = 1; i <= N; i++) {
				pushStatus[i] = false;
			}

			// System.out.println("각 턴이 끝난 최종 보드");
			// print(board);
			// System.out.println();
		}

		for (int i = 1; i <= N; i++) {
			if (alive[i]) {
				answer += originalHp[i] - hp[i];
			}
		}
		System.out.println(answer);
	}

	private static void isAlive() {
		List<Integer> deadPeople = new ArrayList<>();

		for (int i = 1; i <= N; i++) {
			if (hp[i] <= 0) { // 기사가 죽었다면
				alive[i] = false; // 죽은 사람 표시 남김.
				deadPeople.add(i);
			}
		}

		// 죽은 사람의 맵 정보 수정.
		for (int i = 0; i < deadPeople.size(); i++) {
			int num = deadPeople.get(i);
			int x = pos[num][0];
			int y = pos[num][1];

			for (int j = x; j < x + size[num][0]; j++) {
				for (int k = y; k < y + size[num][1]; k++) {
					board[j][k] = 0;
				}
			}
		}
	}

	private static void damage(int exceptIdx) {
		// System.out.println("현재 트랩 위치 ");

		// for (int i = 0; i < L; i++) {
		// 	for (int j = 0; j < L; j++) {
		// 		System.out.print(trap[i][j] + " ");
		// 	}
		// 	System.out.println();
		// }
		// System.out.println();

		for (int i = 0; i < L; i++) {
			for (int j = 0; j < L; j++) {
				if (board[i][j] > 0 && trap[i][j] == 1) {
					if (board[i][j] == exceptIdx)
						continue;
					if (pushStatus[board[i][j]]) {
						hp[board[i][j]]--;
					}
					// System.out.println("데미지 받는 기사 번호 : " + board[i][j]);

				}
			}
		}
		// for (int i = 0; i < hp.length; i++) {
		// 	System.out.print(hp[i] + " ");
		// }
		//
		// System.out.println();
	}

	private static void updatePosition(int[][] prevPos) {
		for (int i = 1; i <= N; i++) {
			if (!alive[i])
				continue;
			int px = prevPos[i][0];
			int py = prevPos[i][1];
			for (int x = px; x < px + size[i][0]; x++) {
				for (int y = py; y < py + size[i][1]; y++) {
					board[x][y] = 0; // 기존 좌표 없앰.
				}
			}
		}
		// System.out.println("이전 좌표를 지운 보드");
		// print(board);
		// System.out.println();

		for (int i = 1; i <= N; i++) {
			if (!alive[i])
				continue;
			int sx = pos[i][0];
			int sy = pos[i][1];
			for (int x = sx; x < sx + size[i][0]; x++) {
				for (int y = sy; y < sy + size[i][1]; y++) {
					// System.out.println("새로운 좌표 : " + x + " " + y + " : " + i);
					board[x][y] = i; // 새로운 좌표로 갱신
				}
			}
		}
		// System.out.println("갱신 좌표를 적용한 보드");
		// print(board);
		// System.out.println();
	}

	private static boolean rec_func(int idx, int d) {
		int x = pos[idx][0]; // 기존 x 좌표
		int y = pos[idx][1]; // 기존 y 좌표

		int nx = x + dirs[d][0]; // 이동하려는 x 좌표
		int ny = y + dirs[d][1]; // 이동하려는 y 좌표

		// System.out.println(x + " " + y + " -> " + nx + " " + ny);

		List<Integer> peopleList = new ArrayList<>();

		for (int i = nx; i < nx + size[idx][0]; i++) { // 이동하려는 위치에 기사가 자신의 크기를 차지했을 때
			for (int j = ny; j < ny + size[idx][1]; j++) {
				if (i == -1 || j == -1)
					break;
				int num = board[i][j];
				if (num == idx)
					continue; // 자기 자신일 경우 continue.

				if (num != 0) { // 빈공간이 아닐 때
					if (num == -1) { // 벽이라면 이동할 수 없으므로 이동을 종료.
						return false;
					} else { // 다른 기사가 해당 위치에 존재한다면
						peopleList.add(num); // 연쇄로 밀어주기 위해 리스트에 삽입.
						pushStatus[num] = true;
					}
				}
			}
		}

		// list에 아무런 정보가 없다는 것은, 이동하려는 위치는 모두 빈 공간이라는 뜻.
		if (peopleList.isEmpty()) {
			pos[idx][0] = nx; // 다음 칸으로 위치정보 갱신 후 종료.
			pos[idx][1] = ny;
			return true;
		}

		// 해당 공간에 위치한 기사들을 for문을 반복해 연쇄로 밀어주기.
		for (int i = 0; i < peopleList.size(); i++) {
			boolean isAvailable = rec_func(peopleList.get(i), d);
			if (!isAvailable)
				return false;
		}

		// 다른 연쇄 작업을 성공한 경우, 기존 좌표도 이동.
		pos[idx][0] = nx;
		pos[idx][1] = ny;
		return true;
	}

	private static void print(int[][] board) {
		for (int x = 0; x < L; x++) {
			for (int y = 0; y < L; y++) {
				System.out.print(board[x][y] + " ");
			}
			System.out.println();
		}
	}
}