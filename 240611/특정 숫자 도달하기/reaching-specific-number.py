import sys
si = sys.stdin.readline 

lst = list(map(int, si().split()))
res = 0
idx = 0
for i in range(len(lst)) :
    if lst[i] >= 250 : 
        break
    res += lst[i]
    idx += 1

print("%d %.1lf"%(res, res/idx))