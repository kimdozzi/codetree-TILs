import sys
si = sys.stdin.readline

n,m=map(int,si().split())
board = [list(si().rstrip()) for _ in range(n)]
dxs, dys = [1, 1, 1, -1, -1, -1, 0, 0], [-1, 0, 1, -1, 0, 1, -1, 1]
ans = 0
def in_range(x, y):
    return 0 <= x < n and 0 <= y < m

for i in range(n) :
    for j in range(m) :
        if board[i][j] == 'L' : 
            for k in range(8) :
                flag = False
                cnt = 0
                x,y = i,j
                for l in range(2) :
                    nx,ny = x + dxs[k], y + dys[k]
                    if not in_range(nx,ny) : 
                        flag = True 
                        break

                    if board[nx][ny] == 'E' :
                        cnt += 1
                    x,y = nx,ny
                
                if not flag and cnt == 2 :
                    ans += 1

print(ans)