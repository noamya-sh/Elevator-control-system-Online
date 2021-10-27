package ex0;


import ex0.algo.ElevAlgo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Tasksssss {
    public static final int NULL=0,SRC=1, DEST=2,SRCDEST=3;
//    public static final int INIT=0, GOING2SRC=1, GOIND2DEST=2, DONE=3;
//    public static final int UP=1,DOWN=-1;
//    private int pos,direction;
//    private double Time;
//    private Elevator e;
//    private int[] floors;
//    private ArrayList<CallForElevator> calls;
////    private ArrayList<Double> timeCalls;
////    private LinkedList<Integer> task;
//
//
//    public Tasksssss() {
//        this.pos=0;
//        this.e=null;
//        this.Time=0;
//        this.direction=UP;
//        this.calls=new ArrayList<CallForElevator>();
//        this.floors=new int[e.getMaxFloor()-e.getMinFloor()+1];
////        this.timeCalls=new ArrayList<Double>();
////        this.task=new LinkedList<Integer>();
//    }
//    public Tasksssss(Elevator el) {
//        this.e=el;
//        this.Time=0;
//        this.direction=el.getState();
//        this.pos=el.getPos();
//        this.floors=new int[e.getMaxFloor()-e.getMinFloor()+1];
//        this.calls=new ArrayList<CallForElevator>();
////        this.timeCalls=new ArrayList<Double>();
////        this.task=new LinkedList<Integer>();
//    }
//    public int getDirection(){return this.direction;}
//    public double getTime() {return Time;}
//    public void setTime(double time) {Time = time;}
//    public Elevator getE() {return e;}
//    public void setE(Elevator e) {this.e = e;}
//
//    public void addTask(CallForElevator c){
//        int src=c.getSrc(),dest=c.getDest(),dir=c.getType();
//        if (task.contains(src))
//        if (this.direction==UP && dir == UP) {
//            if (pos<src){
//                int i=0;
//                while (task.get(i) < src){i++;}
//                task.add(i,src);
//                i=0;
//                while (task.get(i) < dest){ i++;}
//                task.add(i,dest);
//
//            }
//            else{
//
//            }
//        }
//        else if()
//
//    }
//    public void completeCall(CallForElevator c){
//        removeFromFloors(c);
//        timeCalls.remove(calls.indexOf(c));
//        calls.remove(c);
//    }
//
//    public void add2floors(CallForElevator c){
//        int src=c.getSrc(),dest=c.getDest();
//        if (floors[calFloor(src)]!=SRC){
//            if(floors[calFloor(src)]==DEST)
//                floors[calFloor(src)]=SRCDEST;
//            else
//                floors[calFloor(src)]=SRC;
//        }
//        if (floors[calFloor(dest)]!=DEST){
//            if(floors[calFloor(dest)]==SRC)
//                floors[calFloor(dest)]=SRCDEST;
//            else
//                floors[calFloor(dest)]=DEST;
//        }
//    }
//
//    public void removeFromFloors(CallForElevator c){
//        int src=c.getSrc(),dest=c.getDest();
//        if (floors[calFloor(src)]!=SRC){ //all src of calls taken together
//            floors[calFloor(src)]=DEST;
//        }
//        //now check if this floor is dest of other CallForElevator
//        Iterator iter = calls.iterator();
//        while (iter.hasNext()) {
//            CallForElevator other=(CallForElevator) iter.next();
//            if (other.getDest()==dest)
//                return;
//        }
//        if (floors[calFloor(dest)]!=DEST){ //if not found other dest, if this srcDest (NULL is impossible)
//            floors[calFloor(dest)]=SRC;
//            return;
//        }
//        else // in this index only dest (only of this CallforElevator)
//            floors[calFloor(dest)]=NULL;
//
//    }
//    private double calTime(int sorc, int dest){
//        return e.getTimeForClose()+e.getStartTime()+Math.abs(sorc-dest)*e.getSpeed()+e.getStopTime()+e.getTimeForOpen();
//    }
//
//    private int calFloor(int a){
//        return a-e.getMinFloor();
//    }
//    private double addTime(CallForElevator newCall){
//        if (floors[calFloor(newCall.getSrc())] > 0 && floors[calFloor(newCall.getDest())] > 0)
//            return 0;
//        Iterator iter = calls.iterator();
//        double allTime=0;
//        int ind=-1;
//        while (iter.hasNext()) {
//            double t=0;
//            CallForElevator c= (CallForElevator) iter.next();
//            ind++;
//            if (c.getState()==DONE) { //@@@@@@
//                completeCall(c);
//                continue;
//            }
//            if (c.getType()==UP && newCall.getType()==UP){
//
//                if (newCall.getSrc()>e.getPos() && c.getDest()>newCall.getSrc())//src under c.dest
//                    t+=stop1();
//                if (newCall.getDest()>e.getPos() && c.getDest()>newCall.getDest())//also dest under c.dest
//                    t+=stop1();
//                allTime+=t;
//                addTimeCalls(ind,t);
//                continue;
//            }
//            if (c.getType()==DOWN && newCall.getType()==DOWN){
//                if (newCall.getSrc()<e.getPos() && c.getDest()<newCall.getSrc())//newCall.src above c.dest
//                    t+=stop1();
//                if (newCall.getDest()<e.getPos() && c.getDest()<newCall.getDest()){//also newCall.dest above c.dest
//                    t+=stop1();
//                }
//                allTime+=t;
//                continue;
//            }
//            if (e.getState()==newCall.getType() && e.getState()!=c.getType()){}
//            if (e.getState()==c.getType() && e.getState()!=newCall.getType()){}
//
//        }
//        return allTime;
//    }
//
//    private void addTimeCalls(int ind, double time) {
//        timeCalls.set(ind,timeCalls.get(ind)+time);
//    }
//
//    private double stop1() {
//        return e.getTimeForClose()+e.getTimeForOpen()+e.getStartTime()+e.getStopTime();
//    }

}
