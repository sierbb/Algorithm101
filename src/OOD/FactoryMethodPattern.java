package OOD;

public class FactoryMethodPattern {
}


class Room{

}

class OrdinaryRoom extends Room{
    public OrdinaryRoom(String name, int size){
    }
}

class MagicRoom extends Room{

    public MagicRoom(String name, int size, int magic){
    }
}

class MazeGame{

    public void createGame(){
        Room room1 = getRoom("O1", 1);
        Room room2 = getRoom("O2", 2);
    }

    /**
     * Idea: Wrap the specific room creation logic into a generate function
     * So a child class of MazeGame only needs to override this function
     * to create its customized room
     * @param name
     * @param size
     * @return
     */
    protected Room getRoom(String name, int size){
        return new OrdinaryRoom(name, size);
    }
}

class MagicMazeGame extends MazeGame{

    @Override
    protected Room getRoom(String name, int size){
        return new MagicRoom(name, size, 1);
    }
}


