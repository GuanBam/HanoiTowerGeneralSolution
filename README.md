# HanoiTowerGeneralSolution
General Solution for Hanoi Tower(More than 3 towers)

## Three Tower Problem
For classic Hanoi Tower Probelm (3 Pegs)

The recursion idea will be:

1. Moving N-1 from source to buffer (The left one will be the largest disk)
2. Moving 1 from source to destination (Move the largest disk to destination)
3. Moving N-1 from buffer to destination (Moving all disk on buffer to destination)

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

## More Than Three Tower
There's an algorithm proved to be the best solution for four Pegs case and it should also be the best(not proven yet) for more than four pegs.

"Frame-Stewart Algorithm", the idea is similar to classic problems.

Instead of moveing N-1 disks from source to buffer, this time we choose to move K (1<=k<N) disks to buffer.

The problem here is now how to determine which will be the best K.

### Basic Case
When it's classic situation (three pegs). The relationship between move and disks is:

M(N) = 2^N - 1

|-|-|-|-|-|-|
|Disks|1|2|3|4|5|
|Moves|1|3|7|15|31|
