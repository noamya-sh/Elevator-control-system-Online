package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;

public class ElevAlgo{
//    public static final int UP=1, DOWN=-1;
//    private Building building;
//    private Elevator[] e;
//    private ArrayList<Integer>[] queue;
//    private ArrayList<Integer>[] calls;
//
//    /**
//     * @return the Building on which the (online) elevator algorithm works on.
//     */
//    @Override
//    public Building getBuilding() {
//        return this.building;
//    }
//
//    /**
//     * @return he algorithm name - can be any String.
//     */
//    @Override
//    public String algoName() {
//        return "null";
//    }
//
//    /**
//     * This method is the main optimal allocation (aka load-balancing) algorithm for allocating the
//     * "best" elevator for a call (over all the elevators in the building).
//     *
//     * @param c the call for elevator (src, dest)
//     * @return the index of the elevator to which this call was allocated to.
//     */
//    @Override
//    public int allocateAnElevator(CallForElevator c) {
//        for (int i=0;i<e.length;i++){
//            if (e[i].getPos() == c.getSrc() && e[i].getState()==Elevator.LEVEL && queue[i].isEmpty()){
//
//                return i;}
//        }
//        int min=Integer.MAX_VALUE;
//        for (int i=0;i<e.length;i++){
//            if (e[i].getState()==Elevator.UP && c.getType()==Elevator.UP && e[i].getPos()<c.getSrc())
//                if (disTime(e[i],c.getSrc()) >= 2)
//                    return i;
//        }
//        for (int i=0;i<e.length;i++){
//            if (e[i].getState()==Elevator.LEVEL && queue[i].isEmpty()){
//                return i;
//            }
//        }
//        int minTask=Integer.MAX_VALUE;
//        int ind=0;
//        for (int i=0;i<e.length;i++){
//            if (queue[i].size()<minTask){
//                minTask=queue[i].size();
//                ind =i;
//            }
//        }
//        return i;
//    }
//
//    /**
//     * This method is the low level command for each elevator in terms of the elevator API: GoTo, Stop,
//     * The simulator calls the method every time stamp (dt), note: in most cases NO action is needed.
//     *
//     * @param elev the current Elevator index on which the operation is performs.
//     */
//    @Override
//    public void cmdElevator(int elev) {
//        Elevator curr = this.getBuilding().getElevetor(elev);
//    }
}
