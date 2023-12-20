import sys
si = sys.stdin.readline

board=[list(map(int, si().split())) for _ in range(19)]

dirs = [[0,1],[1,1],[1,0],[-1,1]]

def calculate(x,y,direction) :
    idx = direction

    flag = False
    for k in range(4) :
        nx, ny = x + dirs[idx][0], y + dirs[idx][1]
        if board[nx][ny] != board[x][y] : 
            flag = True
            break
        x,y = nx, ny
    
    if not flag : 
        return True
    return False


for i in range(15) :
    for j in range(15) :
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
                print(i+3, j-2)
                exit(0)