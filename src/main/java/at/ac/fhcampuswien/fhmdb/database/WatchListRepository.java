package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.utils.Observable;
import at.ac.fhcampuswien.fhmdb.utils.Observer;
import com.j256.ormlite.dao.Dao;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchListRepository implements Observable {
    Dao<WatchListMovieEntity, Long> dao;

    private static WatchListRepository instance;
    private final List<Observer> observers = new ArrayList<>();
    private String message;
    private Alert.AlertType type;

    private WatchListRepository(/*Dao<WatchListMovieEntity, Long> dao*/) throws SQLException {

        this.dao = Database.getWatchListDao();
    }

    public static WatchListRepository getInstance() throws SQLException
    {
        if (instance == null)
            instance = new WatchListRepository();
        return instance;
    }

    public List<WatchListMovieEntity> getWatchList() throws SQLException{
        return dao.queryForAll();
    }

    public void addToWatchList(String apiId)throws SQLException{
        List<WatchListMovieEntity> existing = dao.queryForEq("apiId", apiId);

        // If no matching entry exists, create and insert a new one
        if (existing.isEmpty()) {
            WatchListMovieEntity newEntity = new WatchListMovieEntity(apiId);
             dao.create(newEntity);  // returns 1 if insertion was successful
            pushInformation("Movie added to watchlist");
            return;
        }

        // If an entry already exists, return 0 (nothing was added)
        pushInformation("Movie already on watchlist");
    };


    public void removeFromWatchList(String apiId)throws SQLException {
        // Query the database to find any WatchListMovieEntity entries that match the given apiId
        List<WatchListMovieEntity> found = dao.queryForEq("apiId", apiId);
        // If a matching entry is found, delete the first one from the list
        if (!found.isEmpty()) {
            dao.delete(found.get(0));  // returns 1 if deletion was successful
            pushInformation("Movie removed from watchlist");
            return;
        }
        // If no matching entry is found, return 0 (nothing was deleted)
        pushInformation("Movie is not on watchlist");
    };

    @Override
    public void subscribe(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void unsubscribe(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notifySubscribers() {
        for (Observer o: observers)
            o.writeAlert("Watchlist",this.message,this.type);
    }

    public void pushInformation(String message)
    {
        pushAlert(message, Alert.AlertType.INFORMATION);
    }


    public void pushAlert(String message, Alert.AlertType type)
    {
        this.message = message;
        this.type = type;
        notifySubscribers();
    }

}
