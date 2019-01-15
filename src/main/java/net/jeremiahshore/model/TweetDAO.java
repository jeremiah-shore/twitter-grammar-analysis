package net.jeremiahshore.model;

import com.google.gson.Gson;
import net.jeremiahshore.AppConfig;
import net.jeremiahshore.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TweetDAO {
    private static final String ENDPOINT_URL_BASE = "https://api.twitter.com/1.1/statuses/show.json?id=" ;
    private Authenticator authenticator;

    public TweetDAO(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public Tweet fetchTweet(String id_str) throws IOException {
        String url = ENDPOINT_URL_BASE + id_str + "&tweet_mode=extended";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + authenticator.getBearerToken().getAccess_token())
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        writeTweetJsonToFile(responseBody);

        Gson gson = new Gson();
        return gson.fromJson(responseBody, Tweet.class);
    }

    public void writeTweetJsonToFile(String tweetJson) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(AppConfig.TWEET_DATA_FILE, true));
        writer.append(tweetJson);
        writer.append("\n");
        writer.close();
    }

    public List<Tweet> readTweetsFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(AppConfig.TWEET_DATA_FILE)));
        List<Tweet> tweets = new ArrayList<>();

        String tweetJson = reader.readLine();
        while(tweetJson != null && !tweetJson.equals("")) {
            Tweet tweet = new Gson().fromJson(tweetJson, Tweet.class);
            tweets.add(tweet);
            tweetJson = reader.readLine();
        }

        return tweets;
    }
}
