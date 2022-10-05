package com.lx.main;

import com.lx.annotations.MainMethod;

public class MainClassTest2 {
    @MainMethod
    public void mainMethod() throws Exception {
        new Solution().combinationSum2(new int[]{1, 2, 3, 4}, 10);
    }
}
