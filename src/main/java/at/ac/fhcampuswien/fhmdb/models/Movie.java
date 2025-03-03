package at.ac.fhcampuswien.fhmdb.models;

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

    private String title;
    private String description;
    private List<Genre> genres;
    // TODO add more properties here

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {return genres;}

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


    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        // TODO add some dummy data here
        List<Genre> genreList;
        Collections.addAll(genreList = new ArrayList<Genre>(),Genre.SCIENCE_FICTION, Genre.ACTION);
        movies.add(new Movie("Avatar", "Film about the Aliens and not the bad one", genreList ));
        Collections.addAll(genreList = new ArrayList<Genre>(),Genre.SCIENCE_FICTION, Genre.ACTION);
        movies.add(new Movie("Star Wars Episode 1", "There is Podracing!!", genreList));
        Collections.addAll(genreList = new ArrayList<Genre>(),Genre.SCIENCE_FICTION, Genre.ACTION);
        movies.add(new Movie("Star Wars Episode 4", "Luke goes on an Adventure!", genreList));
        Collections.addAll(genreList = new ArrayList<Genre>(),Genre.COMEDY, Genre.DRAMA, Genre.BIOGRAPHY);
        movies.add(new Movie("The Life of Brian", "Classic film from Monty Python", genreList));
        //added some cases for filter options testing
        Collections.addAll(genreList = new ArrayList<Genre>(),Genre.FAMILY, Genre.DRAMA, Genre.ANIMATION,Genre.COMEDY);
        movies.add(new Movie("Finding Nemo", "movie about an lost fish namend nemo, great family film", genreList ));
        Collections.addAll(genreList = new ArrayList<Genre>(),Genre.FAMILY, Genre.DRAMA, Genre.ANIMATION,Genre.COMEDY);
        movies.add(new Movie("Finding Dori", "movie about another lost fish namend dori, great family film", genreList ));

        return movies;
    }
}
