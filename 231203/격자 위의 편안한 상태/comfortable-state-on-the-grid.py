import sys
si = sys.stdin.readline

n,m=map(int,si().split())
board=[[0] * n for _ in range(n)]
dist =[[0,1],[0,-1],[1,0],[-1,0]]


def is_range(x,y) :
    return 0<=x<n and 0<=y<n
    
def check(x,y) :
    global board, dist
    cnt = 0
    for i in range(4) :
        nx = x + dist[i][0]
        ny = y + dist[i][1]
        if is_range(nx,ny) and board[nx][ny] != 0 :
            cnt += 1
    
    if cnt == 3 :
        return True
    return False


for _ in range(m) :
    r,c=map(int,si().split())
    r,c = r-1,c-1
    board[r][c] = 1
    if check(r,c) :
        print(1)
    else :
        print(0)