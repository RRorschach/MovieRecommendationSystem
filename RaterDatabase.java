import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.CSVReader;

public class RaterDatabase {

	private static HashMap<String, Rater> ourRaters;

	private static void initialize() {
		// this method is only called from addRatings
		if (ourRaters == null) {
			ourRaters = new HashMap<String, Rater>();
		}
	}

	public static void initialize(String filename) {
		if (ourRaters == null) {
			ourRaters = new HashMap<String, Rater>();
			addRatings("data/" + filename);
		}
	}

	public static void addRatings(String filename) {
		initialize();

		try (BufferedReader br = new BufferedReader(new FileReader(filename));
				CSVReader csvReader = new CSVReader(br);) {

			String[] nextRec;

			while ((nextRec = csvReader.readNext()) != null) {
				if (nextRec[0].equalsIgnoreCase("rater_id")) {
					continue;
				}

				String id = nextRec[0];
				String item = nextRec[1];
				String rating = nextRec[2];
				addRaterRating(id, item, Double.parseDouble(rating));
			}
		} catch (FileNotFoundException e) {
			System.out.println("The file " + filename + " doesn't exist...");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addRaterRating(String raterID, String movieID, double rating) {
		initialize();
		Rater rater = null;
		if (ourRaters.containsKey(raterID)) {
			rater = ourRaters.get(raterID);
		} else {
			rater = new EfficientRater(raterID);
			ourRaters.put(raterID, rater);
		}
		rater.addRating(movieID, rating);
	}

	public static Rater getRater(String id) {
		initialize();

		return ourRaters.get(id);
	}

	public static ArrayList<Rater> getRaters() {
		initialize();
		ArrayList<Rater> list = new ArrayList<Rater>(ourRaters.values());

		return list;
	}

	public static int size() {
		return ourRaters.size();
	}
}
