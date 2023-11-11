n,m=map(int,input().split())
arr=list(map(int,input().split()))
ans,cnt=0,0
for i in range(len(arr)) :
    if arr[i] > m :
        cnt += 1
    else :
        cnt = 0
    
    ans=max(ans,cnt)

print(ans)