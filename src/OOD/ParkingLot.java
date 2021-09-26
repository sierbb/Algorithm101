package OOD;
import java.util.*;

enum VehicleSize{
    Compact(1), //because we added constructor for int size
    Large(2);

    private final int size;

    VehicleSize(int size){
        this.size = size;
    }

    public int getSize(){
        return this.size;
    }
}

public class ParkingLot {
    private final Level[] levels;

    public ParkingLot(int numOfLevels, int numOfSpotsPerLevel){
        levels = new Level[numOfLevels];
        for (int i=0; i<levels.length; i++){
            levels[i] = new Level(numOfSpotsPerLevel);
        }
    }

    public boolean hasSpot(Vehicle v){
        for (Level level : levels){
            if (level.hasSpot(v)){
                return true;
            }
        }
        return false;
    }

    public boolean park(Vehicle v){
        //how to make it efficient to lookup vehicle? Use HashMap<Vehicle, Spot>
        for (Level level : levels){
            if (level.park(v)){
                return true;
            }
        }
        return false;
    }

    public boolean leave(Vehicle v){
        for (Level level : levels){
            if (level.leave(v)){
                return true;
            }
        }
        return false;
    }

}

class Level{
    private final int limit;
    private final List<ParkingSpot> spots;

    public Level(int numOfSpots){
        limit = numOfSpots;
        spots = new ArrayList<ParkingSpot>();
        //insert spot to the list
        int i=0;
        while ( i<numOfSpots/2){ //4 /2 = 2
            spots.add(new ParkingSpot(VehicleSize.Compact));
            i++;
        }
        while( i<numOfSpots){
            spots.add(new ParkingSpot(VehicleSize.Large));
            i++;
        }
        Collections.unmodifiableList(spots);
    }

    public boolean hasSpot(Vehicle v){
        //return whether there is any spot unoccupied. Scan through every spot available
        for (ParkingSpot spot : spots){
            if (spot.fit(v)){
                return true;
            }
        }
        return false;
    }

    public boolean park(Vehicle v){
        //randomly park a vehicle
        for (ParkingSpot spot : spots){
            if (spot.fit(v)){
                spot.park(v);
                return true;
            }
        }
        return false;
    }

    public boolean leave(Vehicle v){
        //make a spacific vehicle to leave
        for (ParkingSpot spot : spots){
            if (spot.getVehicle() != null && spot.getVehicle() == v){
                spot.leave();
                return true;
            }
        }
        return false;
    }

}

class ParkingSpot{
    private final VehicleSize size;
    private Vehicle currentVehicle;

    public ParkingSpot(VehicleSize size) {
        this.size = size;
    }

    public boolean fit(Vehicle v){
        //Bad extesion, not extenable if we later add new Vehicle size to enum class
//        return size.ordinal() >= v.getSize().ordinal();
        //Should add a size attribute to the enum and use it to compare?
        return currentVehicle == null && size.getSize() >= v.getSize().getSize();
    }

    public void park(Vehicle v){
        currentVehicle = v;
    }

    public void leave(){
        currentVehicle = null;
    }

    public Vehicle getVehicle(){
        return currentVehicle;
    }

}

abstract class Vehicle{
    private final String plate = null;
    public abstract VehicleSize getSize();
}

class Car extends Vehicle{

    public Car(){
        super();
    }

    @Override
    public VehicleSize getSize(){
        return VehicleSize.Compact;
    }
}


class Truck extends Vehicle{
    @Override
    public VehicleSize getSize(){
        return VehicleSize.Large;
    }
}
