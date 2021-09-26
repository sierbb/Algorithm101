package OOD;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class ParkingLotTest {

    @Test
    public void testParkingLot(){
        ParkingLot pl = new ParkingLot(4, 2);

        List<Vehicle> vehicleList = new ArrayList<>();
        for (int i=0; i < 10; i++){
            Vehicle v = i % 2 == 0? new Car() : new Truck();
            vehicleList.add(v);
            if (i < 8){
                //when total car count <=40, we should be able to fit them
                assertTrue(pl.hasSpot(v));
                assertTrue(pl.park(v));
            }else {
                assertFalse(pl.hasSpot(v));
                assertFalse(pl.park(v));
            }
        }
        assert vehicleList.size() == 10;
        int count = 0;
        for (int i=0; i<vehicleList.size(); i++){
            Vehicle cur = vehicleList.get(i);
            //the first 8 vehicles can leave because they parked there, the latter can not
            assertTrue(count >= 8 || pl.leave(cur));
            count++;
        }
    }
}