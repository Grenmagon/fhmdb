package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.database.Database;
import at.ac.fhcampuswien.fhmdb.database.MovieEntity;
import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.dao.Dao;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.*;


public class Movie implements Comparable<Movie>{

    @Override
    public int compareTo(Movie o)
    {
        return this.title.compareTo(o.getTitle());
    }

    public enum Genre
    {
        ACTION,
        ADVENTURE,
        ANIMATION,
        BIOGRAPHY,
        COMEDY,
        CRIME,
        DRAMA,
        DOCUMENTARY,
        FAMILY,
        FANTASY,
        HISTORY,
        HORROR,
        MUSICAL,
        MYSTERY,
        ROMANCE,
        SCIENCE_FICTION,
        SPORT,
        THRILLER,
        WAR,
        WESTERN
    }
    private String id;

    public enum Rating {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9);

        private final int value;

        Rating(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private String title;
    private String description;
    private List<Genre> genres;

    private int releaseYear;
    private String imgUrl;
    private int lengthInMinutes;
    private List<String> directors;
    private List<String> writers;
    private List<String> mainCast;
    private double rating;

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<Genre> getGenres() {return genres;}

    public void setGenres(List<Genre> genres)
    {
        this.genres = genres;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public int getReleaseYear()
    {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear)
    {
        this.releaseYear = releaseYear;
    }

    public int getLengthInMinutes()
    {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes)
    {
        this.lengthInMinutes = lengthInMinutes;
    }

    public List<String> getDirectors()
    {
        return directors;
    }

    public void setDirectors(List<String> directors)
    {
        this.directors = directors;
    }

    public List<String> getWriters()
    {
        return writers;
    }

    public void setWriters(List<String> writers)
    {
        this.writers = writers;
    }

    public List<String> getMainCast()
    {
        return mainCast;
    }

    public void setMainCast(List<String> mainCast)
    {
        this.mainCast = mainCast;
    }

    public double getRating()
    {
        return rating;
    }

    public void setRating(double rating)
    {
        this.rating = rating;
    }

    public String getGenresString()
    {
        String genresString = "";
        for (Genre g: genres)
        {
            if (!genresString.isEmpty())
                genresString += ", ";

            genresString += g.name();
        }
        return genresString;
    }

    static public String[] getGenreStringArray(){
        /*
        Arrays.stream =converts the array into a stream so we can perform operations on it
        .map(Enum::name)=
        .map-->transforms each element in the stream
        Enum::name is a method reference that calls the name() method on each enum constant
        he name() method returns the string representation of the enum constant
        .toArray(String[]::new) --> Converts the processed stream back into a String[] array, create a new String array
         */
        return Arrays.stream(Movie.Genre.values()).map(Movie.Genre::name).toArray(String[]::new);
    }

    static public String[] getRatingStringArray() {
        return Arrays.stream(Movie.Rating.values()).map(r ->String.valueOf(r.getValue())).toArray(String[]::new);
    }

    public static List<Movie> allMoviesAPI() throws IOException, MovieAPIException
    {
        String json = MovieAPI.getMoviesFilter(null, null, 0, 0);
        return  getMoviesFromJson(json);
    }

    public static List<Movie> getMoviesFromJson(String json)
    {
        if (json.equals(MovieAPI.ERROR))
            return null;
        Gson gson = new Gson();
        Type movieListType = new TypeToken<List<Movie>>() {}.getType();
        List<Movie> movies = gson.fromJson(json, movieListType);
        return movies;
    }

    public static List<Movie> getMoviesFromDB() throws SQLException {
        MovieRepository mr = new MovieRepository();
        // Fetch all movies as MovieEntities, then convert to Movie
        List<MovieEntity> movieEntities = mr.getAllMovies();
        List<Movie> movies = new ArrayList<>();

        for (MovieEntity entity : movieEntities) {
            movies.add(MovieEntity.toMovie(entity));  // Convert MovieEntity to Movie
        }

        return movies;
    }

    public static void loadFromApiToDB() throws SQLException, IOException, MovieAPIException {
        MovieRepository mr = new MovieRepository();
        mr.removeAll();
        mr.addAllMovies(allMoviesAPI());
    }

}
