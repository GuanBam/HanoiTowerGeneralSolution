import java.util.Stack;

/**
 * Self-defined Constructor to store Peg Information
 * @author tianyang guan tyguancn@gmail.com
 */
//PegsStack constructor store Stack Value for each pegs, and store Pegs ID
public class PegsStack {
    /**
     * Stack used to store disks value
     */
    public Stack<Integer> PegStackValue;
    /**
     * ID for current Peg
     */
    public int PegID;
    /**
     * Status for current Peg (Empty or not)
     */
    public boolean PegStatus;

    /**
     * Define PegsStack with empty Stack
     * @param id PegsStack ID
     * @param status PegsStack Initialize status
     */
    public PegsStack(int id, boolean status){
        PegStackValue = new Stack<Integer>();
        PegID = id;
        PegStatus = status;
    }

    /**
     * Define PegsStack with empty Stack
     * @param value PegsStack Stack
     * @param id PegsStack ID
     * @param status PegsStack Initialize status
     */
    public PegsStack(Stack<Integer> value,int id, boolean status){
        PegStackValue = value;
        PegID = id;
        PegStatus = status;
    }

    /**
     * Check if stack of current PegsStack is empty
     * @return true or false
     */
    public boolean isEmpty(){
        //System.out.print("Peg "+PegID + " Size:" + PegStackValue.size()+"\t");
        if(this.PegStackValue.size()==0){
            return true;}
        else
            return false;
    }
}
