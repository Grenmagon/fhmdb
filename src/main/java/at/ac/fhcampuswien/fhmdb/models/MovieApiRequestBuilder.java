package at.ac.fhcampuswien.fhmdb.models;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MovieApiRequestBuilder {
    private final String baseUrl;
    private String queryText;
    private Movie.Genre genreFilter;
    private Integer yearFilter;
    private Double ratingFilter;

    public MovieApiRequestBuilder(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public MovieApiRequestBuilder withQuery(String text) {
        this.queryText = text;
        return this;
    }

    public MovieApiRequestBuilder withGenre(Movie.Genre genre) {
        this.genreFilter = genre;
        return this;
    }

    public MovieApiRequestBuilder withYear(int year) {
        this.yearFilter = year;
        return this;
    }

    public MovieApiRequestBuilder withRating(double rating) {
        this.ratingFilter = rating;
        return this;
    }

    public String build() {
        List<String> parts = new ArrayList<>();

        if (queryText != null && !queryText.isBlank()) {
            String encoded = URLEncoder.encode(queryText, StandardCharsets.UTF_8);
            parts.add("query=" + encoded);
        }
        if (genreFilter != null) {
            parts.add("genre=" + genreFilter.name());
        }
        if (yearFilter != null && yearFilter > 0) {
            parts.add("releaseYear=" + yearFilter);
        }
        if (ratingFilter != null && ratingFilter > 0) {
            parts.add("ratingFrom=" + ratingFilter);
        }

        if (parts.isEmpty()) {
            return baseUrl;
        }

        return baseUrl + "?" + String.join("&", parts);
    }
}
