package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

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
    public JFXButton sortBtn;

    @FXML
    public JFXButton resetBtn;



    public void setObservableMovies(ObservableList<Movie> observableMovies) {
        this.observableMovies = observableMovies;

    }

     private ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Movie> initList = Movie.allMoviesAPI();
        if (initList != null)
            observableMovies.addAll(initList);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        // DONE add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Movie.getGenreStringArray());

        // DONE add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here
        searchBtn.setOnAction(actionEvent -> filmFilterAPI((String) genreComboBox.getValue(), searchField.getCharacters().toString())); // TODO: releaseYear und ratingFrom hinzufügen



        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if(sortBtn.getText().equals("Sort (asc)")) {
                // DONE sort observableMovies ascending
                sortDesc();
                sortBtn.setText("Sort (desc)");
            } else {
                // DONE sort observableMovies descending
                sortAsc();
                sortBtn.setText("Sort (asc)");
            }
        });

        resetBtn.setOnAction(actionEvent -> onResetClicked());

    }
     public void sortAsc (){
         observableMovies .sort(null);
     }
    public void sortDesc (){
        observableMovies.sort(Collections.reverseOrder());
    }



    public void filmFilterAPI(String genreString, String filter) {  // TODO: releaseYear und ratingFrom hinzufügen
        //filter = filter.toLowerCase();
        Movie.Genre genre = null;
        if (genreString != null && !genreString.isEmpty())
            genre = Movie.Genre.valueOf(genreString); //String von Genre in Enum umwandeln
        String ergebnisJson = MovieAPI.getMoviesFilter(filter, genre, 0,0);
        if (!ergebnisJson.equals(MovieAPI.ERROR))
            observableMovies.setAll(Movie.getMoviesFromJson(ergebnisJson));

    }



    @FXML
    public void onResetClicked() {
        searchField.clear();  // Suchfeld leeren
        genreComboBox.getSelectionModel().clearSelection();  // Genre-Filter zurücksetzen
        observableMovies.setAll(Movie.allMoviesAPI());  // Komplette Film-Liste wiederherstellen
    }

    public ObservableList<Movie> getObservableMovies() {
        return observableMovies;
    }


}