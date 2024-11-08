package OkHttp;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;

public class Base {
    private String apikey;
    private String token;
    private String baseURL;
    public OkHttpClient client;

    public void setup () {
        Dotenv dotenv = Dotenv.load();

        this.apikey = dotenv.get("TRELLO_API_KEY");
        this.token = dotenv.get("TRELLO_TOKEN");
        this.baseURL = dotenv.get("URL");
        this.client = new OkHttpClient();
    }

    public String getApikey() {
        return apikey;
    }
    public String getToken() {
        return token;
    }
    public String getBaseURL() {
        return baseURL;
    }
    public OkHttpClient getClient() {
        return client;
    }

    public static void main (String [] args){
        Base base = new Base();
        base.setup();

        System.out.println("API KEY: " + base.baseURL);
        System.out.println("TOKEN: " + base.token);
        System.out.println("URL: " + base.baseURL);
        System.out.println("CLIENT: " + base.client);
    }
}
