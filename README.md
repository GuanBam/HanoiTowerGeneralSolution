# HanoiTowerGeneralSolution
General Solution for Hanoi Tower(More than 3 towers)

## Three Tower Problem
For classic Hanoi Tower Probelm (3 Pegs)
The recursion idea will be
. Moving N-1 from source to buffer (The left one will be the largest disk)
. Moving 1 from source to destination (Move the largest disk to destination)
. Moving N-1 from buffer to destination (Moving all disk on buffer to destination)
```Python
/**
* @N: number of disk to move
* @src: source peg
* @buff: buffer peg
* @dst: destination peg
**/
Hanoi(N, src, buff, dst):
    if n==1:
        dst.push(src.pop())
    else:
        Hanoi(N-1, src, dst, buff)
        Hanoi(1, src, buff, dst)
        Hanoi(N-1, buff, src, dst)
```
