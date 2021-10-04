package OOD;

import java.util.*;


interface NestedInteger {
    // @return true if this NestedInteger holds a single integer, rather than a nested list.
    public boolean isInteger();
    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
    public Integer getInteger();

    // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return empty list if this NestedInteger holds a single integer
    public List<NestedInteger> getList();
 }


public class FlattenNestedListIterator implements Iterator<Integer> {

    private Deque<NestedInteger> stack = new ArrayDeque<>();

    public FlattenNestedListIterator(List<NestedInteger> nestedList) {
        //First add the first layer element into stack
        for (int i=nestedList.size()-1; i>=0; i--){
            stack.offerFirst(nestedList.get(i));
        }
    }

    @Override
    public Integer next() {
        if (!hasNext()){
            return null;
        }
        return stack.pollFirst().getInteger();
    }

    @Override
    public boolean hasNext() {
        //This while loop is to make sure the top element is an integer
        while (!stack.isEmpty() && !stack.peekFirst().isInteger()){
            //good: O(1) OR worst case O(L/N) = we need to iterate through all element in one list
            //poll and push the top element back to stack until we see an integer
            NestedInteger cur = stack.pollFirst();
            List<NestedInteger> list = cur.getList();
            for (int i=list.size()-1; i>=0; i--){ //add from end to begin back to stack
                stack.offerFirst(list.get(i));
            }
        }
        return !stack.isEmpty();
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */

//Clarification:
//n = all nested Integer
//d = max Depth of nestedList
//L = all nested Lists

//M1:
//Faltten the nestedList into flatten list when initialize, using recursion.
//Maintain a pointer to point to which element is the next element.

//TC:O(n+L) ini
//SC:O(n+d) d for recursion call stack, n for flatten list


//M2:
//Use a stack to flatten the stack top only when we call hasNext.
//If stack top is a list, we flatten it until it is a interger
//[[1,3],2,[4,5]] ->
//stack [ [4,5,], 2, 3, 1
//hasNext() -> check if stack.size() > 0
//next() -> if stack top is a list, pop and flatten it and push it back to stack in reverse order

//TC:O(n) ini; hasNext O(1); next O(1) or if stack top is list, O(n/k)
//SC:O(n)



class FlattenNestedListIteratorM3 implements Iterator<Integer>{

    Deque<Integer> indexStack;
    Deque<List<NestedInteger>> listStack;

    public FlattenNestedListIteratorM3(List<NestedInteger> nestedList){
        indexStack = new ArrayDeque<>();
        listStack = new ArrayDeque<>();
        indexStack.offerFirst(0); //default index when adding a new layer
        listStack.offerFirst(nestedList);
    }

    public Integer next(){
        if (!hasNext()){
            return null;
        }
        //update the index as we poll element
        int index = indexStack.pollFirst();
        indexStack.offerFirst(index + 1);
        return listStack.peekFirst().get(index).getInteger();

    }

    public boolean hasNext(){
        //check whether the top element from listStack has been used up, if so, read the next element
        while (!indexStack.isEmpty()){
            if (indexStack.peekFirst() >= listStack.peekFirst().size() ){
                indexStack.pollFirst();
                listStack.pollFirst();
                continue; //go read the parent layer
            }
            //if the top element is an integer
            if (listStack.peekFirst().get(indexStack.peekFirst()).isInteger()){
                break;
            }

            //if the top element is a list, pull this list out and create a new layer
            List<NestedInteger> newList = listStack.peekFirst().get(indexStack.peekFirst()).getList();
            listStack.offerFirst(newList);
            indexStack.offerFirst(indexStack.pollFirst()+1); //last layer +1
            indexStack.offerFirst(0); //new layer starts at 0
        }
        return !indexStack.isEmpty();
    }
}


//M3:Two stacks
//Use an indexStack to record the index position of each element we currently reading
//Use an listStack to record the current element we are reading

//So the next element will be listStack().peekFirst().get(indexStack().peekFirst()).getInteger()
//for hasNext() we'll need to check whether this element is integer, if not, we need to pull it out add to stack as a new layer,
//and add new layer's index as 0 to indexStack

//TC:O(1) ini; O(L/N) for
//SC:O(D) for indexStack; O(D) for listStack (at most reference of D???)


