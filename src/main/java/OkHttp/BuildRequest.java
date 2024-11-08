package OkHttp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class BuildRequest extends Base {

    private final OkHttpClient client = new OkHttpClient();

    public JsonObject buildRequest() throws IOException {

        HttpUrl url = HttpUrl.parse((getBaseURL())+ "members/me")
                .newBuilder()
                .addQueryParameter("key", getApikey())
                .addQueryParameter("token", getToken())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try(Response  response = client.newCall(request).execute()){
            if(response.isSuccessful() && response.body() != null){
                return new Gson().fromJson(response.body().string(), JsonObject.class);
            }else{
                System.out.println("Solicitud fallida con el siguiente estatus: " + response.code());
            }
        } return null;
    }

    public static void main (String [] args){
        BuildRequest buildRequest = new BuildRequest();
        buildRequest.setup();
        Gson  gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            JsonObject response = buildRequest.buildRequest();
            System.out.println("Respuesta: \n" + gson.toJson(response));
            System.out.println("URL: "+ response.get("url").getAsString());
            System.out.println("ID: "+ response.get("id").getAsString());
            System.out.println("Username: "+ response.get("username").getAsString());
            System.out.println("Nombre: "+ response.get("fullName").getAsString());
            System.out.println("Correo: "+ response.get("email").getAsString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
