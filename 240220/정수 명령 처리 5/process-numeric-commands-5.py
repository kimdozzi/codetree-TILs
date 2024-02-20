import sys
si =sys.stdin.readline
n=int(si())
arr = []
for i in range(n) :
    lst = list(si().split())
    if lst[0] == "push_back" :
        arr.append(lst[1])
    elif lst[0] == "get" :
        print(arr[int(lst[1])-1])
    elif lst[0] == "size" :
        print(len(arr))
        
    else :
        if arr :
            arr.pop()