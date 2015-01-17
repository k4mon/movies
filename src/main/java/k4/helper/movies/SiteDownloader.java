package k4.helper.movies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SiteDownloader {
	
	private URL connector;
	private HttpURLConnection httpConnection;
	private StringBuilder result;
	
	public String getWatchlist(User user){
		System.out.println("Getting list for: " + user.getWatchlistURL());
		result = new StringBuilder();
		try {
			openConnection(user);
			setHttpConnectionParameters();
			readSource();
			closeConnection();
		} catch (MalformedURLException e) {
			System.out.println("Failed when setting URL!" + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Failed when setting HttpURLConnection!" + e);
			e.printStackTrace();
		}
		return result.toString();
	}
	
	public String getMovie(String titleURLPostfix){
		result = new StringBuilder();
		try {
			openConnectionToMovie(titleURLPostfix);
			setHttpConnectionParameters();
			readSource();
			closeConnection();
		} catch (MalformedURLException e) {
			System.out.println("Failed when setting URL!" + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Failed when setting HttpURLConnection!" + e);
			e.printStackTrace();
		}
		return result.toString();
	}

	private void closeConnection() {
		httpConnection.disconnect();
	}

	private void readSource() throws UnsupportedEncodingException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				httpConnection.getInputStream(), "UTF-8"));
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			result.append(inputLine);
		in.close();
	}

	private void setHttpConnectionParameters() throws IOException,
			ProtocolException {
		httpConnection = (HttpURLConnection) connector.openConnection();
		httpConnection.addRequestProperty("Host", "www.filmweb.pl");
		httpConnection.addRequestProperty("Connection", "keep-alive");
		httpConnection.addRequestProperty("Cache-Control", "max-age=0");
		httpConnection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
		httpConnection.addRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
		httpConnection.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
		HttpURLConnection.setFollowRedirects(false);
		httpConnection.setInstanceFollowRedirects(false);
		httpConnection.setDoOutput(true);
		httpConnection.setUseCaches(true);
		httpConnection.setRequestMethod("GET");
	}

	private void openConnection(User user) throws MalformedURLException {
		connector = new URL(user.getWatchlistURL());
	}
	
	private void openConnectionToMovie(String titleURLPostfix) throws MalformedURLException{
		connector = new URL("http://www.filmweb.pl" + titleURLPostfix);
	}
	
}
