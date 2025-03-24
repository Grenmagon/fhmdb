package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
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
    public JFXComboBox releaseYearComboBox;

    @FXML
    public JFXComboBox ratingComboBox;

    @FXML
    public JFXButton sortBtn;

    @FXML
    public JFXButton resetBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    public void setObservableMovies(ObservableList<Movie> observableMovies) {
        this.observableMovies = observableMovies;

    }

    private ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        // TODO add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Movie.getGenreStringArray());

        releaseYearComboBox.setPromptText("Filter by Release Year");
        releaseYearComboBox.getItems().addAll(Movie.getDecadesStringArray());


        ratingComboBox.setPromptText("Filter by Rating");
        ratingComboBox.getItems().addAll(Movie.getRatingStringArray());


        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here
        searchBtn.setOnAction(actionEvent -> filmFilter((String) genreComboBox.getValue(),
                                                               searchField.getCharacters().toString(),
                                                              (String) releaseYearComboBox.getValue(),
                                                              (Integer) ratingComboBox.getValue() ));



        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                // TODO sort observableMovies ascending
                sortDesc();
                sortBtn.setText("Sort (desc)");
            } else {
                // TODO sort observableMovies descending
                sortAsc();
                sortBtn.setText("Sort (asc)");
            }
        });

        resetBtn.setOnAction(actionEvent -> onResetClicked());

    }

    public void sortAsc() {
        observableMovies.sort(null);
    }

    public void sortDesc() {
        observableMovies.sort(Collections.reverseOrder());
    }


    /*public void filmFilter(String genre, String filter) { // gehört eigentlich private, damit wir Unittests machen können auf public gesetzt
        //String genre = (String) genreComboBox.getValue();//welches Genre haben wir gesetzt?--> für Unit Testing nach oben gesetzt
        boolean searchGenre = true;
        Movie.Genre g = Movie.Genre.ACTION;
        if (genre != null)
            g = Movie.Genre.valueOf(genre); //String von Genre in Enum umwandeln
        else
            searchGenre = false;
        //String filter = searchField.getCharacters().toString().toLowerCase();--> für Unit Testing nach oben gesetzt
        filter = filter.toLowerCase();
        //System.out.println(filter);

        //observableMovies.removeAll();
        List<Movie> filtered = new ArrayList<>();

        for (int i = 0; i < allMovies.size(); i++) {
            boolean check = true;
            Movie movie = allMovies.get(i);
            if (searchGenre && !movie.getGenres().contains(g))
                check = false;

            if (!movie.getTitle().toLowerCase().contains(filter) && !movie.getDescription().toLowerCase().contains(filter))
                check = false;

            if (check) {
                filtered.add(movie);
            }*/

    public void filmFilter(String genre, String filter, String decades, Integer rating) { // gehört eigentlich private, damit wir Unittests machen können auf public gesetzt
        //String genre = (String) genreComboBox.getValue();//welches Genre haben wir gesetzt?--> für Unit Testing nach oben gesetzt
        boolean searchGenre = true;
        boolean searchDecades = true;
        boolean searchRating = true;
        Integer d = Integer.parseInt(decades);
        Movie.Genre g = Movie.Genre.ACTION;
        if (genre != null)
            g = Movie.Genre.valueOf(genre); //String von Genre in Enum umwandeln
        else
            searchGenre = false;


        //String filter = searchField.getCharacters().toString().toLowerCase();--> für Unit Testing nach oben gesetzt
        filter = filter.toLowerCase();
        //System.out.println(filter);

        //observableMovies.removeAll();
        List<Movie> filtered = new ArrayList<>();

        for (Movie movie : allMovies) {
            boolean check = true;

            if (searchGenre && !movie.getGenres().contains(g))
                check = false;

            if (!movie.getTitle().toLowerCase().contains(filter) && !movie.getDescription().toLowerCase().contains(filter))
                check = false;
            if (searchDecades) {
                int movieYear = movie.getReleaseYear();
                if (movieYear < d || movieYear >= d + 10) {
                    check = false;
                }
                if (searchRating && movie.getRating() < rating) {
                    check = false;
                }
            }


            if (check) {
                filtered.add(movie);
            }


            observableMovies.setAll(filtered); // funktioniert besser als removen und alle einzeln hinzufügen

        }
    }

    @FXML
    public void onResetClicked() {
        searchField.clear();  // Suchfeld leeren
        genreComboBox.getSelectionModel().clearSelection();  // Genre-Filter zurücksetzen
        observableMovies.setAll(allMovies);  // Komplette Film-Liste wiederherstellen
    }

    public ObservableList<Movie> getObservableMovies() {
        return observableMovies;
    }

}



