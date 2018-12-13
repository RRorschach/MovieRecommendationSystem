import java.util.Comparator;

public class MovieIDComparator implements Comparator<Rating> {

	public int compare(Rating r1, Rating r2) {
		return Double.compare(r1.getValue(), r2.getValue());
	}

}
