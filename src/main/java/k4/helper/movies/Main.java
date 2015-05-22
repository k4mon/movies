package k4.helper.movies;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.google.common.collect.Lists;


public class Main
{

    private static SiteDownloader connector = new SiteDownloader();
    private static SiteParser parser = new SiteParser();
    private final static Random GENERATOR = new Random();
    private static Scanner input = new Scanner( System.in );


    public static void main( String[] args )
    {

        System.out.print( "Podaj pierwszego użytkownika: " );
        String name1 = input.nextLine();
        System.out.print( "Podaj drugiego użytkownika: " );
        String name2 = input.nextLine();
        if( name2.equals( "" ) )
        {
            getMovieList( name1 );
        }
        else
        {
            getMovieList( name1, name2 );
        }
        input.close();
    }


    private static void getMovieList( String username )
    {
        User user = new User( username );
        user.setWatchlist( createWatchList( user ) );
        ArrayList<Movie> userWatchlist = user.getWatchlist();
        System.out.println( "Lista ma " + user.getWatchlist().size() + " pozycji!" );

        System.out.print( "Wybierz gatunek: " );
        String genre = input.nextLine();
        System.out.println( "Twój wybrany gatunek to " + genre );
        if( !genre.equals( "" ) )
        {

            for( Movie movie : user.getWatchlist() )
            {
                if( movie.getGenre().contains( genre ) )
                {
                    userWatchlist.remove( movie );
                }
            }
        }

        System.out.println( "Znaleziono " + userWatchlist.size() + " wspólnych filmów o gatunku " + genre );

        int randomMovieNumber = GENERATOR.nextInt( userWatchlist.size() );
        Movie finalMovie = userWatchlist.get( randomMovieNumber );

        System.out.println( "Twój film na dzisiaj to: " + finalMovie.getName() );
        System.out.println( finalMovie.getYear() );
        System.out.println( finalMovie.getDirector() );
        System.out.println( finalMovie.getGenre() );
        System.out.println( finalMovie.getProduction() );
        System.out.println( finalMovie.getPosterURL() );
        System.out.println( finalMovie.getDescription() );
    }


    private static ArrayList<Movie> createWatchList( User user )
    {
        ArrayList<Movie> movieList = Lists.newArrayList();
        for( String movieMidfix : parser.parseWatchlistSourceForMovieList( connector.getWatchlistForUser( user ) ) )
        {
            String movieSource = connector.getMovie( movieMidfix );
            String descriptionSource = connector.getMovieDescription( movieMidfix );
            movieList.add( parser.parseMovieSourceForMovie( movieSource, descriptionSource ) );
        }
        return movieList;
    }


    private static void getMovieList( String username1, String username2 )
    {
        ArrayList<Movie> listOfBothUsersMovies = Lists.newArrayList();
        User user1 = new User( username1 );
        User user2 = new User( username2 );
        user1.setWatchlist( createWatchList( user1 ) );
        user2.setWatchlist( createWatchList( user2 ) );

        listOfBothUsersMovies = user1.compareTo( user2 );
        System.out.println( "Znaleziono " + listOfBothUsersMovies.size() + " wspólnych filmów!" );

        System.out.print( "Wybierz gatunek: " );
        String genre = input.nextLine();
        System.out.println( "Twój wybrany gatunek to " + genre );
        if( !genre.equals( "" ) )
        {

            for( Movie movie : listOfBothUsersMovies )
            {
                if( !movie.getGenre().contains( genre ) )
                {
                    listOfBothUsersMovies.remove( movie );
                }
            }
        }

        System.out.println( "Znaleziono " + listOfBothUsersMovies.size() + " wspólnych filmów o gatunku " + genre );

        int randomMovieNumber = GENERATOR.nextInt( listOfBothUsersMovies.size() );
        Movie finalMovie = listOfBothUsersMovies.get( randomMovieNumber );

        System.out.println( "Film wylosowany dla Was to: " + finalMovie.getName() );
        System.out.println( finalMovie.getYear() );
        System.out.println( finalMovie.getDirector() );
        System.out.println( finalMovie.getGenre() );
        System.out.println( finalMovie.getProduction() );
        System.out.println( finalMovie.getPosterURL() );
        System.out.println( finalMovie.getDescription() );
    }

}
