package at.ac.fhcampuswien.fhmdb.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    private String uid;
    private String title;
    private String description;
    private List<Genre> genres;
    private int releaseYear;
    private String imgUrl;
    private int lengthInMinutes;
    private List<String> director;
    private List<String> writer;
    private List<String> cast;
    private double rating;

    // DONE add more properties here

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
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

    public List<String> getDirector()
    {
        return director;
    }

    public void setDirector(List<String> director)
    {
        this.director = director;
    }

    public List<String> getWriter()
    {
        return writer;
    }

    public void setWriter(List<String> writer)
    {
        this.writer = writer;
    }

    public List<String> getCast()
    {
        return cast;
    }

    public void setCast(List<String> cast)
    {
        this.cast = cast;
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


    public static List<Movie> allMoviesAPI(){

        String json = MovieAPI.getMoviesFilter(null, null, 0, 0);
        //System.out.println(json);
        return  getMoviesFromJson(json);
    }


    public static List<Movie> getMoviesFromJson(String json)
    {
        Gson gson = new Gson();
        Type movieListType = new TypeToken<List<Movie>>() {}.getType();
        List<Movie> movies = gson.fromJson(json, movieListType);
        /*
        for (Movie m: movies)
            System.out.println(m.title);
         */
        return movies;
    }

}
