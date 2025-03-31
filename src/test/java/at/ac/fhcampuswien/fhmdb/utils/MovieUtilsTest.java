package at.ac.fhcampuswien.fhmdb.utils;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieUtilsTest {
    private List<Movie> movies;



    @Test
    public void testGetLongestMovieTitle_returnsCorrectLength() {
        Movie m1 = new Movie("Up", "", List.of(Movie.Genre.ACTION));
        Movie m2 = new Movie("Harry Potter and the Philosopher's Stone", "", List.of(Movie.Genre.FANTASY));
        Movie m3 = new Movie("It", "", List.of(Movie.Genre.HORROR));

        List<Movie> movies = List.of(m1, m2, m3);

        int result = MovieUtils.getLongestMovieTitle(movies);

        assertEquals(40, result);
    }

    @Test
    public void testGetMostPopularActor_returnsCorrectActor() {
        Movie m1 = new Movie("Film A", "", List.of(Movie.Genre.ACTION));
        m1.setMainCast(List.of("Tom Hanks", "Brad Pitt"));

        Movie m2 = new Movie("Film B", "", List.of(Movie.Genre.DRAMA));
        m2.setMainCast(List.of("Tom Hanks", "Natalie Portman"));

        Movie m3 = new Movie("Film C", "", List.of(Movie.Genre.THRILLER));
        m3.setMainCast(List.of("Tom Hanks"));

        List<Movie> movies = List.of(m1, m2, m3);

        String result = MovieUtils.getMostPopularActor(movies);

        assertEquals("Tom Hanks", result);
    }


    @Test
    public void testCountMoviesFrom_returnsCorrectCount() {
        Movie m1 = new Movie("Film A", "", List.of(Movie.Genre.ACTION));
        m1.setDirectors(List.of("Quentin Tarantino"));

        Movie m2 = new Movie("Film B", "", List.of(Movie.Genre.DRAMA));
        m2.setDirectors(List.of("James Cameron"));

        Movie m3 = new Movie("Film C", "", List.of(Movie.Genre.ACTION));
        m3.setDirectors(List.of("Quentin Tarantino"));

        List<Movie> movies = List.of(m1, m2, m3);

        long result = MovieUtils.countMoviesFrom(movies, "Quentin Tarantino");

        assertEquals(2, result);
    }

    @Test
    public void testGetMoviesBetweenYears_returnsCorrectMovies() {
        Movie m1 = new Movie("Film A", "", List.of(Movie.Genre.ACTION));
        m1.setReleaseYear(1999);

        Movie m2 = new Movie("Film B", "", List.of(Movie.Genre.DRAMA));
        m2.setReleaseYear(2005);

        Movie m3 = new Movie("Film C", "", List.of(Movie.Genre.COMEDY));
        m3.setReleaseYear(2012);

        Movie m4 = new Movie("Film D", "", List.of(Movie.Genre.HISTORY));
        m4.setReleaseYear(1995);

        List<Movie> movies = List.of(m1, m2, m3, m4);

        List<Movie> result = MovieUtils.getMoviesBetweenYears(movies, 1999, 2005);

        assertEquals(2, result.size());
        assertTrue(result.contains(m1));
        assertTrue(result.contains(m2));
    }
}
