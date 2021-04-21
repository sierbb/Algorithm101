package myObject;

import java.util.Arrays;

public class ArrayList<T>{

    private static final int initialCapacity = 15;
    private T[] array;
    private int size; //number of elements contained in array, array must start at index 0. maintain this when call add(),remove()

    //Constructor 1: with size as input
    public ArrayList(int capacity) {
        //implements an Arraylist that should just be the same functionality as Java.util.ArrayList
        if (capacity>=0) {
            array = (T[]) new Object[capacity];
        } else {
            throw new IllegalArgumentException("Illegal capacity: "+capacity);
        }
    }

    //Constructor 2: without any input
    public ArrayList() {
        //call the other constructor!
        this(initialCapacity);
    }

    /**
     * Append the provided element to the end of the list
     * @param ele element to be added
     */
    public void add(T ele) {
        //before IndexOutOfBound, call expandCapacity()
        if (size==array.length) {
            expandCapacity();
        }
        size++;
        array[size-1] = ele;
    }

    public void remove() {
        //remove the last elements
        if (size == 0) {
            return;
        }
        size--;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }


    public void checkIndex(int index){
        if (index >= size || index <0) {
            throw new IndexOutOfBoundsException("Illegal index: "+index);
        }
    }

    public T get(int index) {
        checkIndex(index);
        return array[index];
    }

    public T set(int index, T ele){
        checkIndex(index);
        T oldElement = array[index];
        checkIndex(index);
        array[index] = ele;
        return oldElement;
    }

    public T remove(int index) {
        //remove element at the index, move all the elements starting at index+1 to before
        checkIndex(index);
        T oldElement = array[index];
        for (int i=index+1; i<size; i++) { //fill from left to right, will override the original element
            array[i-1] = array[i];
        }
        size--;
        return oldElement;
    }

    public boolean remove(T ele){ //remove the first occurrence of the object
        //return true if object exists
        if (ele == null){
            for (int i=0; i<size; i++){
                if (array[i] == null){
                    remove(i);
                    return true;
                }
            }
        } else {
            for (int i=0; i<size; i++){
                if (array[i].equals(ele)){
                    remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE;

    private void expandCapacity() {
        //expand the capacity and copy all the elements over to new array
        //consider overflow adjust the new capacity
        int oldCapacity = array.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - MAX_ARRAY_SIZE > 0) { newCapacity = MAX_ARRAY_SIZE; }
//        T[] newArray = (T[]) new Object[newCapacity];  //unchecked cast
//        for (int i =0; i<size; i++) {
//            newArray[i] = array[i];
//        }
        array = Arrays.copyOf(array, newCapacity);
    }

    public T[] toArray() {
        //return a copy of the array so that operation on the copy is free to modify the returned array
        return Arrays.copyOf(array, size); //the second argument is the length of the array
    }

}
