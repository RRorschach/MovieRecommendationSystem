import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerSimilarRatings {

	public void printAverageRatings() {
		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		MovieDatabase.initialize(moviefile);
		RaterDatabase.initialize(ratingsfile);

		System.out.println(MovieDatabase.size() + " movies found");
		System.out.println(RaterDatabase.size() + " users rated movies");

		int minimalRaters = 35;
		ArrayList<Rating> avgRatingMovieList = fr.getAverageRatings(minimalRaters);
		Collections.sort(avgRatingMovieList, new MovieIDComparator());
		System.out.println(avgRatingMovieList.size() + " movies found with the given criteria");
		for (Rating r : avgRatingMovieList) {
			String currMovieID = r.getItem();
			double currAvgRating = r.getValue();
			String movieTitle = MovieDatabase.getTitle(currMovieID);
			System.out.println(currAvgRating + " " + movieTitle);
		}

	}

	public void printAverageRatingsByYearAfterAndGenre() {
		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		MovieDatabase.initialize(moviefile);
		RaterDatabase.initialize(ratingsfile);

		System.out.println(MovieDatabase.size() + " movies found");
		System.out.println(RaterDatabase.size() + " users rated movies");

		int minimalRaters = 8;
		String genre = "Drama";
		int year = 1990;

		Filter genreFilter = new GenreFilter(genre);
		Filter yearFilter = new YearAfterFilter(year);
		Filter filters = new AllFilters();
		AllFilters allFilters = (AllFilters) filters;
		allFilters.addFilter(genreFilter);
		allFilters.addFilter(yearFilter);

		ArrayList<Rating> avgRatingMovieList = fr.getAverageRatingsByFilter(minimalRaters, allFilters);
		Collections.sort(avgRatingMovieList, new MovieIDComparator());
		System.out.println(avgRatingMovieList.size() + " movies found with the given criteria");
		for (Rating r : avgRatingMovieList) {
			String currMovieID = r.getItem();
			double currAvgRating = r.getValue();
			String movieTitle = MovieDatabase.getTitle(currMovieID);
			String currGenre = MovieDatabase.getGenres(currMovieID);
			int currYear = MovieDatabase.getYear(currMovieID);

			System.out.println(currAvgRating + " " + currYear + " " + movieTitle);
			System.out.println("\t" + currGenre);
		}

	}

	public void printSimilarRatings() {
		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		MovieDatabase.initialize(moviefile);
		RaterDatabase.initialize(ratingsfile);

		System.out.println(MovieDatabase.size() + " movies found");
		System.out.println(RaterDatabase.size() + " users rated movies");

		String id = "71";
		int numSimilarRaters = 20;
		int minimalRaters = 5;

		ArrayList<Rating> weightedAvgRatingsMovieList = fr.getSimilarRatings(id, numSimilarRaters, minimalRaters);
		System.out.println(weightedAvgRatingsMovieList.size() + " movies found with the given criteria");
		for (Rating r : weightedAvgRatingsMovieList) {
			String currMovieID = r.getItem();
			String currMovieTitle = MovieDatabase.getTitle(currMovieID);

			System.out.println(currMovieTitle + " " + r.getValue());
		}

	}

	public void printSimilarRatingsByGenre() {
		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		MovieDatabase.initialize(moviefile);
		RaterDatabase.initialize(ratingsfile);

		System.out.println(MovieDatabase.size() + " movies found");
		System.out.println(RaterDatabase.size() + " users rated movies");

		String genre = "Mystery";
		Filter currFilter = new GenreFilter(genre);

		String id = "964";
		int numSimilarRaters = 20;
		int minimalRaters = 5;

		ArrayList<Rating> weightedAvgRatingsMovieList = fr.getSimilarRatingsByFilter(id, numSimilarRaters,
				minimalRaters, currFilter);
		System.out.println(weightedAvgRatingsMovieList.size() + " movies found with the given criteria");
		for (Rating r : weightedAvgRatingsMovieList) {
			String currMovieID = r.getItem();
			String currMovieTitle = MovieDatabase.getTitle(currMovieID);
			String currMovieGenre = MovieDatabase.getGenres(currMovieID);

			System.out.println(currMovieTitle + " " + r.getValue());
			System.out.println("\t" + currMovieGenre);
		}

	}

	public void printSimilarRatingsByDirector() {
		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		MovieDatabase.initialize(moviefile);
		RaterDatabase.initialize(ratingsfile);

		System.out.println(MovieDatabase.size() + " movies found");
		System.out.println(RaterDatabase.size() + " users rated movies");

		String directors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
		Filter currFilter = new DirectorsFilter(directors);

		String id = "120";
		int numSimilarRaters = 10;
		int minimalRaters = 2;

		ArrayList<Rating> weightedAvgRatingsMovieList = fr.getSimilarRatingsByFilter(id, numSimilarRaters,
				minimalRaters, currFilter);
		System.out.println(weightedAvgRatingsMovieList.size() + " movies found with the given criteria");
		for (Rating r : weightedAvgRatingsMovieList) {
			String currMovieID = r.getItem();
			String currMovieTitle = MovieDatabase.getTitle(currMovieID);
			String currDirectors = MovieDatabase.getDirector(currMovieID);

			System.out.println(currMovieTitle + " " + r.getValue());
			System.out.println("\t" + currDirectors);
		}
	}

	public void printSimilarRatingsByGenreAndMinutes() {
		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		MovieDatabase.initialize(moviefile);
		RaterDatabase.initialize(ratingsfile);

		System.out.println(MovieDatabase.size() + " movies found");
		System.out.println(RaterDatabase.size() + " users rated movies");

		String genre = "Drama";
		Filter genreFilter = new GenreFilter(genre);
		int minTime = 80;
		int maxTime = 160;
		Filter minFilter = new MinutesFilter(minTime, maxTime);

		Filter filters = new AllFilters();
		AllFilters allFilters = (AllFilters) filters;
		allFilters.addFilter(genreFilter);
		allFilters.addFilter(minFilter);

		String id = "168";
		int numSimilarRaters = 10;
		int minimalRaters = 3;

		ArrayList<Rating> weightedAvgRatingsMovieList = fr.getSimilarRatingsByFilter(id, numSimilarRaters,
				minimalRaters, filters);
		System.out.println(weightedAvgRatingsMovieList.size() + " movies found with the given criteria");
		for (Rating r : weightedAvgRatingsMovieList) {
			String currMovieID = r.getItem();
			int currMovieMinute = MovieDatabase.getMinutes(currMovieID);
			String currMovieTitle = MovieDatabase.getTitle(currMovieID);
			String currMovieGenre = MovieDatabase.getGenres(currMovieID);

			System.out.println("current movie ID is: " + currMovieID);
			System.out.println(r.getValue() + " " + currMovieTitle + " Time: " + currMovieMinute);
			System.out.println("\t" + currMovieGenre);
		}
	}

	public void printSimilarRatingsByYearAfterAndMinutes() {
		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "ratings.csv";
		FourthRatings fr = new FourthRatings();
		MovieDatabase.initialize(moviefile);
		RaterDatabase.initialize(ratingsfile);

		System.out.println(MovieDatabase.size() + " movies found");
		System.out.println(RaterDatabase.size() + " users rated movies");

		int minTime = 70;
		int maxTime = 200;
		Filter minFilter = new MinutesFilter(minTime, maxTime);
		int year = 1975;
		Filter yearFilter = new YearAfterFilter(year);

		Filter filters = new AllFilters();
		AllFilters allFilters = (AllFilters) filters;
		allFilters.addFilter(yearFilter);
		allFilters.addFilter(minFilter);

		String id = "314";
		int numSimilarRaters = 10;
		int minimalRaters = 5;

		ArrayList<Rating> weightedAvgRatingsMovieList = fr.getSimilarRatingsByFilter(id, numSimilarRaters,
				minimalRaters, filters);
		System.out.println(weightedAvgRatingsMovieList.size() + " movies found with the given criteria");
		for (Rating r : weightedAvgRatingsMovieList) {
			String currMovieID = r.getItem();
			int currMovieMinute = MovieDatabase.getMinutes(currMovieID);
			int currYear = MovieDatabase.getYear(currMovieID);
			String currMovieTitle = MovieDatabase.getTitle(currMovieID);
			//String currDirectos = MovieDatabase.getDirector(currMovieID);

			System.out.println(
					r.getValue() + " " + currMovieTitle + " -- " + " Year: " + currYear + " Time: " + currMovieMinute);
		}
	}
}
