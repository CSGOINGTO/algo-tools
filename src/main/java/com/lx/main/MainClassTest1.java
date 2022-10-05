package com.lx.main;

import com.lx.annotations.MainMethod;

public class MainClassTest1 {

    @MainMethod
    public void mainMethod1() {
        new AlgoClassTest1().process1();
    }

    @MainMethod
    public void mainMethod2() {
        new AlgoClassTest2().h1(1, 1.1);
    }


}
