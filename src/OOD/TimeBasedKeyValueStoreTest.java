package OOD;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class TimeBasedKeyValueStoreTest {

    @Before
    public void init(){

    }

    @Test
    public void testTimeMap1() throws InterruptedException {
        //sorted timestamp
        TimeBasedKeyValueStore map = new TimeBasedKeyValueStore();
        long d1 = System.currentTimeMillis();
        sleep(1);
        long d2 = System.currentTimeMillis();
        sleep(1);
        long d3 = System.currentTimeMillis();
        map.set("A", "a", d1);
        map.set("A", "b", d2);
        map.set("A", "c", d3);
        map.set("A", "d");
        //return value with latest timestamp
        assertEquals(map.get("A", 0), "");
    }

    @Test
    public void testTimeMap2() throws InterruptedException {
        TimeBasedKeyValueStore map = new TimeBasedKeyValueStore();
        long d1 = System.currentTimeMillis();
        sleep(1);
        long d2 = System.currentTimeMillis();
        sleep(1);
        long d3 = System.currentTimeMillis();
        assertTrue(d3 > d2);
        map.set("A", "a", d1);
        map.set("A", "b", d2);
        map.set("A", "c");
        map.set("A", "d");
        assertEquals(map.get("A"), "d");
        assertEquals(map.get("A", d1), "a");
        assertEquals(map.get("A", d2), "b");
    }

    @Test
    public void testTimeMap3() throws InterruptedException {
        TimeBasedKeyValueStore map = new TimeBasedKeyValueStore();
        long d1 = System.currentTimeMillis();
        sleep(1);
        long d2 = System.currentTimeMillis();
        sleep(1);
        long d3 = System.currentTimeMillis();
        map.set("A", "a", d1);
        map.set("A", "b", d2);
        map.set("A", "c", d3);
        map.set("A", "d");
        assertEquals(map.get("B"), "");
        assertEquals(map.get("A", d1-1), "");
        assertEquals(map.get("A", d3+1), "d");
    }

    @Test
    public void testTimeMap4() throws InterruptedException{
        TimeBasedKeyValueStore map = new TimeBasedKeyValueStore();
        long d1 = System.currentTimeMillis();
        sleep(1);
        long d2 = System.currentTimeMillis();
        sleep(1);
        long d3 = System.currentTimeMillis();
        map.set("A", "a", d1);
        map.set("A", "b", d2);
        map.set("A", "c", d3);
        map.set("A", "d");
        //if no timestamp provided, return the one with latest timestamp
        assertEquals(map.get("A"), "d");
    }

    @After
    public void close(){
        System.out.println("close....");
    }
}
