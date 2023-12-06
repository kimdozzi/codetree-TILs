import sys 
si = sys.stdin.readline

n,t=map(int,si().split())
commands= si().rstrip()
board=[list(map(int,si().split())) for _ in range(n)]
x = y = (n//2)
dxs, dys = [-1,0,1,0],[0,1,0,-1]
res = board[x][y]
direction = 0

def in_range(x,y) :
    return 0 <= x < n and 0 <= y < n

for i in range(len(commands)) :
    if commands[i] == 'R' :
        direction = (direction + 1 + 4) % 4
    elif commands[i] == 'L' :
        direction = (direction - 1 + 4) % 4
    else :
        dx, dy = dxs[direction], dys[direction]
        nx, ny = x + dx, y + dy
        if in_range(nx,ny) :
            res += board[nx][ny]
            x,y = nx,ny

print(res)