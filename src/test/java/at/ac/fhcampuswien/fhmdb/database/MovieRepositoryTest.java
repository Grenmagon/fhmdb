package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieRepositoryTest {

    private static JdbcConnectionSource connectionSource;
    private MovieRepository movieRepository;

    @BeforeAll
    public static void setUpBeforAll() throws SQLException {
        connectionSource = new JdbcConnectionSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
        //DatabaseTestUtil.setTestConnection(connectionSource);  // Helper, siehe unten
    }

    @AfterAll
    public static void closeDB() throws Exception
    {
        connectionSource.close();
    }

    @BeforeEach
    public void setUp() throws SQLException {
        movieRepository = new MovieRepository();
        movieRepository.removeAll(); // leere DB vor jedem Test
    }

    @Test
    public void testAddToDB() throws SQLException {
        Movie movie = new Movie("Test Movie", "A description", List.of(Movie.Genre.ACTION));
        movie.setId("API123");
        movieRepository.addToDB(movie);

        List<MovieEntity> all = movieRepository.getAllMovies();
        assertEquals(1, all.size());
        assertEquals("Test Movie", all.get(0).getTitle());
    }

    @Test
    public void testRemoveFromDB() throws SQLException {
        Movie movie = new Movie("To Remove", "Desc", List.of(Movie.Genre.DRAMA));
        movie.setId("REMOVE001");
        movieRepository.addToDB(movie);

        movieRepository.removeFromDB(movie);

        List<MovieEntity> all = movieRepository.getAllMovies();
        assertTrue(all.isEmpty());
    }

    @Test
    public void testAddAllMovies() throws SQLException {
        Movie m1 = new Movie("M1", "D1", List.of(Movie.Genre.ADVENTURE));
        m1.setId("A1");
        Movie m2 = new Movie("M2", "D2", List.of(Movie.Genre.FANTASY));
        m2.setId("A2");

        int count = movieRepository.addAllMovies(List.of(m1, m2));
        assertEquals(2, count);

        List<MovieEntity> all = movieRepository.getAllMovies();
        assertEquals(2, all.size());
    }

    @Test
    public void testRemoveAll() throws SQLException {
        Movie m1 = new Movie("M1", "D1", List.of(Movie.Genre.ADVENTURE));
        m1.setId("A1");
        movieRepository.addToDB(m1);

        int deleted = movieRepository.removeAll();
        assertEquals(1, deleted);

        List<MovieEntity> all = movieRepository.getAllMovies();
        assertTrue(all.isEmpty());
    }
}
