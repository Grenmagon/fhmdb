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
}