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
        Reset,
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

        return movies;
    }
}
