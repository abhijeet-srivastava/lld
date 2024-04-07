package observer;

import java.util.List;

public class NewsAgency implements Observable {
    List<Channel>  observers;

    @Override
    public void register(Observer ob) {

    }

    @Override
    public void deregister(Observer ob) {

    }

    @Override
    public void notifyObservers() {

    }
}
