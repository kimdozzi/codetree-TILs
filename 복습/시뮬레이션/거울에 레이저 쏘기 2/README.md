## 문제

https://www.codetree.ai/missions/5/problems/shoot-a-laser-in-the-mirror-2?&utm_source=clipboard&utm_medium=text

</br>

---
## 풀이
`\` 모양일 때, `/` 모양일 때의 경우를 잘 생각하여야 한다. 동(dir = 3), 남(dir = 0), 서(dir = 1), 북(dir = 2)으로 정하고, dx,dy테크닉을 이용한다.

<img src="https://github.com/kimdozzi/codetree-TILs/assets/95005061/1d2ca1c6-45e6-47b3-886f-0e14f0ab2e81" width="300" height="300"/>

### Case 1. `\` 모양

<img src="https://github.com/kimdozzi/codetree-TILs/assets/95005061/dade9441-3835-4af8-97ac-6021a9dc1678" width="300" height="300"/>

대각선 기준 왼쪽 위의 경우를 보자. 남쪽으로 향하는 레이저(dir = 0)가 거울에 반사되어 서쪽으로(dir = 1) 향하게 되고, 동쪽으로 향하는 레이저(dir = 3)이 거울에 반사되어 북쪽으로(dir = 2)로 향하게 된다.
대각선 기준 오른쪽 아래의 경우도 살펴보면, 2로 들어오면 3으로 나가고, 1로 들어오면 0으로 나간다. 

대각선 왼쪽 상단   : 0 -> 1, 3 -> 2

대각선 오른쪽 하단 : 1 -> 0, 2 -> 3 

이는 현재 방향 dir에 1을 xor하면 나오는 결과이다. 
```python
0 ^ 1 = 1, 1 ^ 1 = 0  # 0000 ^ 0001 = 0001, 0001 ^ 0001 = 0000
3 ^ 1 = 2, 2 ^ 1 = 3  # 0011 ^ 0001 = 0010, 0010 ^ 0001 = 0011
```


### Case 2. `/` 모양
<img src="https://github.com/kimdozzi/codetree-TILs/assets/95005061/a109ded7-4a57-469e-bf58-275dd4be4e89" width="300" height="300"/>

원리는 같다. 다만, xor 연산을 이용하는 것이 아닌, 3 - 현재 방향 dir을 해주면 된다. 위와 같은 케이스를 고려하여 다음 이동할 좌표를 구해주고, move_num을 1씩 증가시키면서 총 몇번 거울에 튕기는 지 계산해주면 된다. 좀 어려웠다....ㅎ


---
## 코드
```python
# 변수 선언 및 입력
n = int(input())
arr = [input() for _ in range(n)]

start_num = int(input())


# 주어진 숫자에 따라
# 시작 위치와 방향을 구합니다.
def initialize(num):
    if num <= n:
        return 0, num - 1, 0
    elif num <= 2 * n:
        return num - n - 1, n - 1, 1
    elif num <= 3 * n:
        return n - 1, n - (num - 2 * n), 2
    else:
        return n - (num - 3 * n), 0, 3

    
def in_range(x, y):
    return 0 <= x and x < n and 0 <= y and y < n
    

# (x, y)에서 시작하여 next_dir 방향으로
# 이동한 이후의 위치를 반환합니다.
def move(x, y, next_dir):
    dxs, dys = [1, 0, -1, 0], [0, -1, 0, 1]
    nx, ny = x + dxs[next_dir], y + dys[next_dir]
    return nx, ny, next_dir


def simulate(x, y, move_dir):
    move_num = 0
    while in_range(x, y):
        # 0 <-> 1 / 2 <-> 3
        if arr[x][y] == '/':
            x, y, move_dir = move(x, y, move_dir ^ 1)
        # 0 <-> 3 / 1 <-> 2
        else:
            x, y, move_dir = move(x, y, 3 - move_dir)
        
        move_num += 1
    
    return move_num
    

# 시작 위치와 방향을 구합니다.
x, y, move_dir = initialize(start_num)

# (x, y)에서 move_dir 방향으로 시작하여
# 시뮬레이션을 진행합니다.
move_num = simulate(x, y, move_dir)
print(move_num)

```
