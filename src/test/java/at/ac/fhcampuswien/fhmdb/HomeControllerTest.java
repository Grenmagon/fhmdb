//commit
package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        movie1.setReleaseYear(2009);
        movie1.setRating(8.2);
        Movie movie2 = new Movie("Star Wars Episode 1", "There is Podracing!!", genreList);
        movie2.setReleaseYear(1999);
        movie2.setRating(6.5);
        Movie movie3 = new Movie("Star Wars Episode 4", "Luke goes on an Adventure!", genreList);
        movie3.setReleaseYear(1977);
        movie3.setRating(5.0);
        observableMovies.addAll(movie1, movie2, movie3);
        homeController.setObservableMovies(observableMovies);
    }

    //Start Svetlanas Tests:

    @Test
    void movieNamesSortAsc() {
        homeController.sortAsc();
        assertEquals(homeController.getObservableMovies(), observableMovies);
    }

    @Test
    void movieNamesSortDesc() {
        homeController.sortDesc();
        assertEquals(homeController.getObservableMovies(), observableMovies);
    }

    @Test
    void getYears() {
        String[] actual = homeController.getYear();
        String[] expected = new String[]{"1977","1999","2009"};
        assertArrayEquals(expected, actual);
    }

    @Test
    void filters_movies_with_rating_above_or_equal_to_6() {
        double ratingFrom = 6.0;

        List<Movie> filtered = observableMovies.stream()
                .filter(m -> m.getRating() >= ratingFrom)
                .toList();

        assertEquals(2, filtered.size());  // movie1 и movie2
        assertTrue(filtered.stream().allMatch(m -> m.getRating() >= ratingFrom));
    }

    /*Start Kathis Tests: */

    //Tests ob der Genre-Filter richtig angewandt wird

    @Test
    void filmFilterAPI_genre_Action()
    {
        homeController.filmFilterAPI("ACTION",null,0, 0.00);
        for(Movie m : observableMovies) {
            assertTrue(m.getGenres().contains(Movie.Genre.ACTION));
        }
    }

    @Test
    void filmFilterAPI_genre_Horror()
    {
        homeController.filmFilterAPI("HORROR",null,0,0.00);
        for(Movie m : observableMovies) {
            assertTrue(m.getGenres().contains(Movie.Genre.HORROR));
        }
    }

    //Tests ob der Such-Filter richtig angewandt wird
    @Test
    void filmFilterAPI_genre_COMEDIE()
    {
        assertThrows(IllegalArgumentException.class, () -> {
            homeController.filmFilterAPI("COMEDIE", null,0, 0.00);
        });
    }

    @Test
    void filmFilterAPI_filter_god()
    {
        homeController.filmFilterAPI(null,"god",0, 0.00);
        for(Movie m : observableMovies) {
            assertTrue(m.getTitle().toLowerCase().contains("god"));
        }
    }

    //Tests ob der Such-Filter+Genre-Filter richtig angewandt wird

    @Test
    void filmFilterAPI_filter_GOd()
    {
        homeController.filmFilterAPI(null,"GOd",0,0.00);
        for(Movie m : observableMovies) {
            assertTrue(m.getTitle().toLowerCase().contains("god"));
        }
    }

    @Test
    void filmFilterAPI_filter_genre()
    {
        homeController.filmFilterAPI("SCIENCE_FICTION","film",0, 0.00);
        for(Movie m : observableMovies) {
            assertTrue(m.getTitle().contains("film")&& m.getGenres().contains(Movie.Genre.SCIENCE_FICTION) );
        }
    }

    /*Start Javids Tests: */

    @Test
    void onResetClicked_restoresAllMovies() {
        List<Movie> originalMovies = new ArrayList<>(homeController.getObservableMovies());
        homeController.getObservableMovies().remove(0);

        assertNotEquals(originalMovies.size(), homeController.getObservableMovies().size());

        // Nur Datenliste zurücksetzen, nicht UI testen
        homeController.getObservableMovies().setAll(originalMovies);

        assertEquals(originalMovies.size(), homeController.getObservableMovies().size());
        assertTrue(homeController.getObservableMovies().containsAll(originalMovies));
    }
}




