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

### Basic Case Least Move
When it's classic situation (three pegs). The relationship between move and disks is:

M(N) = 2^N - 1

|Disks|1|2|3|4|5|6|7|8|9|10|
|-|-|-|-|-|-|-|-|-|-|-|-|-|
|Moves|1|3|7|15|31|63|127|255|511|1023|

### Frame-Stewart Algorithm
The core of algorithm lookes like below: 
1. Moving N-K from source to buffer (The left one will be the largest disk)
2. Moving K from source to destination (Move the largest disk to destination)
3. Moving N-K from buffer to destination (Moving all disk on buffer to destination)
To get the best K for current condition, we need tranverse all possible cases to determin which K will cause least move.

Here let's consider N as disks in total,  K as disks to move, P as available pegs number, M() as function to obtain least move, we got:

M(N,P) = min(M(K,P) + M(N-K,P-1) + M(K,P)) (K from 1 to N)  ==>>   M(N,P) = min(2M(K,P) + M(N-K,P-1))

Since we can calculate the move for basic case (three pegs), DP can be used to calculate all possible cases to reduce repeated calculation.

Let's say here's a DPLeastMove two-dimension array initialized like below (value represent for the least move):
|Pegs\Disks|1|2|3|4|5|6|
|-|-|-|-|-|-|-|
|3|1|3|7|15|31|63|
|4|1|3|||||
|5|1|3|||||

And the problem turns into DP[P][N] = min(2DP[P][K] + DP[P][N-K]) (K from 1 to N)

Notice: When trying to find the minimum move, do not have to go through all N cases. 

The result of each cases will looks like an upper opening curve, which means the value will go down and then raise up, once it start to raise up, we can break the loop.

Now we know the way to find the least move, we still need an array to store what is the K for the least move, record the K into anorth similar two-dimension array or you can store it with the least move like [least move, K].

Now we can solve the Hanoi Tower Problem.
