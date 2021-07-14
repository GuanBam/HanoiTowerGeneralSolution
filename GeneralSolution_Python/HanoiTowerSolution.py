"""
Code used to solve general Hanoi Tower Problem
@author GuanBam    tyguancn@gmail.com
"""

"""
Class Peg used to store peg status and ID
"""
class Peg:
    """
    Peg Class
    @param pegId    Identification Number of Peg
    @param pegStatus    Array to store disk ID
    """
    def __init__(self, pegId, pegStatus):
        self.pegId = pegId
        self.pegStatus = pegStatus
        
    """
    Check if current Peg is empty
    @return True or False
    """
    def IsEmpty(self):
        return True if self.pegStatus==[] else False

"""
Class HanoiTowerSolution used to solve problem with more than 3 towers and 3 disks
"""
class HanoiTowerSolution:
    """
    Initialize class
    @param numOfPegs    Number of Pegs
    @param numOfDisks   Number of Disks
    """
    def __init__(self, numOfPegs, numOfDisks):
        assert numOfPegs>=3 and numOfDisks>=3, "Input Error, both pegs and disks should more than 3"
        self.numOfPegs = numOfPegs
        self.numOfDisks = numOfDisks
        
        """Initialize DP Table for best solution"""
        self.dpBestSolution = [[[0,0] for i in range(numOfDisks)] for i in range(numOfPegs-2)]
        for i in range(1, self.numOfDisks + 1):
            self.dpBestSolution[0][i-1] = [i, 2**i - 1]
        for i in range(1, self.numOfPegs - 2):
            self.dpBestSolution[i][0] = [1, 1]
            self.dpBestSolution[i][1] = [1, 3]          
        self.FindBestSolution(self.numOfDisks, self.numOfPegs)

        self.errorCount = 0
        self.stepCount = 0
        """Initialize Pegs Status"""
        self.pegArray = [Peg(0,[])]
        self.pegArray[0].pegStatus = [i for i in range(self.numOfDisks,0,-1)]
        for i in range(1,self.numOfPegs):
            self.pegArray.append(Peg(i,[]))
        self.GeneralSolution(self.numOfDisks, self.numOfPegs, 0, 1, self.numOfPegs-1)
        print("Least Move: ", self.dpBestSolution[-1][-1][1])
        print("Used Move: ", self.stepCount)
        print(self.errorCount, " Error Found")
        
    """
    Function to Solve General Hanoi Tower Problem
    @param N    Number of Disks
    @param P    Number of Pegs
    @param sourceID   ID of source peg
    @param bufferID     ID of buffer peg
    @param destID       ID of destination peg
    """
    def GeneralSolution(self, N, P, sourceID, bufferID, destID):
        bufferID = bufferID
        K = self.dpBestSolution[P-3][N-1][0]
        """Basic case (Can be solved by classic solution)"""
        if (N<=2 or P<=3 or K<1):
            self.ClassicSolution(N,self.pegArray[sourceID], self.pegArray[bufferID], self.pegArray[destID])
        else:
            """Move K disks from source peg to buffer peg"""
            self.GeneralSolution(K, P, sourceID, destID, bufferID)
            """Update a new buffer for next move, since last one is occuiped"""
            newBuff = bufferID
            if self.EmptyPegs(destID)!=[]:
                newBuff = self.EmptyPegs(destID)[0]
            """Move left disks to destination via new buffer peg"""
            self.GeneralSolution(N - K, P-1, sourceID, newBuff, destID)
            """Move disks on last buffer to the destination peg"""
            self.GeneralSolution(K, P, bufferID, sourceID, destID)
            
    """
    Function to Solve classic Hanoi Tower Problem
    @param srcPeg    source Peg
    @param bufPeg    buffer Peg
    @param dstPeg    destination Peg
    """
    def ClassicSolution(self, N, srcPeg, bufPeg, dstPeg):
        if N==1:
            """If statement for error check (upper disk larger than original one )"""
            if dstPeg.pegStatus!=[] and srcPeg.pegStatus[-1]>dstPeg.pegStatus[-1]:
                self.errorCount += 1
            """Moving disk from source peg to destination peg"""
            dstPeg.pegStatus.append(srcPeg.pegStatus.pop())
            """Print each move"""
            self.stepCount += 1
            self.PrintPegStatus()
        else:
            self.ClassicSolution(N-1, srcPeg, dstPeg, bufPeg)
            self.ClassicSolution(1, srcPeg, bufPeg, dstPeg)
            self.ClassicSolution(N-1, bufPeg, srcPeg, dstPeg)

    """
    Function to Find Best Solution
    @param N   Remaing Disks Number
    @param P   Available Pegs Number
    @return        Number of Least Move
    """
    def FindBestSolution(self, N, P):
        """Base case (Boundary)"""
        if N<0 or P <3:
            return -1
        """Avoid repeat calculation"""
        if self.dpBestSolution[P-3][N-1][1] != 0:
            return self.dpBestSolution[P-3][N-1][1]
        """Initialize worst case"""
        leastMove = 2*self.FindBestSolution(N-1, P) + self.FindBestSolution(1, P-1)
        K = N-1
        """Tranverse all cases"""
        for k in range(N - 2, 0, -1):        
                temp = 2*self.FindBestSolution(k, P)+self.FindBestSolution(N-k, P-1)
                if temp<leastMove:
                    leastMove = temp
                    K = k
                else:
                    break
        """Update DP Table"""
        self.dpBestSolution[P-3][N-1] = [K, leastMove]
        return leastMove
    
    """
    Function to Find all available empty pegs
    @param dstId   Destination Peg ID
    @return              All available Peg ID
    """
    def EmptyPegs(self, dstId):
        availablePegId = []
        for i in range(self.numOfPegs):
            if self.pegArray[i].IsEmpty() and (i != dstId):
                availablePegId.append(i)
        return availablePegId

    def PrintPegStatus(self):
        print("Step "+str(self.stepCount))
        for i in range(self.numOfPegs):
            print("Peg ", (i+1), self.pegArray[i].pegStatus)
        print("----------------------------------------------------------------------")
            

