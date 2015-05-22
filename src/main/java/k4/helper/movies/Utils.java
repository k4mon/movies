package k4.helper.movies;

/**
 * Created by Kamon on 2015-05-22.
 */
public final class Utils {

    private final static String RESOURCES = "resources/";
    private final static String EXTENSION = ".ser";

    public static String createUserFileName(String username) {
        return RESOURCES + username + EXTENSION;
    }
}
