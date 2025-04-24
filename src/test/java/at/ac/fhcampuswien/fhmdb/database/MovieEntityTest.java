package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieEntityTest {

    private Movie testMovie;
    private MovieEntity entity;

    @BeforeEach
    void setUp() {
        testMovie = new Movie("Test Title", "Test Description",
                Arrays.asList(Movie.Genre.ACTION, Movie.Genre.ADVENTURE));
        testMovie.setId("api-123");
        testMovie.setReleaseYear(2022);
        testMovie.setImgUrl("http://example.com/image.jpg");
        testMovie.setLengthInMinutes(120);
        testMovie.setRating(8.7);

        entity = new MovieEntity("api-456", "Another Movie", "Cool description",
                "DRAMA,COMEDY", 2010, "http://test.com", 90, 7.2);

    }

    @Test
    void testGenresToString()
    {
        String actual = MovieEntity.genresToString(Arrays.asList(Movie.Genre.ACTION, Movie.Genre.ADVENTURE));
        String expected = "ACTION,ADVENTURE";

        assertEquals(expected, actual);
    }

    @Test
    void testFromMovie() {
        MovieEntity entity = MovieEntity.fromMovie(testMovie);

        assertEquals(testMovie.getId(), entity.getApiId());
        assertEquals(testMovie.getTitle(), entity.getTitle());
        assertEquals(testMovie.getDescription(), entity.getDescription());
        assertEquals("ACTION,ADVENTURE", entity.getGenres());
        assertEquals(testMovie.getReleaseYear(), entity.getReleaseYear());
        assertEquals(testMovie.getImgUrl(), entity.getImgUrl());
        assertEquals(testMovie.getLengthInMinutes(), entity.getLengthInMinutes());
        assertEquals(testMovie.getRating(), entity.getRating());
    }

    @Test
    void testToMovie() {

        Movie movie = MovieEntity.toMovie(entity);

        assertEquals("api-456", movie.getId());
        assertEquals("Another Movie", movie.getTitle());
        assertEquals("Cool description", movie.getDescription());
        assertTrue(movie.getGenres().contains(Movie.Genre.DRAMA));
        assertTrue(movie.getGenres().contains(Movie.Genre.COMEDY));
        assertEquals(2010, movie.getReleaseYear());
        assertEquals("http://test.com", movie.getImgUrl());
        assertEquals(90, movie.getLengthInMinutes());
        assertEquals(7.2, movie.getRating());
    }

    @Test
    void testfromMovies() {
        List<Movie> movies = Arrays.asList(testMovie);
        List<MovieEntity> entities = MovieEntity.fromMovies(movies);

        assertEquals(1, entities.size());
        assertEquals("api-123", entities.get(0).getApiId());

    }

    @Test
    void testToMovies()
    {
        List<MovieEntity> entities = Arrays.asList(entity);
        List<Movie> backToMovies = MovieEntity.toMovies(entities);


        assertEquals(1, backToMovies.size());
        assertEquals("api-456", backToMovies.get(0).getId());
    }
}
