import sys
si = sys.stdin.readline

n=int(si())
s=si().rstrip()

res = 0
for i in range(len(s)) :
    for j in range(i+1,len(s)) :
        for k in range(j+1, len(s)) :
            if s[i]=='C' and s[j] =='O' and s[k] =='W' :
                res += 1
print(res)