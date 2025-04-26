package at.ac.fhcampuswien.fhmdb.models;

//Exception liefert speziellen Error
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
