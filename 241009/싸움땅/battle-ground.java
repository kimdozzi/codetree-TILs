import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Player {
	int x;
	int y;
	int d;
	int s;
	boolean hasGun;
	int gunDamage;

	public Player(int x, int y, int d, int s, boolean hasGun, int gunDamage) {
		this.x = x;
		this.y = y;
		this.d = d;
		this.s = s;
		this.hasGun = hasGun;
		this.gunDamage = gunDamage;
	}
}

public class Main {
	static int N, M, K;
	static List<Integer>[][] board;
	static Player[] players;
	static int[] points;
	static int[][] playerBoard;
	static int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		K = sc.nextInt();

		board = new List[N][N];
		players = new Player[M + 1];
		points = new int[M + 1];
		playerBoard = new int[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = new ArrayList<>();
				int damage = sc.nextInt();
				board[i][j].add(damage);
			}
		}

		for (int i = 1; i <= M; i++) {
			int x = sc.nextInt() - 1;
			int y = sc.nextInt() - 1;
			int d = sc.nextInt();
			int s = sc.nextInt();
			players[i] = new Player(x, y, d, s, false, 0);
			playerBoard[x][y] = i;
		}

		for (int t = 1; t <= K; t++) {
			simulation();
			
		}
		for (int i = 1; i <= M; i++) {
			System.out.print(points[i] + " ");
		}
	}

	private static void simulation() {
		for (int idx = 1; idx <= M; idx++) {
			Player player = players[idx];
			int x = player.x;
			int y = player.y;

			// 1칸 이동.
			int nx = x + dirs[player.d][0];
			int ny = y + dirs[player.d][1];

			// 격자를 벗어나는 경우, 정반대 방향으로 방향을 바꾸어 1칸 이동.
			if (!inRange(nx, ny)) {
				player.d = (player.d < 2) ? (d+2) : (d-2);
				nx = x + dirs[player.d][0];
				ny = y + dirs[player.d][1];
			}

			// 이동하려는 칸에 플레이어가 있다면
			if (playerBoard[nx][ny] > 0) {
				player.x = nx;
				player.y = ny;
				playerBoard[x][y] = 0;

				// 승자, 패자 정보 받아옴.
				int[] data = getfightInfo(player, nx, ny, idx);
				int loser = data[1];

				// 패자 플레이어 정보 받아옴.
				Player loserPlayer = players[loser];

				// 진 사람은 가지고 있는 총을 격자에 내려놓음. (자기 총 없어짐)
				board[nx][ny].add(loserPlayer.gunDamage);
				loserPlayer.gunDamage = 0;
				loserPlayer.hasGun = false;

				// 자기가 가진 방향으로 1칸 이동.
				int lx = loserPlayer.x;
				int ly = loserPlayer.y;

				for (int d = 0; d < 4; d++) {
					int newD = (loserPlayer.d + d) % 4;
					int lnx = lx + dirs[newD % 4][0];
					int lny = ly + dirs[newD % 4][1];

					if (!inRange(lnx, lny))
						continue;

					if (playerBoard[lnx][lny] > 0)
						continue;

					// 해당 칸이 빈칸이라면
					if (playerBoard[lnx][lny] == 0) {
						// 해당 칸으로 패자는 이동.
						loserPlayer.x = lnx;
						loserPlayer.y = lny;
						loserPlayer.d = newD;

						// 자기가 원래 있던 칸은 0으로 초기화.
						playerBoard[lx][ly] = 0;
						// 새로운 곳으로 갱신.
						playerBoard[loserPlayer.x][loserPlayer.y] = loser;
						break;
					}
				}

				// 이긴 사람
				int winner = data[0];

				// 승자 플레이어 정보 받아옴.
				Player winnerPlayer = players[winner];
				int wx = winnerPlayer.x;
				int wy = winnerPlayer.y;

				// 총 정보 정렬. (오름차순)
				Collections.sort(board[nx][ny]);

				// 공격력 높은 총으로 바꿈.
				if (board[nx][ny].get(board[nx][ny].size() - 1) > winnerPlayer.gunDamage) {
					int temp = winnerPlayer.gunDamage;
					winnerPlayer.gunDamage = board[nx][ny].get(board[nx][ny].size() - 1);
					board[nx][ny].set(board[nx][ny].size() - 1, temp);
				}

				winnerPlayer.x = nx;
				winnerPlayer.y = ny;

				playerBoard[wx][wy] = 0;
				playerBoard[winnerPlayer.x][winnerPlayer.y] = winner;

			}

			// 이동하려는 칸에 플레이어가 없다면
			else {
				// 해당 칸에 총이 있는지 확인.
				// 총이 있다면
				if (!board[nx][ny].isEmpty()) {
					Collections.sort(board[nx][ny]);
					int damage = board[nx][ny].get(board[nx][ny].size() - 1);

					// 이미 총을 가지고 있다면
					if (player.hasGun) {
						if (player.gunDamage < damage) {
							int temp = player.gunDamage;
							player.gunDamage = damage;
							board[nx][ny].set(board[nx][ny].size() - 1, temp);
						}
					}
					// 총이 없다면
					else {
						player.hasGun = true;
						player.gunDamage = damage;
						board[nx][ny].set(board[nx][ny].size() - 1, 0);
					}
				}

				// 위치 이동.
				player.x = nx;
				player.y = ny;
				// 기존 위치 0
				playerBoard[x][y] = 0;

				// 새로운 위치 갱신.
				playerBoard[player.x][player.y] = idx;
			}
		}
	}

	private static void printBoard() {
		System.out.println("== 플레이어 보드 ==");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(playerBoard[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	private static int[] getfightInfo(Player player, int nx, int ny, int idx) {
		// 이동하려는 칸에 있는 플레이어의 번호 저장.
		int nearbyPlayerNumber = playerBoard[nx][ny];
		// 해당 번호로 플레이어 정보를 가져옴.
		Player nearbyPlayer = players[nearbyPlayerNumber];

		// 두 플레이어가 싸움.
		int myAttack = player.s + (player.hasGun ? player.gunDamage : 0);
		int nearbyAttack = nearbyPlayer.s + (nearbyPlayer.hasGun ? nearbyPlayer.gunDamage : 0);

		// 승자, 패자의 정보 가져옴.
		int[] info = fight(myAttack, nearbyAttack, player, nearbyPlayer, idx, nearbyPlayerNumber);
		int winner = info[0];
		int loser = info[1];

		Player winnerPlayer = players[winner];
		Player loserPlayer = players[loser];

		points[winner] += (winnerPlayer.gunDamage + winnerPlayer.s) - (loserPlayer.gunDamage + loserPlayer.s);
		return new int[] {winner, loser};
	}

	private static int[] fight(int myAttack, int nearbyAttack, Player player, Player nearbyPlayer, int playerNumber,
		int nearbyPlayerNumber) {
		int lower, winner;

		if (myAttack == nearbyAttack) {
			// 능력치가 높다면
			if (player.s > nearbyPlayer.s) {
				// 내가 이김.
				winner = playerNumber;
				lower = nearbyPlayerNumber;
			}
			// 상대방이 이김.
			else {
				winner = nearbyPlayerNumber;
				lower = playerNumber;
			}
		} else if (myAttack > nearbyAttack) {
			// 내가 이김
			winner = playerNumber;
			lower = nearbyPlayerNumber;
		} else {
			// 상대가 이김
			winner = nearbyPlayerNumber;
			lower = playerNumber;
		}
		return new int[] {winner, lower};
	}

	private static boolean inRange(int nx, int ny) {
		return 0 <= nx && nx < N && 0 <= ny && ny < N;
	}
}