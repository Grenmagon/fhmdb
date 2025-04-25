package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.MovieRepository;
import at.ac.fhcampuswien.fhmdb.database.WatchListMovieEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchListRepository;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.MovieAPIException;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView<Movie> movieListView; // Typisierung hinzugefügt

    @FXML
    public JFXComboBox<String> genreComboBox;// Typisierung hinzugefügt

    @FXML
    public JFXComboBox<String> releaseYearComboBox;// Typisierung hinzugefügt

    @FXML
    public JFXComboBox<String> ratingComboBox;// Typisierung hinzugefügt

    @FXML
    public JFXButton sortBtn;

    @FXML
    public JFXButton resetBtn;

    @FXML
    public JFXButton fillDB;

    @FXML
    private JFXButton toggleViewBtn;

    @FXML
    private Label errorLabel;



    public boolean showingWatchlist = false;

    private WatchListRepository watchListRepository;
    private MovieRepository movieRepository;
    private List<Movie> allMovies = new ArrayList<>();

    @FXML
    private void onToggleViewClicked() {
        showingWatchlist = !showingWatchlist;

        if (showingWatchlist) {
            toggleViewBtn.setText("Back Home");
            try {
                fillListWithWatchlist();
                clearFilter();
            } catch (Exception e) {
                showError("Watchlist-Error", "Error loading Watchlist", e);
            }
        } else {
            toggleViewBtn.setText("To Watchlist");
            observableMovies.setAll(allMovies);  // Zeige wieder alle Filme
            clearFilter();
        }

    }

    public void fillListWithWatchlist() throws SQLException {
        List<WatchListMovieEntity> watchListIds = watchListRepository.getWatchList();
        Set<String> watchlistApiIds = watchListIds.stream()
                .map(WatchListMovieEntity::getApiId)
                .collect(Collectors.toSet());

        List<Movie> watchlistedMovies = allMovies.stream()
                .filter(movie -> watchlistApiIds.contains(movie.getId()))
                .toList();
        observableMovies.setAll(watchlistedMovies);
    }


    public void setObservableMovies(ObservableList<Movie> observableMovies) {
        this.observableMovies = observableMovies;
    }

    private ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            watchListRepository = new WatchListRepository(/*Database.getWatchListDao()*/);
            movieRepository = new MovieRepository();
        } catch (SQLException e) {
            showError("DB Error", "Failed to initialize WatchListRepository", e);
        }
        List<Movie> initList = null;
        //= Movie.allMoviesAPI();
        try {
            initList = Movie.getMoviesFromDB();
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            showError("DB Error", "Es ist ein Fehler beim laden der Filme aufgetreten", e);
        }

        if (initList != null)
            allMovies = new ArrayList<>(initList);
        observableMovies.setAll(allMovies);
        // observableMovies.addAll(initList);         // add dummy data to observable list
