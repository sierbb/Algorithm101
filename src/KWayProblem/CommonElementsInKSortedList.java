package KWayProblem;

import java.util.*;

/**
 * Find all common elements in K sorted lists.
 *
 * Assumptions
 * The input and its elements are not null, and support fast random access.
 * There could be duplicate elements in each of the lists.
 *
 * Examples
 * Input = {{1, 2, 2, 3}, {2, 2, 3, 5}, {2, 2, 4}}, the common elements are {2, 2}.
 */
public class CommonElementsInKSortedList {

    public List<Integer> commonElementsInKSortedArrays(List<List<Integer>> input) {
        //Algorithm: Find common elements - M1:Build one of the array into HashSet and check whether element in the other array in the hashset.
        //M2: Since is sorted list, can put one pointer on one array, then move the second pointer on the other array based on the value.
        //M2a.Iterative move pointer. TC:O(kn); SC:O(n)
        //M2a.Binary reduction. TC:O(kn); SC:O(kn)
        //M2c.all together -> k pointers on k array, move k pointers together -> O(k) each time, do it nk times -> O(nk^2); SC:O(k)
        //Assumption: there are duplicate elements in the arrays -> dont need to do anything
        if (input == null || input.size() == 0) return new ArrayList<>();
        List<Integer> common = commonElementsHelper(input.get(0), input.get(1));
        for (int i=2; i<input.size(); i++){
            common = commonElementsHelper(common, input.get(i));
        }
        return common;
    }

    private List<Integer> commonElementsHelper(List<Integer> one, List<Integer> two){
        List<Integer> out = new ArrayList<>();
        int i=0;
        int j=0;
        while (i<one.size() && j<two.size()){
            if (one.get(i) == two.get(j)){
                out.add(one.get(i));
                i++;
                j++;
            } else { //shuixiaoyishui
                if (one.get(i) <= two.get(j)){
                    i++;
                } else {
                    j++;
                }
            }
        }
        return out;
    }

}
