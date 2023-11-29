#dxs,dys=[0,1,0,-1],[1,0,-1,0]

#for dx,dy in zip(dxs, dys) :
#    nx,ny = x + dx, y + dy

# is_range와 같이 범위를 확인하는 조건은 항상 if문 가장 앞에 작성하는 것이 좋다. 

import sys
si = sys.stdin.readline
n= int(si())
board= [list(map(int,si().split())) for _ in range(n)]

dxs,dys=[0,1,0,-1],[1,0,-1,0]

def is_range(x,y) :
    return 0<=x<n and 0<=y<n

ans =0 
for x in range(n) :
    for y in range(n) :
        cnt = 0
        for dx,dy in zip(dxs,dys) :
            nx,ny=x+dx,y+dy
            if is_range(nx, ny) :
                if board[nx][ny] == 1 :
                    cnt += 1
        
        if cnt >=3 : ans += 1

print(ans)