/*
        ClickEventHandler<Movie> watchlistBtnClicked = (Movie movie) -> {
            System.out.println("hmm:" + showingWatchlist);

            try {
                int result = watchListRepository.addToWatchList(movie.getId());
                if (result == 1) {
                    System.out.println("Added to Watchlist: " + movie.getTitle());
                } else {
                    System.out.println("Already in Watchlist: " + movie.getTitle());
                }
            } catch (Exception e) {
                showError("DB Error", "Could not add movie to watchlist", e);
            }
        };

 */

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell(this /*, watchlistBtnClicked*/)); // use custom cell factory to display data

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

    public void watchlistBtnClicked_Home()
    {

    }

    public void watchlistBtnClicked_Watchlist()
    {

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
        try {
            ergebnisJson = MovieAPI.getMoviesFilter(filter, genre, releaseYear, rating);
            observableMovies.setAll(Movie.getMoviesFromJson(ergebnisJson));
        } catch (IOException e) {
            showError("IOError", "Fehler beim Laden der Filme", e);
        } catch (MovieAPIException e) {
            showError("MovieAPI Error", "Fehler beim Laden der Filme", e);
        }

    }

    public void filterMoviesLocally(String genreString, String searchText, int releaseYear, double minRating) {
        // parse genre if specified
        final Movie.Genre genre = (genreString != null && !genreString.isEmpty())
                ? Movie.Genre.valueOf(genreString)
                : null;

        // build filtered list
        List<Movie> toFilter = new ArrayList<>();
        if (!showingWatchlist)
            toFilter = new ArrayList<>(allMovies);
        else
        {
            try {
                fillListWithWatchlist();
                toFilter = new ArrayList<>(observableMovies);
            } catch (SQLException e) {
                showError("Watchlist-Error", "Error loading Watchlist", e);
            }
        }
        List<Movie> filtered = toFilter.stream()
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
        clearFilter();

        // Restore the full, unfiltered movie list from our in-memory copy
        if (!showingWatchlist)
            observableMovies.setAll(allMovies);
        else {
            try {
                fillListWithWatchlist();
            } catch (SQLException e) {
                showError("Watchlist-Error", "Error loading Watchlist", e);
            }
        }
    }

    private void clearFilter() {
        //  Clear the search text field so the user sees an empty query box
        searchField.clear();
        // Reset all combo-box filters (genre, year, rating) back to “none selected”
        Stream.of(genreComboBox, releaseYearComboBox, ratingComboBox)
                .forEach(cb -> cb.getSelectionModel().clearSelection());
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

    public static void showError(String title, String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public void showSuccessMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: green;");
        errorLabel.setVisible(true);
    }

    private void refillMovieDb() {
        try {
            Movie.loadFromApiToDB();
            List<Movie> fresh = Movie.getMoviesFromDB();
            allMovies = new ArrayList<>(fresh);
        } catch (SQLException e) {
            showError("DB Error", "Es ist ein Fehler beim reloaden der Filme aufgetreten", e);
        } catch (IOException e) {
            showError("IOError", "Fehler beim Laden der Filme", e);
        } catch (MovieAPIException e) {
            showError("MovieAPI Error", "Fehler beim Laden der Filme", e);
        }
        observableMovies.setAll(allMovies);
        releaseYearComboBox.getItems().setAll(getYears());
    }


    public void removeFromWatchlist(Movie movie){
        try {
            // Entferne den Film aus der Watchlist, indem die Movie-ID verwendet wird
            watchListRepository.removeFromWatchList(movie.getId());
            fillListWithWatchlist();
            showSuccessMessage("Film removed from Watchlist: " + movie.getTitle());
        } catch (Exception e) {
            showError("DB Error", "Could not remove movie from watchlist", e);
        }
    }

    public void addToWatchlist(Movie movie)
    {
        try {
            WatchListRepository watchListRepository = new WatchListRepository();
            int result = watchListRepository.addToWatchList(movie.getId());
            if (result == 1) {
                showSuccessMessage("Added to Watchlist: " + movie.getTitle());
            } else {
                showSuccessMessage("Already in Watchlist: " + movie.getTitle());
            }
        } catch (Exception e) {
            showError("DB Error", "Could not add movie to watchlist", e);
        }
    }

    public void onDetailButtonClicked(Movie movie) {
        // Create a new Stage (window) for showing movie details
        Stage detailsStage = new Stage();
        detailsStage.setTitle("Movie Details");

        // Create a VBox layout to display the movie details
        VBox detailsLayout = new VBox(15);
        detailsLayout.setStyle("-fx-background-color: #333333; -fx-padding: 20px; -fx-border-radius: 10px;");

        // Create and style the title label
        Label titleLabel = new Label("Title: " + movie.getTitle());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #FFD700;");

        // Create and style the description label
        Label descriptionLabel = new Label("Description: " + (movie.getDescription() != null ? movie.getDescription() : "No description available"));
        descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #FFFFFF;");

        // Create and style the genres label
        Label genresLabel = new Label("Genres: " + movie.getGenresString());
        genresLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #FFFFFF;");

        // Add the movie image (if available)
        String imgUrl = movie.getImgUrl();
        String imageUrl = (imgUrl != null && !imgUrl.isEmpty()) ? imgUrl : "default_image_url.jpg"; // Use default if null or empty
        Image image = new Image(imgUrl);  // Load the image from URL
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);  // Set the image size
        imageView.setFitHeight(300);
        imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.7), 10, 0.3, 0, 4);");


        // Add all the components to the layout
        detailsLayout.getChildren().addAll(imageView, titleLabel, descriptionLabel, genresLabel);

        // Create and set the Scene for the details window
        Scene detailsScene = new Scene(detailsLayout, 400, 500);  // Adjust the size as needed
        detailsStage.setScene(detailsScene);

        // Show the details window
        detailsStage.show();
    }

}