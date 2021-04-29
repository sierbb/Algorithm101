package myObject;

import myObject.HashMap;

public class HashMapTest {

    public static void main(String[] args){
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        //Put nodes
        for (int i = 0; i <= 'z'-'a'; i++){
            System.out.println("Putting key: "+ String.valueOf((char)('a'+i)));
            System.out.println(map.put(String.valueOf((char)('a'+i)), i));
        }

        //check nodes
        System.out.println("size: " + map.size());
        System.out.println(map.isEmpty());
        System.out.println(map.containsKey("b"));
        System.out.println(map.containsKey("h"));
        System.out.println(map.containsValue(10));
        System.out.println(map.get("p"));

        //update value
        map.put("b", 2);
        System.out.println(map.get("b"));

        //remove node
        map.remove("b");
        map.remove("h");
        System.out.println(map.containsKey("b"));
        System.out.println(map.containsKey("h"));
        System.out.println(map.get("p"));
        System.out.println(map.get("v"));

        map.printIndexAndNode();

    }
}
