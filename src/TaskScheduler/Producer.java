package TaskScheduler;

import java.beans.IntrospectionException;
import java.util.Random;
import java.util.UUID;

/**
 * The producer will just keep putting randome task infinitely.
 */
public class Producer implements Runnable{

    private final DelayQueue<DelayTask> q;
    private final Random rand = new Random();

    public Producer(DelayQueue<DelayTask> q){
        this.q = q;
    }

    @Override
    public void run(){
        while ( true ){
            int delay = rand.nextInt(1000);
            try{
                DelayTask task = new DelayTask(new DefaultTask(UUID.randomUUID().toString()), delay);
                System.out.println("Put: " + task);
                q.put(task);
                Thread.sleep(2000);

            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
