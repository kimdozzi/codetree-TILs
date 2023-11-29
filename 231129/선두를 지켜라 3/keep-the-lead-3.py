import sys
si = sys.stdin.readline

n,m=map(int,si().split())
A = [0] * 1000001
B = [0] * 1000001

pos = 1
for _ in range(n) :
    v,t = map(int,si().split())
    for _ in range(t) :
        A[pos] += A[pos-1] + v
        pos += 1

pos = 1
for _ in range(m) :
    v,t = map(int,si().split())
    for _ in range(t) :
        B[pos] += B[pos-1] + v
        pos += 1

cnt = 0
flag = -1
for i in range(1,pos) :
    if flag != 2 and A[i] == B[i] :
        flag = 2
        cnt += 1
    elif flag != 0 and A[i] > B[i] : 
        flag = 0
        cnt += 1
    elif flag != 1 and B[i] > A[i] :
        flag = 1
        cnt += 1

print(cnt)