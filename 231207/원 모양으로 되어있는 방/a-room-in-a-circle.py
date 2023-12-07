import sys
si = sys.stdin.readline
n = int(si())
arr = [int(si()) for i in range(n)]

mn_sum = float('inf')
for i in range(n) :
    j = (i+1)%n
    cnt = 1
    temp_sum = []
    while cnt < n :
        temp_sum.append(arr[j] * cnt)
        j = (j + 1) % n 
        cnt += 1
    mn_sum = min(mn_sum, sum(temp_sum))

print(mn_sum)