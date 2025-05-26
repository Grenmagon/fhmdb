package at.ac.fhcampuswien.fhmdb.utils;

import javafx.scene.control.Alert;

public interface Observer {
    void writeAlert(String title, String message, Alert.AlertType type);
}
