import java.util.*;

/**
 * Solve Hanoi Tower Problems with more than 3 pegs
 * @author tianyang guan tyguancn@gmail.com
 */

public class HanoiSolution {
    /**
     * Constant Variable, Number of Tower
     */
    final int  NumberOfTower;

    /**
     * Constant Variable, Number of Disk
     */
    final int NumberOfDisk;

    /**
     * Constant Variable, Stack for initialize case of source peg [n,n-1,....,3,2,1]
     */
    final Stack<Integer> SourcePeg;

    /**
     * Constant Variable, Array for self-defined constructor PegsStack, array includes all pegs status
     */
    final PegsStack[] TotalPeg;

    /**
     * Variable, Record the current moving step
     */
    private int TotalStep;

    /**
     * Variable, Record total error occurred
     */
    private int ErrorCount;
    /**
     * Constant Variable, Self-defined constructor record best solution according to Frame-Stewart Algorithm
     */
    final HanoiBestCase bestSolution;

    private boolean[] bufferStatus;
    /**
     * General Solution Function for Towers more than 3
     * @param NumOfTower Number of Tower
     * @param NumOfDisk Number of Disk
     */
    public HanoiSolution(int NumOfTower, int NumOfDisk){
        NumberOfTower = NumOfTower;
        NumberOfDisk = NumOfDisk;

        TotalStep = 0;
        ErrorCount = 0;
        /* SourcePeg is the Stack for first Peg, which should be [n,n-1,.....3,2,1] n is the disks number */
        SourcePeg = new Stack<>();
        for(int i = NumOfDisk;i>0;i--){
            SourcePeg.push(i);
        }

        /* Initialize all pegs, except the first peg, all of them should be empty at beginning */

        /* Self-defined constructor PegsStack is used, details in PegsStack.java */
        TotalPeg = new PegsStack[NumOfTower];
        /* Assign the source peg status */
        TotalPeg[0] = new PegsStack(SourcePeg,0,true);
        /* initialize other peg status */
        for(int i=1;i<NumberOfTower;i++)
            TotalPeg[i] = new PegsStack(i,false);

        /* Find the best case for current input */
        bestSolution= new HanoiBestCase(NumberOfTower,NumberOfDisk);

        /* You may uncomment below two lines to check the output of bestSolution */
        //bestSolution.PrintDPLeastMove();
        //bestSolution.PrintDPBestCase();

        /* Print Initialize Peg Status */
        PrintCurrentStatus();

        /* Start to find Solution */
        Solution(NumberOfDisk,NumberOfTower,0,AvailableBuffer()[0], NumberOfTower-1);

        /* Given Conclusion */
        System.out.println("The Least Step should be: " + bestSolution.LeastMove());
        System.out.println("This function take steps: " +TotalStep);
        System.out.print("Total Error Move: " + ErrorCount);
    }

    /**
     * Solution function used to slide disks according to the value get from best case
     * @param disk Number of Disks need to be moved
     * @param pegs Number of Pegs are involved
     * @param sourceId PegStack ID for the one is holding disks
     * @param buffId PegStack Id for the one gonna passing disks
     * @param destId PegStack Id for the one gonna holding disks
     */
    public void Solution(int disk, int pegs,int sourceId, int buffId,int destId){
        /* Initialize buffId, buffId represent the pegs Id which will be used as buff here */
        int bufferId = buffId;

        /*
           Request K first, K represent for how many disks need to be moved.
           Don't have to worry about out of index due to the next if statement will help.
        */
        int K = bestSolution.DPBestCase[pegs - 3][disk - 1];
        /* If disk<=2 or pegs<=3 or k<1 there's no need to find next K, classic solution can be used */
        if (disk <= 2 || pegs <= 3 || K < 1) {
            //System.out.println("\n Start Classic Solution\n disk =" + disk + " Src=" + sourceId + " Dst=" + destId);
            /* Using classic solution to solve remaining problem */
            ClassicSolution(disk, TotalPeg[sourceId], TotalPeg[bufferId], TotalPeg[destId]);
            //System.out.println("End of Classic Solution");
        }
        else {
            //System.out.println("Next Recursion K = " + K + " Src =" + (sourceId+1) + "to Buf = " + (bufferId+1));
            /*
               Here the buff will change with the value we set at the beginning of this function.
               Move K disks from source to buffer
             */
            Solution(K, pegs, sourceId, destId, bufferId);

            //System.out.println("Next Recursion K = " + (disk - K) + " Src=" + (sourceId+1) + "to Dst=" + (destId+1));
            /*
                After first move finished, new buffer need be assigned.
                Cause original buffer is occupied, destination can't be used.
                So here check which buffer is empty and assign it as the new buffer for next move.
            */
            int newBufferId=bufferId;
            if(!TotalPeg[bufferId].isEmpty()){
                if(AvailableBuffer(destId).length!=0)
                    newBufferId = AvailableBuffer(destId)[0];
            }
            /* Move Disk-K disks from source to destination */
            Solution(disk - K, pegs - 1, sourceId, newBufferId, destId);

            //System.out.println("Next Recursion K = " + K + " Buf=" + (sourceId+1) + "to Dst=" + (destId+1));
            /* Move K disks from buffer to destination */
            Solution(K, pegs, bufferId, sourceId, destId);
        }

    }

