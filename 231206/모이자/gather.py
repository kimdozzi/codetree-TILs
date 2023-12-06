import sys
si = sys.stdin.readline

n=int(si())
arr=[0]+list(map(int,si().split()))
mn_sum = float('inf')

for i in range(1,n+1) :
    cur = arr[i]
    num_sum = 0

    for j in range(1,n+1) :
        if i == j : continue
        num_sum += abs(i-j) * arr[j]
    mn_sum = min(mn_sum, num_sum)

print(mn_sum)