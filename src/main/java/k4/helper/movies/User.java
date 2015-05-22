package k4.helper.movies;

import java.util.HashMap;


public class User
{

    private String username;
    private HashMap<String, Movie> watchlist;

    public User( String username )
    {
        setUsername( username );
        setWatchlist(MovieMapHandler.createMovieMap(username));
    }


    public String getUsername()
    {
        return username;
    }


    public void setUsername( String username )
    {
        this.username = username;
    }

    public HashMap<String, Movie> getWatchlist()
    {
        return watchlist;
    }


    public void setWatchlist( HashMap<String, Movie> watchlist )
    {
        this.watchlist = watchlist;
    }





}
