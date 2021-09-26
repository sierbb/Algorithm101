package OOD;

public class SingletonExample {

    private SingletonExample INSTANCE = new SingletonExample();

    private SingletonExample(){
    }

    public SingletonExample getInstance(){
        return INSTANCE;
    }
}

class SingletonLazyExample{

    private SingletonLazyExample INSTANCE = null;

    private SingletonLazyExample(){
    }

    public SingletonLazyExample getInstance(){
        if (INSTANCE == null){
            INSTANCE = new SingletonLazyExample();
        }
        return INSTANCE;
    }
}
