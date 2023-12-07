import sys
si = sys.stdin.readline
n=int(si())
arr=list(map(int,si().split()))

mx_sum = float('-inf')
for i in range(len(arr)) :
    for j in range(len(arr)) :
        if i == j : continue
        
        if i == 0: 
            if i+1 == j : continue
        elif i == n-1 :
            if i-1 == j : continue
        else :
            if i-1 == j or i+1 == j : continue
        
        mx_sum=max(mx_sum,arr[i]+arr[j])

print(mx_sum)