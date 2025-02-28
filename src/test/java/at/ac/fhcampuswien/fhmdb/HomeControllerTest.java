package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HomeControllerTest {

    private HomeController homeController;
    private ObservableList<Movie> observableMovies;

    @BeforeEach
        // set for each test below
    void setUp() {
        homeController = new HomeController();
        observableMovies = FXCollections.observableArrayList(); //  automatically updates corresponding UI elements when underlying data changes
        List<Movie.Genre> genreList = new ArrayList<>();
        genreList.add(Movie.Genre.ACTION);
        genreList.add(Movie.Genre.SCIENCE_FICTION);
        Movie movie1 = new Movie("Avatar", "Film about the Aliens and not the bad one", genreList);
        Movie movie2 = new Movie("Star Wars Episode 1", "There is Podracing!!", genreList);
        Movie movie3 = new Movie("Star Wars Episode 4", "Luke goes on an Adventure!", genreList);
        observableMovies.addAll(movie1, movie2, movie3);
        homeController.setObservableMovies(observableMovies);
    }

    //Start Svetlanas Tests:

    @Test
    void movieNamesSortAsc() {
        homeController.sortAsc();
        assertEquals("Avatar", observableMovies.get(0).getTitle());
        assertEquals("Star Wars Episode 1", observableMovies.get(1).getTitle());
        assertEquals("Star Wars Episode 4", observableMovies.get(2).getTitle());

    }

    @Test
    void movieNamesSortDesc() {
        homeController.sortDesc();
        assertEquals("Star Wars Episode 4", observableMovies.get(0).getTitle());
        assertEquals("Star Wars Episode 1", observableMovies.get(1).getTitle());
        assertEquals("Avatar", observableMovies.get(2).getTitle());
    }


    /*Start Kathis Tests: */

    //Tests ob der Genre-Filter richtig angewandt wird
    @Test
    void filmFilter_Action(){
        observableMovies.setAll(Movie.initializeMovies());
        homeController.filmFilter(Movie.Genre.ACTION.name(), "");

        assertEquals("Avatar", observableMovies.get(0).getTitle());
        assertEquals("Star Wars Episode 1", observableMovies.get(1).getTitle());
        assertEquals("Star Wars Episode 4", observableMovies.get(2).getTitle());


    }

    @Test
    void filmFilter_ScienceFiction(){
        observableMovies.setAll(Movie.initializeMovies());
        homeController.filmFilter(Movie.Genre.SCIENCE_FICTION.name(), "");

        assertEquals("Avatar", observableMovies.get(0).getTitle());
        assertEquals("Star Wars Episode 1", observableMovies.get(1).getTitle());
        assertEquals("Star Wars Episode 4", observableMovies.get(2).getTitle());

    }

    @Test
    void filmFilter_Comedy(){
        observableMovies.setAll(Movie.initializeMovies());
        homeController.filmFilter(Movie.Genre.COMEDY.name(), "");

        assertEquals("The Life of Brian", observableMovies.get(0).getTitle());
        assertEquals("Finding Nemo", observableMovies.get(1).getTitle());
        assertEquals("Finding Dori", observableMovies.get(2).getTitle());

    }

    //Tests ob der Such-Filter richtig angewandt wird
    @Test
    void filmFilter_film(){
        observableMovies.setAll(Movie.initializeMovies());
        homeController.filmFilter(null, "film");

        assertEquals("Avatar", observableMovies.get(0).getTitle());
        assertEquals("The Life of Brian", observableMovies.get(1).getTitle());
        assertEquals("Finding Nemo", observableMovies.get(2).getTitle());
        assertEquals("Finding Dori", observableMovies.get(3).getTitle());

    }
    @Test
    void filmFilter_movie(){
        observableMovies.setAll(Movie.initializeMovies());
        homeController.filmFilter(null, "movie");

        assertEquals("Finding Nemo", observableMovies.get(0).getTitle());
        assertEquals("Finding Dori", observableMovies.get(1).getTitle());

    }


    //Tests ob der Such-Filter+Genre-Filter richtig angewandt wird

    @Test
    void filmFilter_film_animation(){
        observableMovies.setAll(Movie.initializeMovies());
        homeController.filmFilter(Movie.Genre.ANIMATION.name(), "film");

        assertEquals("Finding Nemo", observableMovies.get(0).getTitle());
        assertEquals("Finding Dori", observableMovies.get(1).getTitle());

    }

    @Test
    void filmFilter_an_action(){
        observableMovies.setAll(Movie.initializeMovies());
        homeController.filmFilter(Movie.Genre.ACTION.name(), "an");

        assertEquals("Avatar", observableMovies.get(0).getTitle());
        assertEquals("Star Wars Episode 4", observableMovies.get(1).getTitle());

    }

    /*Start Javids Tests: */


}




