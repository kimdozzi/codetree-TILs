import sys
si = sys.stdin.readline

board=[list(map(int, si().split())) for _ in range(19)]

dirs = [[0,1],[1,1],[1,0],[1,-1]]

def in_range(x,y) :
    return 0 <= x,y < 19

def calculate(x,y,direction) :
    idx = direction

    flag = False
    for k in range(4) :
        nx, ny = x + dirs[idx][0], y + dirs[idx][1]
        if not in_range(nx,ny) :
            flag = True
            break

        if board[nx][ny] == board[x][y]:
            x,y = nx, ny
        else :
            flag = True
            break
    
    if not flag : 
        return True
    return False


for i in range(19) :
    for j in range(19) :
        if board[i][j] > 0 :
            if calculate(i,j,0) :
                print(board[i][j])
                print(i+1, j+3)
                exit(0)
            
            elif calculate(i,j,1) :
                print(board[i][j])
                print(i+3, j+3)
                exit(0)

            
            elif calculate(i,j,2) :
                print(board[i][j])
                print(i+3, j+1)
                exit(0)
                
            elif calculate(i,j,3) :
                print(board[i][j])
                print(i+3, j-1)
                exit(0)

print(0)