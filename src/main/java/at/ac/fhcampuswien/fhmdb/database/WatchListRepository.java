package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchListRepository {
    Dao<WatchListMovieEntity, Long> dao;

    public WatchListRepository(Dao<WatchListMovieEntity, Long> dao) throws SQLException {
        this.dao = dao;
    }

    List<WatchListMovieEntity> getWatchList() throws SQLException{
        return dao.queryForAll();
    }

    public int addToWatchList(String apiId)throws SQLException{
        List<WatchListMovieEntity> existing = dao.queryForEq("apiId", apiId);

        // If no matching entry exists, create and insert a new one
        if (existing.isEmpty()) {
            WatchListMovieEntity newEntity = new WatchListMovieEntity(apiId);
            return dao.create(newEntity);  // returns 1 if insertion was successful
        }

        // If an entry already exists, return 0 (nothing was added)
        return 0;
    };


    int removeFromWatchList(String apiId)throws SQLException {
        // Query the database to find any WatchListMovieEntity entries that match the given apiId
        List<WatchListMovieEntity> found = dao.queryForEq("apiId", apiId);
        // If a matching entry is found, delete the first one from the list
        if (!found.isEmpty()) {
            return dao.delete(found.get(0));  // returns 1 if deletion was successful
        }
        return 0; // If no matching entry is found, return 0 (nothing was deleted)
    };


}