    /**
     * Classic Hanoi Tower Solution with Three Pegs
     * @param num Number of Disks need to be moved
     * @param source PegsStack is holding disks
     * @param buffer PegsStack gonna passing disks
     * @param destination PegsStack gonna holding disks
     */
    public void ClassicSolution(int num,  PegsStack source, PegsStack buffer, PegsStack destination){
        /* Set baseline, move the disk on the top of src peg to the top of dst peg */
        if(num == 1){
            if(!destination.isEmpty() && source.PegStackValue.peek() > destination.PegStackValue.peek()) {
                System.out.print("\n Error Occurred  \n");
                ErrorCount += 1;
            }
            destination.PegStackValue.push(source.PegStackValue.pop());
            /* Calculating the step used */
            TotalStep += 1;
            /* Print current peg status */
            PrintCurrentStatus();
        }
        /* Classic Hanoi Tower Recursion */
        else{
            /* Move num-1 from source to buff */
            ClassicSolution(num-1,source,destination,buffer);
            /* Move rest 1 from source to destination */
            ClassicSolution(1,source,buffer,destination);
            /* Move num-1 from buff to destination */
            ClassicSolution(num-1,buffer,source,destination);
        }
    }

    /**
     * Request for All Empty Pegs ID
     * @return Array of Empty Pegs ID
     */
    public int[] AvailableBuffer(){
        int Number = 0;
        /* Traverse all pegs */
        for(int i=0; i<NumberOfTower; i++)
        {
            /* Count the number of empty pegs */
            if(TotalPeg[i].isEmpty())
                Number += 1;
        }
        /* Initialize int array to store the index of empty pegs */
        int[] availableBuffer = new int[Number];
        /* Initialize current represent for array index */
        int current = 0;
        for(int i=0; i<NumberOfTower;i++){
            if( TotalPeg[i].isEmpty()){
                availableBuffer[current] = TotalPeg[i].PegID;
                current += 1;
            }
        }
        //System.out.print("\nAvailable Buffer: "+availableBuffer.length);
        return availableBuffer;
    }

    /**
     * Request for All Empty Pegs ID expect the certain one
     * @param dest dest, which should be excluded
     * @return Array of Available Pegs ID
     */
    public int[] AvailableBuffer(int dest){
        int Number = 0;
        for(int i=0; i<NumberOfTower; i++)
        {
            if(i!=dest && TotalPeg[i].isEmpty())
                Number += 1;
        }
        int[] availableBuffer = new int[Number];
        int current = 0;
        for(int i=0; i<NumberOfTower; i++){
            if(i!=dest && TotalPeg[i].isEmpty()){
                availableBuffer[current] = TotalPeg[i].PegID;
                current += 1;
            }
        }
        //System.out.print("\nAvailable Buffer: "+availableBuffer.length);
        return availableBuffer;
    }

    /**
     * Print Current Pegs Status
     */
    public void PrintCurrentStatus(){
        //Print Current Step Number
        System.out.print("Current Step: "+ TotalStep + "\n");
        //Print All Pegs Status
        for(int i =0; i<NumberOfTower;i++)
            if(TotalPeg[i]!=null)
                System.out.print("Tower "+ (i+1) + ": "+TotalPeg[i].PegStackValue.toString()+"\n");
            else
                System.out.print("Tower "+ (i+1) + ": [ ]\n");
        System.out.print("#####################################\n");
    }


}
