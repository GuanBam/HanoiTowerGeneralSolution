/**
 * Find Best Solution as reference for HanoiSolution.java
 * @author tianyang guan tyguancn@gmail.com
 */
public class HanoiBestCase {
    /**
     * Number of Tower
     */
    final int NumberOfTower;
    /**
     * Number of Disk
     */
    final int NumberOfDisk;
    /**
     * Two-dimension array for least move steps, record the number of least move
     * Row for Pegs ID, Column for Disk Number
     */
    public int[][] DPLeastMove;
    /**
     * Two-dimension array for best case, record the number of best K disks to move
     * Row for Pegs ID, Column for Disk Number
     */
    public int[][] DPBestCase;

    /**
     * Initialize the Base line
     * @param NumOfTower Number of Tower
     * @param NumOfDisk Number of Disk
     */
    public HanoiBestCase(int NumOfTower, int NumOfDisk){
        NumberOfTower = NumOfTower;
        NumberOfDisk = NumOfDisk;

        /* Array store the least move for each situation */
        DPLeastMove = new int[NumberOfTower-2][NumberOfDisk];
        /* First row for case of Pegs=3, least step should be  2^(Disk Number) -1 */
        for(int i=0;i<NumOfDisk;i++)
            DPLeastMove[0][i] = (int)Math.pow(2.,(double)i+1)-1;
        /* First and Second Column for Pegs more than 3 with Disks 1 and 2, the least step should always be 1 and 3 */
        for(int i=1;i<NumberOfTower-2;i++) {
            DPLeastMove[i][0] = 1;
            DPLeastMove[i][1] = 3;
        }

        /* Array store the best K for each move */
        DPBestCase = new int[NumberOfTower-2][NumberOfDisk];
        /* Call leastMoves Function to fill both table */
        leastMoves(NumberOfDisk,NumberOfTower);
    }

    /**
     * Dynamic Programming to find least moves for all involved case
     * @param disks Number of Remaining Disks
     * @param pegs Number of Available Pegs
     * @return least move steps
     */
    public int leastMoves(int disks, int pegs){
        /* Check condition if it is out of boundary */
        if (disks<0 || pegs<3)
            return -1;
        /* Check if already get the best solution for the condition */
        if(DPLeastMove[pegs-3][disks-1]!=0){
            return DPLeastMove[pegs-3][disks-1];
        }
        /* Initialize the least moves with worst case */
        double least_moves;
        int moving_disk;
        least_moves = 2*leastMoves(disks-1,pegs) + leastMoves(1,pegs-1);
        moving_disk = disks-1;
        /* Traverse all possible case to find the best case */
        for (int r=disks-2;r>0;--r){
            double moves = 2*leastMoves(r,pegs) + leastMoves(disks-r,pegs-1);
            /* Update best case */
            if (moves<least_moves){
                least_moves=moves;
                moving_disk = r;
            }
            /*
                if current moves > least moves
                There's no need go further, since the step will take more for further case
            */
            else
                break;
        }
        /* Update both DP table and return the value */
        DPBestCase[pegs-3][disks-1] = moving_disk;
        DPLeastMove[pegs-3][disks-1] = (int)least_moves;
        return (int) least_moves;
    }

    /**
     * Print DP Least Move Table
     */
    public void PrintDPLeastMove(){
        System.out.print("Least Move Table:\nP\\D");
        for(int i=0; i<NumberOfDisk; i++)
            System.out.print("\t\t"+(i+1));
        for(int i=0; i<NumberOfTower-2; i++) {
            System.out.print("\n" + (i + 3));
            for(int k=0; k<NumberOfDisk; k++)
                System.out.print("\t\t"+DPLeastMove[i][k]);
        }
    }

    /**
     * Print DP Best Case Table
     */
    public void PrintDPBestCase(){
        System.out.print("\n\nMoving Number Table:\nP\\D");
        for(int i=0; i<NumberOfDisk; i++)
            System.out.print("\t\t"+(i+1));
        for(int i=0; i<NumberOfTower-2; i++) {
            System.out.print("\n" + (i + 3));
            for(int k=0; k<NumberOfDisk; k++)
                System.out.print("\t\t"+DPBestCase[i][k]);
        }
    }

    /**
     * Return Total Least Move
     * @return least move for final answer
     */
    public int LeastMove(){
        return DPLeastMove[NumberOfTower-3][NumberOfDisk-1];
    }
}
