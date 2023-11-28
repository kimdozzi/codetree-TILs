# N명의 개발자, T번에 걸쳐 t초에 x개발자가 y개발자와 악수를 나눔 
# k번의 악수동안 전염병을 옮김, 개발자 번호 p 

import sys
si = sys.stdin.readline

n,k,p,tc = map(int, si().split())

people = [-1] * (n+1)
people[p] = k

data = []
for _ in range(tc) :
    t,x,y = map(int,si().split())
    data.append((t,x,y))
data.sort()

for _,x,y in data :
    if people[x] > 0 :
        if people[y] == -1 :
            people[y] = k
            
        people[x] -= 1
    
    if people[y] > 0 :
        if people[x] == -1 :
            people[x] = k
        people[y] -= 1

ans = ''
for i in people[1:] :
    if i >= 0 :
        ans+='1'
    else:
        ans+='0'

print(ans)