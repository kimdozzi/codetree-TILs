dir = {'W':(0,-1), 'S':(-1,0), 'N':(1,0), 'E' : (0,1)}
n = int(input())
x,y=0,0
for _ in range(n) :
    direction, num = input().split()
    dx,dy = dir[direction]
    for _ in range(int(num)):
        x += dx
        y += dy
print(y,x)