package myObject;

import myObject.maxHeap;

public class HeapTest {

    public static void main(String[] args){

        class MyInteger implements Comparable<MyInteger> {

            public int value;

            public MyInteger(int value) {
                this.value = value;
            }

            public int compareTo(MyInteger i){
                if (value == i.value){ //value is primitive type
                    return 0;
                }
                return value < i.value ? 1 : -1;
            }

        }

        maxHeap<MyInteger> maxHeap = new maxHeap<MyInteger>(5);
        //MyInteger x.compareTo(y) returns -1 if x < y --> larger value has higher priority
        //
        maxHeap.offer(new MyInteger(1));
        maxHeap.offer(new MyInteger(2));
        maxHeap.offer(new MyInteger(4));
        maxHeap.offer(new MyInteger(5));
        maxHeap.offer(new MyInteger(3));
        System.out.println(maxHeap.peek().value); //5

        maxHeap.offer(new MyInteger(6));
        maxHeap.offer(new MyInteger(7));
        System.out.println(maxHeap.poll().value); //7

        maxHeap.update(0, new MyInteger(0)); //update heap top (6) to 0
        while (!maxHeap.isEmpty()){
            System.out.println(maxHeap.poll().value); //descending order
        }

    }
}
