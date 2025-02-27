package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HomeControllerTest {
    private HomeController homeController;
    private ObservableList<Movie> observableMovies;

    @BeforeEach // set for each test below
    void setUp() {
        homeController = new HomeController();
        observableMovies = FXCollections.observableArrayList(); //  automatically updates corresponding UI elements when underlying data changes
        List<Movie.Genre> genreList = new ArrayList<>();
        genreList.add(Movie.Genre.ACTION);
        genreList.add(Movie.Genre.SCIENCE_FICTION);
        Movie movie1 = new Movie("Avatar", "Film about the Aliens and not the bad one", genreList);
        Movie movie2 = new Movie("Star Wars Episode 1", "There is Podracing!!", genreList);
        Movie movie3 = new Movie("Star Wars Episode 4", "Luke goes on an Adventure!", genreList);
        observableMovies.addAll(movie1,movie2, movie3);
        homeController.setObservableMovies(observableMovies);
    }


    @Test
    void movieNamesSortAsc() {
       homeController.sortAsc();
   assertEquals("Avatar",observableMovies.get(0).getTitle());
   assertEquals("Star Wars Episode 1",observableMovies.get(1).getTitle());
   assertEquals("Star Wars Episode 4",observableMovies.get(2).getTitle());

    }

    @Test
    void movieNamesSortDesc() {
        homeController.sortDesc();
        assertEquals("Star Wars Episode 4",observableMovies.get(0).getTitle());
        assertEquals("Star Wars Episode 1",observableMovies.get(1).getTitle());
        assertEquals("Avatar",observableMovies.get(2).getTitle());
    }

}
