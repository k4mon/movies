package k4.helper.movies;

import java.util.ArrayList;
import com.google.common.collect.Lists;


public class User
{

    private String username;
    private ArrayList<Movie> watchlist;


    public ArrayList<Movie> getWatchlist()
    {
        return watchlist;
    }


    public void setWatchlist( ArrayList<Movie> watchlist )
    {
        this.watchlist = watchlist;
    }


    public User( String username )
    {
        setUsername( username );
        watchlist = Lists.newArrayList();
    }


    public String getUsername()
    {
        return username;
    }


    public void setUsername( String username )
    {
        this.username = username;
    }


    public ArrayList<Movie> compareTo( User user2 )
    {
        ArrayList<Movie> results = this.getWatchlist();
        results.retainAll( user2.getWatchlist() );
        return results;
    }

}
