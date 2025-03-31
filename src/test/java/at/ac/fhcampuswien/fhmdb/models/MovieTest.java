// commit
package at.ac.fhcampuswien.fhmdb.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest
{


    @Test
    void compareTo_Bigger()
    {
        List<Movie.Genre> genreList;
        Collections.addAll(genreList = new ArrayList<Movie.Genre>(), Movie.Genre.SCIENCE_FICTION, Movie.Genre.ACTION);
        Movie m1 = new Movie("Avatar", "Film about the Aliens and not the bad one", genreList );
        Movie m2 = new Movie("Bvatar", "Film about the Aliens and not the bad one", genreList );

        assertEquals(-1, m1.compareTo(m2));
    }
    @Test
    void compareTo_Smaler()
    {
        List<Movie.Genre> genreList;
        Collections.addAll(genreList = new ArrayList<Movie.Genre>(), Movie.Genre.SCIENCE_FICTION, Movie.Genre.ACTION);
        Movie m1 = new Movie("Bvatar", "Film about the Aliens and not the bad one", genreList );
        Movie m2 = new Movie("Avatar", "Film about the Aliens and not the bad one", genreList );

        assertEquals(1, m1.compareTo(m2));
    }
    @Test
    void compareTo_Equal()
    {
        List<Movie.Genre> genreList;
        Collections.addAll(genreList = new ArrayList<Movie.Genre>(), Movie.Genre.SCIENCE_FICTION, Movie.Genre.ACTION);
        Movie m1 = new Movie("Avatar", "Film about the Aliens and not the bad one", genreList );
        Movie m2 = new Movie("Avatar", "Film about the Aliens and not the bad one", genreList );

        assertEquals(0, m1.compareTo(m2));
    }

    @Test
    void getTitle()
    {
        List<Movie.Genre> genreList;
        Collections.addAll(genreList = new ArrayList<Movie.Genre>(), Movie.Genre.SCIENCE_FICTION, Movie.Genre.ACTION);
        Movie m = new Movie("Avatar", "Film about the Aliens and not the bad one", genreList );
        assertEquals("Avatar", m.getTitle());
    }

    @Test
    void getDescription()
    {
        List<Movie.Genre> genreList;
        Collections.addAll(genreList = new ArrayList<Movie.Genre>(), Movie.Genre.SCIENCE_FICTION, Movie.Genre.ACTION);
        Movie m = new Movie("Avatar", "Film about the Aliens and not the bad one", genreList );
        assertEquals("Film about the Aliens and not the bad one", m.getDescription());
    }

    @Test
    void getGenres()
    {
        List<Movie.Genre> genreList;
        Collections.addAll(genreList = new ArrayList<Movie.Genre>(), Movie.Genre.SCIENCE_FICTION, Movie.Genre.ACTION);
        Movie m = new Movie("Avatar", "Film about the Aliens and not the bad one", genreList );
        assertEquals(genreList, m.getGenres());
    }

    @Test
    void getGenresString()
    {
        List<Movie.Genre> genreList;
        Collections.addAll(genreList = new ArrayList<Movie.Genre>(), Movie.Genre.SCIENCE_FICTION, Movie.Genre.ACTION);
        Movie m = new Movie("Avatar", "Film about the Aliens and not the bad one", genreList );
        assertEquals("SCIENCE_FICTION, ACTION", m.getGenresString());
    }


    @Test
    void getGenreStringArraySize()
    {
        String[] actual = Movie.getGenreStringArray();
        Movie.Genre[] expected = Movie.Genre.values();
        assertEquals(actual.length,expected.length);
    }

    @Test
    void getGenreStringCompareFirst() {
        String[] actual = Movie.getGenreStringArray();
        Movie.Genre[] expected = Movie.Genre.values();
        assertEquals(actual[0],expected[0].name());
    }
    @Test
    void getGenreStringCompareLast() {
        String[] actual = Movie.getGenreStringArray();
        Movie.Genre[] expected = Movie.Genre.values();
        assertEquals(actual[actual.length-1],expected[expected.length-1].name());
    }

    @Test
    void getMoviesFromJson() {
        // JSON nachbauen,simulieren
        String json = "[\n" +
                "{\n" +
                "    \"id\": \"81d317b0-29e5-4846-97a6-43c07f3edf4a\",\n" +
                "    \"title\": \"The Godfather\",\n" +
                "    \"genres\": [\n" +
                "      \"DRAMA\"\n" +
                "    ],\n" +
                "    \"releaseYear\": 1972,\n" +
                "    \"description\": \"The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.\",\n" +
                "    \"imgUrl\": \"https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@.V1.jpg\",\n" +
                "    \"lengthInMinutes\": 175,\n" +
                "    \"directors\": [\n" +
                "      \"Francis Ford Coppola\"\n" +
                "    ],\n" +
                "    \"writers\": [\n" +
                "      \"Mario Puzo\",\n" +
                "      \"Francis Ford Coppola\"\n" +
                "    ],\n" +
                "    \"mainCast\": [\n" +
                "      \"Marlon Brando\",\n" +
                "      \"Al Pacino\",\n" +
                "      \"James Caan\"\n" +
                "    ],\n" +
                "    \"rating\": 9.2\n" +
                "  }\n" +
                "]";
        List<Movie> actual = Movie.getMoviesFromJson(json);
        Movie m = new Movie("The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", List.of(Movie.Genre.DRAMA));
        m.setReleaseYear(1972);
        m.setLengthInMinutes(175);
        m.setRating(9.2);
        m.setDirectors(List.of("Francis Ford Coppola"));
        m.setWriters(List.of("Mario Puzo", "Francis Ford Coppola"));
        m.setMainCast(List.of("Marlon Brando", "Al Pacino", "James Caan"));
        m.setImgUrl("https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@.V1.jpg");
        m.setId("81d317b0-29e5-4846-97a6-43c07f3edf4a");

        assertEquals(m.getId(), actual.get(0).getId());
        assertEquals(m.getTitle(), actual.get(0).getTitle());
        assertEquals(m.getGenres(), actual.get(0).getGenres());
        assertEquals(m.getReleaseYear(), actual.get(0).getReleaseYear());
        assertEquals(m.getDescription(), actual.get(0).getDescription());
        assertEquals(m.getImgUrl(), actual.get(0).getImgUrl());
        assertEquals(m.getLengthInMinutes(), actual.get(0).getLengthInMinutes());
        assertEquals(m.getDirectors(), actual.get(0).getDirectors());
        assertEquals(m.getWriters(), actual.get(0).getWriters());
        assertEquals(m.getMainCast(), actual.get(0).getMainCast());
        assertEquals(m.getRating(), actual.get(0).getRating());
    }

}