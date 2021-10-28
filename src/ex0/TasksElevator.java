package ex0;

import java.util.ArrayList;

public class TasksElevator {
    public static final int NULL=0,SRC=1, DEST=2,SRCDEST=3;
    public static final int INIT=0, GOING2SRC=1, GOIND2DEST=2, DONE=3;
    public static final int UP=1,DOWN=-1;
    private int var;
    private int lastDirect;
    private Elevator e;
    private int[] floors;
    private ArrayList<CallForElevator> calls;

    public TasksElevator(Elevator el){
        lastDirect=UP;
        this.e = el;
        this.floors=new int[e.getMaxFloor()-e.getMinFloor()+1];
        this.calls=new ArrayList<CallForElevator>();
        this.var=0-e.getMinFloor();
    }

    public int[] getFloors() {
        return floors;
    }

    public ArrayList<CallForElevator> getCalls() {
        return calls;
    }

    public int getLastDirect() {
        return lastDirect;
    }

    public void setLastDirect(int lastDirect) {
        this.lastDirect = lastDirect;
    }

    public void addDest2floors(int src){
        for (int i=0; i<calls.size();i++){
            if (calls.get(i).getSrc()==src){
                if (floors[calFloor(calls.get(i).getDest())]==NULL)
                    floors[calFloor(calls.get(i).getDest())]=DEST;
                else if (floors[calFloor(calls.get(i).getDest())]==SRC)
                    floors[calFloor(calls.get(i).getDest())]=SRCDEST;
            }
        }
        cleanFloor(src);
    }
    public void addSrc2floors(int src){
        if (floors[calFloor(src)]==NULL)
            floors[calFloor(src)]=SRC;
        else if (floors[calFloor(src)]==DEST)
            floors[calFloor(src)]=SRCDEST;
    }
    public void cleanFloor(int floor){
        floors[calFloor(floor)]=NULL;
    }
    public int stateFloor(int pos){
        return floors[pos+var];
    }

    public double taskTime(CallForElevator c){
        if (c.getState()==DONE){
            return 0;
        }
        double x=c.getTime(INIT);
        if(lastDirect==UP && c.getType()==UP){
//            if ((c.getState()==GOING2SRC||c.getState()==INIT) && e.getPos()>c.getSrc()) {//Extreme case
//                int a = getTop();
//                int b = getBottom();
//                return x+numStop(b,a)*stop1()+disTime((a-(e.getPos()+var))+(a-b)+((c.getSrc()+var)-b));
//
//                //test
//            }
            return x+numStop(e.getPos()+var,c.getDest()+var)*stop1()+disTime(c.getDest()-e.getPos());
        }
        if(lastDirect==DOWN && c.getType()==DOWN){
//            if ((c.getState()==GOING2SRC||c.getState()==INIT) && e.getPos()<c.getSrc()) {//Extreme case
//                int a = getTop();
//                int b = getBottom();
//                return x+numStop(b,a)*stop1()+disTime(((e.getPos()+var)-b)+(a-b)+(a-(c.getSrc()+var)));
//                //test
//            }
            return x+numStop(e.getPos()+var,c.getDest()+var)*stop1()+disTime(e.getPos()-c.getDest());
        }
        return x;
    }
    public double timeAddition(CallForElevator c){
        double all=0,t=0;
        for (int i = 0;i<calls.size();i++){
            CallForElevator ca = calls.get(i);
            if (c.getType()==UP && ca.getType()==UP){
                if (c.getSrc()<e.getPos() && ca.getDest()<e.getPos()) {//extreme case
                    double a = 2 * stop1();
                    if (c.getDest() >= ca.getDest())
                        a -= stop1();
                    if (c.getSrc() >= ca.getDest())
                        a -= stop1();
                    all+=a;
                    continue;
                }
                if (c.getSrc()<e.getPos())
                    continue;
                if (c.getSrc()<ca.getDest() && c.getSrc()!=ca.getSrc())
                    all+=stop1();
                if (c.getDest()<ca.getDest() && c.getDest()!=ca.getDest())//
                    all+=stop1();
                continue;
            }//Updatae to DAWN
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
    public double disTime(int dis){
        return dis/e.getSpeed();
    }
    public int numStop(int start,int end){
//        int count=0;
//        for (int i=start;i<=end;i++)
//            if (floors[i]>0)
//                count++;
        ArrayList<Integer> arr=new ArrayList<>();
        for (int i=0;i<calls.size();i++){
            if((calls.get(i).getSrc()+var)>=start && (calls.get(i).getSrc()+var)<=end && !arr.contains(calls.get(i).getSrc()))
                arr.add(calls.get(i).getSrc());
            if((calls.get(i).getDest()+var)>=start && (calls.get(i).getDest()+var)<=end && !arr.contains(calls.get(i).getDest()))
                arr.add(calls.get(i).getDest());
        }
        return arr.size();
    }
    /**
     * @return index in floor array.
     */

    public int calFloor(int a){
        return a+var;
    }
    private double stop1() {
        return e.getTimeForClose()+e.getTimeForOpen()+e.getStartTime()+e.getStopTime();
    }
    private double stopE() {
        return e.getTimeForOpen()+e.getStopTime();
    }
    public void cleanCall() {
        for (int i = 0;i<calls.size();i++){
            if (calls.get(i).getState()==DONE)
                calls.remove(calls.get(i));
        }
    }

    public int getVar() {
        return var;
    }
}
