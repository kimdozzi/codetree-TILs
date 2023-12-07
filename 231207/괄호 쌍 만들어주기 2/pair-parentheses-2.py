import sys
si = sys.stdin.readline
s=si().rstrip()
res = 0
for i in range(len(s)-1) :
    if s[i] == '(' and s[i+1] == '(':
        for j in range(i+1,len(s)-1) :
            if s[j] ==')' and s[j+1]==')' :
                res += 1

print(res)