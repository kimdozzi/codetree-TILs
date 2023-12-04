import sys
si = sys.stdin.readline 

def in_range(x,y) :
    return 0<=x<r and 0<=y<c

dirs = ((1,0),(0,1),(-1,0),(0,-1))

r,c=map(int,si().split())
board=[[0] * c for _ in range(r)]
x,y = 0,0
board[x][y] = 1
num = 2
direction = 0
while num-1 < r*c :
    nx,ny = x+dirs[direction][0], y+dirs[direction][1]
    if in_range(nx,ny) and board[nx][ny] == 0 :
        board[nx][ny] = num
        num += 1
        x,y=nx,ny
        
    else :
        direction = (direction + 1 + 4) % 4

for i in board:
    print(*i, sep=' ')