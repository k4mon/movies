package k4.helper.movies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) {

		String url1 = "http://www.filmweb.pl/user/kamon4/films/wanna-see";
		String url2 = "http://www.filmweb.pl/user/mrukot/films/wanna-see";

		String site1 = getSite(url1);
		String site2 = getSite(url2);

		Document doc1 = Jsoup.parse(site1);
		Document doc2 = Jsoup.parse(site2);

		ArrayList<String> list1 = getMovieList(doc1);
		ArrayList<String> list2 = getMovieList(doc2);

		list1.retainAll(list2);
		System.out.println(list1.size());
		for (String movie : list1) {
			System.out.println(movie);
		}

	}

	private static ArrayList<String> getMovieList(Document doc) {
		ArrayList<String> temp = new ArrayList<String>();
		Elements content1 = doc.getElementsByAttribute("href");
		for (org.jsoup.nodes.Element element : content1) {
			Elements content2 = element.getElementsByAttributeValueContaining(
					"href", "/film/");
			for (org.jsoup.nodes.Element element2 : content2) {
				Elements content3 = element2.getElementsByAttributeValueNot(
						"class", "fNoImg0");
				for (org.jsoup.nodes.Element element3 : content3) {
					Elements content4 = element3.getElementsByAttribute("href");
					for (int i = 0; i < content4.size(); i++) {
						temp.add(content4.text());
					}
				}
			}
		}

		return temp;
	}

	private static String getSite(String url) {
		System.out.println("Getting list for: " + url);
		try {
			URL connector = new URL(url);
			HttpURLConnection httpConnection = (HttpURLConnection) connector
					.openConnection();

			httpConnection.addRequestProperty("Host", "www.filmweb.pl");
			httpConnection.addRequestProperty("Connection", "keep-alive");
			httpConnection.addRequestProperty("Cache-Control", "max-age=0");
			httpConnection
					.addRequestProperty("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			httpConnection
					.addRequestProperty(
							"User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
			httpConnection.addRequestProperty("Accept-Encoding",
					"gzip,deflate,sdch");
			httpConnection.addRequestProperty("Accept-Language",
					"en-US,en;q=0.8");
			HttpURLConnection.setFollowRedirects(false);
			httpConnection.setInstanceFollowRedirects(false);
			httpConnection.setDoOutput(true);
			httpConnection.setUseCaches(true);

			httpConnection.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(
					httpConnection.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuilder a = new StringBuilder();
			while ((inputLine = in.readLine()) != null)
				a.append(inputLine);
			in.close();
			httpConnection.disconnect();
			return a.toString();
		} catch (MalformedURLException e) {
			System.out.println("Failed when setting URL!" + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Failed when setting HttpURLConnection!" + e);
			e.printStackTrace();
		}

		return "";

	}
}
