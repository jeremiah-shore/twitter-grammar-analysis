package net.jeremiahshore;

import com.google.gson.Gson;
import net.jeremiahshore.model.Tweet;
import net.jeremiahshore.model.TweetDAO;

import java.io.*;
import java.util.List;

public class Main {
    private static final String TWEET_ID_FILEPATH = Main.class.getClassLoader().getResource("all_ids.json").getPath();
    private static final String TEST_TWEET_ID = "1067030122497138689";

    public static void main(String[] args) {
        Authenticator authenticator = new Authenticator();
        TweetDAO tweetDAO = new TweetDAO(authenticator);
        //getTweetData(tweetDAO);

        try {
            List<Tweet> tweets = tweetDAO.readTweetsFromFile();
            Analyzer analyzer = new Analyzer(tweets);
            analyzer.getWordUseCount();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void getTweetData(TweetDAO tweetDAO) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(TWEET_ID_FILEPATH)));
            String[] tweetIds = new Gson().fromJson(reader.readLine(), String[].class);
            for(int i = 0; i < 500; i++) {
                tweetDAO.fetchTweet(tweetIds[i]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
