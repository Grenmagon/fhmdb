package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private static JdbcConnectionSource connectionSource;
    private static Dao<MovieEntity, Long> movieDao;

    @BeforeAll
    static void setUp() throws SQLException {
        // In-Memory DB für Tests
        connectionSource = new JdbcConnectionSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        movieDao = DaoManager.createDao(connectionSource, MovieEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
    }

    @AfterAll
    static void teardown() throws Exception {
        connectionSource.close();
    }

    @Test
    void testInsertAndQueryMovie() throws SQLException {
        MovieEntity movie = new MovieEntity(
                "api-id-123",
                "Test Movie",
                "Test Description",
                "DRAMA",
                2024,
                "https://example.com/image.jpg",
                120,
                8.5
        );

        movieDao.create(movie);
        MovieEntity fromDb = movieDao.queryForId(movie.getId());

        assertNotNull(fromDb);
        assertEquals(movie.getApiId(), fromDb.getApiId());
        assertEquals(movie.getId(), fromDb.getId());
    }

    @Test
    void testUniqueApiIdConstraint() throws SQLException {
        MovieEntity movie1 = new MovieEntity("unique-api-id", "Title 1", "Desc", "DRAMA", 2024, "", 100, 7.0);
        MovieEntity movie2 = new MovieEntity("unique-api-id", "Title 2", "Desc", "DRAMA", 2024, "", 100, 7.0);

        movieDao.create(movie1);
        assertThrows(SQLException.class, () -> movieDao.create(movie2));  // Wegen unique = true auf apiId
    }

    @Test
    void testTableIsCreated() throws SQLException {
        assertTrue(movieDao.isTableExists());
    }
}
