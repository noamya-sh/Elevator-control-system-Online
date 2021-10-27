package ex0;

import java.util.ArrayList;

public class TasksElevator {
    public static final int NULL=0,SRC=1, DEST=2,SRCDEST=3;
    public static final int INIT=0, GOING2SRC=1, GOIND2DEST=2, DONE=3;
    public static final int UP=1,DOWN=-1;
    public int var;
    private int lastDirect;
    private Elevator e;
    private int[] floors;
    private ArrayList<CallForElevator> calls;


//    public TasksElevator(){
//        lastDirect=UP;
//        this.e = null;
//        this.floors=new int[e.getMaxFloor()-e.getMinFloor()+1];//[-k,-1],0,[1,k]
//        this.calls=new ArrayList<CallForElevator>();
//    }
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
//    public double getTime(){
//        double all=0,t=0;
//        for (int i = 0;i<calls.size();i++){
//            CallForElevator ca = calls.get(i);
//            if (ca.getState()==DONE){
//                calls.remove(ca);
//                continue;
//            }
//            if(lastDirect==UP && ca.getType()==UP){
//                if (ca.getState()==GOING2SRC && e.getPos()>ca.getSrc()) {//Extreme case
//                    int a = getTop();
//                    int b = getBottom();
//                    all += (numStop(b,a)*stop1()+disTime((a-e.getPos()+var)+(a-b)+(ca.getSrc()+var-b)));
//                    continue;
//                    //test
//                }
//                all += numStop(e.getPos()+var,ca.getDest()+var)*stop1()+disTime(ca.getDest()-e.getPos());
//                continue;
//            }
//            if(lastDirect==DOWN && ca.getType()==DOWN){
//                if (ca.getState()==GOING2SRC && e.getPos()<ca.getSrc()) {//Extreme case
//                    int a = getTop();
//                    int b = getBottom();
//                    all += (numStop(b,a)*stop1()+disTime((e.getPos()+var-b)+(a-b)+(a-ca.getSrc()+var)));
//                    continue;
//                    //test
//                }
//                all += numStop(e.getPos()+var,ca.getDest()+var)*stop1()+disTime(e.getPos()-ca.getDest());
//                continue;
//            }
//        }
//        return all;
//    }
    public double taskTime(CallForElevator c){
        if (c.getState()==DONE){
            return 0;
        }
        if(lastDirect==UP && c.getType()==UP){
            if ((c.getState()==GOING2SRC||c.getState()==INIT) && e.getPos()>c.getSrc()) {//Extreme case
                int a = getTop();
                int b = getBottom();
                return numStop(b,a)*stop1()+disTime((a-(e.getPos()+var))+(a-b)+((c.getSrc()+var)-b));

                //test
            }
            return numStop(e.getPos()+var,c.getDest()+var)*stop1()+disTime(c.getDest()-e.getPos());
        }
        if(lastDirect==DOWN && c.getType()==DOWN){
            if ((c.getState()==GOING2SRC||c.getState()==INIT) && e.getPos()<c.getSrc()) {//Extreme case
                int a = getTop();
                int b = getBottom();
                return numStop(b,a)*stop1()+disTime(((e.getPos()+var)-b)+(a-b)+(a-(c.getSrc()+var)));
                //test
            }
            return numStop(e.getPos()+var,c.getDest()+var)*stop1()+disTime(e.getPos()-c.getDest());
        }
        return 0;
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
    public int getNext(){
        int next=e.getMinFloor();
        if (lastDirect == UP && e.getPos()+var==getTop())
            lastDirect = DOWN;
        if (lastDirect == DOWN && e.getPos()+var==getBottom())
            lastDirect = UP;

        if (lastDirect == UP){
            for (int i=e.getPos()+var+1;i<floors.length;i++)
                if (floors[i]>0){
                    next=i-var;
                    break;
                }
        }
        if (lastDirect == DOWN ){
            for (int i=e.getPos()+var-1;i>=0;i--)
                if (floors[i]>0){
                    next=i-var;
                    break;
                }
        }
        return next;
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
    private int getBottom() {
        for (int i=0;i<floors.length;i++){
            if (floors[i]>0)
                return i;
        }
        return 0;
    }
    /**
     * @return index in floor array.
     */
    private int getTop() {
        for (int i=floors.length-1;i>=0;i--){
            if (floors[i]>0)
                return i;
        }
        return floors.length-1;
    }

    public int calFloor(int a){
        return a+var;
    }
    private double stop1() {
        return e.getTimeForClose()+e.getTimeForOpen()+e.getStartTime()+e.getStopTime();
    }

    public void cleanCall() {
        for (int i = 0;i<calls.size();i++){
            if (calls.get(i).getState()==DONE)
                calls.remove(calls.get(i));
        }
    }
}
