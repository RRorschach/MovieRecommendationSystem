
public class Testing {

	public static void main(String[] args) {

//		MovieRunnerSimilarRatings mrsr = new MovieRunnerSimilarRatings();
//		mrsr.printAverageRatings();
//		mrsr.printSimilarRatingsByYearAfterAndMinutes();
		
		MovieRunnerWithFilters mrf = new MovieRunnerWithFilters();
		//mrf.printAverageRatings();
		mrf.printAverageRatingsByDirectorsAndMinutes();
	}

}
