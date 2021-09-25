package DemoTaskScheduler;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This is using PriorityBlockingQueue with sleeping delay time
 * which has the downside that if added new task with smaller delay time than current,
 * the consumer will still be sleep.
 *
 */

class SomeTask implements Runnable{
    private String name;

    public SomeTask(int name){
        this.name = "TaskId_" + name;
    }

    @Override
    public void run(){
    }

    public String toString(){
        return name;
    }
}

public class DelayTaskScheduler {

    private DelayQueue<DelayTask> q;
    private static Random rand = new Random();

    public DelayTaskScheduler(DelayQueue<DelayTask> q){
        this.q = q;
    }

    public void runTask(long delayTime, Runnable task){
        DelayTask taskA = new DelayTask(delayTime, task);
        q.put(taskA);
        System.out.println("Queue size:" + q.size());
    }

    public static void main(String[] args){

        DelayTaskScheduler obj = new DelayTaskScheduler(new DelayQueue<DelayTask>());
        //current thread act as producer to run task
        //Another process/ thread to do the get task and run them
        new Thread(new Consumer(obj.q)).start(); //consumer will keep taking and run those task if any

        int count = 0;
        while (true){
            try{
                count++;
                long delay = rand.nextInt(5000);
                obj.runTask(delay, new SomeTask(count));
                Thread.sleep(1000);
            }catch (InterruptedException e){

            }
        }
    }
}

class Consumer implements Runnable{
    private DelayQueue<DelayTask> q;

    public Consumer(DelayQueue<DelayTask> q){
        this.q = q;
    }

    public void run(){
        while (true){
            try{
                DelayTask task = q.get();
                if (task != null){
                    task.run();
                }
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}


class DelayTask implements Comparable{

    private Runnable task = null;
    private long executionTime;

    public DelayTask(long delayTime, Runnable task){
        this.task = task;
        this.executionTime = System.currentTimeMillis() + delayTime;
    }

    public long getExecutionTime(){
        return this.executionTime;
    }

    public String getName(){
        return task.toString();
    }

    @Override
    public int compareTo(Object other){
        return (int)(this.executionTime - ((DelayTask)other).executionTime);
    }

    public void run(){
        task.run();
    }

}


class DelayQueue<E>{
    //act as minheap to sort the tasks by executionTime
    private PriorityQueue<DelayTask> q;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public DelayQueue(){
        q = new PriorityQueue<>();
    }

    public void put(DelayTask task){
        lock.lock();
        try{
            if (q.size() == 0){
                condition.signal();
            }
            q.offer(task);
            if (q.peek() == task && q.size() > 1){
                System.out.println("Putting a task with higher priority! " + task.getName());
                //if put in a task that has higher priority
                condition.signal();
            }
        }finally{
            lock.unlock();
        }
    }

//    public synchronized DelayTask get(){
//        while (q.size() == 0){
//            try{
//                wait();
//            }catch(InterruptedException e){
//                e.printStackTrace();
//            }
//        }
//        DelayTask next = q.peek();
//        long executionTime = next.getExecutionTime();
//        if (System.currentTimeMillis() > executionTime){
//            return q.poll();
//        }
//        return null;
//    }

    public DelayTask get() throws InterruptedException{
        lock.lock();
        try{
            //Polling
            while (q.size() == 0){
                try{
                    condition.await();
//                    wait(); //release lock, and need notify() to exit the wait?
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }

            DelayTask next = q.peek();
            long executionTime = next.getExecutionTime();

            //Calculate the time to sleep for
            long delay = executionTime - System.currentTimeMillis();
            if (delay <= 0){
                System.out.println("Consumer: Running task" + next.getName());
                return q.poll();
            }else {
                System.out.println("Consumer: Going to sleep for task " + next.getName() + ", delay time: " + delay);
                condition.awaitNanos(delay);
//                    Thread.sleep(delay); //release lock again
            }
        }finally{
            lock.unlock();
        }
        return null;
    }


    public synchronized int size(){
        return q.size();
    }
}
