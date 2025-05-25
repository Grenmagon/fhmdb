package at.ac.fhcampuswien.fhmdb.ui.sort;
import java.util.List;
import at.ac.fhcampuswien.fhmdb.models.Movie;

public class SortContext {
    private SortState state = new NotSortedState();

    public void setState(SortState newState) {
        this.state = newState;
    }

    public List<Movie> sort(List<Movie> movies) {
        return state.sort(movies);
    }
}
