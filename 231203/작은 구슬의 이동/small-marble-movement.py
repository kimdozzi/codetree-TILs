import sys
si = sys.stdin.readline

dxs,dys=[0,1,-1,0],[1,0,0,-1]
dic = ['R','D','U','L']

n,t=map(int,si().split())
x,y,direction = si().split()
x,y=int(x)-1,int(y)-1
board=[[0]*n for _ in range(n)]
board[x][y] = 1

def is_range(nx,ny) :
    return 0 <=nx < n and 0 <=ny < n

d = dic.index(direction)
for tt in range(1,t+1) :
    nx, ny = dxs[d] + x, dys[d] + y
    if not is_range(nx,ny) :
        d = 3 - d
        continue
    board[x][y] = 0
    board[nx][ny] = 1
    x,y = nx,ny

print(x+1,y+1)