import sys
si = sys.stdin.readline
n=int(si())
arr=[int(si()) for _ in range(n)]
ans = -1
for i in range(len(arr)) :
    for j in range(i+1,len(arr)) :
        for k in range(j+1,len(arr)) :
            flag = False
            res=0
            a,b,c=arr[i],arr[j],arr[k]
            for l in range(3,-1,-1) :
                d = 10 ** l
                if ((a // d) + (b // d) + (c // d)) >= 10 :
                    flag = True
                    break
                a %= d
                b %= d
                c %= d
            
            if not flag :
                res += arr[i]+arr[j]+arr[k]
                ans = max(ans, res)
print(ans)