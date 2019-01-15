package net.jeremiahshore;

import com.google.gson.Gson;
import net.jeremiahshore.model.Token;
import okhttp3.*;

import java.io.*;

public class Authenticator {
    private static final String CACHE_FILEPATH = Main.class.getClassLoader().getResource("token-cache.json").getPath();
    private static final String APP_ONLY_AUTH_ENDPOINT_URL = "https://api.twitter.com/oauth2/token";
    private static final MediaType CONTENT_TYPE = MediaType.get("application/x-www-form-urlencoded;charset=UTF-8");
    private Token bearerToken;

    public Authenticator() {
        confirmBearerToken();
    }

    public Token getBearerToken() {
        return bearerToken;
    }

    private void confirmBearerToken() {
        try {
            if(isCacheEmpty()) {
                System.out.println("token cache is empty");
                retrieveBearerToken();
            }
            bearerToken = parseToken(readFromCache());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isCacheEmpty() throws IOException {
        String cache = readFromCache();
        return  cache == null || cache.equals("");
    }

    private String readFromCache() throws IOException {
        File file = new File(CACHE_FILEPATH);
        BufferedReader br = new BufferedReader(new FileReader(file));
        return br.readLine();
    }

    private void retrieveBearerToken() throws IOException {
        System.out.println("retrieving bearer token from Twitter API...");
        OkHttpClient client = new OkHttpClient();
        String credential = Credentials.basic(AppConfig.CONSUMER_API_KEY, AppConfig.CONSUMER_API_SECRET_KEY);
        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();
        Request request = new Request.Builder()
                .url(APP_ONLY_AUTH_ENDPOINT_URL)
                .method("POST", requestBody)
                .header("Authorization", credential)
                .build();
        Response response = client.newCall(request).execute();
        //todo: confirm type = bearer, and relevant error handling
        writeToCache(response.body().string());
    }

    private void writeToCache(String json) throws IOException {
        System.out.println("persisting token to cache...");
        BufferedWriter writer = new BufferedWriter(new FileWriter(CACHE_FILEPATH));
        writer.write(json);
        writer.close();
        System.out.println("token cached successfully!");
    }

    private Token parseToken(String json) {
        System.out.println("parsing token...");
        Gson gson = new Gson();
        Token token = gson.fromJson(json, Token.class);
        System.out.println("token created!");
        return token;
    }

}
