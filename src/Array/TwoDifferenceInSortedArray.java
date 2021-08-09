package Array;

public class TwoDifferenceInSortedArray {

    public int[] twoDiff(int[] array, int target) {
        //corner case
        if (array == null || array.length == 0) return new int[0];
        int dis = Math.abs(target);
        int i=0, j=1;
        while (j < array.length){
            //if array[j]-array[i] smaller than abs(target), keep moving i until i==j
            while ( i < j && array[j]-array[i] > dis){
                i++;
            }
            //if array[j]-array[i] equals target, return immediately
            if (array[j] - array[i] == dis && i!=j){
                if (target >= 0){
                    return new int[]{i, j};
                }else {
                    return new int[]{j, i};
                }
            }
            //now either we dont get to the target, or array[j]-array[i] > target
            j++;
        }
        return new int[0];
    }
}

//[[1,4,4,8,14],0]
//  i j

//[1,2,3,6,9]
//i    j
//if a[j]-a[i]>target, j++
//if a[j]-a[i]<tarrget,i++
//if a[j]-a[i]==target,return

//[[1,5,5,10,14,22,23,30],-12]