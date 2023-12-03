import sys
si = sys.stdin.readline

n = int(si())
direction = {'N':0,'E':1,'S':2,'W':3}
dir = ((-1,0),(0,1),(1,0),(0,-1))
x, y = 1000, 1000

cnt = 0
arr = []
for _ in range(n) :
    dir_str, d = si().split()
    arr.append((dir_str, d))

flag = False
for dir_str,d in arr :
    for _ in range(int(d)) :
        nx,ny = x+dir[direction[dir_str]][0], y+dir[direction[dir_str]][1]
        cnt += 1
        if nx == 1000 and ny == 1000 : 
            print(cnt)
            flag = True
            break

        x,y = nx,ny

if not flag :
    print(-1)