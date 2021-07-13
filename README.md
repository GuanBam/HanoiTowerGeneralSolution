# HanoiTowerGeneralSolution
General Solution for Hanoi Tower(More than 3 towers)

For classic Hanoi Tower Probelm (3 Pegs)
We say Pegs: A,B,C.
```
Hanoi(N, src, buff, dst):
    if n==1:
        dst.push(src.pop())
    else:
        Hanoi(N-1, src, dst, buff)
        Hanoi(N,)
```
