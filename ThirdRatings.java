import java.util.ArrayList;

public class ThirdRatings {

	private ArrayList<Rater> myRaters;
	private FirstRatings fr;

	public ThirdRatings() {
		// default constructor
		this("ratings.csv");
	}

	public ThirdRatings(String ratingsfile) {
		fr = new FirstRatings();
		myRaters = fr.loadRaters(ratingsfile);
	}

	public int getRaterSize() {
		return myRaters.size();
	}

	private double getAverageByID(String id, int minimalRaters) {
		double avgRating = 0;
		double ratings = 0;
		int ratingCount = 0;
		for (Rater r : myRaters) {
			ArrayList<String> currMovieIDList = r.getItemsRated();
			for (String movieID : currMovieIDList) {
				if (movieID.equals(id)) {
					double currRating = r.getRating(movieID);
					ratings = ratings + currRating;
					ratingCount += 1;
				}
			}
		}

		if (ratingCount < minimalRaters) {
			avgRating = 0.0;
		} else {
			avgRating = (ratings / ratingCount);
		}

		return avgRating;
	}

	// because we cant use .contains() to search an ArrayList of ogjects we create a
	// loop
	// to search if the objects exists in the ArrayList
	public Rating lookupRating(ArrayList<Rating> avgRatingMovieList, String item, double value) {
		for (Rating r : avgRatingMovieList) {
			if (r.getItem().equals(item) && r.getValue() == value) {
				return r;
			}
		}

		return null;
	}

	public ArrayList<Rating> getAverageRatings(int minimalRaters) {
		ArrayList<Rating> avgRatingMovieList = new ArrayList<Rating>();
		ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
		for (String movieID : movies) {
			double currAvgRating = getAverageByID(movieID, minimalRaters);
			Rating currRating = new Rating(movieID, currAvgRating);
			if (currAvgRating != 0.0) {
				if (lookupRating(avgRatingMovieList, currRating.getItem(), currRating.getValue()) == null) {
					avgRatingMovieList.add(currRating);
				}
			}
		}

		return avgRatingMovieList;
	}

	public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
		ArrayList<Rating> ratingListWithFilter = new ArrayList<Rating>();
		ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
		for (String movieID : movies) {
			double currAvgRating = getAverageByID(movieID, minimalRaters);
			Rating currRating = new Rating(movieID, currAvgRating);
			if (currAvgRating != 0.0) {
				if (lookupRating(ratingListWithFilter, currRating.getItem(), currRating.getValue()) == null) {
					ratingListWithFilter.add(currRating);
				}
			}
		}

		return ratingListWithFilter;
	}
}
