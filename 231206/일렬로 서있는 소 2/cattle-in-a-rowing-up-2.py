import sys
si = sys.stdin.readline
n=int(si())
arr=list(map(int,si().split()))
res = 0
for i in range(n) :
    for j in range(i+1, n) :
        for k in range(j+1, n) :
            if arr[i] <= arr[j] <= arr[k] :
                res += 1

print(res)