package com.lx.main;

import com.lx.annotations.AlgoMethod;

import java.util.*;

public class Solution {

    public volatile boolean isRunOver = false;

    public void watch() throws Exception {
        combinationSum2(new int[]{1, 2, 3, 4, 5, 6, 7}, 10);
        isRunOver = true;
    }

    @AlgoMethod
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) throws Exception{
        Arrays.sort(candidates);
        Set<List<Integer>> setRes = new HashSet<>();
        dfs(candidates, target, setRes, new ArrayList<>(), 0);
        return new ArrayList<>(setRes);
    }

    public static List<List<Integer>> combinationSum2(int[] candidates, int target, int test) throws Exception{
        return null;
    }

    private static void dfs(int[] candidates, int target, Set<List<Integer>> setRes, List<Integer> tmpList, int position) {
        if (target < 0) return;
        if (target == 0) {
            setRes.add(new ArrayList<>(tmpList));
            return;
        }
        for (int i = position; i < candidates.length; i++) {
            // 保证i=position的第一次可以进行
            if (i > position && candidates[i - 1] == candidates[i]) {
                continue;
            }
            target -= candidates[i];
            tmpList.add(candidates[i]);
            dfs(candidates, target, setRes, tmpList, i + 1);
            target += candidates[i];
            tmpList.remove((Integer) candidates[i]);
        }
    }
}
