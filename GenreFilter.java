
public class GenreFilter implements Filter {

	private String myGenre;

	public GenreFilter(String genre) {
		myGenre = genre;
	}

	@Override
	public boolean satisfies(String id) {
		String currGenres = MovieDatabase.getGenres(id);
		String[] genres = currGenres.trim().split(",");
		for (int k = 0; k < genres.length; k++) {
			if (genres[k].trim().equals(myGenre)) {
				return true;
			}
		}
		return false;
	}

}
