package TaskScheduler;

import static org.junit.Assert.*;
import org.junit.Test;

import java.net.InterfaceAddress;
import java.util.Random;

public class DelayedTaskSchedulerTest {

    @Test
    public void testDelayedTaskScheduler(){
        System.out.println("Test 1:");
        DelayQueue<DelayTask> queue = new DelayQueue<>();
        DelayedTaskScheduler scheduler = new DelayedTaskScheduler(queue);
        Random rand = new Random();

        //Start a consumer thread to take from the queue
        new Thread(new Consumer(queue), "Consumer thread").start();

        //Current thread act as producer to "run" task
        int count = 0;
        while ( count <= 5 ){
            int delayTime = rand.nextInt(1000);
            try{
                DelayTask task = new DelayTask(delayTime);
                scheduler.runTask(delayTime, new DefaultTask());
                Thread.sleep(1000);
                count++;
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testProducerConsumer(){
        System.out.println("Test 2:");
        DelayQueue<DelayTask> queue = new DelayQueue<>();
        Thread prod = new Thread(new Producer(queue), "Producer Thread");
        Thread cons = new Thread(new Consumer(queue), "Consumer Thread");

        prod.start();
        System.out.println("Producer thread started");
        cons.start();
        System.out.println("Consumer thread started");

        try{
            Thread.sleep(5000);
            prod.interrupt(); // calling interrupt() method
            cons.interrupt();
            System.out.println("Both threads terminated");
        }catch (InterruptedException e){

        }

    }

}