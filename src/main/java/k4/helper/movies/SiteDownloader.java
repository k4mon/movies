package k4.helper.movies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class SiteDownloader
{

    private static final String GENERAL_PREFIX = "http://www.filmweb.pl";
    private static final String WATCHLIST_PREFIX = "/user/";
    private static final String WATCHLIST_POSTFIX = "/films/wanna-see";
    private static final String DESCRIPTION_POSTFIX = "/descs";


    public String getWatchlistForUser( String username )
    {
        String result = "";
        try
        {
            result = tryToGetWatchlistForUser( username );

        }
        catch( MalformedURLException e )
        {
            System.out.println( "Failed when setting URL!" + e );
            e.printStackTrace();
        }
        catch( IOException e )
        {
            System.out.println( "Failed when setting HttpURLConnection!" + e );
            e.printStackTrace();
        }

        return result;
    }


    public String getMovie( String titleURLPostfix )
    {
        String result = "";
        try
        {
            result = tryToGetMovieForTitleURLPostfix(titleURLPostfix);

        }
        catch( MalformedURLException e )
        {
            System.out.println( "Failed when setting URL!" + e );
            e.printStackTrace();
        }
        catch( IOException e )
        {
            System.out.println( "Failed when setting HttpURLConnection!" + e );
            e.printStackTrace();
        }

        return result;
    }


    public String getMovieDescription( String titleURLPostfix )
    {
        String result = "";
        try
        {
            result = tryToGetMovieDescriptionForTitleURLPostfix(titleURLPostfix);

        }
        catch( MalformedURLException e )
        {
            System.out.println( "Failed when setting URL!" + e );
            e.printStackTrace();
        }
        catch( IOException e )
        {
            System.out.println( "Failed when setting HttpURLConnection!" + e );
            e.printStackTrace();
        }

        return result;
    }


    private String tryToGetWatchlistForUser( String username ) throws IOException
    {
        URL url = createURLForUserWatchlist( username );
        return tryToGetSourceForURL( url );
    }


    private String tryToGetMovieForTitleURLPostfix( String titleURLPostfix ) throws IOException
    {
        URL url = createURLForMovieFromPostfix(titleURLPostfix);
        return tryToGetSourceForURL( url );
    }


    private String tryToGetMovieDescriptionForTitleURLPostfix( String titleURLPostfix ) throws IOException
    {
        URL url = createURLForMovieDescriptionFromPostfix(titleURLPostfix);
        return tryToGetSourceForURL( url );
    }


    private String tryToGetSourceForURL( URL url ) throws IOException, UnsupportedEncodingException
    {
        HttpURLConnection connection = openConnection( url );
        setHttpConnectionParameters( connection );
        String result = readSource(connection);
        closeConnection( connection );
        return result;
    }


    private URL createURLForUserWatchlist( String username ) throws MalformedURLException
    {
        return new URL( getAddressForUserWatchlist(username) );
    }

    public String getAddressForUserWatchlist(String username){
        return GENERAL_PREFIX + WATCHLIST_PREFIX + username + WATCHLIST_POSTFIX;
    }


    private URL createURLForMovieFromPostfix( String titleURLPostfix ) throws MalformedURLException
    {
        return new URL( getAddressForMovieFromPostfix(titleURLPostfix) );
    }

    public String getAddressForMovieFromPostfix( String titleURLPostfix ){
        return GENERAL_PREFIX + titleURLPostfix;
    }


    private URL createURLForMovieDescriptionFromPostfix( String titleURLPostfix ) throws MalformedURLException
    {
        return new URL( getAddressForMovieDescriptionFromPostfix(titleURLPostfix) );
    }

    public String getAddressForMovieDescriptionFromPostfix( String titleURLPostfix ){
        return getAddressForMovieFromPostfix(titleURLPostfix) + DESCRIPTION_POSTFIX;
    }


    private HttpURLConnection openConnection( URL url ) throws IOException
    {
        return (HttpURLConnection)url.openConnection();
    }


    private void setHttpConnectionParameters( HttpURLConnection connection ) throws IOException
    {
        connection.addRequestProperty( "Host", "www.filmweb.pl" );
        connection.addRequestProperty( "Connection", "keep-alive" );
        connection.addRequestProperty( "Cache-Control", "max-age=0" );
        connection.addRequestProperty( "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" );
        connection.addRequestProperty(
            "User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36" );
        connection.addRequestProperty( "Accept-Encoding", "gzip,deflate,sdch" );
        connection.addRequestProperty( "Accept-Language", "en-US,en;q=0.8" );
        connection.setFollowRedirects( false );
        connection.setInstanceFollowRedirects( false );
        connection.setDoOutput( true );
        connection.setUseCaches( true );
        connection.setRequestMethod( "GET" );

    }


    private String readSource( HttpURLConnection connection ) throws UnsupportedEncodingException, IOException
    {
        StringBuilder sourceCreator = new StringBuilder();
        BufferedReader in = new BufferedReader( new InputStreamReader( connection.getInputStream(), "UTF-8" ) );
        String inputLine;
        while( (inputLine = in.readLine()) != null )
            sourceCreator.append( inputLine );
        in.close();
        return sourceCreator.toString();
    }


    private void closeConnection( HttpURLConnection connection )
    {
        connection.disconnect();
    }

}
