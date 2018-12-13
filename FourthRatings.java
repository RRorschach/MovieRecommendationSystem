import java.util.ArrayList;
import java.util.Collections;

public class FourthRatings {

	private double getAverageByID(String id, int minimalRaters) {
		double avgRating = 0;
		double ratings = 0;
		int ratingCount = 0;
		for (Rater r : RaterDatabase.getRaters()) {
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

	private double dotProduct(Rater me, Rater r) {
		double product = 0.0;
		ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
		ArrayList<Double> myRatings = new ArrayList<Double>();
		ArrayList<Double> otherRatings = new ArrayList<Double>();
		for (String movieID : movies) {
			if (me.hasRating(movieID) && r.hasRating(movieID)) {
				double currMineRating = me.getRating(movieID) - 5;
				myRatings.add(currMineRating);

				double currOtherRating = r.getRating(movieID) - 5;
				otherRatings.add(currOtherRating);
			}
		}

		for (int k = 0; k < myRatings.size(); k++) {
			product = product + (myRatings.get(k) * otherRatings.get(k));
		}

		return product;
	}

	private ArrayList<Rating> getSimilarities(String id) {
		ArrayList<Rating> similarityRatersList = new ArrayList<Rating>();
		for (Rater currRater : RaterDatabase.getRaters()) {
			String currRaterID = currRater.getID();
			if (!currRaterID.equals(id)) {
				double dotProd = dotProduct(RaterDatabase.getRater(id), currRater);
				if (dotProd >= 0) {
					Rating similarityRating = new Rating(currRaterID, dotProd);
					similarityRatersList.add(similarityRating);
				}
			}
		}

		Collections.sort(similarityRatersList, new SimilarityValueComparator());

		return similarityRatersList;
	}

	public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
		ArrayList<Rating> weightedAvgRatingsMovieList = new ArrayList<Rating>();
		ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());

		ArrayList<Rating> similarityRatingList = getSimilarities(id);

		for (String movieID : movies) {
			int countRaters = 0;
			double sumOfWeightedAvgRatings = 0;
			for (int k = 0; k < numSimilarRaters; k++) {
				Rating currRating = similarityRatingList.get(k);
				// System.out.println("the current rating is: " + currRating);
				String currRaterID = currRating.getItem();
				// System.out.println("the current rater ID is: " + currRaterID);
				Rater raterFromSimilarityList = RaterDatabase.getRater(currRaterID);
				if (raterFromSimilarityList.hasRating(movieID)) {
					// take the similarity rating
					double similarityRating = raterFromSimilarityList.getRating(movieID);
					// take the given rate
					double rateWithRate = currRating.getValue();
					double weightedAvgRatings = similarityRating * rateWithRate;
					countRaters++;
					sumOfWeightedAvgRatings += weightedAvgRatings;
					// System.out.println("The current raters count is: " + countRaters);
				}
			}

			if (countRaters >= minimalRaters) {
				// System.out.println("The current raters count is: " + countRaters);
				double weightedAvgMovieRating = sumOfWeightedAvgRatings / countRaters;
				// String movieTitle = MovieDatabase.getTitle(movieID);
				// System.out.println(movieTitle);
				weightedAvgRatingsMovieList.add(new Rating(movieID, weightedAvgMovieRating));
			}
		}

		Collections.sort(weightedAvgRatingsMovieList, new SimilarityValueComparator());
		return weightedAvgRatingsMovieList;
	}

	public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters,
			Filter filterCriteria) {
		ArrayList<Rating> weightedAvgRatingsMovieList = new ArrayList<Rating>();
		ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);

		ArrayList<Rating> similarityRatingList = getSimilarities(id);

		for (String movieID : movies) {
			int countRaters = 0;
			double sumOfWeightedAvgRatings = 0;
			for (int k = 0; k < numSimilarRaters; k++) {
				Rating currRating = similarityRatingList.get(k);
				// System.out.println("the current rating is: " + currRating);
				String currRaterID = currRating.getItem();
				// System.out.println("the current rater ID is: " + currRaterID);
				Rater raterFromSimilarityList = RaterDatabase.getRater(currRaterID);
				if (raterFromSimilarityList.hasRating(movieID)) {
					// take the similarity rating
					double similarityRating = raterFromSimilarityList.getRating(movieID);
					// take the given rate
					double rateWithRate = currRating.getValue();
					double weightedAvgRatings = similarityRating * rateWithRate;
					countRaters++;
					sumOfWeightedAvgRatings += weightedAvgRatings;
					// System.out.println("The current raters count is: " + countRaters);
				}
			}

			if (countRaters >= minimalRaters) {
				// System.out.println("The current raters count is: " + countRaters);
				double weightedAvgMovieRating = sumOfWeightedAvgRatings / countRaters;
				// String movieTitle = MovieDatabase.getTitle(movieID);
				// System.out.println(movieTitle);
				weightedAvgRatingsMovieList.add(new Rating(movieID, weightedAvgMovieRating));
			}
		}

		Collections.sort(weightedAvgRatingsMovieList, new SimilarityValueComparator());
		return weightedAvgRatingsMovieList;
	}

}
