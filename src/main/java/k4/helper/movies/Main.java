package k4.helper.movies;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	private static SiteDownloader connector = new SiteDownloader();
	private static SiteParser parser = new SiteParser();
	private static ArrayList<String> finalList = new ArrayList<String>();
	private final static Random GENERATOR = new Random();

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Podaj pierwszego użytkownika: ");
		String name1 = input.nextLine();
		System.out.print("Podaj drugiego użytkownika: ");
		String name2 = input.nextLine();
		input.close();
		if (name2.equals("")) {
			getMovieList(name1);
		} else {
			getMovieList(name1, name2);
		}
	}

	private static void getMovieList(String username) {

		User user = new User(username);
		String site = connector.getWatchlist(user);
		user.setWatchlist(parser.parseSourceForMovieList(site));
		ArrayList<String> list = user.getWatchlist();
		System.out.println("Lista ma " + list.size() + " pozycji!\n");
		int randomMovieNumber = GENERATOR.nextInt(list.size());
		String movieURL = user.getWatchlist().get(randomMovieNumber);

		String movieData = connector.getMovie(movieURL);

		System.out.println("Twój film na dzisiaj to: " + getName(movieData));
		System.out.println(getYear(movieData));
		System.out.println(getDirector(movieData));
		System.out.println(getGenre(movieData));
		System.out.println(getProduction(movieData));
		System.out.println(getPoster(movieData));
		System.out.println(getDescription(movieURL));
	}

	private static void getMovieList(String username1, String username2) {

		User user1 = new User(username1);
		User user2 = new User(username2);

		String site1 = connector.getWatchlist(user1);
		String site2 = connector.getWatchlist(user2);

		user1.setWatchlist(parser.parseSourceForMovieList(site1));
		user2.setWatchlist(parser.parseSourceForMovieList(site2));

		finalList = user1.compareTo(user2);
		System.out.println("Znaleziono " + finalList.size()
				+ " wspólnych filmów!\n");

		int randomMovieNumber = GENERATOR.nextInt(finalList.size());

		String movieData = connector.getMovie(finalList.get(randomMovieNumber));

		System.out.println("Film wylosowany dla Was to: " + getName(movieData)
				+ " !");
		System.out.println(getYear(movieData));
		System.out.println(getDirector(movieData));
		System.out.println(getGenre(movieData));
		System.out.println(getProduction(movieData));
		System.out.println(getPoster(movieData));
	}

	private static String getName(String movie) {
		Document doc = Jsoup.parse(movie);
		Elements content = doc.getElementsByAttributeValue("property",
				"og:title");
		return content.attr("content");

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

	private static String getDescription(String movieURL) {
		String descMovieURL = movieURL + "/descs";
		String movieDescData = connector.getMovie(descMovieURL);
		Document doc = Jsoup.parse(movieDescData);
		Element content = getDescriptionTag(doc);
		return content.text();
	}

	private static Element getDescriptionTag(Document doc) {
		return doc.getElementsByClass("def").get(0)
				.getElementsByAttributeValueMatching("class", "text").get(0);
	}
}
