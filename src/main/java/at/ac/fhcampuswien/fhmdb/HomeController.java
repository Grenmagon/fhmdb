package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.Database;
import at.ac.fhcampuswien.fhmdb.database.WatchListRepository;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.MovieAPIException;
import at.ac.fhcampuswien.fhmdb.ui.ClickEventHandler;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXComboBox releaseYearComboBox;

    @FXML
    public JFXComboBox ratingComboBox;

    @FXML
    public JFXButton sortBtn;

    @FXML
    public JFXButton resetBtn;

    @FXML
    public JFXButton fillDB;

    private WatchListRepository watchListRepository;
    private List<Movie> allMovies = new ArrayList<>();

    public void setObservableMovies(ObservableList<Movie> observableMovies) {
        this.observableMovies = observableMovies;
    }

    private ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            watchListRepository = new WatchListRepository(Database.getWatchListDao());
        } catch (SQLException e) {
            showError("DB Error", "Failed to initialize WatchListRepository", e);
        }
       List<Movie> initList = null;
       //= Movie.allMoviesAPI();
        try
        {
            initList = Movie.getMoviesFromDB();
        }
        catch (SQLException e)
        {
            //throw new RuntimeException(e);
            showError("DB Error", "Es ist ein Fehler beim laden der Filme aufgetreten",e);
        }

        if (initList != null)
            allMovies = new ArrayList<>(initList);
            observableMovies.setAll(allMovies);
        // observableMovies.addAll(initList);         // add dummy data to observable list

        ClickEventHandler<Movie> onAddToWatchlistClicked = (Movie movie) -> {
            try {
                int result = watchListRepository.addToWatchList(movie.getId());
                if (result == 1) {
                    System.out.println("Added to Watchlist: " + movie.getTitle());
                } else {
                    System.out.println("Already in Watchlist: " + movie.getTitle());
                }
            } catch (SQLException e) {
                showError("DB Error", "Could not add movie to watchlist", e);
            }
        };

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell(onAddToWatchlistClicked)); // use custom cell factory to display data

        // DONE add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Movie.getGenreStringArray());

        // DONE add event handlers to buttons and call the regarding methods
        releaseYearComboBox.setPromptText("Filter by Release Year");
        releaseYearComboBox.getItems().addAll(getYears());


        ratingComboBox.setPromptText("Filter by Rating");
        ratingComboBox.getItems().addAll(Movie.getRatingStringArray());

        searchBtn.setOnAction(actionEvent -> filterMoviesLocally(
                genreComboBox.getValue() == null ? null : genreComboBox.getValue().toString(),
                searchField.getText(),
                releaseYearComboBox.getValue() == null ? 0 : Integer.parseInt(releaseYearComboBox.getValue().toString()),
                ratingComboBox.getValue() == null ? 0.0 : Double.parseDouble(ratingComboBox.getValue().toString())
        ));


        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
                // DONE sort observableMovies ascending
            if (sortBtn.getText().equals("Sort (asc)")) {
                // Done
                sortDesc();
                sortBtn.setText("Sort (desc)");
            } else {
                // DONE sort observableMovies descending
                sortAsc();
                sortBtn.setText("Sort (asc)");
            }
        });

        resetBtn.setOnAction(actionEvent -> onResetClicked());

        fillDB.setOnAction(actionEvent -> refillMovieDb());
    }

    public void sortAsc() {
        observableMovies.sort(null);
    }

    public void sortDesc() {
        observableMovies.sort(Collections.reverseOrder());
    }

    public String[] getYears() {
        return observableMovies.stream().map(movie -> String.valueOf(movie.getReleaseYear())).distinct().sorted().toArray(String[]::new);
    }

    public void filmFilterAPI(String genreString, String filter, int releaseYear, double rating) {
        Movie.Genre genre = null;
        if (genreString != null && !genreString.isEmpty())
            genre = Movie.Genre.valueOf(genreString); //String von Genre in Enum umwandeln
        String ergebnisJson = null;
        try
        {
            ergebnisJson = MovieAPI.getMoviesFilter(filter, genre, releaseYear, rating);
            observableMovies.setAll(Movie.getMoviesFromJson(ergebnisJson));
        }
        catch (IOException e)
        {
            showError("IOError", "Fehler beim Laden der Filme", e);
        }
        catch (MovieAPIException e)
        {
            showError("MovieAPI Error", "Fehler beim Laden der Filme", e);
        }

    }
    public void filterMoviesLocally(String genreString, String searchText, int releaseYear, double minRating) {
        // parse genre if specified
        final Movie.Genre genre = (genreString != null && !genreString.isEmpty())
                ? Movie.Genre.valueOf(genreString)
                : null;

        // build filtered list
        List<Movie> filtered = allMovies.stream()
                .filter(m -> genre == null || m.getGenres().contains(genre))
                .filter(m -> searchText == null || searchText.isBlank()
                        || m.getTitle().toLowerCase().contains(searchText.toLowerCase())
                        || (m.getDescription() != null && m.getDescription().toLowerCase().contains(searchText.toLowerCase())))
                .filter(m -> releaseYear <= 0 || m.getReleaseYear() == releaseYear)
                .filter(m -> minRating <= 0.0 || m.getRating() >= minRating)
                .collect(Collectors.toList());

        // update UI list
        observableMovies.setAll(filtered);
    }

    @FXML
    public void onResetClicked() {
        //  Clear the search text field so the user sees an empty query box
        searchField.clear();

        // Reset all combo-box filters (genre, year, rating) back to “none selected”
        Stream.of(genreComboBox, releaseYearComboBox, ratingComboBox)
                .forEach(cb -> cb.getSelectionModel().clearSelection());

        // Restore the full, unfiltered movie list from our in-memory copy
        observableMovies.setAll(allMovies);
    }
        /*searchField.clear();  // Suchfeld leeren
        Stream.of(genreComboBox, releaseYearComboBox, ratingComboBox)
                .forEach( comboBox ->comboBox.getSelectionModel().clearSelection());  // Genre-Filter zurücksetzen
        try
        {
            observableMovies.setAll(allMovies);  // Komplette Film-Liste wiederherstellen
        }
        catch (IOException e)
        {
            showError("IOError", "Fehler beim Laden der Filme", e);
        }
        catch (MovieAPIException e)
        {
            showError("MovieAPI Error", "Fehler beim Laden der Filme", e);
        }
    }*/

    public ObservableList<Movie> getObservableMovies() {
        return observableMovies;
    }

    public static void showError(String title, String message, Exception e)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    private void refillMovieDb()
    {
        try
        {
            Movie.loadFromApiToDB();
            List<Movie> fresh = Movie.getMoviesFromDB();
            allMovies = new ArrayList<>(fresh);
            observableMovies.setAll(allMovies);
            releaseYearComboBox.getItems().setAll(getYears());
        }
        catch (SQLException e)
        {
            showError("DB Error", "Es ist ein Fehler beim reloaden der Filme aufgetreten",e);
        }
        catch (IOException e)
        {
            showError("IOError", "Fehler beim Laden der Filme", e);
        }
        catch (MovieAPIException e)
        {
            showError("MovieAPI Error", "Fehler beim Laden der Filme", e);
        }
    }

}



