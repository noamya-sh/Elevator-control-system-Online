package ex0;

import java.util.ArrayList;

public class attempt {
    public static final int UP = 1, LEVEL = 0, DOWN = -1, ERROR = -2;
    private Elevator e;
    private int stateElevator;
    private int pos;
    private ArrayList<Integer> queue;
    private ArrayList<CallForElevator> calls;

    public void add(CallForElevator c){
        calls.add(c);
        if ((stateElevator==UP && c.getType()==UP && pos > c.getSrc()) || (stateElevator==UP && c.getType()==UP && disTime(c.getSrc()) < 2)){
            int j=StartDown(0);
            int i=StartUp(j);
            while (queue.get(i)<c.getSrc()){i++;}
            if(queue.get(i)!=c.getSrc())
                queue.add(i++, c.getSrc());
            while (queue.get(i)<c.getDest()){i++;}
            if(queue.get(i)!=c.getDest())
                queue.add(i, c.getDest());
            return;
        }
        if (stateElevator==UP && c.getType()==UP){
            int i=0;
            while (queue.get(i)<c.getSrc()){i++;}
            if(queue.get(i)!=c.getSrc())
                queue.add(i++, c.getSrc());
            while (queue.get(i)<c.getDest()){i++;}
            if(queue.get(i)!=c.getDest())
                queue.add(i, c.getDest());
            return;
        }
        if ((stateElevator==DOWN && c.getType()==DOWN) && (pos < c.getSrc()|| disTime(c.getSrc()) < 2)){
            int j=StartUp(0);
            int i=StartDown(j);
            while (queue.get(i)>c.getSrc()){i++;}
            if(queue.get(i)!=c.getSrc())
                queue.add(i++, c.getSrc());
            while (queue.get(i)>c.getDest()){i++;}
            if(queue.get(i)!=c.getDest())
                queue.add(i, c.getDest());
            return;
        }
        if (stateElevator==DOWN && c.getType()==DOWN){
            int i=0;
            while (queue.get(i)>c.getSrc()){i++;}
            if(queue.get(i)!=c.getSrc())
                queue.add(i++, c.getSrc());
            while (queue.get(i)>c.getDest()){i++;}
            if(queue.get(i)!=c.getDest())
                queue.add(i, c.getDest());
            return;
        }
        if (stateElevator==DOWN && c.getType()==UP){
            int i=StartUp(0);
            while (queue.get(i)<c.getSrc()){i++;}
            if(queue.get(i)!=c.getSrc())
                queue.add(i++, c.getSrc());
            while (queue.get(i)<c.getDest()){i++;}
            if(queue.get(i)!=c.getDest())
                queue.add(i, c.getDest());
            return;
        }
        else {

        }


    }
    public double disTime(int target){
        int p =pos;
        double speed = e.getSpeed();
        return Math.abs(target-p)*speed;
    }
    private int StartDown(int start) {
        int i=start;
        while (queue.get(i+1)!=null && queue.get(i+1)-queue.get(i) > 0){i++;}
        return i;
    }
    private int StartUp(int start) {
        int i=start;
        while (queue.get(i+1)!=null && queue.get(i+1)-queue.get(i) < 0){i++;}
        return i;
    }
}
