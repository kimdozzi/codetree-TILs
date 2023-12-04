import sys
si = sys.stdin.readline 

def in_range(x,y) :
    return 0<=x<r and 0<=y<c

dirs = ((0,1),(1,0),(0,-1),(-1,0))

r,c=map(int,si().split())
board=[[''] * c for _ in range(r)]
x,y = 0,0
board[x][y] = chr(65)
num = 1
direction = 0
cnt = 1
while cnt < r*c :
    nx,ny = x+dirs[direction][0], y+dirs[direction][1]
    if in_range(nx,ny) and board[nx][ny] == '':
        board[nx][ny] = chr(num + 65)
        num = (num + 1 + 65) % 65
        x,y=nx,ny
        cnt+=1
        
    else :
        direction = (direction + 1 + 4) % 4

for i in board:
    print(*i, sep=' ')