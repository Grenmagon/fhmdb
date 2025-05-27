package at.ac.fhcampuswien.fhmdb.ui.sort;
import java.util.List;
import at.ac.fhcampuswien.fhmdb.models.Movie;

public class SortContext {
    private SortState state = new NotSortedState();

    public void getNextState(){
        state = state.getNext();
    }
    public void setState(SortState newState) {
        this.state = newState;
    }

    public String getText()
    {
        return state.getText();
    }

    public List<Movie> sort(List<Movie> movies) {
        return state.sort(movies);
    }
}
