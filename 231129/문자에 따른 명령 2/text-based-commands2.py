import sys
si = sys.stdin.readline

commands = list(si().rstrip())

x,y=0,0
dx,dy = (1,0,-1,0), (0,-1,0,1)
direction = 3

for command in commands :
    if command == 'L' :
        direction = (direction - 1 + 4) % 4

    elif command == 'R' :
        direction = (direction + 1 + 4) % 4
    
    else :
        nx, ny = x + dx[direction], y + dy[direction]
        x, y = nx, ny 

print(x, y)