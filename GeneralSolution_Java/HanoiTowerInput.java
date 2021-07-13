import java.util.Scanner;

/**
 * Generate Input Baseline and request User Input
 * @author tianyang guan tyguancn@gmail.com
 */
public class HanoiTowerInput {
    /**
     * Number Of Tower
     */
    private int NumOfTower;
    /**
     * Number of Disk
     */
    private int NumOfDisk;
    /**
     * Lower Boundary of Number of Tower
     */
    private int TowerLowBoundary;
    /**
     * Higher Boundary of Number of Tower
     */
    private int TowerHighBoundary;
    /**
     * Lower Boundary of Number of Disk
     */
    private int DiskLowBoundary;
    /**
     * Higher Boundary of Number of Disk
     */
    private int DiskHighBoundary;

    /**
     * Initialize HanoiTowerInput with default boundary limitation
     */
    public HanoiTowerInput(){
        TowerLowBoundary = 5;
        TowerHighBoundary = 10;
        DiskLowBoundary = 3;
        DiskHighBoundary = 40;
    }

    /**
     * Initialize HanoiTowerInput with user input
     * @param towerLowBoundary Lower Boundary of Number of Tower
     * @param towerHighBoundary Higher Boundary of Number of Tower
     * @param diskLowBoundary Lower Boundary of Number of Disk
     * @param diskHighBoundary Higher Boundary of Number of Disk
     */
    public HanoiTowerInput(int towerLowBoundary, int towerHighBoundary, int diskLowBoundary, int diskHighBoundary){
        TowerLowBoundary = towerLowBoundary;
        TowerHighBoundary = towerHighBoundary;
        DiskLowBoundary = diskLowBoundary;
        DiskHighBoundary = diskHighBoundary;
    }

    /**
     * Request for user input to define the specific number of Towers and Disks
     */
    public void RequestInput(){
        // input as Scanner to get Number of Towers and Number of Disks
        Scanner input = new Scanner(System.in);
        //Request input from user side
        System.out.print("Enter the Number of Tower(Int:" + this.TowerLowBoundary + "-" +this.TowerHighBoundary +"):");
        //Loop until the input is valid
        while (true) {
            //try catch to avoid wrong type input
            try {
                NumOfTower = input.nextInt();
                //ValidTowerNumber Function to valid number range
                if (ValidTowerNumber(NumOfTower))
                    break;
                System.out.print("Please Enter Int in [" + this.TowerLowBoundary + "," +this.TowerHighBoundary +"]:");
            } catch (Exception ex) {
                System.out.print("Please Enter Int in [" + this.TowerLowBoundary + "-" +this.TowerHighBoundary +"]:");
                input.next();
            }
        }

        System.out.print("Enter the Number of Disk(Int:"+this.DiskLowBoundary +"-"+this.DiskHighBoundary+"):");
        while (true) {
            try {
                NumOfDisk = input.nextInt();
                if (ValidDiskNumber(NumOfDisk))
                    break;
                System.out.print("Please Enter Int in ["+this.DiskLowBoundary+","+this.DiskHighBoundary+"]");
            } catch (Exception ex) {
                System.out.print("Please Enter Int in ["+this.DiskLowBoundary+","+this.DiskHighBoundary+"]");
                input.next();
            }
        }
        System.out.print("Tower Number: "+ NumOfTower +"\nDisk Numer: "+ NumOfDisk+"\n");
    }

    /**
     * Function used to create Hanoi Tower module
     */
    public void StartSolution(){
        System.out.println("Start to Initialize Module\n");
        HanoiSolution hanoiSolution = new HanoiSolution(NumOfTower,NumOfDisk);
    }

    /**
     * Function used to valid Tower number
     * @param number user input
     * @return true or false
     */
    public boolean ValidTowerNumber ( int number){
        if (number >= this.TowerLowBoundary && number <= this.TowerHighBoundary)
            return true;
        else
            return false;
    }

    /**
     * Function used to valid Disk number
     * @param number user input
     * @return true or false
     */
    public boolean ValidDiskNumber ( int number){
        if (number >= this.DiskLowBoundary && number <= this.DiskHighBoundary)
            return true;
        else
            return false;
    }
}
