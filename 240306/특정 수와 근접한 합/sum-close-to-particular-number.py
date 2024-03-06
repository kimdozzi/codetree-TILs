import sys
si = sys.stdin.readline
n,s=map(int,si().split())
arr=list(map(int,si().split()))

ans = float('inf')
for i in range(len(arr)) :
    for j in range(len(arr)) :
        if i==j: continue
        tmp = 0
        for k in range(len(arr)) :
            if i == k or j == k : continue
            tmp += arr[k]
        
        ans = min(ans, abs(tmp-s))

print(ans)