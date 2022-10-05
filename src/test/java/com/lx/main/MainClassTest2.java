package com.lx.main;

import com.lx.annotations.MainMethod;

import java.io.IOException;

public class MainClassTest2 {
    @MainMethod
    public void mainMethod() throws Exception {
        Solution.combinationSum2(new int[]{1, 2, 3, 4}, 10);
    }


    @MainMethod
    public void mainMethod1() {
        try {
            Solution.combinationSum2(new int[]{1, 2, 3, 4, 5, 6}, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }


        AlgoClassTest1 algoClassTest1 = new AlgoClassTest1();
        algoClassTest1.process1();
        try {
            algoClassTest1.process2();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
