import java.util.Comparator;

public class SimilarityValueComparator implements Comparator<Rating> {

	public int compare(Rating r1, Rating r2) {
		return Double.compare(r2.getValue(), r1.getValue());
	}
}
