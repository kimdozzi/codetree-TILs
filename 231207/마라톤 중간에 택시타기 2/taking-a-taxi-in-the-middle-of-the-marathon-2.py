import sys
si = sys.stdin.readline

n=int(si())
arr=[]
for _ in range(n) :
    x,y = map(int,si().split())
    arr.append((x,y))

mn_sum = float('inf')
for i in range(1,len(arr)-1) :
    temp = []
    for j in range(len(arr)) :
        if i == j : continue
        temp.append(arr[j])
    temp_sum = 0
    for j in range(1,len(temp)) :
        temp_sum += abs(temp[j-1][0]-temp[j][0])+abs(temp[j-1][1]-temp[j][1])   
    mn_sum=min(mn_sum, temp_sum)

print(mn_sum)