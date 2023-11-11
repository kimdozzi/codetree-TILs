import sys
si = sys.stdin.readline

n = int(si())
arr = [int(si()) for _ in range(n)]

ans, cnt = 0, 0
for i in range(len(arr)) :
    if i >= 1 and arr[i-1] < arr[i] :
        cnt += 1
    else :
        cnt = 1
    
    ans = max(ans, cnt)

print(ans)