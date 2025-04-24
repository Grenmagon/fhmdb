package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@DatabaseTable(tableName = "movie")
public class MovieEntity
{
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(canBeNull = false, unique = true)
    private String apiId;
    @DatabaseField
    private String title;
    @DatabaseField
    private String description;
    @DatabaseField
    private String genres;
    @DatabaseField
    private int releaseYear;
    @DatabaseField
    private String imgUrl;
    @DatabaseField
    private int lengthInMinutes;
    @DatabaseField
    private double rating;

    public MovieEntity()
    {}

    public MovieEntity(String apiId, String title, String description, String genres, int releaseYear, String imgUrl, int lengthInMinutes, double rating)
    {
        this.apiId = apiId;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    public String getApiId()
    {
        return apiId;
    }

    public void setApiId(String apiId)
    {
        this.apiId = apiId;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getGenres()
    {
        return genres;
    }

    public void setGenres(String genres)
    {
        this.genres = genres;
    }

    public int getReleaseYear()
    {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear)
    {
        this.releaseYear = releaseYear;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public int getLengthInMinutes()
    {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes)
    {
        this.lengthInMinutes = lengthInMinutes;
    }

    public double getRating()
    {
        return rating;
    }

    public void setRating(double rating)
    {
        this.rating = rating;
    }

    public static MovieEntity fromMovie(Movie movie)
    {
        return new MovieEntity(movie.getId(), movie.getTitle(), movie.getDescription(), genresToString(movie.getGenres()), movie.getReleaseYear(), movie.getImgUrl(), movie.getLengthInMinutes(), movie.getRating());
    }

    public static Movie toMovie(MovieEntity entity)
    {
        List<Movie.Genre> genreList = new ArrayList<>();
        for(String genreString: entity.genres.split(","))
        {
            genreList.add(Movie.Genre.valueOf(genreString));
        }
        Movie movie = new Movie(entity.title, entity.description, genreList);
        movie.setId(entity.apiId);
        movie.setReleaseYear(entity.releaseYear);
        movie.setImgUrl(entity.imgUrl);
        movie.setLengthInMinutes(entity.lengthInMinutes);
        movie.setRating(entity.rating);
        return movie;
    }

    public static List<MovieEntity> fromMovies(List<Movie> movies)
    {
        List<MovieEntity> entities = new ArrayList<>();
        for (Movie movie: movies)
            entities.add(fromMovie(movie));
        return entities;
    }

    public static List<Movie> toMovies(List<MovieEntity> movieEntities)
    {
        List<Movie> movies = new ArrayList<>();
        for (MovieEntity entity: movieEntities)
            movies.add(toMovie(entity));
        return movies;
    }


    public static String genresToString(List<Movie.Genre> genres)
    {
        StringJoiner joiner = new StringJoiner(",");
        for(Movie.Genre genre: genres)
        {
            joiner.add(genre.name());
        }
        return joiner.toString();
    }

    @Override
    public String toString()
    {
        return "MovieEntity{" +
                "id=" + id +
                ", apiId='" + apiId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", genres='" + genres + '\'' +
                ", releaseYear=" + releaseYear +
                ", imgUrl='" + imgUrl + '\'' +
                ", lengthInMinutes=" + lengthInMinutes +
                ", rating=" + rating +
                '}';
    }
}
