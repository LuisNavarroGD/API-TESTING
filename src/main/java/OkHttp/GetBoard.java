package OkHttp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;

import java.io.IOException;

public class GetBoard extends CreateBoard{
    Dotenv dotenv = Dotenv.load();
    public String boardID = dotenv.get("ID_TABLERO");
    public String boardName = dotenv.get("NOMBRE_TABLERO");

    private final OkHttpClient client = new OkHttpClient();

    public Response fetchBoard() throws IOException {
        HttpUrl url = HttpUrl.parse(getBaseURL() + "boards/" + boardID)
                .newBuilder()
                .addQueryParameter("key", getApikey())
                .addQueryParameter("token", getToken())
                .build();

        Request request  = new Request.Builder()
                .url(url)
                .get()
                .build();

        return client.newCall(request).execute();
    }

    public static void main(String[] args) {
        GetBoard getBoard = new GetBoard();
        getBoard.setup();

        try {
            Response response = getBoard.fetchBoard();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject jsonObject = gson.fromJson(response.body().string(), JsonObject.class);
            System.out.println(gson.toJson(jsonObject));
            System.out.println("Nombre del tablero: " + jsonObject.get("name").getAsString());
            System.out.println("ID del tablero: " + jsonObject.get("id").getAsString());
            System.out.println("Descripcion del tablero: " + jsonObject.get("desc").getAsString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
