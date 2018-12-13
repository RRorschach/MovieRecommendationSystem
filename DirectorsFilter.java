
public class DirectorsFilter implements Filter {

	private String myDirector;

	public DirectorsFilter(String directors) {
		myDirector = directors;
	}

	@Override
	public boolean satisfies(String id) {
		String[] myNewDirector = myDirector.trim().split(",");
		String currDirectors = MovieDatabase.getDirector(id);
		String[] directors = currDirectors.trim().split(",");

		for (int k = 0; k < directors.length; k++) {
			for (String s : myNewDirector) {
				if (directors[k].trim().equals(s)) {
					return true;
				}
			}
		}
		return false;
	}
}
