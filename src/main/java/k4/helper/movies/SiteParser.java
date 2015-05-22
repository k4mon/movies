package k4.helper.movies;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SiteParser
{

    public ArrayList<String> parseWatchlistSourceForMovieList( String site )
    {
        ArrayList<String> list = new ArrayList<String>();
        Document doc = Jsoup.parse( site );
        getMovieTitlesFromContent( doc, list );
        return list;
    }


    public Movie parseMovieSourceForMovie( String movieSource, String descriptionSource )
    {
        Movie movie = new Movie();
        movie.setName( getMovieNameFromSource( movieSource ) );
        movie.setYear( getMovieYearFromSource( movieSource ) );
        movie.setDirector( getMovieDirectorFromSource( movieSource ) );
        movie.setGenre( getMovieGenreFromSource( movieSource ) );
        movie.setProduction( getMovieProductionFromSource( movieSource ) );
        movie.setDescription( getMovieDescriptionFromSource( descriptionSource ) );
        movie.setPosterURL( getPosterURLFromSource( movieSource ) );

        return movie;
    }


    private void getMovieTitlesFromContent( Document doc, List<String> list )
    {

        Elements content1 = doc.getElementsByAttribute( "href" );

        for( org.jsoup.nodes.Element element : content1 )
        {
            if( element.hasClass( "fNoImg0" ) )
            {
                list.add( element.attr( "href" ) );
            }
        }
    }


    private String getMovieNameFromSource( String movieSource )
    {
        Document doc = Jsoup.parse( movieSource );
        Elements content = doc.getElementsByAttributeValue( "property", "og:title" );
        return content.attr( "content" );
    }


    private String getMovieYearFromSource( String movieSource )
    {
        Document doc = Jsoup.parse( movieSource );
        Elements content = doc.getElementsByClass( "halfsize" );
        String result = content.text();
        return result.substring( 1, result.length() - 1 );
    }


    private String getMovieDirectorFromSource( String movieSource )
    {
        Document doc = Jsoup.parse( movieSource );
        Elements content = doc.getElementsByAttributeValue( "rel", "v:directedBy" );
        return content.text();
    }


    private String getMovieGenreFromSource( String movieSource )
    {
        Document doc = Jsoup.parse( movieSource );
        Elements content = doc.getElementsByAttributeValueContaining( "href", "/search/film?genreIds=" );
        String result = "";
        for( Element element : content )
        {
            result += (element.text() + ", ");
        }
        result = result.substring( 0, result.length() - 2 );
        return result;
    }


    private String getMovieProductionFromSource( String movieSource )
    {
        Document doc = Jsoup.parse( movieSource );
        Elements content = doc.getElementsByAttributeValueContaining( "href", "/search/film?countryIds=" );
        String result = "";
        for( Element element : content )
        {
            result += (element.text() + ", ");
        }
        result = result.substring( 0, result.length() - 2 );
        return result;
    }


    private String getMovieDescriptionFromSource( String descriptionSource )
    {
        Document doc = Jsoup.parse( descriptionSource );
        Element content = doc.getElementsByClass( "def" ).get( 0 ).getElementsByAttributeValueMatching( "class", "text" ).get( 0 );
        return content.text();
    }


    private String getPosterURLFromSource( String movieSource )
    {
        Document doc = Jsoup.parse( movieSource );
        Elements content = doc.getElementsByAttributeValue( "rel", "v:image" );
        return content.attr( "href" );
    }

}
