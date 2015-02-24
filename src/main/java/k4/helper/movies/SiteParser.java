package k4.helper.movies;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SiteParser {

	private ArrayList<String> list;

	public ArrayList<String> parseSourceForMovieList(String site) {
		list = new ArrayList<String>();
		Document doc = Jsoup.parse(site);
		getMovieTitlesFromContent(doc);
		return list;
	}

	private void getMovieTitlesFromContent(Document doc) {

		Elements content1 = doc.getElementsByAttribute("href");

		for (org.jsoup.nodes.Element element : content1) {
			if (element.hasClass("fNoImg0")) {
				list.add(element.attr("href"));
			}
		}
	}
}
