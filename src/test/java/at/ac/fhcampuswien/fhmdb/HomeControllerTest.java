//commit
package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.WatchListMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchListRepository;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HomeControllerTest {

    private HomeController homeController;
    private ObservableList<Movie> observableMovies;
    WatchListRepository mockWatchListRepository;

    @BeforeEach
        // set for each test below
    void setUp() {
        homeController = new HomeController();
        observableMovies = FXCollections.observableArrayList(); //  automatically updates corresponding UI elements when underlying data changes
        List<Movie.Genre> genreList = new ArrayList<>();
        genreList.add(Movie.Genre.ACTION);
        genreList.add(Movie.Genre.SCIENCE_FICTION);
        Movie movie1 = new Movie("Avatar", "Film about the Aliens and not the bad one", genreList);
        movie1.setId("1");
        movie1.setReleaseYear(2009);
        movie1.setRating(8.2);
        Movie movie2 = new Movie("Star Wars Episode 1", "There is Podracing!!", genreList);
        movie2.setId("2");
        movie2.setReleaseYear(1999);
        movie2.setRating(6.5);
        Movie movie3 = new Movie("Star Wars Episode 4", "Luke goes on an Adventure!", genreList);
        movie3.setId("3");
        movie3.setReleaseYear(1977);
        movie3.setRating(5.0);
        observableMovies.addAll(movie1, movie2, movie3);
        homeController.setAllMovies(observableMovies);
        homeController.setObservableMovies(observableMovies);
        //homeController.errorLabel = new Label();  // manually inject Label for test
        //homeController.toggleViewBtn = new com.jfoenix.controls.JFXButton();
        //homeController.allMovies = FXCollections.observableArrayList();
        //homeController.errorLabel = new Label();

        //mock the WatchListRepository, because we don't want to hit a real database during a unit test

        mockWatchListRepository = mock(WatchListRepository.class);
        homeController.setWatchListRepository( mockWatchListRepository);  // inject the mock manually
        // Setup movies
        /*
        homeController.setAllMovies( Arrays.asList(
                new Movie("Movie 1", "Description 1", List.of()),
                new Movie("Movie 2", "Description 2", List.of())
        ));

         */


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
        String[] actual = homeController.getYears();
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

    /* new Tests Kathi for Exercise three*/

    /*
    //Testing the error label
    @Test
    void testShowError_setsErrorMessageAndVisible() {
        String errorMessage = "An error occurred!";

        homeController.showError(errorMessage, "an unnokn error occured", new Exception());

        assertEquals(errorMessage, homeController.errorLabel.getText()); //Check if error label shows correct text
        assertTrue(homeController.errorLabel.isVisible()); //Check if error label is visible
    }
*/
    //Testing on TogleSwitched
/*
    //If showingWatchlist becomes true, button text becomes "Back Home"
    @Test
    void testOnToggleViewClicked_switchesToWatchlist() {
        // Initially showingWatchlist is false

        homeController.onToggleViewClicked();

        assertTrue(homeController.showingWatchlist);
        assertEquals("Back Home", homeController.toggleViewBtn.getText());
    }
*/
    //If showingWatchlist becomes false, button text becomes "To Watchlist"

    /*
    @Test
    void testOnToggleViewClicked_switchesToHome() {
        // Set it to true first
        homeController.showingWatchlist = true;

        homeController.onToggleViewClicked();

        assertFalse(homeController.showingWatchlist);
        assertEquals("To Watchlist", homeController.toggleViewBtn.getText());
    }
    */

    //test three cases: Normal case where one movie matches,Empty watchlist case,Exception case (if database throws an error)


    @Test
    void testFillListWithWatchlist_successfullyFiltersMovies() throws SQLException {
        // Arrange
        WatchListMovieEntity entity = new WatchListMovieEntity();
        entity.setApiId("1");  // only Movie 1 is in the watchlist
        when(mockWatchListRepository.getWatchList()).thenReturn(List.of(entity));

        // Act
        homeController.fillListWithWatchlist();

        // Assert
        ObservableList<Movie> result = homeController.getObservableMovies();
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Avatar", result.get(0).getTitle());
    }

    @Test
    void testFillListWithWatchlist_emptyWatchlist() throws SQLException {
        // Arrange
        when(mockWatchListRepository.getWatchList()).thenReturn(List.of());

        // Act
        homeController.fillListWithWatchlist();

        // Assert
        ObservableList<Movie> result = homeController.getObservableMovies();
        assertTrue(result.isEmpty());
    }

    @Test
    void testFillListWithWatchlist_whenSQLExceptionThrown() throws SQLException {
        // Arrange
        when(mockWatchListRepository.getWatchList()).thenThrow(new SQLException("DB Error"));

        // Act & Assert
        assertThrows(SQLException.class, () -> homeController.fillListWithWatchlist());
    }


//If showingWatchlist == false, it should reset observableMovies to the full movie list
    /*
    @Test
    void testOnResetClicked_WhenNotShowingWatchlist() {
        // Arrange
        homeController.showingWatchlist = false;
        homeController.getObservableMovies().clear();

        // Act
        homeController.onResetClicked();

        // Assert
        assertEquals(homeController.getAllMovies().size(), homeController.getObservableMovies().size());
        assertTrue(homeController.getObservableMovies().containsAll(homeController.getAllMovies()));
    }

     */

    //If showingWatchlist == true, it should call fillListWithWatchlist()
/*
    @Test
    void testOnResetClicked_WhenShowingWatchlist() throws SQLException {
        // Arrange
        homeController = spy(homeController);  // Spy to verify method calls
        homeController.showingWatchlist = true;

        doNothing().when(homeController).fillListWithWatchlist();

        // Act
        homeController.onResetClicked();

        // Assert
        verify(homeController, times(1)).fillListWithWatchlist();
    }

 */

    //refillMovieDb->>Fills observableMovies correctly

    /*
    @Test
    void testRefillMovieDb_Successful() throws Exception {
        // Arrange
        HomeController controller = spy(new HomeController());
        controller.setObservableMovies(FXCollections.observableArrayList());
        //controller.releaseYearComboBox = new ComboBox<>();
        //controller.releaseYearComboBox.getItems().setAll(homeController.getYears());

        List<Movie> mockMovies = List.of(
                new Movie("Mock Movie 1", "Description", List.of(Movie.Genre.ACTION, Movie.Genre.DRAMA))
        );

        // Mock static methods
        try (MockedStatic<Movie> movieStatic = mockStatic(Movie.class)) {
            movieStatic.when(Movie::loadFromApiToDB).thenAnswer(invocation -> null); //loadFromApiToDB does nothing
            movieStatic.when(Movie::getMoviesFromDB).thenReturn(mockMovies);

            // Act
            controller.refillMovieDb();
        }

        // Assert
        assertEquals(1, controller.getObservableMovies().size());
        assertEquals("Mock Movie 1", controller.getObservableMovies().get(0).getTitle());
    }
     */

    //RemoveFromWatchlist->>Removes from DB, fills watchlist, shows success
    @Test
    void testRemoveFromWatchlist_Success() throws Exception {
        // Arrange
        HomeController controller = spy(new HomeController());
        WatchListRepository watchListRepositoryMock = mock(WatchListRepository.class);
        controller.setWatchListRepository(watchListRepositoryMock);

        Movie testMovie = new Movie( "Test Movie", "Desc", List.of());

        // IMPORTANT: Stub getWatchListRepository() to return your mock
        doReturn(watchListRepositoryMock).when(controller).getWatchListRepository();

        //doNothing().when(controller.getWatchListRepository()).removeFromWatchList(anyString());
        doNothing().when(controller).fillListWithWatchlist();
        doNothing().when(controller).showSuccessMessage(anyString());

        // Act
        controller.removeFromWatchlist(testMovie);

        // Assert
        verify(controller.getWatchListRepository(), times(1)).removeFromWatchList(testMovie.getId());
        verify(controller, times(1)).fillListWithWatchlist();
        //verify(controller, times(1)).showSuccessMessage(contains("removed"));
    }

    //addToWatchlist-->>Adds to DB, shows success message
    @Test
    void testAddToWatchlist_SuccessfulAddition() throws Exception {
        // Arrange
        HomeController controller = spy(new HomeController());

        Movie testMovie = new Movie("Test Movie", "Desc", List.of());
        testMovie.setId("1");

        WatchListRepository mockRepository = mock(WatchListRepository.class);
        controller.setWatchListRepository( mockRepository);

        when(mockRepository.addToWatchList(anyString())).thenReturn(1);

        doNothing().when(controller).showSuccessMessage(anyString());
        doNothing().when(controller).showError(anyString(),anyString(),any());

        // Act
        controller.addToWatchlist(testMovie);

        // Assert
        verify(mockRepository, times(1)).addToWatchList(testMovie.getId());
        //verify(controller, times(1)).showSuccessMessage(contains("Added"));
    }

    //onDetailButtonClicked-->>Opens a detail window without exception

    /*
    @Test
    void testOnDetailButtonClicked_DoesNotThrow() {
        // Arrange
        HomeController controller = new HomeController();
        Movie movie = new Movie("Test Movie", "A test description", List.of());

        // Act & Assert
        assertDoesNotThrow(() -> controller.onDetailButtonClicked(movie));
    }

     */












}




