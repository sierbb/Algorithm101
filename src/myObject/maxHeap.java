package myObject;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class maxHeap<T extends Comparable<T>>{
    //Initialization the private variables: the heap is actually an array (a complete binary tree)
    //here size is actual size, also private because we have a function to return this.
    //must maintain this.size for each poll/offer operation
    private T[] array;
    private int size;

    //Constructor 1: an entire array as input
    //what we do in a constructor is to fill in the private/public variables
    public maxHeap(T[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("input array can not be null or empty");
        }
        this.array = array;
        size = array.length;
        heapify();
    }

    //Constructor 2: capacity as input
    //assign an new array of the capacity size to this.array, and set this.size=0
    public maxHeap(int cap) {
        if (cap <= 0) {
            throw new IllegalArgumentException("capacity can not be <= 0");
        }
        array = (T[]) new Comparable[cap];
        size = 0;
    }

    private void heapify() {
    //use precolateDown() to do heapify: first add all nodes, then for each non-leaf node [0, n/2-1], start from nodes from bottom layer to top so when higher layer node do precolateDown(), the lower nodes are all sorted, swap down until it is good.
    //use the size of the heap
        for (int i= this.size/2 - 1; i>=0; i--){
            percolateDown(i);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return size == array.length;
    }

    private void percolateUp(int index) {
        //percolateUp means we compare the node with its father. If current node has higher priority then swap it up.
        // do until its can no longer move up(root or larger than father)
        if (index == 0){
            return;
        }
        while (index >0){
            int parentIdx = (index-1)/2;
            if (array[parentIdx].compareTo(array[index]) > 0) {
                swap(parentIdx, index);
            } else {
                break;
            }
            index = parentIdx;
        }
    }

    private void percolateDown(int index) {
        //percolateDown mean we first get the lowest priority child, then compare lowest child with current node, then swap if current is lower than lower child. do until can no move down.
        while (index <= size/2-1){  //can only do if it is non-leaf
            int child_left = index*2+1;
            int child_right = index*2+2;
            //Get the child with lowest priority, if current index has even lower priority then percolateDown index
            int lowerIdx =  child_right <=size-1 && array[child_left].compareTo(array[child_right]) < 0 ? child_left: child_right;
            if (array[index].compareTo(array[lowerIdx]) > 0 ){
                swap(index, lowerIdx);
            } else {
                break;
            }
            index = lowerIdx;
        }
    }

    public T peek() {
        //read node at index 0
        if (size == 0){
            throw new NoSuchElementException("heap is empty");
        }
        return array[0];
    }

    public T poll() {
        //remove node at index 0, use last node (larger) to fill, and precolateDown()
        //int != null, if want to return null must change return type to Integer
        if (size == 0){
            throw new NoSuchElementException("heap is empty");
        }
        T result = array[0];
        array[0] = array[size-1];
        //must maintain size immediately!!
        size--;
        percolateDown(0);
        return result;
    }

    public void offer(T ele) {
        //offer is to offer to tail, then precolateUp
        if (size==array.length){ //expand array if full
            array = Arrays.copyOf(array, array.length*2);
        }
        array[size] = ele;
        size++;
        percolateUp(size-1);
    }

    public T update(int index, T ele) {
        //change node value, then compare to its old self to decide whether we need up or down
        if (index<0 || index > size-1){
            throw new ArrayIndexOutOfBoundsException("invalid index range");
        }
        T result = array[index];
        array[index] = ele;
        if (result.compareTo(ele) > 0)	{ //if new element has higher priority
            percolateUp(index);
        } else {
            percolateDown(index);
        }
        return result;
    }

    private void swap(int l, int r) {
        T tmp = array[l];
        array[l] = array[r];
        array[r] = tmp;
    }

}
