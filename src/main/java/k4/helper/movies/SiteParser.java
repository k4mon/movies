package k4.helper.movies;

import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SiteParser {

	private ArrayList<String> crap;
	private HashMap<String, String> map;

	public HashMap<String, String> parseSourceForMovieList(String site) {
		crap = new ArrayList<String>();
		map = new HashMap<String, String>();
		Document doc = Jsoup.parse(site);
		getMovieTitlesFromContent(doc);
		cleanResults();
		return map;
	}

	private void cleanResults() {
		crap.remove(crap.size() - 1);
		Integer endOfCrap = crap.indexOf("ranking top500");
		for(String crapTitle : crap.subList(0, endOfCrap + 1)){
			map.remove(crapTitle);
		}
	}

	private void getMovieTitlesFromContent(Document doc) {
		Elements content1 = doc.getElementsByAttribute("href");
		for (org.jsoup.nodes.Element element : content1) {
			Elements content2 = element.getElementsByAttributeValueContaining(
					"href", "/film/");
			for (org.jsoup.nodes.Element element2 : content2) {
				Elements content3 = element2.getElementsByAttributeValueNot(
						"class", "fNoImg0");
				for (org.jsoup.nodes.Element element3 : content3) {
					Elements content4 = element3.getElementsByAttribute("href");
					System.out.println(content4);
					for (int i = 0; i < content4.size(); i++) {
						if(content4 != null){
							map.put(content4.text(), content4.attr("href"));
							crap.add(content4.text());
							System.out.println("dodano adres: "
									+ map.get(content4.text())
									+ " do mapy, z kluczem: " + content4.text());
					}
					}
				}
			}
		}
	}
}
