package k4.helper.movies;

import java.util.ArrayList;

public class User {

	private String username;
	private final String prefix = "http://www.filmweb.pl/user/";
	private final String watchlistPostfix = "/films/wanna-see";
	private ArrayList<String> moviesURLs;
	private final String watchlistURL;

	public User(String username) {
		setUsername(username);
		this.watchlistURL = prefix + username + watchlistPostfix;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getWatchlistURL() {
		return watchlistURL;
	}

	public ArrayList<String> compareTo(User user2) {
		ArrayList<String> results = this.getWatchlist();
		results.retainAll(user2.getWatchlist());
		return results;
	}

	public void setWatchlist(ArrayList<String> parseSourceForMovieList) {
		this.moviesURLs = parseSourceForMovieList;
	}

	public ArrayList<String> getWatchlist() {
		return moviesURLs;
	}

}
