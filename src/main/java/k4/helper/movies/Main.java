package k4.helper.movies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	private static SiteDownloader connector = new SiteDownloader();
	private static SiteParser parser = new SiteParser();
	private static ArrayList<String> finalList = new ArrayList<String>();
	private static HashMap<String, String> finalMap = new HashMap<String, String>();

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Podaj pierwszego użytkownika: ");
		String name1 = input.nextLine();
		System.out.print("Podaj drugiego użytkownika: ");
		String name2 = input.nextLine();
		input.close();
		getMovieList(name1, name2);

	}

	public static void getMovieList(String username1, String username2) {

		User user1 = new User(username1);
		User user2 = new User(username2);

		String site1 = connector.getWatchlist(user1);
		String site2 = connector.getWatchlist(user2);

		user1.setWatchlist(parser.parseSourceForMovieList(site1));
		user2.setWatchlist(parser.parseSourceForMovieList(site2));

		finalList = user1.compareTo(user2);
		System.out.println("Znaleziono " + finalList.size()
				+ " wspólnych filmów!");
		for (String movie : finalList) {
			System.out.println(movie);
			finalMap.put(movie, user1.getWatchlist().get(movie));
		}

		String movie1 = connector.getMovie(finalMap.get(finalList.get(0)));

		System.out.println(getYear(movie1));
		System.out.println(getDirector(movie1));
		System.out.println(getGenre(movie1));
		System.out.println(getProduction(movie1));
		System.out.println(getPoster(movie1));
	}

	private static String getPoster(String movie) {
		Document doc = Jsoup.parse(movie);
		Elements content = doc.getElementsByAttributeValue("rel", "v:image");
		return content.attr("href");
	}

	private static String getGenre(String movie) {
		Document doc = Jsoup.parse(movie);
		Elements content = doc.getElementsByAttributeValueContaining("href",
				"/search/film?genreIds=");
		String result = "";
		for (Element element : content) {
			result += (element.text() + ", ");
		}
		result = result.substring(0, result.length() - 2);
		return result;
	}

	private static String getProduction(String movie) {
		Document doc = Jsoup.parse(movie);
		Elements content = doc.getElementsByAttributeValueContaining("href",
				"/search/film?countryIds=");
		String result = "";
		for (Element element : content) {
			result += (element.text() + ", ");
		}
		result = result.substring(0, result.length() - 2);
		return result;
	}

	private static String getYear(String movie) {
		Document doc = Jsoup.parse(movie);
		Elements content = doc.getElementsByClass("halfsize");
		String result = content.text();
		return result.substring(1, result.length() - 1);
	}

	private static String getDirector(String movie) {
		Document doc = Jsoup.parse(movie);
		Elements content = doc.getElementsByAttributeValue("rel",
				"v:directedBy");
		return content.text();
	}
}
