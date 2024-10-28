package Retrofit;

import io.github.cdimascio.dotenv.Dotenv;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Base {
    private String apikey;
    private String token;
    private String baseURL;
    private Retrofit retrofit;


    public Base(){
        Dotenv dotenv = Dotenv.load();

        apikey = dotenv.get("TRELLO_API_KEY");
        token = dotenv.get("TRELLO_TOKEN");
        baseURL = dotenv.get("URL");

        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public String getApikey() {
        return apikey;
    }
    public String getToken() {
        return token;
    }public String getBaseURL() {
        return baseURL;
    }public Retrofit getRetrofit() {
        return retrofit;
    }


    public class BoardInfo {
        private String id;
        private String avatarUrl;
        private String url;
        private String email;
        private String fullName;
        private String username;
        private String memberType;

        public String getId() { return id; }
        public String getAvatarUrl() { return avatarUrl; }
        public String getUrl() { return url; }
        public String getEmail() { return email; }
    }

    public static void main (String[]args){
        Base base = new Base();

        System.out.println("KEY: " + base.getApikey());
        System.out.println("TOKEN: " + base.getToken());
        System.out.println("URL:  " + base.getBaseURL());
    }
}
