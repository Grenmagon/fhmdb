package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class Database
{
    public static final String DB_URL = "jdbc:h2:file: ./db/fhmdb";
    public static final String USER = "user";
    public static final String PASSWD = "pass";

    private static JdbcConnectionSource connectionSource;
    private static Dao<MovieEntity, Long> movieEntitiesDao;
    private static Dao<WatchListMovieEntity, Long> watchListDao;

    private static Database instance;

    private Database() throws SQLException
    {
        createConnectionSource();
        movieEntitiesDao = DaoManager.createDao(connectionSource, MovieEntity.class);
        watchListDao = DaoManager.createDao(connectionSource, WatchListMovieEntity.class);
        createTables();
    }

    public static Database getDB() throws SQLException
    {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    private static void createConnectionSource() throws SQLException
    {
        connectionSource = new JdbcConnectionSource(DB_URL, USER, PASSWD);
    }

    public static JdbcConnectionSource getConnectionSource() throws SQLException
    {
        getDB();
        return connectionSource;
    }

    public static Dao<MovieEntity,Long> getMovieEntitiesDao() throws SQLException
    {
        getDB();
        return movieEntitiesDao;
    }
    public static Dao<WatchListMovieEntity, Long> getWatchListDao() throws SQLException {
        // Make sure the database instance and DAO are initialized
        getDB();

        // Return the DAO that allows interacting with the watchList table
        return watchListDao;
    }

    // Hier werden die neuen Tabellen hinzugefügt
    private static void createTables() throws SQLException
    {
        TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, WatchListMovieEntity.class);
    }

}
