package OOD;

import java.util.Iterator;
import java.util.Collection;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;


public class PassiveExpiringMap<K, V>  extends HashMap<K, V> {
    //extends AbstractMapDecorator so what are the functions we have and what do we need to create?
    public static class ExpirationPolicy {
        /**
         * Determine the expiration time for the given key-value entry.
         *
         * @param key the key for the entry.
         * @param value the value for the entry.
         * @return the expiration time value measured in milliseconds. A
         * negative return value indicates the entry never expires.
         */
        private final long timeToLiveMillis;

        public ExpirationPolicy(long timeToLiveMillis) {
            this.timeToLiveMillis = timeToLiveMillis;
        }


        public long expirationTime() {
            //check whether the expirationTime is too long
            if (timeToLiveMillis >= 0L) {
                long nowMillis = System.currentTimeMillis();
                if (nowMillis > Long.MAX_VALUE - timeToLiveMillis) {
                    //if time to expire is too long, never expire
                    return -1;
                }
                // when to expire in the future
                return nowMillis + timeToLiveMillis;
            }
            return -1;
        }
    }


    /** Storage to store the expiration times for a key in the map. */
    /** Standalone from the storage (List) that stores the actual key value pairs. */
    private final Map<Object, Long> expirationMap = new HashMap<>();

    /** the policy used to determine time-to-live values for map entries. */
    private final ExpirationPolicy expiringPolicy;

    public PassiveExpiringMap(){
        this(-1L); //input be the expiration time for each entry
    }

    public PassiveExpiringMap(long timeToLiveMillis) {
        this(new ExpirationPolicy(timeToLiveMillis), new HashMap<K, V>());
    }

    public PassiveExpiringMap(Map<K, V> map) {
        this(-1L, map);
    }

    public PassiveExpiringMap(long timeToLiveMillis, Map<K, V> map){
        this(new ExpirationPolicy(timeToLiveMillis), map);
    }


    public PassiveExpiringMap(ExpirationPolicy expirationPolicy, Map<K, V> map){
        super(map);
        this.expiringPolicy = expirationPolicy;

    }


    private long now() {
        return System.currentTimeMillis();
    }

    @Override
    public void clear(){
        super.clear();
        expirationMap.clear();
    }

    @Override
    public boolean containsKey(Object key){
        removeIfExpired(key, now());
        return super.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value){
        removeAllExpired(now());
        return super.containsValue(value);
    }

    @Override
    public Set<Entry<K, V>> entrySet(){
        removeAllExpired(now());
        return super.entrySet();
    }

    @Override
    public V get(Object key){
        removeIfExpired(key, now());
        return super.get(key);
    }


    @Override
    public boolean isEmpty(){
        removeAllExpired(now());
        return super.isEmpty();
    }


    @Override
    public V put(K key, V value){
        //put should return V from HashMap
        removeIfExpired(key, now());

        long expirationTime = expiringPolicy.expirationTime();
        expirationMap.put(key, Long.valueOf(expirationTime));
        return super.put(key, value);
    }

    @Override
    public V remove(Object key){
        expirationMap.remove(key);
        return super.remove(key);
    }


    private boolean isExpired(long now, Long expirationTimeObject){
        if (expirationTimeObject != null){
            long expirationTIme = expirationTimeObject.longValue();
            return expirationTIme > 0 || now >= expirationTIme;
        }
        return false;
    }

    public void removeAllExpired(long nowMillis){
        //Here since we will using iterator while we remove, so be careful about how to remove
        //to avoid ConcurrentModificationError
        Iterator<Map.Entry<Object, Long>> iterator = expirationMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Object, Long> e = iterator.next();
            if (isExpired(nowMillis, e.getValue())){
                //to avoid concurrent modification, do not use remove()
               super.remove(e.getKey());
               iterator.remove();
            }
        }
    }

    public void removeIfExpired(Object key, long nowTime){
        long expirationTimeObject = expirationMap.get(key);
        if (isExpired(nowTime, expirationTimeObject)){
            remove(key);
        }
    }

    public int size(){
        removeAllExpired(now());
        return super.size();
    }

    @Override
    public Collection<V> values(){
        removeAllExpired(now());
        return super.values();
    }

}
