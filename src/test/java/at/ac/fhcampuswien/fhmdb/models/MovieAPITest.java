package at.ac.fhcampuswien.fhmdb.models;

import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieAPITest
{
    private static final String MOCK_RESPONSE = "[\n" +
            "{\n" +
            "    \"id\": \"81d317b0-29e5-4846-97a6-43c07f3edf4a\",\n" +
            "    \"title\": \"The Godfather\",\n" +
            "    \"genres\": [\n" +
            "      \"DRAMA\"\n" +
            "    ],\n" +
            "    \"releaseYear\": 1972,\n" +
            "    \"description\": \"The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.\",\n" +
            "    \"imgUrl\": \"https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg\",\n" +
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

    private OkHttpClient mockClient;
    private Call mockCall;
    private Response mockResponse;
    private ResponseBody mockResponseBody;

    @BeforeEach
    void setUp() throws IOException {
        // Mock OkHttpClient, Call, Response, und ResponseBody
        mockClient = mock(OkHttpClient.class);
        mockCall = mock(Call.class);
        mockResponse = mock(Response.class);
        mockResponseBody = mock(ResponseBody.class);

        when(mockClient.newCall(any())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockResponse.isSuccessful()).thenReturn(true);
        when(mockResponse.body()).thenReturn(mockResponseBody);
        when(mockResponseBody.string()).thenReturn(MOCK_RESPONSE);

        // Setze den CLIENT in der getesteten Klasse auf das Mock-Objekt
        MovieAPI.CLIENT = mockClient;
    }

    @Test
    void testGetMoviesFilter_Success() throws IOException {
        String response = MovieAPI.getMoviesFilter("God", Movie.Genre.ACTION, 2010, 8.0);

        assertNotNull(response);
        assertTrue(response.contains("The Godfather"));
        assertTrue(response.contains("\"rating\": 9.2"));
    }

    @Test
    void testGetMoviesFilter_EmptyResponse() throws IOException {
        when(mockResponseBody.string()).thenReturn("");

        String response = MovieAPI.getMoviesFilter("", null, 0, 0);

        assertEquals(MovieAPI.ERROR, response);
    }

    @Test
    void testGetMoviesFilter_NullResponse() throws IOException {
        when(mockResponse.body()).thenReturn(null);

        String response = MovieAPI.getMoviesFilter("", null, 0, 0);

        assertEquals(MovieAPI.ERROR, response);
    }

    @Test
    void testGetMoviesFilter_IOException() throws IOException {
        when(mockCall.execute()).thenThrow(new IOException("Network error"));

        String response = MovieAPI.getMoviesFilter("Test", null, 0, 0);

        assertEquals(MovieAPI.ERROR, response);
    }

}