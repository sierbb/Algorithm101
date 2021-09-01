package TaskScheduler;

public class Consumer implements Runnable {

    private final DelayQueue<DelayTask> q;

    public Consumer(DelayQueue<DelayTask> q){
        this.q = q;
    }

    /**
     * Consumer will keep taking tasks out of queue
     * and do whatever it needs.
     */
    @Override
    public void run(){
        while (true){
            try{
                DelayTask task = q.take();
                System.out.println("Take: " + task);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }
}
