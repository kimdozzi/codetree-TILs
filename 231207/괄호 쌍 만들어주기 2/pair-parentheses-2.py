import sys
si = sys.stdin.readline
s=si().rstrip()
res = 0
for i in range(len(s)) :
    if s[i] == '(' :
    for j in range(i+1,len(s)) :
        if s[j] ==')' :
            res += 1

print(res)