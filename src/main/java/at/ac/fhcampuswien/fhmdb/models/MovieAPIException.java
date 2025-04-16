package at.ac.fhcampuswien.fhmdb.models;

public class MovieAPIException extends Exception
{
    MovieAPIException()
    {
        super("API Error");
    }

    MovieAPIException(String message)
    {
        super(message);
    }
}
