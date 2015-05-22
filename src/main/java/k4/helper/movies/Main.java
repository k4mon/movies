package k4.helper.movies;

import java.util.*;


public class Main
{


    private final static Random GENERATOR = new Random();
    private static Scanner input = new Scanner( System.in );

    public static void main( String[] args )
    {

        System.out.print("Podaj pierwszego użytkownika: ");
        String name1 = input.nextLine();
        System.out.print("Podaj drugiego użytkownika: ");
        String name2 = input.nextLine();
        if( name2.equals( "" ) )
        {
            User user = new User(name1);
            getMovieList( user );
            MovieMapHandler.serializeUserMovieMapToFile(user);
        }
        else
        {
            User user1 = new User(name1);
            User user2 = new User(name2);
            getMovieList( user1, user2 );
            MovieMapHandler.serializeUserMovieMapToFile(user1);
            MovieMapHandler.serializeUserMovieMapToFile(user2);
        }
        input.close();
    }


    private static void getMovieList( User user )
    {
        HashMap<String,Movie> userWatchlist = new HashMap<String,Movie>(user.getWatchlist());
        System.out.println( "Lista ma " + userWatchlist.size() + " pozycji!" );

        System.out.print( "Wybierz gatunek: " );
        String genre = input.nextLine();
        System.out.println( "Twój wybrany gatunek to " + genre );
        if( !genre.equals( "" ) )
        {
            Iterator<Map.Entry<String, Movie>> iterator = userWatchlist.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, Movie> entry = iterator.next();
                if (!entry.getValue().getGenre().contains(genre)){
                    iterator.remove();
                }
            }
        }

        System.out.println("Znaleziono " + userWatchlist.size() + " wspólnych filmów o gatunku " + genre);

        List<String> valuesList = new ArrayList<String>(userWatchlist.keySet());
        int randomMovieNumber = GENERATOR.nextInt( valuesList.size() );
        String randomMovieKey = valuesList.get(randomMovieNumber);

        Movie finalMovie = userWatchlist.get( randomMovieKey );

        System.out.println( "Twój film na dzisiaj to: " + finalMovie.getName() );
        System.out.println( finalMovie.getYear() );
        System.out.println( finalMovie.getDirector() );
        System.out.println( finalMovie.getGenre() );
        System.out.println( finalMovie.getProduction() );
        System.out.println( finalMovie.getPosterURL() );
        System.out.println( finalMovie.getDescription() );
    }


    private static void getMovieList( User user1, User user2 )
    {
        HashMap<String, Movie> listOfBothUsersMovies = MovieMapHandler.createSharedMovieMap(user1, user2);
        System.out.println( "Znaleziono " + listOfBothUsersMovies.size() + " wspólnych filmów!" );

        System.out.print( "Wybierz gatunek: " );
        String genre = input.nextLine();
        System.out.println( "Twój wybrany gatunek to " + genre );
        if( !genre.equals( "" ) )
        {
            Iterator<Map.Entry<String, Movie>> iterator = listOfBothUsersMovies.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, Movie> entry = iterator.next();
                if (!entry.getValue().getGenre().contains(genre)){
                    iterator.remove();
                }
            }
        }

        if(listOfBothUsersMovies.isEmpty()){
            System.out.println( "Nie znaleziono wspólnych filmów o gatunku " + genre );
        }
        else{
            System.out.println( "Znaleziono " + listOfBothUsersMovies.size() + " wspólnych filmów o gatunku " + genre );

            List<String> valuesList = new ArrayList<String>(listOfBothUsersMovies.keySet());
            int randomMovieNumber = GENERATOR.nextInt( valuesList.size() );
            String randomMovieKey = valuesList.get(randomMovieNumber);

            Movie finalMovie = listOfBothUsersMovies.get( randomMovieKey );

            System.out.println( "Film wylosowany dla Was to: " + finalMovie.getName() );
            System.out.println( finalMovie.getYear() );
            System.out.println( finalMovie.getDirector() );
            System.out.println( finalMovie.getGenre() );
            System.out.println( finalMovie.getProduction() );
            System.out.println( finalMovie.getPosterURL() );
            System.out.println( finalMovie.getDescription() );
        }
    }
}
