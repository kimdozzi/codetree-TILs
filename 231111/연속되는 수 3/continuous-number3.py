import sys
si = sys.stdin.readline
n = int(si())
lst = [int(si()) for _ in range(n)]
arr = [1] * len(lst)

for i in range(1,len(lst)) :
    if lst[i] < 0 and lst[i-1] < 0 :
        arr[i] += arr[i-1]
    
    elif lst[i] > 0 and lst[i-1] > 0 :
        arr[i] += arr[i-1]


mx = 0
for i in arr :
    mx = max(mx, i)
print(mx)