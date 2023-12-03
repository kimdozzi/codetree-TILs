# 방향을 틀어야하는 조건
# 1. 격자를 벗어나게 되거나, 혹은 그 곳에 내가 방문했던 적이 있어 숫자가 이미 적혀있는 경우 

import sys
si = sys.stdin.readline
n,m=map(int,si().split())
board=[[0]*m for _ in range(n)]

def is_range(x,y) :
    return 0 <= x < n and 0 <= y < m

x,y=0,0
dir = ((0,1),(1,0),(0,-1),(-1,0))
direction = 0 
board[x][y] = 1

cnt = 1
while cnt < (n*m) :
    nx, ny = x + dir[direction][0], y + dir[direction][1]
    if is_range(nx,ny) and board[nx][ny] == 0 :
        board[nx][ny] = board[x][y] + 1
        x,y = nx,ny
        cnt += 1

    else :
        direction = (direction + 1 + 4) % 4

    
for i in board :
    print(*i, sep=' ')