import java.util.ArrayList;
import java.util.HashMap;

public class EfficientRater implements Rater {

	private String myID;
	private HashMap<String, Rating> myRatings;

	public EfficientRater(String id) {
		myID = id;
		myRatings = new HashMap<String, Rating>();
	}

	public void addRating(String item, double rating) {
		myRatings.put(item, new Rating(item, rating));
	}

	public boolean hasRating(String item) {
		if (myRatings.containsKey(item)) {
			return true;
		}

		return false;
	}

	public String getID() {
		return myID;
	}

	public String toString() {
		return "[" + getID() + "," + myRatings + "]";
	}

	public double getRating(String item) {
		Rating currRate = myRatings.get(item);
		String currMovieID = currRate.getItem();
		if (currMovieID.equals(item)) {
			return currRate.getValue();
		}

		return -1;
	}

	public int numRatings() {
		return myRatings.size();
	}

	public ArrayList<String> getItemsRated() {
		ArrayList<String> list = new ArrayList<String>();
		for (String movieID : myRatings.keySet()) {
			list.add(movieID);
		}

		return list;
	}

}
