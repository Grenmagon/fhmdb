package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.HomeController;
import at.ac.fhcampuswien.fhmdb.database.WatchListRepository;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genres = new Label();
    private final JFXButton watchlistBtn = new JFXButton("Add to Watchlist");
    private final JFXButton detailsBtn = new JFXButton("Show Details");
    private final VBox layout = new VBox(title, detail, genres);


    //private final ClickEventHandler<Movie> watchlistBtnClicked;
    private HomeController homeController;

    // Constructor that accepts a click handler
    public MovieCell(HomeController homeController/*, ClickEventHandler<Movie> watchlistBtnClicked*/) {
        //this.watchlistBtnClicked = watchlistBtnClicked;
        this.homeController = homeController;
    }


    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setText(null);
            setGraphic(null);  //Damit die leeren Elemente nicht mehr angezeigt werden
        } else {
            //System.out.println(movie.getTitle() + " " + homeController.showingWatchlist);
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            detail.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );

            genres.setText(movie.getGenresString());

            // color scheme
            title.getStyleClass().add("text-yellow");
            detail.getStyleClass().add("text-white");
            genres.getStyleClass().add("text-white");

            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // layout
            title.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setWrapText(true);
            genres.setWrapText(true);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
            if (!layout.getChildren().contains(watchlistBtn)) {
                layout.getChildren().add(watchlistBtn);
            }

            //watchlistBtn.setOnAction(event -> watchlistBtnClicked.onClick(movie));
            if (homeController.showingWatchlist) {
                watchlistBtn.setText("Remove from Watchlist");
                watchlistBtn.setOnAction(event -> homeController.removeFromWatchlist(movie));
            }
            else {
                watchlistBtn.setText("Add to Watchlist");
                watchlistBtn.setOnAction(event -> homeController.addToWatchlist(movie));
            }


            // Details button
            detailsBtn.setOnAction(event -> homeController.onDetailButtonClicked(movie));

            // Watchlist Button Styling
            watchlistBtn.setStyle("-fx-background-color: #f5c518; -fx-text-fill: black; -fx-font-size: 12px; -fx-padding: 5px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

            // Details Button Styling
            detailsBtn.setStyle("-fx-background-color: #f5c518; -fx-text-fill: black; -fx-font-size: 12px; -fx-padding: 5px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;");


            // Set up layout
            title.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setWrapText(true);
            genres.setWrapText(true);

            // Combine the buttons with movie info into a horizontal layout
            HBox buttons = new HBox(watchlistBtn, detailsBtn);
            buttons.setSpacing(10);
            buttons.setStyle("-fx-padding: 5px;");
            layout.getChildren().add(buttons);

            setGraphic(layout);
        }
    }

}

