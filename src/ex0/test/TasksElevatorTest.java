package ex0.test;

import ex0.TasksElevator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//Tests for each function that can apply without implement Building, Elevator and CallForElevator.
class TasksElevatorTest {
    public static final int NULL=0,SRC=1, DEST=2,SRCDEST=3;
    public static final int INIT=0, GOING2SRC=1, GOIND2DEST=2, DONE=3;
    public static final int UP=1,DOWN=-1;
    TasksElevator t=new TasksElevator();
    int[] fl=t.getFloors();
    int var=0;

    @Test
    void getFloors() {
        int[] a=new int[5];
        assertArrayEquals(a,t.getFloors());
    }

    @Test
    void getVar() {
        assertEquals(t.getVar(),var);
    }

    @Test
    void getLastDirect() {
        assertEquals(t.getLastDirect(),UP);
    }

    @Test
    void setLastDirect() {
        t.setLastDirect(DOWN);
        assertEquals(t.getLastDirect(),DOWN);
    }

    @Test
    void addSrc2floors() {
        t.addSrc2floors(1);
        assertEquals(t.getFloors()[1],SRC);
    }

    @Test
    void cleanFloor() {
        t.cleanFloor(1);
        assertEquals(t.getFloors()[1],NULL);
    }

    @Test
    void stateFloor() {
        t.addSrc2floors(2);
        assertEquals(t.stateFloor(2),SRC);
    }
}