package DFS;

import java.util.*;

public class FactorsCombination {
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> res = new ArrayList<>();
        if (n <= 1) return res;
        //get factors list
        List<Integer> factors = new ArrayList<>();
        for (int i=2; i<n; i++){
            if ((n%i) == 0) factors.add(i);
        }
        List<Integer> combination = new ArrayList<>();
        DFS(n, factors, 0, combination, res);
        return res;
    }

    private void DFS(int n, List<Integer>factors, int level, List<Integer> combination, List<List<Integer>> res){
        //base case:
        if (n == 1) res.add(new ArrayList<Integer>(combination));
        for (int i=level; i<factors.size(); i++){
            //if can be divided, go the next DFS
            if (n% factors.get(i) == 0){
                combination.add(factors.get(i));
                DFS(n/factors.get(i), factors, i, combination, res);
                combination.remove(combination.size()-1);
            }
        }
    }
}

//clarification: factor can be from [2, n-1]. So 1 and n not count.
//return List of list of factors. Can be in any order.

//n = 1
//n = 12 -> 2,3,4,6 can all be its factor sorted - O(n)
//factrocs = k

//          DFS(12)
//2(6)            3(4)       4(3)    6(2)
//2(3) 3(2) 6(1)*  4(1)*     3(1)*  x
//3(1)* x
//at most logn level? each level we have k possible solution

//M1: DFS
//O(k^n);
//SC:O(logn) for number of layers for recurison tree

class Solution {
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> res = new ArrayList<>();
        if (n <= 1) return res;
        List<Integer> combination = new ArrayList<>();
        DFS(n, 2, combination, res);
        return res;
    }

    private void DFS(int n, int factor, List<Integer> combination, List<List<Integer>> res){
        //check whether each possible factor from here
        //since we are only adding to logn, we will not hit 1 in this way
        for (int i=factor; i*i<=n; i++){
            if (n%i == 0){
                //save the current snapshot to result
                combination.add(i);
                combination.add(n/i);
                res.add(new ArrayList<Integer>(combination));
                combination.remove(combination.size()-1);
                //add and continue on the next possible factor
                DFS(n/i, i, combination, res);
                combination.remove(combination.size()-1);
            }
        }
    }
}

//M1: DFS
//TC:O(n^k);
//SC:O(n) for call stack

//M2: Optimized factors list to logn instead of having to do it n times?
//TC:O(sqrt(n)^k)
//SC:O(n)