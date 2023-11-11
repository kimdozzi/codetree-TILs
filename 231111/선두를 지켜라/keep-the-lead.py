import sys
si = sys.stdin.readline

n,m = map(int, si().split())

arr_A, arr_B = [0] * 1000001, [0] * 1000001

cur_A = 1
for _ in range(n):
    v,t = map(int,si().split())
    for i in range(t) :
        arr_A[cur_A] = arr_A[cur_A-1] + v
        cur_A += 1

cur_B = 1
for _ in range(m):
    v,t = map(int,si().split())
    for i in range(t) :
        arr_B[cur_B] = arr_B[cur_B-1] + v
        cur_B += 1

ans = 0
flag = 0
for i in range(1,cur_A) :
    if arr_A[i] > arr_B[i] : 
        if flag == 2 : 
            ans += 1
        flag = 1

    elif arr_A[i] < arr_B[i] :
        if flag == 1 :
            ans += 1
        
        flag = 2
print(ans)