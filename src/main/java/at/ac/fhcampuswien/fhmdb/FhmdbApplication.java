package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.DatabaseConsole;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb");
        stage.setScene(scene);
        stage.show();

        DatabaseConsole.startConsole();
    }

    @Override
    public void stop() {
        DatabaseConsole.stopConsole(); // <-- H2 Web-Console sauber schließen
    }


    public static void main(String[] args) {
        launch();
    }
}