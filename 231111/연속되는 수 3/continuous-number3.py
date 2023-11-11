import sys
si = sys.stdin.readline
n = int(si())
lst = [int(si()) for _ in range(n)]

ans = 0
cnt = 1
flag = False 
for i in range(1,len(lst)) :
    if lst[i] > 0 and lst[i-1] > 0 :
        if not flag :
            flag = True
            ans = max(ans, cnt)
            cnt = 1
        else :
            cnt += 1
    
    elif lst[i] < 0 and lst[i-1] < 0 :
        if flag :
            flag = False
            ans = max(ans, cnt)
            cnt = 1
        else :
            cnt += 1

print(ans)