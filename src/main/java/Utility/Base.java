package Utility;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class Base {
    private String apikey;
    private String token;
    private String baseURL;

    @BeforeClass
    public void setup(){
        Dotenv dotenv = Dotenv.load();

        //Inicializar API KEY Y TOKEN
        this.apikey = dotenv.get("TRELLO_API_KEY");
        this.token = dotenv.get("TRELLO_TOKEN");

        //Configurar restAssure
        this.baseURL = "https://api.trello.com/1";
        RestAssured.baseURI = baseURL;
    }

    public String getApikey() {
        return apikey;
    }

    public String getToken() {
        return token;
    }

    public String getBaseURL(){
        return baseURL;
    }

    public static void main(String[]args){
        Base baseTest = new Base();
        baseTest.setup();
        System.out.println("API KEY: " + baseTest.getApikey());
        System.out.println("TOKEN: " + baseTest.getToken());
        System.out.println("BASE URL: " + baseTest.getBaseURL());
    }

}