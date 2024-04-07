package observer;

public interface Observable {

    void register(Observer ob);

    void deregister(Observer ob);

    void notifyObservers();
}
