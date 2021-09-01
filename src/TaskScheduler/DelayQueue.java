package TaskScheduler;

import UnionFind.EmployeeFreeTime;

import java.util.PriorityQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;


/**
 * DelayQueue can be visited by producer and consumer
 * Using the put() and take() and is thread safe.
 * The elements in the queue should be a Delayed Objet.
 */
public class DelayQueue<E extends Delayed> {

    private PriorityQueue<E> q = new PriorityQueue<E>();
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Thread leader = null;

    public DelayQueue(){
    }

    /**
     * Should be called by a producer to put a task into queue.
     * When see queue is empty before, signal the blocking threads.
     * @param task
     * @return
     */
    public boolean put(E task){
        final Lock lock = this.lock;
        lock.lock();
        try{
            q.offer(task);
            if ( q.peek() == task ) {
                //means q is empty before, signal consumers
                //release leader so that consumers can vote for it
                leader = null;
                condition.signal();
                return true;
            }
        }finally {
            lock.unlock();
        }
        return true;
    }

    /**
     * Current consumer should vote for a leader if it not exists.
     * If not voted, sleep until being signaled.
     * If becomes the leader, sleep for delay time and take the first task.
     * Keep taking tasks.
     * @return
     */
    public E take() throws InterruptedException{
        final Lock lock = this.lock;
        lock.lockInterruptibly();
        try{
            for (;;){
                E first = q.peek();
                if ( first == null ){
                    condition.await(); //release the lock
                }else {
                    long delay = first.getDelay(TimeUnit.NANOSECONDS);
                    if ( delay <= 0 ){
                        return q.poll();
                        //before we poll it, release the leader title
                    }
                    //Need to sleep for delay, but release the task first
                    first  = null;
                    //Now check whether it is going to sleep forever or sleep for delay
                    if ( leader != null ){
                        condition.await();
                    }else {
                        Thread current = Thread.currentThread();
                        leader = current;
                        //successfully voted as leader, sleep for delay
                        try {
                            condition.awaitNanos(delay);
                        }finally {
                            if (leader == current) {
                                leader = null;
                            }
                        }
                    }
                }
            } //this will keep going

        }finally{
            //If we are able to take a task, before we leave, need to release lock
            //Also need to signal the producer if q is empty now
            if ( leader != null && q.peek() == null ){
                condition.signal();
            }
            lock.unlock();
        }

    }
}
