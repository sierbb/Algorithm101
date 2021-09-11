package OOD;

import java.util.*;
import java.util.Random;

class BoundedBlockingQueue {

    private Queue<Integer> q;
    private final int limit;

    public BoundedBlockingQueue(int capacity) {
        this.q = new LinkedList<>();
        this.limit = capacity;

    }

    public synchronized boolean enqueue(int element){
        //1.can only do this when queue is not full, otherwise wait and release the lock
        //2.when it sees queue is empty, notifyAll (producer)
        while ( q.size() == limit ){
            try{
                wait(); //release lock
                System.out.println("Producer waiting...");
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        if ( q.size() == 0 ){
            notifyAll();
        }
        q.offer(element);
        return true;
    }

    public synchronized int dequeue() {
        //1.can only do this when queue is not empty, otherwise wait and release the lock
        //2.when it sees queue is full, notifyAll (consumer)
        while ( q.size() == 0 ){
            try{
                wait(); //release lock
                System.out.println("Consumer waiting...");
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        if ( q.size() == limit ){
            notifyAll();
        }
        return q.poll();
    }

    public synchronized int size(){
        return q.size();
    }
}

class Consumer implements Runnable{
    BoundedBlockingQueue q;
    public Consumer(BoundedBlockingQueue q){
        super();
        this.q = q;
    }

    @Override
    public void run(){
        System.out.println(q.dequeue());
    }
}

class Producer implements Runnable{
    BoundedBlockingQueue q;
    public Producer(BoundedBlockingQueue q){
        this.q = q;
    }

    @Override
    public void run(){
        Random rand = new Random();
        System.out.println(q.enqueue(rand.nextInt(10)));
    }
}

class TestBlockingQueue{
    public static void main(String[] args){
        BoundedBlockingQueue q = new BoundedBlockingQueue(2);
        List<Thread> threads  = new ArrayList<>();
        for (int i=0; i<5; i++){
            threads.add(new Thread(new Producer(q)));
        }
        for (int i=0; i<5; i++){
            threads.add(new Thread(new Consumer(q)));
        }
        for (Thread t : threads){
            t.start();
        }
    }
}
