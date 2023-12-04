import sys
si = sys.stdin.readline

n = int(si())
board = [[0]*n for _ in range(n)]
x = y = (n//2)
board[x][y] = 1
dirs = ((0,1),(-1,0),(0,-1),(1,0))

num = 2 # 격자 내부에서 1씩 증가하면서 저장됨. 
cnt = 0 # cnt가 2가 되면 step의 범위가 1씩 증가함.
direction = 0 # 격자 내부에서의 방향 
step = 1 # 격자 안에서 한 번 움직일 때, 움직이는 총 칸의 개수 ex) 1,1,2,2,3,3,4,4..
idx = 1 # idx가 n*n을 벗어나면 종료되는 변수

def in_range(x,y) :
    return 0<=x<n and 0<=y<n

while idx < n*n : 
    for _ in range(step) : 
        dx,dy = dirs[direction][0], dirs[direction][1] 
        nx, ny = dx+x, dy+y 
        if in_range(nx,ny) and board[nx][ny] == 0 : 
            board[nx][ny] = num 
            num += 1    
            x,y = nx,ny

    direction = (direction + 1 + 4) % 4
    cnt += 1
    if cnt >= 2 :
        step += 1
        cnt = 0
    
    idx += 1

for i in board :
    print(*i,sep=' ')