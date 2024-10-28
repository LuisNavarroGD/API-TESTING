package Retrofit;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class BuildRequest extends Base {

    // Interfaz que define el endpoint y los parámetros de la solicitud
    public interface Trello {
        @GET("/1/members/me")
        Call<Info> getInfo(
                @Query("key") String key,
                @Query("token") String token);
    }

    // Clase de modelo para los datos de respuesta
    public class Info {
        private String id;
        private String avatarUrl;
        private String url;
        private String email;
        private String fullName;
        private String username;
        private String memberType;

        // Getters para los datos de respuesta
        public String getId() { return id; }
        public String getAvatarUrl() { return avatarUrl; }
        public String getUrl() { return url; }
        public String getEmail() { return email; }
        public String getFullName() { return fullName; }
        public String getUsername() { return username; }
        public String getMemberType() { return memberType; }
    }

    private Trello trello;

    // Constructor que configura Retrofit con GsonConverterFactory
    public BuildRequest(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseURL())  // URL base desde la clase Base
                .addConverterFactory(GsonConverterFactory.create())  // Conversión JSON
                .build();

        trello = retrofit.create(Trello.class);
    }

    // Método que ejecuta la solicitud GET y procesa la respuesta
    public void buildRequest(){
        Call<Info> call = trello.getInfo(getApikey(), getToken());
        try{
            Response<Info> response = call.execute();
            if(response.isSuccessful() && response.body() != null) {
                Info info = response.body();
                System.out.println("ID: " + info.getId());
                System.out.println("Avatar URL: " + info.getAvatarUrl());
                System.out.println("URL: " + info.getUrl());
                System.out.println("Email: " + info.getEmail());
                System.out.println("Full Name: " + info.getFullName());
                System.out.println("Username: " + info.getUsername());
                System.out.println("Member Type: " + info.getMemberType());
            } else {
                System.out.println("Error: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método principal para ejecutar el request
    public static void main(String[] args) {
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.buildRequest();
    }
}
