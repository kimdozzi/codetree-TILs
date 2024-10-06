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
	static boolean[] onePush; // 1번만 밀기 위한 상태 배열
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
		onePush = new boolean[N + 1];
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

		for (int t = 1; t <= Q; t++) {
			st = new StringTokenizer(br.readLine());
			int idx = Integer.parseInt(st.nextToken()); // 명령을 받은 기사의 번호
			int d = Integer.parseInt(st.nextToken()); // 기사가 이동할 방향 정보
			// System.out.println(idx + "번 기사 명령 : 방향 -> " + d);

			// 0. 죽은 사람이라면 더 이상 진행하지 않습니다.
			if (isDead(idx))
				continue;

			// 1. 시작 시, 생존한 기사들의 정보를 map에 그립니다.
			drawMap();
			// print(board);
			// System.out.println();

			// 이동하기 전, 기사들의 위치 정보를 담아줍니다.
			// 그 이유는 최종적인 이동을 실패하였을 경우, 되돌아가기 위함입니다.
			int[][] prevPos = new int[N + 1][2];
			for (int i = 1; i <= N; i++) {
				if (isDead(i))
					continue;
				prevPos[i] = pos[i].clone();
			}

			boolean isMove = rec_func(idx, d);

			// 만약 이동에 실패했다면, 이전 위치 복귀
			if (!isMove) {
				initPushStatus(); // pushStatus 배열 초기화.

				// 이전 위치 정보로 다시 되돌림.
				for (int i = 1; i <= N; i++) {
					pos[i] = prevPos[i].clone();
				}
				continue;
			}
			// 이동에 성공했다면, 업데이트 된 맵을 그림.
			else {
				updatePosition(prevPos);
			}

			// 밀린 기사들에 대해서만 데미지 부여.
			damage(idx);

			// 기사들이 살아있는 지 여부를 확인. 죽었다면 처리.
			isAlive();

			// 밀었던 기사들의 상태 정보 초기화.
			initPushStatus();
		}

		for (int i = 1; i <= N; i++) {
			if (alive[i]) {
				answer += originalHp[i] - hp[i];
			}
		}
		System.out.println(answer);
	}

	private static void initPushStatus() {
		for (int i = 1; i <= N; i++) {
			pushStatus[i] = false;
			onePush[i] = false;
		}
	}

	private static boolean isDead(int idx) {
		return !alive[idx];
	}

	private static void drawMap() {
		for (int i = 1; i <= N; i++) {
			if (isDead(i))
				continue; // 죽은 사람은 그리지 않음.

			int sx = pos[i][0];
			int sy = pos[i][1];
			for (int x = sx; x < sx + size[i][0]; x++) {
				for (int y = sy; y < sy + size[i][1]; y++) {
					board[x][y] = i;
				}
			}
		}
	}

	private static void isAlive() {
		List<Integer> deadPeople = new ArrayList<>();

		// 살아있는 기사들에 대해 hp 정보 확인.
		for (int i = 1; i <= N; i++) {
			if (isDead(i))
				continue;
			if (hp[i] <= 0) { // 해당 기사가 죽었다면 죽은 사람 표시 남김.
				alive[i] = false;
				deadPeople.add(i); // 죽은 기사 정보를 가지는 리스트에 저장.
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
		// print(board);
		// System.out.println();
		// print(trap);

		for (int i = 0; i < L; i++) {
			for (int j = 0; j < L; j++) {

				// 해당 좌표에 기사가 존재하고, 트랩이 있을 경우
				if (board[i][j] > 0 && trap[i][j] == 1) {
					if (board[i][j] == exceptIdx) // 자기 자신(명령을 받은 기사)일 경우 넘어감.
						continue;
					if (pushStatus[board[i][j]]) { // 밀린 기사일 경우 데미지 부여.
						hp[board[i][j]]--;
					}
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
			if (isDead(i))
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
			if (isDead(i))
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
		if (!inRange(nx, ny))
			return false;

		List<Integer> peopleList = new ArrayList<>();
		for (int i = nx; i < nx + size[idx][0]; i++) { // 이동하려는 위치에 기사가 자신의 크기를 차지했을 때
			for (int j = ny; j < ny + size[idx][1]; j++) {

				if (!inRange(i, j)) // 격자를 벗어나는 크기이면 종료.
					return false;

				int num = board[i][j]; // 해당 위치의 기사의 번호를 num에 저장.
				if (num == idx) // 자기 자신일 경우 continue.
					continue;

				if (num != 0) { // 이동하려는 곳이 빈 공간이 아닐 때
					if (num == -1) { // 벽이라면 이동할 수 없으므로 이동을 종료.
						return false;
					} else { // 다른 기사가 해당 위치에 존재한다면
						peopleList.add(num); // 연쇄로 밀어주기 위해 리스트에 삽입.
						pushStatus[num] = true; // 해당 기사를 밀었는지 여부를 확인하기 위한 배열.
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
			if (onePush[i])
				continue;
			onePush[i] = true;
			boolean isAvailable = rec_func(peopleList.get(i), d);
			if (!isAvailable)
				return false;
		}

		// 다른 연쇄 작업을 성공한 경우, 기존 좌표도 이동.
		pos[idx][0] = nx;
		pos[idx][1] = ny;
		return true;
	}

	private static boolean inRange(int nx, int ny) {
		return 0 <= nx && nx < L && 0 <= ny && ny < L;
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