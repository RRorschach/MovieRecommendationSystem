import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerWithFilters {

	public void printAverageRatings() {
		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "data/ratings.csv";
		ThirdRatings sr = new ThirdRatings(ratingsfile);
		MovieDatabase.initialize(moviefile);

		System.out.println(MovieDatabase.size() + " movies found");
		System.out.println(sr.getRaterSize() + " users rated movies");

		int minimalRaters = 35;
		ArrayList<Rating> avgRatingMovieList = sr.getAverageRatings(minimalRaters);
		Collections.sort(avgRatingMovieList, new MovieIDComparator());
		System.out.println(avgRatingMovieList.size() + " movies found with the given criteria");
		for (Rating r : avgRatingMovieList) {
			String currMovieID = r.getItem();
			double currAvgRating = r.getValue();
			String movieTitle = MovieDatabase.getTitle(currMovieID);
			System.out.println(currAvgRating + " " + movieTitle);
		}

	}

	public void printAverageRatingsByYear() {
		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "data/ratings.csv";
		ThirdRatings sr = new ThirdRatings(ratingsfile);
		MovieDatabase.initialize(moviefile);

		System.out.println(MovieDatabase.size() + " movies found");
		System.out.println(sr.getRaterSize() + " users rated movies");

		int minimalRaters = 20;
		int year = 2000;
		Filter currFilter = new YearAfterFilter(year);
		ArrayList<Rating> avgRatingMovieList = sr.getAverageRatingsByFilter(minimalRaters, currFilter);
		Collections.sort(avgRatingMovieList, new MovieIDComparator());
		System.out.println(avgRatingMovieList.size() + " movies found with the given criteria");
		for (Rating r : avgRatingMovieList) {
			String currMovieID = r.getItem();
			double currAvgRating = r.getValue();
			String movieTitle = MovieDatabase.getTitle(currMovieID);
			int currYear = MovieDatabase.getYear(currMovieID);
			System.out.println(currAvgRating + " " + currYear + " " + movieTitle);
		}

	}

	public void printAverageRatingsByGenre() {
		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "data/ratings.csv";
		ThirdRatings sr = new ThirdRatings(ratingsfile);
		MovieDatabase.initialize(moviefile);

		System.out.println(MovieDatabase.size() + " movies found");
		System.out.println(sr.getRaterSize() + " users rated movies");

		int minimalRaters = 20;
		String genre = "Comedy";
		Filter currFilter = new GenreFilter(genre);
		ArrayList<Rating> avgRatingMovieList = sr.getAverageRatingsByFilter(minimalRaters, currFilter);
		Collections.sort(avgRatingMovieList, new MovieIDComparator());
		System.out.println(avgRatingMovieList.size() + " movies found with the given criteria");
		for (Rating r : avgRatingMovieList) {
			String currMovieID = r.getItem();
			double currAvgRating = r.getValue();
			String movieTitle = MovieDatabase.getTitle(currMovieID);
			String currGenre = MovieDatabase.getGenres(currMovieID);
			System.out.println(currAvgRating + " " + movieTitle);
			System.out.println("\t" + currGenre);
		}

	}

	public void printAverageRatingsByMinutes() {
		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "data/ratings.csv";
		ThirdRatings sr = new ThirdRatings(ratingsfile);
		MovieDatabase.initialize(moviefile);

		System.out.println(MovieDatabase.size() + " movies found");
		System.out.println(sr.getRaterSize() + " users rated movies");

		int minimalRaters = 5;
		int minTime = 105;
		int maxTime = 135;
		Filter currFilter = new MinutesFilter(minTime, maxTime);
		ArrayList<Rating> avgRatingMovieList = sr.getAverageRatingsByFilter(minimalRaters, currFilter);
		Collections.sort(avgRatingMovieList, new MovieIDComparator());
		System.out.println(avgRatingMovieList.size() + " movies found with the given criteria");
		for (Rating r : avgRatingMovieList) {
			String currMovieID = r.getItem();
			double currAvgRating = r.getValue();
			String movieTitle = MovieDatabase.getTitle(currMovieID);
			int currTime = MovieDatabase.getMinutes(currMovieID);
			System.out.println(currAvgRating + " Time: " + currTime + " " + movieTitle);
		}

	}

	public void printAverageRatingsByDirectors() {
		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "data/ratings.csv";
		ThirdRatings sr = new ThirdRatings(ratingsfile);
		MovieDatabase.initialize(moviefile);

		System.out.println(MovieDatabase.size() + " movies found");
		System.out.println(sr.getRaterSize() + " users rated movies");

		int minimalRaters = 4;
		String director = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
		Filter currFilter = new DirectorsFilter(director);
		ArrayList<Rating> avgRatingMovieList = sr.getAverageRatingsByFilter(minimalRaters, currFilter);
		Collections.sort(avgRatingMovieList, new MovieIDComparator());
		System.out.println(avgRatingMovieList.size() + " movies found with the given criteria");
		for (Rating r : avgRatingMovieList) {
			String currMovieID = r.getItem();
			double currAvgRating = r.getValue();
			String movieTitle = MovieDatabase.getTitle(currMovieID);
			String currDirector = MovieDatabase.getDirector(currMovieID);
			System.out.println(currAvgRating + " " + movieTitle);
			System.out.println("\t" + currDirector);
		}

	}

	public void printAverageRatingsByYearAfterAndGenre() {
		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "data/ratings.csv";
		ThirdRatings sr = new ThirdRatings(ratingsfile);
		MovieDatabase.initialize(moviefile);

		System.out.println(MovieDatabase.size() + " movies found");
		System.out.println(sr.getRaterSize() + " users rated movies");

		int minimalRaters = 8;
		String genre = "Drama";
		int year = 1990;

		Filter genreFilter = new GenreFilter(genre);
		Filter yearFilter = new YearAfterFilter(year);
		Filter filters = new AllFilters();
		AllFilters allFilters = (AllFilters) filters;
		allFilters.addFilter(genreFilter);
		allFilters.addFilter(yearFilter);

		ArrayList<Rating> avgRatingMovieList = sr.getAverageRatingsByFilter(minimalRaters, allFilters);
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

	public void printAverageRatingsByDirectorsAndMinutes() {
		String moviefile = "ratedmoviesfull.csv";
		String ratingsfile = "data/ratings.csv";
		ThirdRatings sr = new ThirdRatings(ratingsfile);
		MovieDatabase.initialize(moviefile);

		System.out.println(MovieDatabase.size() + " movies found");
		System.out.println(sr.getRaterSize() + " users rated movies");

		int minimalRaters = 3;
		String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
		int timeMin = 90;
		int timeMax = 180;

		Filter directorsFilter = new DirectorsFilter(directors);
		Filter minutesFilter = new MinutesFilter(timeMin, timeMax);
		Filter filters = new AllFilters();
		AllFilters allFilters = (AllFilters) filters;
		allFilters.addFilter(directorsFilter);
		allFilters.addFilter(minutesFilter);

		ArrayList<Rating> avgRatingMovieList = sr.getAverageRatingsByFilter(minimalRaters, allFilters);
		Collections.sort(avgRatingMovieList, new MovieIDComparator());
		System.out.println(avgRatingMovieList.size() + " movies found with the given criteria");
		for (Rating r : avgRatingMovieList) {
			String currMovieID = r.getItem();
			double currAvgRating = r.getValue();
			String movieTitle = MovieDatabase.getTitle(currMovieID);
			String currDirectors = MovieDatabase.getDirector(currMovieID);
			int currTime = MovieDatabase.getMinutes(currMovieID);

			System.out.println(currAvgRating + " Time: " + currTime + " " + movieTitle);
			System.out.println("\t" + currDirectors);
		}

	}

}
