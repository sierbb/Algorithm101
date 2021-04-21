package myObject;

import myObject.ArrayList;
import myObject.ArrayQueue;

import java.util.Arrays;

public class ObjectTest {

    public static void main(String[] args) {
        //Test ArrayList
        ArrayList<Integer> list = new ArrayList<>(10);
        for (int i=0; i<20; i++){
            list.add(i);
        }
        System.out.println(Arrays.toString(list.toArray()));
        System.out.println(list.get(10));
        System.out.println(list.remove(19));
        System.out.println("size: " + list.size());
        list.add(21);
        System.out.println("size: " + list.size());
        System.out.println(list.set(10, 15));
        System.out.println(list.get(10));
        System.out.println(list.remove(Integer.valueOf(5)));


        //Test ArrayQueue
        ArrayQueue<Integer> fifo = new ArrayQueue<>();
        fifo.offer(null);
        for (int i=0; i<20; i++){
            fifo.offer(i);
        }
        System.out.println(fifo.size());
        System.out.println(fifo.peek());
        while (!fifo.isEmpty()){
           System.out.println(fifo.poll());
        }
        System.out.println(fifo.peek());


    }

}
