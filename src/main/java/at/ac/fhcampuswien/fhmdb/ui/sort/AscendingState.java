package at.ac.fhcampuswien.fhmdb.ui.sort;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import at.ac.fhcampuswien.fhmdb.models.Movie;


public class AscendingState  implements SortState {
    @Override
    public List<Movie> sort(List<Movie> movies) {
        return movies.stream()
                .sorted(Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    @Override
    public SortState getNext()
    {
        return new DescendingState();
    }

    @Override
    public String getText()
    {
        return "Sort (asc)";
    }
}
