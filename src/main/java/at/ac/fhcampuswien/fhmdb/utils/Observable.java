package at.ac.fhcampuswien.fhmdb.utils;

public interface Observable {
    void subscribe(Observer o);
    void unsubscribe(Observer o);

    void notifySubscribers();
}
