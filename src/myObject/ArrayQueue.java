package myObject;

public class ArrayQueue<T> {

    private static final int initialCapacity = 15;
    private T[] array;
    private int start; //since the array size is fixed till it is expanded, use two index to represent the actual size
    private int end;
    private int notNullCount;

    //Constructor
    public ArrayQueue() {
        //Implements a custom queue using array with initial capacity
        array = (T[])new Object[initialCapacity]; //here use a casting to cast Object[] to T[], otherwise will have "Cannot create a generic array of T"
        //for start and end, their job is to reuse the idle space of the existing array before expand it.
        //(queue poll and offer may eventually move start and end to right and leave the left side unused)
        start = 0;
        end = 0;
        notNullCount = 0; //need to update this element when offer() and poll()
    }

    public void offer(T element) {
        //When do we expand the queue? When size()+1=array.length -> end+1+array.length-start = 0
        //is the only proper time to expand is whenever we see end+1==start? yes, it means end(array) run into the first fill up
        //at this point if we dont expand, array will become size=array.length, end==start, which is not good (isEmpty will return true).
        //so must expand before offering the last element before queue is filled
        if ( (end-start+array.length)%array.length == array.length-1 ) {
            expandCapacity(); //this will change array.length, start, end
        }
        //now just append element at queue end
        end = (end+1)% array.length; //why end must divide array.length? Because end is always possible to OutOfBounds, so this make sure it wont
        array[ (end-1+array.length)%array.length] = element; //here [end-1] should be back to array tail if end =0
        if (element != null) {
            notNullCount++;
        }
        return;
    }

    public T poll() {
        //Since implementing a Queue, the head(start) element will be return
        //this operation will update start and nonNotCount
        if ( (end-start+array.length)%array.length > 0) {
            //start is possible to happen to move back to head as long as size not exceeding array.length, and this is to avoid IndexOutOfBounds
            start = (start+1)%array.length;
            T result  = array[start-1];
            if (result != null) {
                notNullCount --;
            }
            return result;
        } else {
            return null;
        }
    }

    public T peek() {
        //peek head(start) element
        if ( (end-start+array.length)%array.length >0) {
            return array[start];
        } else {
            return null;
        }
    }

    public int size() {
        //This is a math trick that works for two cases
        //case 1: end is on the right to start. Real size = end-start, so (end-start+array.length)%array.length=(end-start)%array.length+0 = real size
        //case 2: end is on the left to start. Real size = (end+array.length-start), so (end+array.length-start)%array.length = real size%length = real size
        //here end is exclusive to the actual elements so its [start, end)
        //So when start=0 && end=array.length-1, if add one more element, array will full, then size==array.length, will need to expand capacity
        return (end-start+array.length)%array.length;
    }

    public boolean isAllNull() {
        //Inspired by zhengzhi - Since we allow null node to enter the queue, size() != notNullCount, so we also need to maintain int notNullCount
        return notNullCount == 0;
    }

    public boolean isEmpty() {
        return end-start ==0;
    }

    private void expandCapacity() {
        T[] newArray = (T[]) new Object[ (int) (array.length * 1.5)];
        for (int i=0; i<array.length-1; i++) {
            //To let the count start from start index, and auto go back to head if OutOfBound
            //here i < not = is because size() now = array.length-1
            //also here i must start from 0 since it is also the index of new array
            newArray[i] = array[(start+i+array.length)%array.length];
        }
        start = 0;
        end = array.length-1; //after expand, new size equals old array size
        array = newArray;
    }

}
