package at.ac.fhcampuswien.fhmdb.ui.sort;
import java.util.List;
import at.ac.fhcampuswien.fhmdb.models.Movie;

public class NotSortedState implements SortState {
    @Override
    public List<Movie> sort(List<Movie> movies) {
        return List.copyOf(movies);
}
}


