import sys
si = sys.stdin.readline

n = int(si())
direction = {'N':0,'E':1,'S':2,'W':3}
di = ((-1,0),(0,1),(1,0),(0,-1))
x, y = 0, 0
elapsed_time = 0
ans = -1

def move(move_dir, dist) :
    global di, x, y, elapsed_time, ans 
    dx, dy = di[move_dir]

    for _ in range(dist) :
        x,y = x+dx, y+dy
        elapsed_time += 1

        if x == 0 and y == 0 :
            ans = elapsed_time
            return True 
    
    return False


for _ in range(n) :
    dir_str, dist = si().split()
    dist = int(dist)

    move_dir = direction[dir_str]

    if move(move_dir, dist) :
        break

print(ans)