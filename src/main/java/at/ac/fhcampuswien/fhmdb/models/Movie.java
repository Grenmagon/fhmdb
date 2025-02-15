package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String description;
    // TODO add more properties here

    public Movie(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        // TODO add some dummy data here
        movies.add(new Movie("Avatar", "Film about the Aliens and not the bad one"));
        movies.add(new Movie("Star Wars Episode 1", "There is Podracing!!"));
        movies.add(new Movie("Star Wars Episode 4", "Luke goes on an Adventure!"));

        return movies;
    }
}
