n,m,k=map(int,input().split())
arr=[0]*n
flag = False
for _ in range(m) :
    idx = int(input())-1
    arr[idx] += 1
    if arr[idx] >= k : 
        print(idx+1)
        flag = True
        break
if not flag :
    print(-1)