package OkHttp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import okhttp3.RequestBody;

import java.io.IOException;

public class UpdateBoard extends GetBoard{
    Dotenv dotenv = Dotenv.load();
    public static final String PATH_ENV = "src/main/resources/.env";
    public static String nuevaDescripcion = "Tablero actualizado con exito";

    private final OkHttpClient client = new OkHttpClient();

    public Response actualizarTablero() throws IOException{
        HttpUrl url = HttpUrl.parse(getBaseURL() + "boards/" + boardID)
                .newBuilder()
                .addQueryParameter("key", getApikey())
                .addQueryParameter("token", getToken())
                .addQueryParameter("desc", nuevaDescripcion)
                .build();

        Request request  = new Request.Builder()
                .url(url)
                .put(RequestBody.create("", MediaType.parse("application/json")))
                .build();

        return client.newCall(request).execute();
    }

    public static void main (String [] args){
        UpdateBoard updateBoard = new UpdateBoard();
        updateBoard.setup();

        try {
            Gson  gson = new GsonBuilder().setPrettyPrinting().create();
            Response response = updateBoard.actualizarTablero();
            JsonObject jsonObject = gson.fromJson(response.body().string(), JsonObject.class);

            System.out.println("Respuesta: " + gson.toJson(jsonObject));
            System.out.println("Codigo de respuesta " + response.code());
            System.out.println("ID del tablero: " + jsonObject.get("id").getAsString());
            System.out.println("Nombre del tablero: " + jsonObject.get("name").getAsString());
            System.out.println("Descripcion actualizada: " + jsonObject.get("desc").getAsString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
