import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int N, M, K; // 행, 열, 총 수행 횟수
	static boolean[][] brokenGunBoard; // 부서진 포탑의 정보를 가짐. true: 부서짐 / false: 부서지지 않음
	static GunInfo[][] gunInfoBoard; // 포탑의 정보를 갖고 있는 보드.
	static boolean[][] attackedGun; // 레이저 공격 시 공격 포탑과 공격을 받은 포탑을 제외한 나머지 포탑의 공격력 1 증가를 위한 배열.
	static List<Pair> latestAttackGun; // 최근에 공격한 포탑의 좌표 정보를 가짐.

	static AttackerInfo attackerInfo; // 공격자 정보를 가짐.
	static Pair targetInfo; // 목표 대상의 정보를 가짐.
	static boolean[][] visit; // 레이저 공격 시 최단 경로로 탐색할 때, 방문 여부를 체크하는 배열.

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		brokenGunBoard = new boolean[N][M];
		gunInfoBoard = new GunInfo[N][M];
		attackedGun = new boolean[N][M];
		latestAttackGun = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				int damage = Integer.parseInt(st.nextToken());
				gunInfoBoard[i][j] = new GunInfo(i, j, (i + j), damage);

				// 최초에 공격력이 0인 포탑을 기록해둠.
				if (gunInfoBoard[i][j].damage == 0)
					brokenGunBoard[i][j] = true;
			}
		}

		for (int t = 1; t <= K; t++) {
			// 0. 살아있는 포탑이 1개면 종료.
			int isAlive = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if (brokenGunBoard[i][j])
						continue;
					isAlive++;
				}
			}

			if (isAlive == 1)
				break;

			// 1. 공격자 선정
			chooseAttacker(t);
			// 핸디캡 (N+M)만큼 공격력 부여.
			gunInfoBoard[attackerInfo.x][attackerInfo.y].damage += N + M;

			// 2. 공격자의 공격
			chooseTarget(t);

			// 3. 공격 방법 선택 및 공격
			chooseMethod();

			// 4. 포탑 부서짐
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if (gunInfoBoard[i][j].damage <= 0) {
						brokenGunBoard[i][j] = true;
					}
				}
			}

			// 5. 포탑 정비
			if (t == K)
				continue;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if (brokenGunBoard[i][j])
						continue;

					if (attackedGun[i][j]) {
						attackedGun[i][j] = false;
						continue;
					}

					gunInfoBoard[i][j].damage += 1;
				}
			}
			latestAttackGun.add(new Pair(attackerInfo.x, attackerInfo.y));
		}

		int answer = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				answer = Math.max(answer, gunInfoBoard[i][j].damage);
			}
		}
		System.out.println(answer);
	}

	private static void chooseMethod() {
		int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}}; // 우 하 좌 상

		// 1. 레이저 공격
		Queue<AttackerInfo> q = new LinkedList<>();
		visit = new boolean[N][M];

		List<Pair> initialPath = new ArrayList<>();
		initialPath.add(new Pair(attackerInfo.x, attackerInfo.y));
		q.add(new AttackerInfo(attackerInfo.x, attackerInfo.y, initialPath));
		visit[attackerInfo.x][attackerInfo.y] = true;

		AttackerInfo finalAttackerInfo = null;

		while (!q.isEmpty()) {
			AttackerInfo info = q.poll();

			// 최단 경로로 목표물 좌표에 도달할 수 있다면.
			if (info.x == targetInfo.x && info.y == targetInfo.y) {
				finalAttackerInfo = info;
				break;
			}

			for (int i = 0; i < 4; i++) {
				int nx = (N + info.x + dirs[i][0]) % N;
				int ny = (M + info.y + dirs[i][1]) % M;

				// 부서진 포탑이 있다면 지날 수 없음.
				if (brokenGunBoard[nx][ny])
					continue;

				// 이미 방문한 경우, 다시 방문할 수 없음.
				if (visit[nx][ny])
					continue;


				visit[nx][ny] = true;
				List<Pair> newPath = new ArrayList<>(info.path);
				newPath.add(new Pair(nx, ny));
				q.add(new AttackerInfo(nx, ny, newPath));
			}
		}
		// 해당 경로가 존재한다면
		int finalDamage = gunInfoBoard[attackerInfo.x][attackerInfo.y].damage;

		if (finalAttackerInfo != null) {

			// i가 1부터인 이유는 공격자 경로 제외.
			for (int i = 1; i < finalAttackerInfo.path.size(); i++) {
				Pair pair = finalAttackerInfo.path.get(i);

				if (brokenGunBoard[pair.x][pair.y])
					continue;

				if (pair.x == targetInfo.x && pair.y == targetInfo.y) {
					gunInfoBoard[pair.x][pair.y].damage -= finalDamage;
				} else {
					gunInfoBoard[pair.x][pair.y].damage -= (finalDamage / 2);
				}
				attackedGun[pair.x][pair.y] = true;
			}
			return;
		}

		// 존재하지 않는다면
		// 2. 포탄 공격
		int x = targetInfo.x;
		int y = targetInfo.y;
		for (int d = 0; d < 8; d++) {
			int nx = (N + x + dirs[d][0]) % N;
			int ny = (M + y + dirs[d][1]) % M;

			// 부서진 포탑이면 패스
			if (brokenGunBoard[nx][ny])
				continue;

			// 공격자이면 패스
			if (nx == attackerInfo.x && ny == attackerInfo.y)
				continue;

			if (nx == x && ny == y) {
				gunInfoBoard[nx][ny].damage -= finalDamage;
			} else {
				gunInfoBoard[nx][ny].damage -= (finalDamage / 2);
			}
			attackedGun[nx][ny] = true;
		}
	}

	private static void chooseTarget(int time) {
		int targetX = -1, targetY = -1;
		int MIN_DAMAGE = Integer.MIN_VALUE;

		targetInfo = new Pair(-1, -1);

		// 공격력이 가장 높은 포탑들을 저장.
		List<GunInfo> candidates = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				// 부서진 포탑이면 패스.
				if (brokenGunBoard[i][j])
					continue;

				// 해당 좌표가 공격자 자기 자신이면 패스.
				if (attackerInfo.x == i && attackerInfo.y == j)
					continue;

				GunInfo gunInfo = gunInfoBoard[i][j];
				if (gunInfo.damage >= MIN_DAMAGE) {
					MIN_DAMAGE = gunInfo.damage;

					targetX = i;
					targetY = j;
				}
			}
		}
		// 맨 처음에 구한 가장 높은 공격력을 가진 포탑의 정보를 저장.
		candidates.add(new GunInfo(targetX, targetY, (targetX + targetY), gunInfoBoard[targetX][targetY].damage));

		// 가장 높은 공격력을 가진 포탑과 동일한 공격력을 가진 포탑들을 후보자로 넣음.
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (brokenGunBoard[i][j])
					continue;

				// 해당 좌표가 공격자 자기 자신이면 패스.
				if (attackerInfo.x == i && attackerInfo.y == j)
					continue;

				if (targetX != i && targetY != j && gunInfoBoard[i][j].damage == candidates.get(0).damage) {
					candidates.add(new GunInfo(i, j, (i + j), gunInfoBoard[i][j].damage));
				}
			}
		}

		// 가장 최근에 공격한 포탑이 가장 강한 포탑.
		if (time > 1 && !latestAttackGun.isEmpty()) {
			Pair latestPair = latestAttackGun.get(0);
			for (int i = 0; i < candidates.size(); i++) {
				GunInfo candidatePair = candidates.get(i);
				if (latestPair.x == candidatePair.x && latestPair.y == candidatePair.y) {

					// 타겟 포탑정보에 저장.
					targetInfo.x = candidatePair.x;
					targetInfo.y = candidatePair.y;
					return;
				}
			}
		}

		// 행과 열의 합이 가장 큰 포탑. 아니라면 열 값이 가장 큰 포탑.
		Collections.sort(candidates, new Comparator<GunInfo>() {
			@Override
			public int compare(GunInfo o1, GunInfo o2) {
				if (o1.totalXY == o2.totalXY) {
					return o1.y - o2.y;
				} else {
					return o1.totalXY - o2.totalXY;
				}
			}
		});
		GunInfo gunInfo = candidates.get(0);

		// 타겟 포탑정보에 저장.
		targetInfo.x = gunInfo.x;
		targetInfo.y = gunInfo.y;
	}

	private static void chooseAttacker(int time) {
		attackerInfo = new AttackerInfo(-1, -1, new ArrayList<>());

		// 공격력이 가장 낮은 포탑들을 저장.
		List<GunInfo> candidates = new ArrayList<>();

		// 가장 낮은 공격력을 가진 포탑을 먼저 구함.
		int MAX_DAMAGE = Integer.MAX_VALUE;
		int tempX = -1, tempY = -1;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				// 부서진 포탑이라면 패스.
				if (brokenGunBoard[i][j])
					continue;

				GunInfo gunInfo = gunInfoBoard[i][j];
				if (gunInfo.damage <= MAX_DAMAGE) {
					MAX_DAMAGE = gunInfo.damage;
					tempX = i;
					tempY = j;
				}
			}
		}
		// 맨 처음에 구한 가장 낮은 공격력을 가진 포탑의 정보를 저장.
		candidates.add(new GunInfo(tempX, tempY, (tempX + tempY), gunInfoBoard[tempX][tempY].damage));

		// 가장 낮은 공격력을 가진 포탑과 동일한 공격력을 가진 포탑들을 후보자로 넣음.
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (brokenGunBoard[i][j])
					continue;

				if (tempX != i && tempY != j && gunInfoBoard[i][j].damage == candidates.get(0).damage) {
					candidates.add(new GunInfo(i, j, (i + j), gunInfoBoard[i][j].damage));
				}
			}
		}

		// 가장 최근에 공격한 포탑이 가장 약한 포탑.
		if (time > 1 && !latestAttackGun.isEmpty()) {
			Pair latestPair = latestAttackGun.get(latestAttackGun.size() - 1);

			for (int i = 0; i < candidates.size(); i++) {
				GunInfo candidatePair = candidates.get(i);
				if (latestPair.x == candidatePair.x && latestPair.y == candidatePair.y) {
					attackerInfo.x = candidatePair.x;
					attackerInfo.y = candidatePair.y;
					return;
				}
			}
		}

		// 행과 열의 합이 가장 큰 포탑. 아니라면 열 값이 가장 큰 포탑.
		Collections.sort(candidates, new Comparator<GunInfo>() {
			@Override
			public int compare(GunInfo o1, GunInfo o2) {
				if (o1.totalXY == o2.totalXY) {
					return o2.y - o1.y;
				} else {
					return o2.totalXY - o1.totalXY;
				}
			}
		});

		GunInfo gunInfo = candidates.get(0);
		attackerInfo.x = gunInfo.x;
		attackerInfo.y = gunInfo.y;
	}

	static class GunInfo {
		int x;
		int y;
		int totalXY;
		int damage;

		public GunInfo(int x, int y, int totalXY, int damage) {
			this.x = x;
			this.y = y;
			this.totalXY = totalXY;
			this.damage = damage;
		}
	}

	static class Pair {
		int x;
		int y;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static class AttackerInfo {
		int x;
		int y;
		private List<Pair> path;

		public AttackerInfo(int x, int y, List<Pair> other) {
			this.x = x;
			this.y = y;
			this.path = new ArrayList<>(other);
		}

		public AttackerInfo(int x, int y) {
			this.x = x;
			this.y = y;
			this.path = new ArrayList<>();
		}
	}
}