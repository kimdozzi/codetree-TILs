import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Tree implements Comparable<Tree> {
	int x;
	int y;
	int cnt;

	public Tree(int x, int y, int cnt) {
		this.x = x;
		this.y = y;
		this.cnt = cnt;
	}

	@Override
	public int compareTo(Tree t) {
		if (this.cnt != t.cnt) {
			return t.cnt - this.cnt;
		}
		if (this.x != t.x) {
			return this.x - t.x;
		}
		return this.y - t.y;
	}
}

public class Main {
	static final int WALL = -1;
	static final int EMPTY = 0;
	static final int EXIST = 1;
	static int answer = 0;
	static int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
	static int[][] cross = {{1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
	static int N, M, K, C; // 격자크기, 박멸이 진행되는 년 수, 제초제 확산 범위, 제초제가 남아있는 년 수
	static int[][] board; // 벽, 빈칸, 나무의 그루 수
	static int[][] sprayBoard; // 제초제가 남아있는 년 수를 담고 있는 배열
	static Tree[][] treeBoard;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		K = sc.nextInt();
		C = sc.nextInt();

		board = new int[N][N];
		sprayBoard = new int[N][N];
		treeBoard = new Tree[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int cnt = sc.nextInt();
				if (cnt > 0)
					board[i][j] = 1;
				else
					board[i][j] = cnt;

				if (board[i][j] == EXIST) {
					treeBoard[i][j] = new Tree(i, j, cnt);
				} else {
					treeBoard[i][j] = null;
				}
			}
		}

		for (int t = 1; t <= M; t++) {
			// 박멸 년 수를 1씩 감소합니다.
			if (t > 1)
				decrease();

			// 나무가 있는 좌표를 구합니다.
			findTree();

			// 나무가 있는 칸들을 업데이트 해줍니다.
			update();

			// 제초제 뿌리기
			spray();

			// 제초된 나무들을 정리합니다.
			sprayUpdate();

		}
		System.out.println(answer);
	}

	private static void decrease() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (sprayBoard[i][j] > 0)
					sprayBoard[i][j]--;
			}
		}
	}

	private static void sprayUpdate() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (sprayBoard[i][j] > 0) {
					if (treeBoard[i][j] != null) {
						answer += treeBoard[i][j].cnt;
						treeBoard[i][j] = null;
					}
					board[i][j] = EMPTY;
				}
			}
		}
	}

	private static void spray() {
		int MIN_N = Integer.MIN_VALUE;
		int sx = 0, sy = 0;

		// 나무가 가장 많이 박멸되는 칸 찾기.
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (treeBoard[i][j] == null)
					continue;
				Tree tree = treeBoard[i][j];
				int x = tree.x;
				int y = tree.y;
				int cnt = tree.cnt;

				for (int d = 0; d < 4; d++) {
					int nx = x;
					int ny = y;
					for (int k = 0; k < K; k++) {
						nx += cross[d][0];
						ny += cross[d][1];

						if (!inRange(nx, ny))
							continue;

						if (board[nx][ny] == EMPTY || board[nx][ny] == WALL)
							continue;

						if (board[nx][ny] == EXIST) {
							cnt += treeBoard[nx][ny].cnt;
						}
					}
				}

				if (MIN_N < cnt) {
					MIN_N = cnt;
					sx = i;
					sy = j;
				}
			}
		}

		// 박멸 시작.
		sprayBoard[sx][sy] = C + 1;

		for (int d = 0; d < 4; d++) {
			int nx = sx;
			int ny = sy;
			for (int k = 0; k < K; k++) {
				nx += cross[d][0];
				ny += cross[d][1];

				if (!inRange(nx, ny))
					break;

				if (board[nx][ny] == WALL)
					break;

				sprayBoard[nx][ny] = C + 1;
			}
		}

	}

	private static void update() {
		// System.out.println("== 업데이트 전 board를 확인 ==");
		// printBoard();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (treeBoard[i][j] != null) {
					board[i][j] = EXIST;
				}
			}
		}
	}

	private static void spread() {
		Queue<Tree> q = new LinkedList<>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (treeBoard[i][j] != null) {
					Tree tree = treeBoard[i][j];
					q.add(new Tree(tree.x, tree.y, tree.cnt));
				}
			}
		}

		while (!q.isEmpty()) {
			Tree tree = q.poll();
			int x = tree.x;
			int y = tree.y;
			int cnt = tree.cnt;
			int emptyCnt = 0;

			for (int d = 0; d < 4; d++) {
				int nx = x + dirs[d][0];
				int ny = y + dirs[d][1];

				if (!inRange(nx, ny))
					continue;

				if (board[nx][ny] == EXIST || board[nx][ny] == WALL || sprayBoard[nx][ny] > 0)
					continue;

				emptyCnt++;
			}

			if (emptyCnt == 0)
				continue;

			int val = cnt / emptyCnt;
			if (val == 0)
				continue;

			for (int d = 0; d < 4; d++) {
				int nx = x + dirs[d][0];
				int ny = y + dirs[d][1];

				if (!inRange(nx, ny))
					continue;

				if (board[nx][ny] == EXIST || board[nx][ny] == WALL || sprayBoard[nx][ny] > 0)
					continue;

				if (treeBoard[nx][ny] == null) {
					Tree newTree = new Tree(nx, ny, val);
					treeBoard[nx][ny] = newTree;
				} else {
					treeBoard[nx][ny].cnt += val;
				}
			}
		}
	}

	private static void grow(Queue<Tree> q) {
		while (!q.isEmpty()) {
			Tree tree = q.poll();
			int x = tree.x;
			int y = tree.y;

			int growthCount = 0;
			for (int d = 0; d < 4; d++) {
				int nx = x + dirs[d][0];
				int ny = y + dirs[d][1];

				if (!inRange(nx, ny))
					continue;

				// 벽이거나 나무가 없다면 패스
				if (board[nx][ny] == WALL || board[nx][ny] == EMPTY)
					continue;

				if (board[nx][ny] == EXIST) {
					growthCount++;
				}
			}
			treeBoard[x][y].cnt += growthCount;
		}
	}

	private static boolean inRange(int nx, int ny) {
		return 0 <= nx && nx < N && 0 <= ny && ny < N;
	}

	private static void findTree() {
		Queue<Tree> q = new LinkedList<>();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {

				// 나무가 있는 칸이면
				if (board[i][j] == EXIST) {
					q.add(new Tree(i, j, treeBoard[i][j].cnt));
				}
			}
		}

		// 나무들은 동시에 성장합니다.
		grow(q);

		// 인접한 4개의 칸 중 벽, 다른 나무, 제초제가 없는 칸에서 번식 시작.
		spread();
	}

}