package OOD;

import javax.swing.plaf.IconUIResource;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A rate limiter should limit the hit per second to the custom threshold
 */
interface RateLimiter{

    public boolean incr();
}


public class RateLimiterFixedWindow implements RateLimiter {

    private static final int DEFAULT_RATE_LIMIT_PER_MINIUTE = Integer.MAX_VALUE;
    private int count;
    private int requestLimit;
    long startTime;
    long intervalLimit;

    public RateLimiterFixedWindow(int requestLimit, long intervallimit){
        if (requestLimit <= 0){
            throw new IllegalArgumentException("Invalid limit");
        }
        this.requestLimit = requestLimit;
        this.intervalLimit = intervallimit;
        this.startTime = System.currentTimeMillis();
    }

    public boolean incr(){
        long cur = System.currentTimeMillis();
        if (cur - startTime >= 2000){
            //reset startTime and count
            startTime = cur;
            count = 0;
        }
        if (count >= requestLimit) {
            System.out.println("Rejecting since exceeding limit");
            return false;
        }
        count++;
        System.out.println("Request accepted");
        return true;
    }

    public static void main(String[] args){
        RateLimiterFixedWindow obj = new RateLimiterFixedWindow(5, 2000);
        int num = 100;
        int processed = 0;
        int produced = 0;
        while (produced < 100){
            try{
                Thread.sleep(200);
                if (obj.incr()){
                    processed++;
                }
                produced++;
                if (produced % 10 == 0){
                    System.out.println("sec: " + System.currentTimeMillis() / 1000L + ", mil: " + System.currentTimeMillis() + " got 5 accepted");
                    System.out.println("Processed: " +processed + ", produced: " + produced + " requests");
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
