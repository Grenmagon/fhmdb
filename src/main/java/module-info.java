module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires com.google.gson;
    requires okhttp3;

    requires ormlite.jdbc;
    requires java.sql;
    requires com.h2database;
    requires java.desktop;

    opens at.ac.fhcampuswien.fhmdb.database to ormlite.jdbc;
    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    opens at.ac.fhcampuswien.fhmdb.models to com.google.gson;
    exports at.ac.fhcampuswien.fhmdb;
}