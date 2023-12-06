import sys
si = sys.stdin.readline
s=list(si().rstrip())

def bin_to_int(x) :
    l = len(x)-1
    res = 0
    for i in range(len(x)) :
        if x[i] == '1' :
            res += (2**l)
            l -= 1
    return res

mx_num = float('-inf')
for i in range(len(s)) :
    if s[i] == '1' :
        s[i] = '0'
        mx_num = max(mx_num, bin_to_int(s))
        s[i] = '1'

    
    else :
        s[i] = '1'
        mx_num = max(mx_num, bin_to_int(s))
        s[i] = '0'




print(mx_num)