package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import ex0.TasksElevator;

import java.util.ArrayList;

public class myAlgo implements ElevatorAlgo {
    public static final int UP = 1, LEVEL = 0, DOWN = -1, ERROR = -2;
    private Building b;
    private Elevator[] e;
    private TasksElevator[] t;
    private int[] stops;
    private int[] go;

    public myAlgo(Building building) {
        this.b=building;
        this.e=new Elevator[building.numberOfElevetors()];
        for (int i=0;i<e.length;i++)
            e[i]=b.getElevetor(i);
        this.t=new TasksElevator[e.length];
        for (int i=0;i<t.length;i++)
            t[i]=new TasksElevator(e[i]);
        this.go=new int[e.length];
        for (int i=0;i<go.length;i++)
            go[i]=e[i].getPos();
        this.stops=new int[e.length];
        for (int i=0;i<stops.length;i++)
            stops[i]=Integer.MAX_VALUE;
    }


    /**
     * @return the Building on which the (online) elevator algorithm works on.
     */
    @Override
    public Building getBuilding() {
        return this.b;
    }

    /**
     * @return he algorithm name - can be any String.
     */
    @Override
    public String algoName() {
        return "The best";
    }

    /**
     * This method is the main optimal allocation (aka load-balancing) algorithm for allocating the
     * "best" elevator for a call (over all the elevators in the building).
     *
     * @param c the call for elevator (src, dest)
     * @return the index of the elevator to which this call was allocated to.
     */
    @Override
    public int allocateAnElevator(CallForElevator c) {
        double temp=Integer.MAX_VALUE;int ind=0;boolean flag=false;

        //first - search elevator without calls
        for (int i=0;i<t.length;i++){
            if (t[i].getCalls().isEmpty()){
                if (temp>t[i].disTime(Math.abs(e[i].getPos()-c.getSrc()))){
                    temp=t[i].disTime(Math.abs(e[i].getPos()-c.getSrc()));
                    ind=i;
                    flag=true;
                }
            }
        }
        if (flag){
            addCall(ind,c);
            return ind;
        }
        //second - search elevator at this direction
        for (int i=0;i<t.length;i++){
            if (c.getType()==t[i].getLastDirect())
                if ((c.getType()==UP && e[i].getPos()<c.getSrc()) || (c.getType()==DOWN && e[i].getPos()>c.getSrc())) {
                    double a= t[i].taskTime(c)+calTime(t[i])+t[i].timeAddition(c);
                    if (a < temp && t[i].taskTime(c)>(e[i].getStopTime()+1)) {
                        temp = a;
                        ind =i;
                        flag=true;
                    }
                }
        }
        if (flag){
            addCall(ind,c);
            return ind;
        }
        //else - search min "cost" of time in elevators
        for (int i=0;i<t.length;i++){
            double a= t[i].taskTime(c)+calTime(t[i])+t[i].timeAddition(c);
            if (a < temp) {
                temp = a;
                ind = i;
            }
        }
        addCall(ind,c);
        return ind;
    }

    private double calTime(TasksElevator tt) {
        double all=0;
        ArrayList<CallForElevator> calls=tt.getCalls();
        for(int i=0;i<calls.size();i++)
            all+=tt.taskTime(calls.get(i));
        return all;
    }

    private void addCall(int ind, CallForElevator c) {
        int v=t[ind].getVar();
        t[ind].getCalls().add(c);
//        if (t[ind].numStop(e[ind].getPos()+v,c.getSrc()+v)==1)
//            e[ind].stop(c.getSrc());
        t[ind].addSrc2floors(c.getSrc());

    }

    /**
     * This method is the low level command for each elevator in terms of the elevator API: GoTo, Stop,
     * The simulator calls the method every time stamp (dt), note: in most cases NO action is needed.
     *
     * @param elev the current Elevator index on which the operation is performs.
     */
    @Override
    public void cmdElevator(int elev) {
        if (e[elev].getState()==LEVEL){
            if (stops[elev]==e[elev].getPos())
                stops[elev]=Integer.MAX_VALUE;
            refresh(elev);
            if(!t[elev].getCalls().isEmpty()){
                int target=closeStop(elev);
                int direct=0;
                if (e[elev].getPos()<target)
                    direct=UP;
                else
                    direct=DOWN;
                t[elev].setLastDirect(direct);
                go[elev]=target;
                e[elev].goTo(target);
            }
        }
        if (e[elev].getState()==UP||e[elev].getState()==DOWN){
            int c=closeStop(elev,e[elev].getState());
            if (go[elev]!=c && stops[elev]==Integer.MAX_VALUE){// && t[elev].disTime(Math.abs(c-e[elev].getPos()))>(e[elev].getStopTime()+1))
                stops[elev]=c;
                e[elev].stop(c);
            }
        }
    }
    private void refresh(int g){
        int x=t[g].stateFloor(e[g].getPos());
        if(x==TasksElevator.SRC||x==TasksElevator.SRCDEST)
            t[g].addDest2floors(e[g].getPos());
        t[g].cleanFloor(e[g].getPos());
        t[g].cleanCall();
    }
    private int closeStop(int elev, int d) {
        int v=t[elev].getVar();
        int[] f=t[elev].getFloors();
        if (d==UP) {
            for (int i = e[elev].getPos() + v; i < f.length; i++)
                if (f[i] > 0)
                    return i - v;
        }
        if  (d==DOWN){
            for (int i=e[elev].getPos()+v;i>=0;i--)
                if (f[i]>0)
                    return i-v;
        }
        return e[elev].getPos();
    }
    private int closeStop(int el){
        int v=t[el].getVar();
        int[] f=t[el].getFloors();
        int i=e[el].getPos()+v+1;
        int j=i-2;
        while (i<f.length || j>=0){
            if (i<f.length && f[i]>0)
                return i-v;
            if (j>=0 && f[j]>0)
                return j-v;
            i++;j--;
        }
       return e[el].getPos();
    }
}