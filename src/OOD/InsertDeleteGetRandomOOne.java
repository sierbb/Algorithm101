package OOD;

import java.util.*;

public class InsertDeleteGetRandomOOne {

    Map<Integer, Integer> map; //store <val, posIdx>
    List<Integer> list; //store val
    Random random = new Random();

    public InsertDeleteGetRandomOOne(){
        map = new HashMap<>();
        list = new ArrayList<>();
    }

    public boolean insert(int val){
        if (!map.containsKey(val)){
            list.add(val);
            map.put(val, list.size()-1);
            return true;
        }else {
            return false;
        }
    }

    public boolean remove(int val){
        if (!map.containsKey(val)){
            return false;
        }else {
            //remove it from list, update map posIdx
            //swap the toDelete val with last ele in list
            int toDeleteIdx = map.get(val);
            int oldVal = list.get(list.size()-1);
            swap(list, toDeleteIdx, list.size()-1);
            list.remove(list.size()-1);
            map.put(oldVal, toDeleteIdx);
            //remove it from map
            map.remove(val);
            return true;
        }
    }

    private void swap(List<Integer> list, int idx1, int idx2){
        int tmp = list.get(idx1);
        list.set(idx1, list.get(idx2));
        list.set(idx2, tmp);
    }

    public int getRandom(){
        //use the Random function to get random int
        if (list.size() == 0){
            return Integer.MIN_VALUE;
        }
        int ran = random.nextInt(list.size());
        return list.get(ran);
    }

    public static void main (String[] args){
        InsertDeleteGetRandomOOne obj = new InsertDeleteGetRandomOOne();
        System.out.println(obj.insert(-1)); //true
        System.out.println(obj.insert(0)); //true
        System.out.println(obj.insert(1)); //true
        System.out.println(obj.insert(2)); //true
        System.out.println(obj.insert(2)); //false
        System.out.println(obj.insert(4)); //true
        System.out.println(obj.remove(3)); //false
        System.out.println(obj.remove(4)); //true
        System.out.println(obj.getRandom()); //??
        System.out.println(obj.getRandom()); //??
        System.out.println(obj.getRandom()); //??
        System.out.println(obj.getRandom()); //??
        System.out.println(obj.getRandom()); //??
    }
}

/**
 * Implement the RandomizedSet class:
 *
 * RandomizedSet() Initializes the RandomizedSet object.
 * bool insert(int val) Inserts an item val into the set if not present. Returns true if the item was not present, false otherwise.
 * bool remove(int val) Removes an item val from the set if present. Returns true if the item was present, false otherwise.
 * int getRandom() Returns a random element from the current set of elements (it's guaranteed that at least one element exists when this method is called). Each element must have the same probability of being returned.
 * You must implement the functions of the class such that each function works in average O(1) time complexity.
 */


// Set store unique val, all O(1)
// insert() checks whether exists, if so, true, else false
// remove() checks whether exists, if so, remove and true, else false
// getRamdom() -> must be random, can not return same val each time.

// Set<int> set; -> insert and remove O(1)
// But getRamdon not possible for set, because unsorted -> DS needs to be array / list
// List<int> for getRamdon -> how to track int in list and in set?

// Map<val, posIdx> -> O(1) referene to position in list.
// List<val> -> val is referenced by map. getRandom() return a random int, -> list.get(int)

// insert() -> check val exsits in map O(1), if so return, else append to list O(1)
// remove -> O(1) check val exists, O(1) to remove by swap val at posIdx=map.get(val) with the last node by List.set(int, Node), then remove last node, update swapped node posIdx into map
// getRandom -> int, return list.get(int) - O(1)

