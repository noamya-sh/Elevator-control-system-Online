package ex0;

import java.util.ArrayList;
/**This class stores all the tasks that the elevator performs in real time,
 * stores the next stops in line and calculates elevator performance times.**/
public class TasksElevator {
    public static final int NULL=0,SRC=1, DEST=2,SRCDEST=3;
    public static final int INIT=0, GOING2SRC=1, GOIND2DEST=2, DONE=3;
    public static final int UP=1,DOWN=-1;
    private int var;//var is the different between minFloor to maxFloor of the building.
    private int lastDirect;
    private Elevator e; //The elevator that this TaskElavator accumulates its tasks.
    private int[] floors; //An array in which the floors on which the elevator "e" should stop are marked.
    private ArrayList<CallForElevator> calls;//ArrayList that stores all the calls that elevator "e" makes in real time

    //constructor
    public TasksElevator(Elevator el){
        lastDirect=UP;
        this.e = el;
        this.floors=new int[e.getMaxFloor()-e.getMinFloor()+1];
        this.calls=new ArrayList<>();
        this.var=0-e.getMinFloor();
    }
    public TasksElevator(){
        lastDirect=UP;
        this.e = null;
        this.floors=new int[5];
        this.calls=new ArrayList<>();
        this.var=0;
    }
    public int[] getFloors() {
        return floors;
    }

    public int getVar() { return var; }

    public ArrayList<CallForElevator> getCalls() {
        return calls;
    }

    public int getLastDirect() {
        return lastDirect;
    }

    public void setLastDirect(int lastDirect) {
        this.lastDirect = lastDirect;
    }

    //this function gets a source floor of a new call and changes the value of the floor in the array.
    public void addSrc2floors(int src){
        if (floors[src+var]==NULL)
            floors[src+var]=SRC;
        else if (floors[src+var]==DEST)
            floors[src+var]=SRCDEST;
    }
    /**This function get source floor input, searches for all calls that are their source floor
     *  and updates their dest floors in the "floors" array**/
    public void addDest2floors(int src){
        for (int i=0; i<calls.size();i++){
            if (calls.get(i).getSrc()==src){
                if (floors[calls.get(i).getDest()+var]==NULL)
                    floors[calls.get(i).getDest()+var]=DEST;
                else if (floors[calls.get(i).getDest()+var]==SRC)
                    floors[calls.get(i).getDest()+var]=SRCDEST;
            }
        }
        cleanFloor(src);//delete src floor value.
    }
    /**Once the elevator stops on a particular floor, this function will call to delete the stop on that floor.**/
    public void cleanFloor(int floor){
        floors[floor+var]=NULL;
    }
    /**Return the value of this floor**/
    public int stateFloor(int pos){
        return floors[pos+var];
    }
    /**This function calculates the call time - from the time
     *  the call was init until it is to be complete (approximate)**/
    public double taskTime(CallForElevator c){
        if (c.getState()==DONE){
            return 0;
        }
        double x=c.getTime(INIT);//Get the time that has passed since the beginning of the call.
        if(lastDirect==UP && c.getType()==UP){
            //add the estimated amount of stops along the way to the destination floor and travel time.
            return x+numStop(e.getPos()+var,c.getDest()+var)*stop1()+disTime(c.getDest()-e.getPos());
        }
        if(lastDirect==DOWN && c.getType()==DOWN){
            //and here respectively to DOWN...
            return x+numStop(e.getPos()+var,c.getDest()+var)*stop1()+disTime(e.getPos()-c.getDest());
        }
        return x;
    }
    /**This function calculates the time that will be added to all the calls that the elevator makes.**/
    public double timeAddition(CallForElevator c){
        double all=0;
        //Depending on the hypothesis of the direction of travel, for each task check whether the stops of the new reading will delay it.
        for (int i = 0;i<calls.size();i++){
            CallForElevator ca = calls.get(i);
            if (c.getType()==UP && ca.getType()==UP){
                if (c.getSrc()<e.getPos() && ca.getDest()<e.getPos()) {//extreme case (hypothetical)
                    double a = 2 * stop1();
                    if (c.getDest() >= ca.getDest())
                        a -= stop1();
                    if (c.getSrc() >= ca.getDest())
                        a -= stop1();
                    all+=a;
                    continue;
                }
                if (c.getSrc()<e.getPos()) //We will probably not perform the new call before the old call...
                    continue;
                if (c.getSrc()<ca.getDest() && c.getSrc()!=ca.getSrc())//If the src floor of new call is on the way to the dest floor of the old.
                    all+=stop1();
                if (c.getDest()<ca.getDest() && c.getDest()!=ca.getDest())//If also dest floor befor dest floor of the old call.
                    all+=stop1();
                continue;
            }//Same as the opposite direction respectively...
            if (c.getType()==DOWN && ca.getType()==DOWN){
                if (c.getSrc()>e.getPos() && ca.getDest()>e.getPos()) {//extreme case
                    double a = 2 * stop1();
                    if (c.getDest() <= ca.getDest())
                        a -= stop1();
                    if (c.getSrc() <= ca.getDest())
                        a -= stop1();
                    all+=a;
                    continue;
                }
                if (c.getSrc()>e.getPos())
                    continue;
                if (c.getSrc()>ca.getDest() && c.getSrc()!=ca.getSrc())
                    all+=stop1();
                if (c.getDest()>ca.getDest() && c.getDest()!=ca.getDest())
                    all+=stop1();
                continue;
            }
        }
        return all;
    }
    /**This function calculates the time required for the elevator to pass the input of several floors.**/
    public double disTime(int dis){
        return dis/e.getSpeed();//time*speed=distance
    }
    /**Function for calculating the number of approximate stops between two given floors.**/
    public int numStop(int start,int end){
        ArrayList<Integer> arr=new ArrayList<>();
        for (int i=0;i<calls.size();i++){
            //if this stop between start to end and is not in arr:
            if((calls.get(i).getSrc()+var)>=start && (calls.get(i).getSrc()+var)<=end && !arr.contains(calls.get(i).getSrc()))
                arr.add(calls.get(i).getSrc());
            if((calls.get(i).getDest()+var)>=start && (calls.get(i).getDest()+var)<=end && !arr.contains(calls.get(i).getDest()))
                arr.add(calls.get(i).getDest());
        }
        return arr.size();
    }
    /**time required to perform stop**/
    private double stop1() {
        return e.getTimeForClose()+e.getTimeForOpen()+e.getStartTime()+e.getStopTime();
    }

    /**delete all calls that done.**/
    public void cleanCall() {
        for (int i = 0;i<calls.size();i++){
            if (calls.get(i).getState()==DONE)
                calls.remove(calls.get(i));
        }
    }
}
