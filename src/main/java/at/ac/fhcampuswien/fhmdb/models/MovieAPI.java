//commit
package at.ac.fhcampuswien.fhmdb.models;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
//import java.sql.SQLOutput;

public class MovieAPI
{
    static final private String MOVIE_URL = "https://prog2.fh-campuswien.ac.at/movies";
    static public OkHttpClient CLIENT = new OkHttpClient();
    static public String ERROR = "Error";


    /*
    Returns a JSON-String of the Movies, acording to the filter
    If a filter is not wanted, just set
    String: null or empty
    Enum: null
    int: 0
     */
    public static String getMoviesFilter(String query, Movie.Genre genre, int releaseYear, double ratingFrom) throws IOException, MovieAPIException
    {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(MOVIE_URL).newBuilder();
        if (query != null && !query.isEmpty())
            urlBuilder.addQueryParameter("query", query);

        if (genre != null)
            urlBuilder.addQueryParameter("genre", genre.name());

        if (releaseYear != 0)
            urlBuilder.addQueryParameter("releaseYear", releaseYear + "");

        if (ratingFrom != 0)
            urlBuilder.addQueryParameter("ratingFrom", ratingFrom + "");

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "http.agent") //without this, you are not permited to acces the server
                .build();

            Response response = CLIENT.newCall(request).execute();
            if (response.body() != null)
            {
                String resString = response.body().string();
                if (resString.isEmpty())
                {
                    throw new MovieAPIException("Empty Response Error!");
                    //return ERROR;
                }
                return resString;
            }
            else
            {
                throw new MovieAPIException("Null Response Error!");
                //return ERROR;
            }

    }


}
