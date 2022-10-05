package com.lx.main;

import com.lx.annotations.MainMethod;

public class MainClassTest1 {

    @MainMethod
    public void mainMethod1() {
        new AlgoClassTest1().process1();
    }

    @MainMethod
    public void mainMethod2() {
        new AlgoClassTest2().h1(1, 2.0);

        Solution.combinationSum2(new int[]{1, 2, 3, 4, 5, 6}, 10);
    }

    @MainMethod
    public void mainMethod3() {
        AlgoClassTest2 algoClassTest2 = new AlgoClassTest2();
        algoClassTest2.h1(10, 20.0);
        algoClassTest2.h2();


        AlgoClassTest2 algoClassTest21 = new AlgoClassTest2();
        algoClassTest21.h2();
        algoClassTest21.h1(100, 30.0);
    }



}
