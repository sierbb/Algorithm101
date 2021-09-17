package OOD;

import java.util.*;

public class RateLimiterSlidingWindow {
    //This is a sliding window RateLimiter
    //Queue size == number of requests allowed in intervalLimit

    Map<String, Queue<Long>> map;
    private int requestLimit;
    private int intervalLimit;

    public RateLimiterSlidingWindow(int requestLimit, int intervalLimit){
        this.requestLimit = requestLimit;
        this.intervalLimit = intervalLimit;
        map = new HashMap<>();
    }

    public boolean request(String user) {
        //TC: O(requests_allowed) to poll older requests out of intervalLimit
        //SC: O(requests_allowed) for storing n requests within intervalLimit

        long now =  System.currentTimeMillis();
        if (!map.containsKey(user)) {
            Queue<Long> q = new LinkedList<>();
            q.offer(now);
            map.put(user, q);
            return true;
        } else {
            Queue userQ = map.get(user);
            //do clean up on the old node

            while (userQ != null && !userQ.isEmpty() &&  (long)userQ.peek() + intervalLimit < now){
//                System.out.println((long)userQ.peek() + "is older than current time: " + now);
                userQ.poll();
            }
            //Then look at Q size, see how many elements still valid
            if (userQ.size() >= requestLimit) {
                System.out.println("Rejecting request since exceeding limit");
                return false;
            }
            System.out.println("Request accepted");
            userQ.offer(now);
            return true;
        }
    }

    public static void main(String[] args){

        RateLimiterSlidingWindow obj = new RateLimiterSlidingWindow(5, 2*1000); //initial map with requestlimit = 10
        long start = System.currentTimeMillis();
        Random rand = new Random();
        int produced = 0;
        int processed = 0;
        while (produced <= 1000){ //send 1000 request
            try{
                Thread.sleep(rand.nextInt(500));
                if (obj.request("Tom")){
                    processed++;
                }
                long elasped = System.currentTimeMillis() - start;
                if (processed % 10 == 0){
                    System.out.println("Processed: " + processed);
                    System.out.println("req./s produced :" +  (long)produced / (elasped/1000));
                    System.out.println("req./s processed :" +  (long)processed / (elasped/1000));
                }

            }catch (InterruptedException e){
                e.printStackTrace();
            }
            produced++;
        }

    }
}

//M0: RateLimiter with fixed window ??
//When will the window start -> (current - start  5 1000) == 0 -> reset count


//M1:
//For RateLimiter with sliding window, use a queue to store all rquest timestamps that are allowed within intervalLimit
//TC: O(requests_allowed) to poll older requests out of intervalLimit
//SC: O(requests_allowed) for storing n requests within intervalLimit

//M2: combination of