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
    private ObservableList<Movie> newobservableMovies;

    public static List<Movie> initializeMoviesTest(){
        List<Movie> movies = new ArrayList<>();
        // DONE add some dummy data here
        List<Movie.Genre> genreList;
        Collections.addAll(genreList = new ArrayList<Movie.Genre>(), Movie.Genre.SCIENCE_FICTION, Movie.Genre.ACTION);
        movies.add(new Movie("Avatar", "Film about the Aliens and not the bad one", genreList ));
        Collections.addAll(genreList = new ArrayList<Movie.Genre>(), Movie.Genre.SCIENCE_FICTION, Movie.Genre.ACTION);
        movies.add(new Movie("Star Wars Episode 1", "There is Podracing!!", genreList));
        Collections.addAll(genreList = new ArrayList<Movie.Genre>(), Movie.Genre.SCIENCE_FICTION, Movie.Genre.ACTION);
        movies.add(new Movie("Star Wars Episode 4", "Luke goes on an Adventure!", genreList));
        Collections.addAll(genreList = new ArrayList<Movie.Genre>(), Movie.Genre.COMEDY, Movie.Genre.DRAMA, Movie.Genre.BIOGRAPHY);
        movies.add(new Movie("The Life of Brian", "Classic film from Monty Python", genreList));
        //added some cases for filter options testing
        Collections.addAll(genreList = new ArrayList<Movie.Genre>(), Movie.Genre.FAMILY, Movie.Genre.DRAMA, Movie.Genre.ANIMATION, Movie.Genre.COMEDY);
        movies.add(new Movie("Finding Nemo", "movie about an lost fish namend nemo, great family film", genreList ));
        Collections.addAll(genreList = new ArrayList<Movie.Genre>(), Movie.Genre.FAMILY, Movie.Genre.DRAMA, Movie.Genre.ANIMATION, Movie.Genre.COMEDY);
        movies.add(new Movie("Finding Dori", "movie about another lost fish namend dori, great family film", genreList ));
        return movies;
    }

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

    @Test
    void filmFilterAPI_genre_Action()
    {
        homeController.filmFilterAPI("ACTION",null);
        for(Movie m : observableMovies) {
            assertTrue(m.getGenres().contains(Movie.Genre.ACTION));
        }

    }

    @Test
    void filmFilterAPI_genre_Horror()
    {
        homeController.filmFilterAPI("HORROR",null);
        for(Movie m : observableMovies) {
            assertTrue(m.getGenres().contains(Movie.Genre.HORROR));
        }

    }

    @Test
    void filmFilterAPI_genre_COMEDIE()
    {

        assertThrows(IllegalArgumentException.class, () -> {
            homeController.filmFilterAPI("COMEDIE", null);
        });

    }

    @Test
    void filmFilterAPI_filter_god()
    {
        homeController.filmFilterAPI(null,"god");
        for(Movie m : observableMovies) {
            assertTrue(m.getTitle().toLowerCase().contains("god"));
        }

    }

    @Test
    void filmFilterAPI_filter_GOd()
    {
        homeController.filmFilterAPI(null,"GOd");
        for(Movie m : observableMovies) {
            assertTrue(m.getTitle().toLowerCase().contains("god"));
        }

    }

    @Test
    void filmFilterAPI_filter_genre()
    {
        homeController.filmFilterAPI("SCIENCE_FICTION","film");
        for(Movie m : observableMovies) {
            assertTrue(m.getTitle().contains("film")&& m.getGenres().contains(Movie.Genre.SCIENCE_FICTION) );
        }

    }


    /*Start Javids Tests: */

    @Test
    void onResetClicked_restoresAllMovies() {
        // Arrange: Erstelle eine Kopie der ursprünglichen Filme, die im Controller gespeichert sind
        List<Movie> originalMovies = new ArrayList<>(observableMovies);

        // Simuliere eine Filterung durch Reduzierung der Liste
        observableMovies.remove(0);
        observableMovies.remove(0);
        assertNotEquals(originalMovies.size(), observableMovies.size()); // Überprüfen, dass sich die Liste geändert hat

        // Act: Simuliere den Reset, indem die ursprüngliche Liste wiederhergestellt wird
        homeController.getObservableMovies().setAll(originalMovies);

        // Assert: Prüfe, ob alle Filme wieder da sind
        assertEquals(originalMovies.size(), homeController.getObservableMovies().size());
        assertTrue(homeController.getObservableMovies().containsAll(originalMovies));
    }

}




