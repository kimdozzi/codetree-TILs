import sys
si = sys.stdin.readline
s=si().rstrip()

dxs, dys = [-1,0,1,0], [0,1,0,-1]
x,y = 0,0
direction = 0
ans = - 1
for i in range(len(s)) :
    if s[i] == 'L' :
        direction = (direction - 1 + 4) % 4
    elif s[i] == 'R' :
        direction = (direction + 1 + 4) % 4
    else :
        dx,dy = dxs[direction], dys[direction]
        x,y = x+dx, y+dy
        if x == 0 and y == 0 :
            ans = i+1
            break

print(ans)