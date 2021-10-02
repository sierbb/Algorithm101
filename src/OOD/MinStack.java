package OOD;

import java.util.*;

public class MinStack {

    private ArrayDeque<Integer> mainStack;
    private ArrayDeque<Pair> minStack;

    /** initialize your data structure here. */
    public MinStack() {
        mainStack = new ArrayDeque<>();
        minStack = new ArrayDeque<>();
    }

    public void push(int val) {
        mainStack.offerFirst(val);
        //if first element, push the val, else push the min(val, min.peek());
        if (!minStack.isEmpty() && val == getMin()){
            minStack.peekFirst().freq++;
        }else if (minStack.isEmpty() || val < getMin()){
            minStack.offerFirst(new Pair(val));
        }
    }

    public void pop() {
        int cur = mainStack.pollFirst();
        if (cur == getMin()){ //if the one to pop is the cur min
            minStack.peekFirst().freq--;
            if (minStack.peekFirst().freq == 0){
                minStack.pollFirst();
            }
        }
    }

    public int top() {
        return mainStack.peekFirst();
    }

    public int getMin() {
        return minStack.peekFirst().val;
    }

    public class Pair{
        private int val;
        private int freq;
        public Pair(int val){
            this.val = val;
            this.freq = 1;
        }
    }

    public static void main(String[] args){
        MinStack obj = new MinStack();
        obj.push(-2);
        obj.push(0);
        obj.push(-3);
        System.out.println(obj.getMin());
        System.out.println(obj.top());
        obj.pop();
        System.out.println(obj.getMin());

    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */

//clarification: Minimum element in O(1)
//stack1 [ -2,
//stack2 [ 0,

//[-2],[0],[-3]

//M1:
//stack + minHeap -> O(logn) for push, O(1) for getMin()

//M2: O(1) to keep track of min using minstack.
//stack1   [ -2, 0, -3
//minStack [ -2, -2,-3 -> improved to keep track of <int, freq> in minStack

//SC:O(n) for both stack