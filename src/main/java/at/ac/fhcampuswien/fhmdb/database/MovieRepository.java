package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository
{
    Dao<MovieEntity, Long> dao;

    public MovieRepository() throws SQLException
    {
        this.dao = Database.getMovieEntitiesDao();
    }

    public void addToDB(Movie movie) throws SQLException
    {
        MovieEntity movieEntity = MovieEntity.fromMovie(movie);
        movieEntity.setId(findMovieInDbOrNew(movie).getId());
        dao.createOrUpdate(movieEntity);
    }

    public void removeFromDB(Movie movie) throws SQLException
    {
        MovieEntity movieEntity = findMovieInDbOrNew(movie);
        if (movieEntity.getId() != 0)
            dao.delete(movieEntity);
    }

    private MovieEntity findMovieInDbOrNew(Movie movie) throws SQLException
    {
        MovieEntity entity;
        List<MovieEntity> existing = dao.queryForEq("apiId", movie.getId());

        if (!existing.isEmpty())
            entity= existing.get(0);
        else
            entity = MovieEntity.fromMovie(movie);
        return entity;
    }

    public List<MovieEntity> getAllMovies() throws SQLException
    {
        return dao.queryForAll();
    }
    public int removeAll() throws SQLException
    {
        return dao.delete(getAllMovies());
    }

    public int addAllMovies(List<Movie> movies) throws SQLException
    {
        return dao.create(MovieEntity.fromMovies(movies));
    }

    //TODO beibehalten????
    public List<Movie> getMoviesFromWatchlist() throws SQLException {
        WatchListRepository watchListRepository = new WatchListRepository();

        List<WatchListMovieEntity> watchList = watchListRepository.getWatchList();
        List<String> apiIds = new ArrayList<>();
        for(WatchListMovieEntity watchListMovieEntity: watchList)
            apiIds.add(watchListMovieEntity.getApiId());

        if (apiIds.isEmpty())
            return new ArrayList<>();

         return MovieEntity.toMovies( dao.queryBuilder().where().in("apiId", apiIds).query());

    }


}
