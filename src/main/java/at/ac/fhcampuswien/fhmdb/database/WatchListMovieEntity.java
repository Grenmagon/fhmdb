package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "watchList")
public class WatchListMovieEntity {
    // This field represents the primary key (ID) of the table
    // It stores the API ID of the movie (e.g., "tt1375666")
    @DatabaseField(id = true)
    private String apiId;

    // ORMLite requires a no-argument constructor to recreate objects from the database
    public WatchListMovieEntity() {}

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    // Constructor with apiId to create new entries easily
    public WatchListMovieEntity(String apiId) {
        this.apiId = apiId;
    }

}
