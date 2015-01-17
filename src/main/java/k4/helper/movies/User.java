package k4.helper.movies;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

	private String username;
	private final String prefix = "http://www.filmweb.pl/user/";
	private final String watchlistPostfix = "/films/wanna-see";
	private HashMap<String, String> watchlist;
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

	public HashMap<String, String> getWatchlist() {
		return watchlist;
	}

	public void setWatchlist(HashMap<String, String> watchlist) {
		this.watchlist = watchlist;
	}

	public String getWatchlistURL() {
		return watchlistURL;
	}

	public ArrayList<String> compareTo(User user2) {
		ArrayList<String> results = this.getWatchlistTitles();
		results.retainAll(user2.getWatchlistTitles());
		return results;
	}

	public ArrayList<String> getWatchlistTitles() {
		return new ArrayList<String>(watchlist.keySet());
	}

}
