package OOD;

import java.util.*;

public class RetainBestCache<K, T extends Rankable> {

    private int entriesToRetain;
    private HashMap<K, T> cache;
    private DataSource<K, T> ds;
    private PriorityQueue<Pair> rankingPriorityQueue;

    /* Constructor with a data source (assumed to be slow) and a cache size */
    public RetainBestCache(DataSource<K, T> ds, int entriesToRetain) {
        this.ds = ds;
        this.entriesToRetain = entriesToRetain;
        this.cache = new HashMap<>();
        this.rankingPriorityQueue = new PriorityQueue<>(new Comparator<Pair>(){
            @Override
            public int compare(Pair p1, Pair p2){
                if (p1.rank == p2.rank){
                    return 0;
                }
                return p1.rank > p2.rank ? 1: -1; //lower rank has higher priority
            }
        });
    }

    /* Gets some data. If possible, retrieves it from cache to be fast. If the data is not cached,
     * retrieves it from the data source. If the cache is full, attempt to cache the returned data,
     * evicting the T with lowest rank among the ones that it has available
     * If there is a tie, the cache may choose any T with lowest rank to evict.
     */
    public T get(K key) {
        //Step 1: if key exists in cache, return directly - cache hit O(1)
        //Step 2: if key not exists, retrieve from DS; check whether key has higher ranking than pq.peek(),
        // if so, poll pq and offer current key - cache miss - O(logn)

        synchronized(this){
            if (cache.containsKey(key)){
                return cache.get(key);
            }
        }

        // Get value and ranking
        T value = ds.get(key);
        long rank = value.getRank();

        synchronized(this){
            //Check cache size to see whether we need evict cache
            if (cache.size() > entriesToRetain && rankingPriorityQueue.size() > 0 && rank > rankingPriorityQueue.peek().rank){
                //current value's rank is higher, need to remove a lowest rank entry
                Pair oldP = rankingPriorityQueue.poll();
                //remove old entry from map as well
                cache.remove(oldP.key);
            }

            //Add new key to both pq and cache
            rankingPriorityQueue.offer(new Pair(key, value.getRank()));
            cache.put(key, value);
        }

        return value;
    }
}


class Pair<K>{
    public K key;
    public long rank;

    public Pair(K key, long rank){
        this.key = key;
        this.rank = rank;
    }
}

/*
 * For reference, here are the Rankable and DataSource interfaces.
 * You do not need to implement them, and sh‍‍‌‍‍‌‌‌‍‌‍‌‌‍‍‌‍‌ould not make assumptions
 * about their implementations.
 */
interface Rankable {
    /**
     * Returns the Rank of this object, using some algorithm and potentially
     * the internal state of the Rankable.
     */
    long getRank();
}

interface DataSource<K, T extends Rankable> {
    T get(K key);
}

//Clarification:
//1. Will there be multiple T for the same K?
//If K and T is unique, then HashMap should be able to do it, other wise will need HashMap<K, Set<T>>

//2.If the cache is fll, what is the evict policy if multiple T has same low ranking? (tie)
//