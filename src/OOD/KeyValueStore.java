package OOD;

import java.util.*;

public class KeyValueStore {

    private Map<String, String> map;

    public KeyValueStore(){
        map = new HashMap<>();
    }

    public String get(String key) throws NoSuchElementException{
        if (!map.containsKey(key)){
            throw new NoSuchElementException("Key "+ key + " not found");
        }
        return map.get(key);
    }

    public void set(String key, String value){
        map.put(key, value);
    }

    public static void main(String[] args){
        KeyValueStore kv = new KeyValueStore();
        kv.set("A", "a");
        kv.set("B", "b");
        System.out.println(kv.get("A"));
        System.out.println(kv.get("B"));
        System.out.println(kv.get("C"));
    }
}
