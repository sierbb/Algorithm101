package TaskScheduler;

interface DelayTaskScheduler {

    /**
     * Set the task to run after delayTime.
     * @param delayTime
     * @param Task
     */
    public void runTask(long delayTime, Runnable Task);

}

public class DelayedTaskScheduler implements DelayTaskScheduler {

    private DelayQueue<DelayTask> q;

    public DelayedTaskScheduler(DelayQueue<DelayTask> q){
        this.q = q;
    }

    public void runTask(long delayTime, Runnable task){
        DelayTask newTask = new DelayTask(task, delayTime);
        q.put(newTask);
    }

}

class TestDelayQueue{

    public static void main(String[] args){
        DelayQueue<DelayTask> queue = new DelayQueue<>();
        new Thread(new Producer(queue), "Producer Thread").start();
        new Thread(new Consumer(queue), "Consumer Thread").start();
    }

}

