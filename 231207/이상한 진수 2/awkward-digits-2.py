import sys
si = sys.stdin.readline
s=list(si().rstrip())

flag = False
for i in range(len(s)) :
    if s[i] == '0' :
        s[i] = '1'
        flag = True
        break
if not flag :
    for i in range(len(s)-1,-1,-1) :
        if s[i] == '1': 
            s[i] = '0'
            break

l = len(s)-1
res = 0
for i in range(len(s)) :
    if s[i] == '1': 
        res += 2 ** l
        l -= 1

print(res)