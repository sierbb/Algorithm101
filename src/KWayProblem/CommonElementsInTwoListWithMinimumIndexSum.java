package KWayProblem;

import java.util.*;

public class CommonElementsInTwoListWithMinimumIndexSum {

    public List<String> getCommonInterest(String[] array1, String[] array2) {
        //Algorithm: Since the list of string are not sorted, we need to first sort them, so we can use linear scan to get the common strings.
        //But we also need to know their index sum, so dont think sorting them is good for the task.
        //We can use HashSet/HashMap to do this.
        //TCO(m+n);
        //SC:O(min(m,n)) for hashMap
        List<String> res = new ArrayList<String>();
        if (array1 == null || array2 == null || array1.length == 0 || array2.length == 0){
            return res;
        }
        Map<String, Integer> map = new HashMap<>();
        for (int i=0; i<array1.length; i++){
            map.put(array1[i], i);
        }
        int sum = Integer.MAX_VALUE;
        for (int j=0; j< array2.length; j++){
            if (map.containsKey(array2[j])){
                int curSum = map.get(array2[j])+j;
                if (curSum < sum){ //replace it with the new element
                    while (res.size() >0){
                        res.remove(res.size()-1);
                    }
                    res.add(array2[j]);
                    sum = curSum;
                }else if (curSum == sum){
                    res.add(array2[j]);
                }
            }
        }
        Collections.sort(res);
        return res;
    }

    public static void main(String[] args){
        CommonElementsInTwoListWithMinimumIndexSum obj = new CommonElementsInTwoListWithMinimumIndexSum();
        String[] input1 = new String[]{"axeei","gnicn","gsoprkn","clq"};
        String[] input2 = new String[]{"tmxs","yyo","btovn","uxefbz","gnicn","axeei","plxecd","gsoprkn"};
        System.out.println(obj.getCommonInterest(input1, input2));

    }
}
