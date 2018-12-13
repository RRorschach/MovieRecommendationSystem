import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.CSVReader;

public class FirstRatings {

	private ArrayList<Movie> listOfMovies;
	private ArrayList<Rater> ratersList;

	public FirstRatings() {
		ratersList = new ArrayList<Rater>();
		listOfMovies = new ArrayList<Movie>();
	}

	public ArrayList<Movie> loadMovies(String filename) {
	
		try (BufferedReader br = new BufferedReader(new FileReader(filename)); 
				CSVReader csvReader = new CSVReader(br);
				) {

			String[] nextRec;
			while ((nextRec = csvReader.readNext()) != null) {

				if (nextRec[0].equalsIgnoreCase("id")) {
					continue;
				}

				String id = nextRec[0];
				String title = nextRec[1];
				String year = nextRec[2];
				String country = nextRec[3];
				String genre = nextRec[4];
				String director = nextRec[5];
				String minutes = nextRec[6];
				String poster = nextRec[7];

				Movie movieInfo = new Movie(id, title, year, genre, director, country, poster, minutes);
				listOfMovies.add(movieInfo);
			}
		} catch (FileNotFoundException e) {
			System.out.println("The file " + filename + " doesn't exist...");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return listOfMovies;
	}

	public int returnGenre(String genreType) {
		int countGenre = 0;
		for (Movie m : listOfMovies) {
			if (m.getGenres().contains(genreType)) {
				countGenre += 1;
			}
		}
		return countGenre;
	}

	public int returnRunTime(int time) {
		int countMovieLength = 0;
		for (Movie m : listOfMovies) {
			if (m.getMinutes() > time) {
				countMovieLength += 1;
			}
		}
		return countMovieLength;
	}

	public HashMap<String, ArrayList<String>> returnDirectors() {
		HashMap<String, ArrayList<String>> directorsMap = new HashMap<String, ArrayList<String>>();
		for (Movie m : listOfMovies) {
			String directorsName = m.getDirector();
			String currMovieTitle = m.getTitle();
			// System.out.println("The directors names are: " + directorsName);
			// take into consideration that there might be more than one director
			String[] names = directorsName.trim().split(",");
			// System.out.println(names.toString() + " " + names.length);
			for (int k = 0; k < names.length; k++) {
				String currName = names[k].trim();
				// System.out.println("The current director is: " + currName);

				if (!directorsMap.containsKey(currName)) {
					ArrayList<String> currList = new ArrayList<String>();
					currList.add(currMovieTitle);
					// System.out.println(currName + " was added in the map");
					directorsMap.put(currName, currList);
				} else if (directorsMap.containsKey(currName)) {
					ArrayList<String> newList = directorsMap.get(currName);
					newList.add(currMovieTitle);
					directorsMap.put(currName, newList);
					// System.out.println("one movie added to the " + currName);
				}
				// System.out.println("");
			}
		}
		return directorsMap;
	}

	public void testMovies() {
		String filename = "data/ratedmoviesfull.csv";
		ArrayList<Movie> moviesInfo = loadMovies(filename);

		System.out.println(moviesInfo.size() + " movies found:");

		String type = "Comedy";
		int countsGenre = returnGenre(type);
		System.out.println(countsGenre + " movies found with genre type: " + type);

		int time = 150;
		int countsRunTime = returnRunTime(time);
		System.out.println(countsRunTime + " movies found with runtime more than " + time + " minutes");

		HashMap<String, ArrayList<String>> directorsMap = returnDirectors();
		System.out.println(directorsMap.size() + " directors found");

		int mostMovies = 0;
		String maxFilmDirect = null;
		for (String s : directorsMap.keySet()) {
			ArrayList<String> currMovieTitle = directorsMap.get(s);
			int currNumberOfMovies = currMovieTitle.size();
			if (currNumberOfMovies > mostMovies) {
				mostMovies = currNumberOfMovies;
				maxFilmDirect = s;
			}
		}

		System.out.println("The max number of films directed by one director are: " + mostMovies + " : "
				+ directorsMap.get(maxFilmDirect) + " and the name of that director is: " + maxFilmDirect);
	}

	public ArrayList<Rater> loadRaters(String filename) {

		String currUserID = "0";
		int index = -1;

		try (BufferedReader br = new BufferedReader(new FileReader(filename));
				CSVReader csvReader = new CSVReader(br);) {
			
			String[] nextRec;
			while ((nextRec = csvReader.readNext()) != null) {

				if (nextRec[0].equalsIgnoreCase("rater_id")) {
					continue;
				}

				String raterID = nextRec[0];
				// System.out.println("Raters ID is: " + raterID);
				String movieID = nextRec[1];
				// System.out.println("Movies ID is: " + movieID);
				String strUserRating = nextRec[2];
				double userRating = Double.parseDouble(strUserRating);
				// System.out.println("Rating of the movie is: " + userRating);

				if (!currUserID.equals(raterID)) {
					Rater raterInfo = new EfficientRater(raterID);
					raterInfo.addRating(movieID, userRating);
					ratersList.add(raterInfo);

					currUserID = raterID;
					index += 1;
				} else if (currUserID.equals(raterID)) {
					ratersList.get(index).addRating(movieID, userRating);
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("The file " + filename + " doesn't exist...");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return ratersList;
	}

	public void testLoadRaters() {
		String filename = "data/ratings.csv";
		ArrayList<Rater> ratersList = loadRaters(filename);
		System.out.println(ratersList.size() + " raters found...");
	}

	public int numberOfRatingsByUser(String raterID) {
		int numbOfRatings = 0;
		for (Rater r : ratersList) {
			String currRater = r.getID();
			if (currRater.equals(raterID)) {
				numbOfRatings = r.numRatings();
			}
		}
		return numbOfRatings;
	}

	public int maxRate() {
		int maxRating = 0;
		for (Rater r : ratersList) {
			int currNumberOfRatings = r.numRatings();
			if (currNumberOfRatings > maxRating) {
				maxRating = currNumberOfRatings;
			}
		}
		return maxRating;
	}

	public void maxRatings() {
		for (Rater r : ratersList) {
			int currRating = r.numRatings();
			if (currRating == maxRate()) {
				System.out.println("User with ID " + r.getID() + " has the most ratings " + currRating);
			}
		}
	}

	public int numbOfRatingsOfMovie(String movieID) {
		int numbRatings = 0;
		for (Rater r : ratersList) {
			ArrayList<String> currMoviesIDList = r.getItemsRated();
			for (String m : currMoviesIDList) {
				if (m.equals(movieID)) {
					numbRatings += 1;
				}
			}
		}
		return numbRatings;
	}

	public ArrayList<String> differentMoviesRated() {
		ArrayList<String> ratedMoviesList = new ArrayList<String>();
		for (Rater r : ratersList) {
			ArrayList<String> currMoviesIDList = r.getItemsRated();
			for (String m : currMoviesIDList) {
				if (!ratedMoviesList.contains(m)) {
					ratedMoviesList.add(m);
				}
			}
		}
		return ratedMoviesList;
	}

	public void testRatings() {
		String filename = "data/ratings.csv";
		ArrayList<Rater> ratersList = loadRaters(filename);
		String raterID = "193";
		int numbOfRatings = numberOfRatingsByUser(raterID);
		System.out.println("user with ID " + raterID + " rated " + numbOfRatings + " movies");

		maxRatings();

		String movieID = "1798709";
		int ratingsNumb = numbOfRatingsOfMovie(movieID);
		System.out.println("Movie with ID " + movieID + " has " + ratingsNumb + " ratings");

		ArrayList<String> ratedMoviesList = differentMoviesRated();
		System.out.println(ratedMoviesList.size() + " movies are rated");
	}

}
