package ex0.test;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import ex0.algo.myAlgo;
import ex0.simulator.ElevetorCallList;
import ex0.simulator.Simulator_A;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class myAlgoTest {


    Building home;
    myAlgo al;

    public myAlgoTest(){

        Simulator_A.initData(5,null);
        home = Simulator_A.getBuilding();
        al = new myAlgo(home);


    }

    @Test
    void allocateAnElevator() {




    }

    @Test
    void cmdElevator() {
    }



    }