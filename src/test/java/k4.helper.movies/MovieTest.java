package k4.helper.movies;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kamon on 2015-05-22.
 */
public class MovieTest {

    private Movie movie;

    @Before
    public void setUp(){
        movie = new Movie();
        movie.setMovieURL("URL");
        movie.setDescription("description");
        movie.setDirector("director");
        movie.setGenre("genre");
        movie.setName("name");
        movie.setPosterURL("posterURL");
        movie.setProduction("production");
        movie.setYear("1999");
    }

    @Test
    public void movieObjectSerializesIntoFile(){
        assertEquals(movie.getDescription(), "description");
    }
}
