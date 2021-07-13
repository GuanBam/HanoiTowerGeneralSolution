/**
 * Main Class
 * @author GuanBam tyguancn@gmail.com
 */
class Main {
    /**
     * Main class
     * @param args args
     */
    public static void main(String[] args){
        /* Class used to obtain user input for number of Tower and Disk */
        HanoiTowerInput HanoiTower = new HanoiTowerInput();
        /* Request input for number of Tower and Disk */
        HanoiTower.RequestInput();
        /* Start to find solution */
        HanoiTower.StartSolution();
    }
}
