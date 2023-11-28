## 문제
https://www.codetree.ai/missions/5/problems/robot-moving-from-side-to-side?&utm_source=clipboard&utm_medium=text


<br>


## 풀이
* 1초마다 시뮬레이션을 돌려서 두 로봇이 같은 곳에 있는지 판별하는 것은 어려움
* 해당 위치를 방문했다면 1을 증가시키는 방식으로도 어려움 -> 몇 초에 해당 위치에 있었는 지 알 수 없음

배열의 각 칸을 매 초마다 해당 로봇이 존재하는 위치 값을 표기하여 문제를 해결할 수 있다. 
다시 말해서, 배열의 1번째 칸의 값은 t=1일 때, 로봇A가 서있는 위치`(robot_A[1] = -1)` 로 표현하면 된다.

<br>

`robot_A[7] = 3`, `robot_B[7] = 3` 이라면, A와 B는 7초에 3의 지점에 같이 위치해 있다.
`robot_A[8] = 2`, `robot_B[8] = 2` 이면, 7초에 같은 위치에 존재했기 때문에, 8초의 경우는 카운트하면 안된다. (바로 직전에는 다른 위치에 있다가 그 다음 번에 같은 위치에 오게 되는 경우에 포함되지 않는다.)


<br>



## 코드
```python
import sys
si = sys.stdin.readline

MAX_T = 1000000

n,m=tuple(map(int, si().split()))
pos_a, pos_b = [0] * (MAX_T + 1), [0] * (MAX_T + 1)

# A가 매 초마다 서있는 위치를 기록
time_a = 1
for _ in range(n): 
    t,d = tuple(si().split())
    for _ in range(int(t)) :
        pos_a[time_a] = pos_a[time_a - 1] + (1 if d == 'R' else -1)
        time_a += 1

time_b = 1
for _ in range(m) :
    t,d = tuple(si().split())
    for _ in range(int(t)) :
        pos_b[time_b] = pos_b[time_b - 1] + (1 if d == 'R' else -1)
        time_b += 1

if time_a < time_b :
    for i in range(time_a, time_b) :
        pos_a[i] = pos_a[i-1]
elif time_a > time_b :
    for i in range(time_b, time_a) :
        pos_b[i] = pos_b[i-1]


ans = 0
for i in range(1, max(time_a, time_b)) :
    if pos_a[i] == pos_b[i] and pos_a[i-1] != pos_b[i-1] :
        ans += 1

print(ans)

```
