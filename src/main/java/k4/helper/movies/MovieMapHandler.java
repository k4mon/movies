package k4.helper.movies;

import com.google.common.collect.Maps;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Kamon on 2015-05-22.
 */
public class MovieMapHandler {

    private final static String RESOURCES = "resources/";
    private final static String EXTENSION = ".ser";
    private static SiteDownloader connector = new SiteDownloader();
    private static SiteParser parser = new SiteParser();

    public static String createUserFileName(String username) {
        return RESOURCES + username + EXTENSION;
    }

    public static HashMap<String, Movie> createMovieMap(String username) {
        HashMap<String, Movie> movieMap;
        if (isFileIsPresent(username)) {
            movieMap = getMovieMapFromFile(username);
            updateMovieMap(movieMap, username);
        } else {
            movieMap = getMovieMapFromWeb(username);
        }

        return movieMap;
    }

    private static void updateMovieMap(HashMap<String, Movie> movieMap, String username) {
        ArrayList<String> currentMoviePostfixes = parser.parseWatchlistSourceForMovieList(connector.getWatchlistForUser(username));

        Iterator<Map.Entry<String, Movie>> iterator = movieMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, Movie> entry = iterator.next();
            if (currentMoviePostfixes.contains(entry.getKey())){
                currentMoviePostfixes.remove(entry.getKey());
            }
            else{
                iterator.remove();
            }
        }

        for(String newMoviePostfix : currentMoviePostfixes){
            addMovieToMap(movieMap, newMoviePostfix);
        }
    }

    private static HashMap<String, Movie> getMovieMapFromFile(String username) {
        HashMap<String, Movie> movieMap = Maps.newHashMap();
        try {
            FileInputStream fileIn = new FileInputStream(createUserFileName(username));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            movieMap = ((HashMap<String, Movie>) in.readObject());
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Movie class not found");
            c.printStackTrace();
        }
        return movieMap;
    }

    private static HashMap<String, Movie> getMovieMapFromWeb(String username) {
        HashMap<String, Movie> movieMap = Maps.newHashMap();
        for (String moviePostfix : parser.parseWatchlistSourceForMovieList(connector.getWatchlistForUser(username))) {
            try {
                addMovieToMap(movieMap, moviePostfix);
            } catch (Exception e) {
                System.out.println("Wyjebało sie na filmie :" + moviePostfix);
            }
        }
        return movieMap;
    }

    private static void addMovieToMap(HashMap<String, Movie> movieMap, String moviePostfix) {
        String movieSource = connector.getMovie(moviePostfix);
        String descriptionSource = connector.getMovieDescription(moviePostfix);
        Movie movie = parser.parseMovieSourceForMovie(movieSource, descriptionSource);
        movie.setMovieURL(connector.getAddressForMovieFromPostfix(moviePostfix));
        movieMap.put(moviePostfix, movie);
    }

    private static boolean isFileIsPresent(String username) {

        boolean condition = false;
        File f = new File(createUserFileName(username));
        if (f.exists() && !f.isDirectory()) {
            condition = true;
        }
        return condition;
    }

    public static void serializeUserMovieMapToFile(User user) {
        try {
            String fileName = createUserFileName(user.getUsername());
            FileOutputStream fileOut =
                    new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(user.getWatchlist());
            System.out.println("Serializacja udana!");
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static HashMap<String, Movie> createSharedMovieMap(User user1, User user2) {
        HashMap<String, Movie> results = Maps.newHashMap();
        HashMap<String, Movie> mapOfUser1 = user1.getWatchlist();
        HashMap<String, Movie> mapOfUser2 = user2.getWatchlist();

        if (mapOfUser1.size() < mapOfUser2.size()) {
            for (String movieKey : mapOfUser1.keySet()) {
                if (mapOfUser2.keySet().contains(movieKey)) {
                    results.put(movieKey, mapOfUser1.get(movieKey));
                }
            }
        } else {
            for (String movieKey : mapOfUser2.keySet()) {
                if (mapOfUser1.keySet().contains(movieKey)) {
                    results.put(movieKey, mapOfUser2.get(movieKey));
                }
            }
        }
        return results;
    }
}