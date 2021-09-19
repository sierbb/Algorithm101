package DFS;

public class BeautifulArrangement {
    public int countArrangement(int n) {
        if (n == 0) {
            return 0;
        }
        int[] count = new int[1];
        int[] permutation = new int[n];
        for (int i = 1; i <= n; i++) {
            permutation[i - 1] = i;
        }
        DFS(permutation, 0, count);
        return count[0];
    }

    public void DFS(int[] permutation, int level, int[] count) {
        //base case
        if (level == permutation.length) {
            count[0]++;
            return;
        }
        //swap elements to current position, and check whether its valid
        for (int i = level; i < permutation.length; i++) {
            swap(permutation, i, level);
            //check whether the swapped element is valid
            if (permutation[level] % (level + 1) == 0 || (level + 1) % permutation[level] == 0) {
                DFS(permutation, level + 1, count);
            }
            swap(permutation, i, level);
        }
    }

    private void swap(int[] array, int idx1, int idx2) {
        int temp = array[idx1];
        array[idx1] = array[idx2];
        array[idx2] = temp;
    }

    public static void main(String[] args) {
        BeautifulArrangement obj = new BeautifulArrangement();
        System.out.println(obj.countArrangement(2));
        System.out.println(obj.countArrangement(1));
    }
}

//clarification: number of beautiful rearrangements
// 1 - n
// m1: find all permutations and check the conditions
//for each position, swap the following letters with the current position
//When we have swap letters for all position, we get a new permutation

//When we form the former letters, check whether the element swapped to current position matches the conditions
//if not, stop and no do DFS further -> eventually we only generate the path for those that matches

//TC:O(k) k is number of valid permutations
//SC:O(n) for recursion call stack