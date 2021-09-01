package TaskScheduler;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Delayed;


class DefaultTask implements Runnable{

    private String name;

    public DefaultTask(){
        this("default");
    }
    public DefaultTask(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }

    @Override
    public void run(){
        System.out.println("Defualt task running");
    }
}



/**
 * The task is being used as the element of the DelayQueue.
 * It will be put into or poll from DelayQueue.
 */
class DelayTask implements Delayed {

    private final long executeTime;
    private Runnable task = null;

    public DelayTask(Runnable task, long delay){
        this.task = task;
        this.executeTime = System.currentTimeMillis() + delay;
    }

    public DelayTask(long delay){
        this(new DefaultTask(), delay);
    }

    public long getDelay(TimeUnit unit){
        //return the real-time time to delay based on current system time in miliseconds
        long delta = executeTime - System.currentTimeMillis();
        return unit.convert(delta, TimeUnit.MILLISECONDS);
    }

    public String toString(){
        return "Task + " + task.toString() + " at " + executeTime;
    }

    @Override
    public int compareTo(Delayed other){
        //The one with earlier executeTime has higher priority
        return (int) (this.executeTime - ((DelayTask) other).executeTime);
    }

    public void run(){
        task.run();
    }

}