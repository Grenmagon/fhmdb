package at.ac.fhcampuswien.fhmdb.utils;

import at.ac.fhcampuswien.fhmdb.models.Movie;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MovieUtils {

    /**
     * Gibt den am häufigsten vorkommenden Schauspieler zurück
     */
    public static String getMostPopularActor(List<Movie> movies) {
        return movies.stream()
                .filter(m -> m.getMainCast() != null)
                .flatMap(m -> m.getMainCast().stream()) // Alle Schauspieler aus allen Filmen sammeln
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())) // Häufigkeit zählen
                .entrySet().stream()
                .max(Map.Entry.comparingByValue()) // Max. Eintrag finden
                .map(Map.Entry::getKey) // Namen extrahieren
                .orElse(null); // Falls leer
    }

    /**
     * Gibt die Länge (Zeichenanzahl) des längsten Filmtitels zurück
     */
    public static int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream()
                .filter(m -> m.getTitle() != null)
                .mapToInt(m -> m.getTitle().length()) // Titel-Längen extrahieren
                .max()
                .orElse(0);
    }

    /**
     * Zählt, wie viele Filme von einem bestimmten Regisseur stammen
     */
    public static long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream()
                .filter(m -> m.getDirectors() != null && m.getDirectors().contains(director))
                .count();
    }

    /**
     * Gibt alle Filme zurück, die zwischen den angegebenen Jahren veröffentlicht wurden
     */
    public static List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(m -> m.getReleaseYear() >= startYear && m.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }
}
